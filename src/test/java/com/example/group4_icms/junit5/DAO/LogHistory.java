/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.LogHistoryDAO;
import com.example.group4_icms.Functions.DTO.LogHistoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @author <Group 4>
 */
public class LogHistory {
    @Test
    public void testAddLogHistory() {
        // Create a sample LogHistoryDTO object
        LogHistoryDTO logHistoryDTO = new LogHistoryDTO("test1", "Admin", "Delete");

        // Create LogHistoryDAO instance
        LogHistoryDAO logHistoryDAO = new LogHistoryDAO();

        // Add log history
        boolean result = logHistoryDAO.addLogHistory(logHistoryDTO);

        // Check the result
        assertTrue(result, "Failed to add log history.");
    }

    @Test
    public void testDeleteLogHistory() {
        // Create a sample LogHistoryDTO object
        LogHistoryDTO logHistoryDTO = new LogHistoryDTO("test1", "Admin", "Delete");

        // Create LogHistoryDAO instance
        LogHistoryDAO logHistoryDAO = new LogHistoryDAO();

        // Delete log history
        boolean result = logHistoryDAO.deleteLogHistory(logHistoryDTO);

        // Check the result
        assertTrue(result, "Failed to delete log history.");
    }
}
