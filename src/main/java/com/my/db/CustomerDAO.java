package com.my.db;

import com.my.db.entity.Customer;
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

public class CustomerDAO {

    private static final Logger LOG = Logger.getLogger(CustomerDAO.class);

    private final DBManager dbManager;

    public CustomerDAO() throws DBException {
        this.dbManager = DBManager.getInstance();
    }

    public CustomerDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public Customer findUserByLogin(String login) throws DBException {
        Customer customer = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("select * from Customers where login = ?");
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                customer = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN,ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }
        return customer;
    }

    public boolean addCustomer(Customer customer) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean flag = false;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("INSERT INTO Customers(phone_number, " +
                    "first_name, last_name, login," +
                    " password_customer, role) VALUES (?,?,?,?,?,?)");
            pstmt.setString(1, customer.getPhone_number());
            pstmt.setString(2, customer.getFirst_name());
            pstmt.setString(3, customer.getLast_name());
            pstmt.setString(4, customer.getLogin());
            pstmt.setString(5, customer.getPassword_customer());
            pstmt.setString(6, customer.getRole());

            pstmt.executeUpdate();
            con.commit();

            flag = true;
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_CREATE_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_CREATE_USER, ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }

        return flag;
    }

    public int findUserIDByLogin(String login) throws DBException {
        int id = -1;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.prepareStatement("select idcustomer from Customers where login = ?");
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("idcustomer");
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN,ex);
        } finally {
            DBManager.getInstance().close(con, stmt, rs);
        }
        return id;
    }

    public boolean sendRequest(String idcustomer, String idcard) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean flag = false;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("INSERT INTO requests(idcustomer, idcard) VALUES (?, ?)");

            pstmt.setString(1, idcustomer);
            pstmt.setString(2, idcard);

            pstmt.executeUpdate();
            con.commit();

            flag = true;
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_SENT_REQUEST, ex);
            throw new DBException(Messages.ERR_CANNOT_SENT_REQUEST,ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }

        return flag;
    }

    public boolean isExistRequest(String idcard) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean flag;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("SELECT * from requests where idcard = ?");

            pstmt.setString(1, idcard);

            rs = pstmt.executeQuery();

            con.commit();

            flag = rs.next();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_FIND_REQUEST, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_REQUEST,ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }

        return flag;
    }

    public List<Customer> getAll(int currentPage) throws DBException {
        List<Customer> customers = new ArrayList<>();
        Customer customer;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("select * from Customers where role = ? limit ?, 5");
            pstmt.setString(1, "user");
            pstmt.setInt(2, currentPage);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                customer = extractUser(rs);

                customers.add(customer);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_FIND_USERS, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_USERS,ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }
        return customers;
    }

    public int countAll() throws DBException {
        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("select count(*) from Customers where role = ?");
            pstmt.setString(1, "user");

            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_FIND_USERS, ex);
            throw new DBException(Messages.ERR_CANNOT_FIND_USERS,ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }
        return count;
    }

    public boolean blockUser(String login) throws DBException {
        boolean flag = false;

        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            LOG.debug(login);
            pstmt = con.prepareStatement("UPDATE Customers SET Status = 'block' WHERE login = ? ");
            pstmt.setString(1, login);

            int i = pstmt.executeUpdate();
            LOG.debug(i);
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_BLOCK_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_BLOCK_USER,ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return flag;
    }

    public List<Customer> search(String search) throws DBException {
        ArrayList<Customer> customers = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("SELECT * FROM Customers where login = ?;");

            pstmt.setString(1, search);

            rs = pstmt.executeQuery();

            while (rs.next()){
                Customer customer = extractUser(rs);

                customers.add(customer);
            }
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_SEARCH_USER, e);
            throw new DBException(Messages.ERR_CANNOT_SEARCH_USER,e);
        }finally {
            DBManager.getInstance().close(con, pstmt, null);
        }

        return customers;
    }

    private Customer extractUser(ResultSet rs) throws SQLException {
        Customer user = new Customer();
        user.setIdcustomer(rs.getInt("idcustomer"));
        user.setLogin(rs.getString("login"));
        user.setPassword_customer(rs.getString("password_customer"));
        user.setFirst_name(rs.getString("first_name"));
        user.setLast_name(rs.getString("last_name"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}
