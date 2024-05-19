package com.example.group4_icms.junit5.Controller;


import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.VC.Controller.HolderClaimController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author <Group 4>
 */
public class HolderClaimControllerTest {
    @BeforeEach
    void addClaim(){
        ClaimDAO claimDAO = new ClaimDAO();
        ClaimDTO claimDTO = new ClaimDTO();
        claimDTO.setId("claim_id");
        claimDAO.addClaim(claimDTO);
    }
    @Test
    void testDeleteClaimFromDatabase() {
        HolderClaimController controller = new HolderClaimController();
        String fId = "claim_id";

        assertDoesNotThrow(() -> controller.testdeleteClaimFromDatabase(fId));
    }

}
