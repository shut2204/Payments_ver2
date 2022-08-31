package com.my.db;

import com.my.db.entity.Request;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    private static final Logger LOG = Logger.getLogger(RequestDAO.class);

    private final DBManager dbManager;

    public RequestDAO() throws DBException {
        this.dbManager = DBManager.getInstance();
    }

    public List<Request> getAll(int currentPage) throws DBException {
        List<Request> requests = new ArrayList<>();
        Request request = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("select * from requests limit ?, 5");
            pstmt.setInt(1, currentPage);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setIdCustomer(rs.getInt("idcustomer"));
                request.setIdCard(rs.getString("idcard"));

                requests.add(request);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }
        return requests;
    }

    public int countAll() throws DBException {
        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("select count(*) from requests");

            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }
        return count;
    }
}
