package com.example.group4_icms.junit5.Controller;

import com.example.group4_icms.Claims;
import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.VC.Controller.SurveyorClaimsController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SurveyorClaimsControllerTest {

    @BeforeAll
    static void addClaim(){
        ClaimDAO claimDAO = new ClaimDAO();
        ClaimDTO claimDTO = new ClaimDTO();
        claimDTO.setId("claim_id");
        claimDTO.setStatus("NEW");

        claimDAO.addClaim(claimDTO);
    }
    @Test
    @DisplayName("Delete Claim From Database Test")
    void testDeleteClaimFromDatabase() {
        SurveyorClaimsController controller = new SurveyorClaimsController();
        String id = "claim_id";

        assertDoesNotThrow(() -> controller.testDeleteClaimFromDatabase(id));
    }


    @Test
    @DisplayName("Update Claim Status In Database Test")
    void testUpdateClaimStatusInDatabase() {
        SurveyorClaimsController controller = new SurveyorClaimsController();
        String id = "claim_id";
        String status = "status";

        assertDoesNotThrow(() -> controller.testUpdateClaimStatusInDatabase(id, status));
    }
}
