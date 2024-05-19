/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.InsuranceCardDAO;
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import com.example.group4_icms.Functions.DTO.InsuranceCardDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @author <Group 4>
 */
public class InsuranceCard {
    @Test
    @DisplayName("Add insurance card test")
    void addInsuranceCardTest() {
        InsuranceCardDTO card = new InsuranceCardDTO();
        card.setCardNumber("1234567890");
        card.setCardHolder("dependent1"); /*Name dependent1 should be in customer table.*/
        card.setExpirationDate(LocalDate.of(2025, 12, 31));
        card.setEffectiveDate(LocalDate.now().atStartOfDay());

        InsuranceCardDAO dao = new InsuranceCardDAO();
        assertTrue(dao.addInsuranceCard(card));
    }

    @Test
    @DisplayName("Update cardholder test")
    void updateCardholderTest() {
        InsuranceCardDAO dao = new InsuranceCardDAO();
        // Assuming cardNumber and cardHolderId exist in the database
        String cardNumber = "1234567890";
        String cardHolderName = "dependent1";

        assertTrue(dao.updateCardholder(cardNumber, cardHolderName));
    }
    @AfterEach
    void deleteInsuranceCard() {
        // 생성된 카드 번호가 존재하면 해당 카드를 삭제합니다.
        if ("1234567890" != null) {
            Connection conn = JDBCUtil.connectToDatabase();
            String deleteSQL = "DELETE FROM insurance_card WHERE card_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, "1234567890");
                pstmt.executeUpdate();
            } catch (SQLException e) {
          } finally {
                JDBCUtil.close(conn);
            }
        }
    }
}
