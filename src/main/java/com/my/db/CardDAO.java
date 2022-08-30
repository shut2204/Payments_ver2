package com.my.db;

import com.my.db.entity.Card;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    private static final Logger LOG = Logger.getLogger(CardDAO.class);

    private final DBManager dbManager;

    public CardDAO() throws DBException {
        this.dbManager = DBManager.getInstance();
    }

    public Card cardById(String id) throws DBException {
        Card card = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("SELECT * FROM Cards WHERE idcard = ?;");

            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()){
                card = new Card();

                card.setIdcard(rs.getLong("idcard"));
                card.setIdcustomer(rs.getInt("idcustomer"));
                card.setBalance(rs.getInt("balance"));
                card.setName_card(rs.getString("name_card"));
                card.setStatus(rs.getString("status"));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_ADD_CARD, ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return card;
    }

    public CardDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Card> getAllByLogin(String login) throws DBException {
        ArrayList<Card> cards = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("SELECT * FROM Cards INNER JOIN Customers ON Cards.idcustomer = Customers.idcustomer WHERE login = ?;");

            pstmt.setString(1, login);
            rs = pstmt.executeQuery();

            while (rs.next()){
                Card card = new Card();

                card.setIdcard(rs.getLong("idcard"));
                card.setIdcustomer(rs.getInt("idcustomer"));
                card.setBalance(rs.getInt("balance"));
                card.setName_card(rs.getString("name_card"));
                card.setStatus(rs.getString("status"));

                cards.add(card);
            }
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_GET_ALL_CARDS, e);
        }finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return cards;
    }

    public boolean add(Card card) throws DBException {
        boolean flag = false;

        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("INSERT INTO DBforProject.Cards " +
                    "(idcustomer, balance, name_card)" +
                    " VALUES (?, 0, ?)");
            pstmt.setString(1, String.valueOf(card.getIdcustomer()));
            pstmt.setString(2, card.getName_card());

            pstmt.executeUpdate();
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_ADD_CARD, ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return flag;
    }

    public boolean addMoney(String idCard, String money) throws DBException {
        boolean flag = false;

        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            LOG.debug(idCard);
            pstmt = con.prepareStatement("UPDATE Cards SET balance = balance + ? WHERE idcard = ? ");
            pstmt.setString(1, money);
            pstmt.setString(2, idCard);

            int i = pstmt.executeUpdate();
            LOG.debug(i);
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_ADD_CARD, ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return flag;
    }

    public boolean transferMoney(String idCard1, String idCard2, String money, String idpayment) throws DBException {
        boolean flag = false;

        PreparedStatement pstmt;
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();

            pstmt = con.prepareStatement("UPDATE Cards SET balance = balance + ? WHERE idcard = ? ");
            pstmt.setString(1, money);
            pstmt.setString(2, idCard2);

            int i = pstmt.executeUpdate();
            pstmt.close();
            LOG.debug(i + "pstmt");

            LOG.debug(idCard1);
            pstmt1 = con.prepareStatement("UPDATE Cards SET balance = balance - ? WHERE idcard = ? ");
            pstmt1.setString(1, money);
            pstmt1.setString(2, idCard1);

            int i1 = pstmt1.executeUpdate();
            pstmt1.close();
            LOG.debug(i1 + "pstmt1");

            pstmt2 = con.prepareStatement("UPDATE Payments_customer SET status = 'sent' WHERE id_payment = ? ");
            pstmt2.setString(1, idpayment);

            int i3 = pstmt2.executeUpdate();
            pstmt1.close();
            LOG.debug(i3 + "pstmt2");

            con.commit();
            LOG.debug("use ok with transfer");
            flag = true;
        }  catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
                System.out.println("JDBC Transaction rolled back successfully");
            } catch (SQLException e1) {
                System.out.println("SQLException in rollback"+e.getMessage());
            }
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    public boolean blockCard(String idCard) throws DBException {
        boolean flag = false;

        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            LOG.debug(idCard);
            pstmt = con.prepareStatement("UPDATE Cards SET Status = 'block' WHERE idcard = ? ");
            pstmt.setString(1, idCard);

            int i = pstmt.executeUpdate();
            LOG.debug(i);
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_BLOCK_CARD, ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return flag;
    }
    }
