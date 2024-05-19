package com.example.group4_icms.junit5.Controller;

import com.example.group4_icms.Functions.DAO.*;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.DTO.DependentDTO;
import com.example.group4_icms.Functions.DTO.PolicyHolderDTO;
import com.example.group4_icms.Functions.DTO.PolicyOwnerDTO;
import com.example.group4_icms.Functions.VC.Controller.PolicyOwnerCustomerController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PolicyOwnerCustomerControllerTest {

    @BeforeAll
    static void AddPolicyHolder() {
        String sql = "INSERT INTO dependents (c_id, policyholderid) VALUES (?, ?)";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "policyholder123");
            pstmt.setString(2, ""); // 실제 policyholderid 값을 설정해야 함
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Dependent added successfully.");
            } else {
                System.out.println("Failed to add dependent.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding dependent: " + e.getMessage());
            e.printStackTrace();
        }    }
    @BeforeAll
    public static void addPolicyOwner() {
        String sql = "INSERT INTO policyowner (c_id) VALUES (?)";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "policyowner123");
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("PolicyOwner added successfully.");
            } else {
                System.out.println("Failed to add PolicyOwner.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding dependent: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @BeforeAll
    public static void addDependent() {
        String sql = "INSERT INTO dependents (c_id, policyholderid) VALUES (?, ?)";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "dependent123");
            pstmt.setString(2, "policyholder123"); // 실제 policyholderid 값을 설정해야 함
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Dependent added successfully.");
            } else {
                System.out.println("Failed to add dependent.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding dependent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Delete Policy Owner Test")
    void testDeletePolicyowner() {
        PolicyOwnerCustomerController controller = new PolicyOwnerCustomerController();
        String id = "policyowner123";

        assertDoesNotThrow(() -> controller.testDeletePolicyowner(id));
    }

    @Test
    @DisplayName("Delete Policy Holder Test")
    void testDeletePolicyholder() {
        PolicyOwnerCustomerController controller = new PolicyOwnerCustomerController();
        String id = "policyholder123";

        assertDoesNotThrow(() -> controller.testDeletePolicyholder(id));
    }

    @Test
    @DisplayName("Delete Dependent Test")
    void testDeleteDependent() {
        PolicyOwnerCustomerController controller = new PolicyOwnerCustomerController();
        String id = "dependent123";

        assertDoesNotThrow(() -> controller.testDeleteDependent(id));
    }
}
