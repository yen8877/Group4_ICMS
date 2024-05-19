package com.example.group4_icms.junit5.Controller;

import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DAO.PolicyHolderDAO;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.PolicyHolderDTO;
import com.example.group4_icms.Functions.VC.Controller.PolicyOwnerClaimController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolicyOwnerClaimControllerTest {
    @BeforeAll
    static void addClaim(){
        ClaimDAO claimDAO = new ClaimDAO();
        ClaimDTO claimDTO = new ClaimDTO();
        claimDTO.setId("claim_id");
        claimDAO.addClaim(claimDTO);
    }
    @Test
    @DisplayName("Delete Claim From Database Test")
    void testDeleteClaimFromDatabase() {
        PolicyOwnerClaimController controller = new PolicyOwnerClaimController();
        String fId = "claim_id";

        assertDoesNotThrow(() -> controller.testDeleteClaimFromDatabase(fId));
    }
}
