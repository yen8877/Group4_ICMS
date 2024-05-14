package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.InsuranceCardDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsuranceCardDAO {
    public boolean addInsuranceCard(InsuranceCardDTO card) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "INSERT INTO insurancecard (cardnumber, cardholder, expirationdate, effectivedate, policyowner) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = JDBCUtil.connectToDatabase();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, card.getCardNumber());
            pstmt.setString(2, card.getCardHolder());
            pstmt.setObject(3, card.getExpirationDate());
            pstmt.setObject(4, card.getEffectiveDate());
            pstmt.setString(5, card.getPolicyOwner());
            pstmt.executeUpdate();

            conn.commit();
            success = true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }

        return success;
    }

    // update the cardholder
    public boolean updateCardholder(String cardNumber, String cardHolderId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "UPDATE insurancecard SET cardholder = ? WHERE cardnumber = ?";

        try {
            conn = JDBCUtil.connectToDatabase();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cardHolderId);
            pstmt.setString(2, cardNumber);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit();
                success = true;
            } else {
                conn.rollback();
                success = false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }

        return success;
    }
}
