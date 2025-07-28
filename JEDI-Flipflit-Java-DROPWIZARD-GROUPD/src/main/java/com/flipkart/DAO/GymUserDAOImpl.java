package com.flipkart.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flipkart.bean.*;
import com.flipkart.bean.GymUser;
import com.flipkart.constants.SQLConstants;
import com.flipkart.utils.DBUtils;
/*
 *@Author : "Sajid"
 *@ClassName: "GymUserDAOImpl"
 *@Exceptions: "java.sql.SQLException"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCustomer, com.flipkart.bean.GymOwner, com.flipkart.bean.GymUser, com.flipkart.utils.DBUtils"
 */
public class GymUserDAOImpl implements GymUserDAO {

    public boolean authenticateUser(GymUser user) {
        // to run without authentication, make isUserValid = true
        Connection connection = null;

        boolean isUserValid = false;
        try {connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_SELECT_USER_LOGIN_CREDENTIAL);

            preparedStatement.setString(1, user.getEmail());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (user.getPassword().equals(rs.getString("password"))
                        && user.getRoleId().equalsIgnoreCase(rs.getString("role"))) {
                    System.out.println(
                            rs.getString("email") + " " + rs.getString("password") + " " + rs.getString("role"));
                    isUserValid = true;
                }
            }

        } catch (SQLException e) {
            printSQLException(e);
        }

        return isUserValid;
    }

    public boolean registerCustomer(GymCustomer customer) {
        Connection connection = null;
        boolean registerSuccess = false;

        try {
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // 1. Insert into the 'user' table
            String queryUser = "INSERT INTO user (email, password, role) VALUES (?, ?, ?)";
            PreparedStatement preparedStatementUser = connection.prepareStatement(queryUser);
            preparedStatementUser.setString(1, customer.getEmail());
            preparedStatementUser.setString(2, customer.getPassword());
            preparedStatementUser.setString(3, "Customer");
            int userRows = preparedStatementUser.executeUpdate();

            // 2. Insert into the 'customer' table
            String queryCustomer = "INSERT INTO customer (email, name, phoneNumber, age, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementCustomer = connection.prepareStatement(queryCustomer);
            preparedStatementCustomer.setString(1, customer.getEmail());
            preparedStatementCustomer.setString(2, customer.getName());
            preparedStatementCustomer.setString(3, customer.getPhoneNumber());
            preparedStatementCustomer.setInt(4, customer.getAge());
            preparedStatementCustomer.setString(5, customer.getAddress());
            int customerRows = preparedStatementCustomer.executeUpdate();

            if (userRows > 0 && customerRows > 0) {
                connection.commit(); // Finalize transaction
                registerSuccess = true;
            } else {
                connection.rollback(); // Cancel transaction
            }

        } catch (SQLException e) {
            printSQLException(e);
            try {
                if (connection != null) connection.rollback(); // Rollback on error
            } catch (SQLException ex) {
                printSQLException(ex);
            }
        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true); // Reset auto-commit
            } catch (SQLException e) {
                printSQLException(e);
            }
        }

        return registerSuccess;
    }

    public boolean registerGymOwner(GymOwner gymOwner) {
        Connection connection = null;
        boolean registerSuccess = false;

        try {
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // 1. Insert into the 'user' table FIRST
            String queryUser = "INSERT INTO user (email, password, role) VALUES (?, ?, ?)";
            PreparedStatement preparedStatementUser = connection.prepareStatement(queryUser);
            preparedStatementUser.setString(1, gymOwner.getEmail());
            preparedStatementUser.setString(2, gymOwner.getPassword());
            preparedStatementUser.setString(3, "GymOwner");
            int userRows = preparedStatementUser.executeUpdate();

            // 2. Insert into the 'gymOwner' table SECOND
            String queryGymOwner = "INSERT INTO gymOwner (email, name, phoneNum, aadharNum, panNum, isVerified) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementGymOwner = connection.prepareStatement(queryGymOwner);
            preparedStatementGymOwner.setString(1, gymOwner.getEmail());
            preparedStatementGymOwner.setString(2, gymOwner.getName());
            preparedStatementGymOwner.setString(3, gymOwner.getPhoneNumber());
            preparedStatementGymOwner.setString(4, gymOwner.getAadharNumber());
            preparedStatementGymOwner.setString(5, gymOwner.getPanNumber());
            preparedStatementGymOwner.setBoolean(6, gymOwner.isVerified());
            int gymOwnerRows = preparedStatementGymOwner.executeUpdate();

            if (userRows > 0 && gymOwnerRows > 0) {
                connection.commit(); // Finalize transaction if both succeed
                registerSuccess = true;
            } else {
                connection.rollback(); // Cancel transaction if either fails
            }

        } catch (SQLException e) {
            printSQLException(e);
            try {
                if (connection != null) connection.rollback(); // Rollback on any SQL error
            } catch (SQLException ex) {
                printSQLException(ex);
            }
        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true); // Reset auto-commit state
            } catch (SQLException e) {
                printSQLException(e);
            }
        }

        return registerSuccess;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}