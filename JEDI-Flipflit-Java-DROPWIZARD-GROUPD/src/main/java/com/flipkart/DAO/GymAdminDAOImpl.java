/**
 *
 */
package com.flipkart.DAO;
import com.flipkart.bean.*;
import com.flipkart.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 *@Author : "Ronil Patil"
 *@ClassName: "GymAdminDAOImpl"
 *@Exceptions: "java.sql.SQLException"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCentre, com.flipkart.bean.GymOwner, com.flipkart.utils.DBUtils"
 */
public class GymAdminDAOImpl implements GymAdminDAO {
    Connection connection = null;
    public List<GymOwner> getAllGymOwners() {
        List<GymOwner> gymOwners = new ArrayList<GymOwner>();
        String query = "select email, name, phoneNum, aadharNum, panNum, isVerified from gymOwner";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                GymOwner gymOwner = new GymOwner();
                gymOwner.setEmail(rs.getString("email"));
                gymOwner.setName(rs.getString("name"));
                gymOwner.setPhoneNumber(rs.getString("phoneNum"));
                gymOwner.setAadharNumber(rs.getString("aadharNum"));
                gymOwner.setPanNumber(rs.getString("panNum"));
                gymOwner.setVerified(rs.getBoolean("isVerified"));
                gymOwners.add(gymOwner);
//	                System.out.println(id + "," + name + "," + email + "," + country + "," + password);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        // Step 4: try-with-resource statement will auto close the connection.
        return gymOwners;
    };

    public List<GymCentre> getAllGyms() {
        Connection connection = null;
        List<GymCentre> gyms = new ArrayList<GymCentre>();
        String query = "select gymId, gymName, ownerEmail, address, slotCount, seatsPerSlotCount, isVerified from gym";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                GymCentre gym = new GymCentre();
                gym.setGymId(rs.getString("gymId"));
                gym.setGymName(rs.getString("gymName"));
                gym.setOwnerEmail(rs.getString("ownerEmail"));
                gym.setAddress(rs.getString("address"));
                gym.setSlotCount(rs.getInt("slotCount"));
                gym.setSeatsPerSlotCount(rs.getInt("seatsPerSlotCount"));
                gym.setVerified(rs.getBoolean("isVerified"));
                gyms.add(gym);
//	                System.out.println(id + "," + name + "," + email + "," + country + "," + password);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        // Step 4: try-with-resource statement will auto close the connection.
        return gyms;
    };

    public List<GymOwner> getPendingGymOwnerRequests() {
        Connection connection = null;
        List<GymOwner> gymOwners = new ArrayList<GymOwner>();
        String query = "select email, name, phoneNum, aadharNum, panNum, isVerified from gymOwner where isVerified = ?;";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.setBoolean(1, false);
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                GymOwner gymOwner = new GymOwner();
                gymOwner.setEmail(rs.getString("email"));
                gymOwner.setName(rs.getString("name"));
                gymOwner.setPhoneNumber(rs.getString("phoneNum"));
                gymOwner.setAadharNumber(rs.getString("aadharNum"));
                gymOwner.setPanNumber(rs.getString("panNum"));
                gymOwner.setVerified(rs.getBoolean("isVerified"));
                gymOwners.add(gymOwner);
//	                System.out.println(id + "," + name + "," + email + "," + country + "," + password);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        // Step 4: try-with-resource statement will auto close the connection.
        return gymOwners;

    };

    public List<GymCentre> getPendingGymRequests() {
        Connection connection = null;
        List<GymCentre> gyms = new ArrayList<GymCentre>();
        String query = "select gymId, gymName, ownerEmail, address, slotCount, seatsPerSlotCount, isVerified from gym where isVerified = ?;";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            //System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.setBoolean(1, false);
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                GymCentre gym = new GymCentre();
                gym.setGymId(rs.getString("gymId"));
                gym.setGymName(rs.getString("gymName"));
                gym.setOwnerEmail(rs.getString("ownerEmail"));
                gym.setAddress(rs.getString("address"));
                gym.setSlotCount(rs.getInt("slotCount"));
                gym.setSeatsPerSlotCount(rs.getInt("seatsPerSlotCount"));
                gym.setVerified(rs.getBoolean("isVerified"));
                gyms.add(gym);
//	                System.out.println(id + "," + name + "," + email + "," + country + "," + password);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        // Step 4: try-with-resource statement will auto close the connection.
        return gyms;

    };

    public void approveSingleOwnerRequest(String gymOwnerEmail) {
        Connection connection = null;
        String SQL_APPROVE_GYM_OWNER_BY_ID="update gymOwner set isVerified=1 WHERE email=?;";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_APPROVE_GYM_OWNER_BY_ID);
            //System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.setString(1, gymOwnerEmail);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    };

    public void approveAllOwnerRequest() {
        Connection connection = null;
        String SQL_APPROVE_ALL_GYMS="update gymOwner set isVerified=1;";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_APPROVE_ALL_GYMS);
            //System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    };

    public void approveSingleGymRequest(String gymId) {
        Connection connection = null;
        String SQL_APPROVE_GYM_BY_ID="update gym set isVerified=1 where gymId = ?;";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_APPROVE_GYM_BY_ID);
            //System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.setString(1, gymId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    };

    public void approveAllGymRequest() {
        Connection connection = null;
        String SQL_APPROVE_ALL_GYMS="update gym set isVerified=1;";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_APPROVE_ALL_GYMS);
            //System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    };

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
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

    public boolean deleteGymOwner(String email) {
        Connection connection = null;
        boolean success = false;
        String query = "DELETE FROM user WHERE email = ? AND role = 'GymOwner'";

        try {
            connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return success;
    }
}
