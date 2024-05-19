/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.JDBCUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author <Group 4>
 */
public class jdbcutilTest {

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static JDBCUtil jdbcutil;

    @BeforeAll
    public static void setUp() {
        connection = jdbcutil.connectToDatabase();
    }

    @Test
    public void testConnectToDatabase() {
        assertNotNull(connection);
    }

    @Test
    public void testCloseConnection() throws SQLException {
        jdbcutil.close(connection);
        assertFalse(connection.isValid(1));
    }

    @Test
    public void testClosePreparedStatement() throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT 1");
        jdbcutil.close(preparedStatement);
        assertTrue(preparedStatement.isClosed());
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}


