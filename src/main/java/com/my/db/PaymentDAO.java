package com.my.db;

import com.my.db.entity.Card;
import com.my.db.entity.Payment;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private static final Logger LOG = Logger.getLogger(CardDAO.class);

    private final DBManager dbManager;

    public PaymentDAO() throws DBException {
        this.dbManager = DBManager.getInstance();
    }

    public boolean prepare(Payment payment) throws DBException {
        boolean flag = false;

        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("INSERT INTO Payments_customer " +
                    "(id_card, to_card, amount, idcustomer, idcustomer2)" +
                    " VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, payment.getId_card());
            pstmt.setString(2, payment.getTo_card());
            pstmt.setString(3, String.valueOf(payment.getAmount()));
            pstmt.setString(4, String.valueOf(payment.getIdcustomer()));
            pstmt.setString(5, String.valueOf(payment.getIdcustomer2()));

            pstmt.executeUpdate();
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_ADD_PAYMENT, ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return flag;
    }

    public List<Payment> getAllById(String id, int page) throws DBException {
        ArrayList<Payment> payments = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("SELECT * FROM Payments_customer where idcustomer = ? or idcustomer2 = ? limit ?, 5;");

            pstmt.setString(1, id);
            pstmt.setString(2, id);
            pstmt.setInt(3, page);
            rs = pstmt.executeQuery();

            while (rs.next()){
                Payment payment = new Payment();

                payment.setId_payment(rs.getInt("id_payment"));
                payment.setId_card(rs.getString("id_card"));
                payment.setAmount(rs.getInt("amount"));
                payment.setDate_of_payment(rs.getString("date_of_payment"));
                payment.setTo_card(rs.getString("to_card"));
                payment.setIdcustomer(rs.getInt("idcustomer"));
                payment.setIdcustomer2(rs.getInt("idcustomer2"));
                payment.setStatus(rs.getString("status"));

                payments.add(payment);
            }
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_GET_ALL_CARDS, e);
        }finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return payments;
    }

    public int countOfPaymentsById(String id) throws DBException{
        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM Payments_customer where idcustomer = ? or idcustomer2 = ?");

            pstmt.setString(1, id);
            pstmt.setString(2, id);
            rs = pstmt.executeQuery();

            while (rs.next()){
                count = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_GET_ALL_CARDS, e);
        }finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return count;
    }
}
