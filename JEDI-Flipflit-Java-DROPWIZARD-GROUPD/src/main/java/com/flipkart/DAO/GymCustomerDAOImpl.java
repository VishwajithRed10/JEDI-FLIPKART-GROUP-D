package com.flipkart.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flipkart.bean.*;
import com.flipkart.utils.DBUtils;
/*
 *@Author : "Aditi Baveja"
 *@ClassName: "GymCustomerDAOImpl"
 *@Exceptions: "java.sql.SQLException"
 *@Version : "1.0"
 *@See : "com.flipkart.bean.GymCentre, com.flipkart.bean.Slot, com.flipkart.bean.Booking, com.flipkart.bean.Payment, com.flipkart.utils.DBUtils"
 */
public class GymCustomerDAOImpl implements GymCustomerDAO {

    public List<GymCentre> fetchGymList() {
        Connection connection = null;
        List<GymCentre> gyms = new ArrayList<GymCentre>();
        String query = "select gymId, gymName, ownerEmail, address, slotCount, seatsPerSlotCount, isVerified from gym";
        try {connection = DBUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(query);
            // System.out.println(statement);
            // Step 3: Execute the query or update query
            ResultSet rs = statement.executeQuery();

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
        return gyms;
    }

    public List<Slot> fetchSlotsByGym(String gymId) {
        Connection connection = null;
        List<Slot> slots = new ArrayList<>();
        String query = "SELECT * FROM slot WHERE gymId = ?";

        try {
            connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gymId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Slot slot = new Slot();
                slot.setSlotId(rs.getString("slotId"));
                slot.setGymId(rs.getString("gymId"));
                slot.setStartTime(rs.getString("startTime"));
                slot.setEndTime(rs.getString("endTime"));
//
                slot.setNumOfSeats(rs.getInt("numOfSeats"));
                slot.setNumOfSeatsBooked(rs.getInt("numOfSeatsBooked"));
                slot.setDate(rs.getDate("date"));
                slots.add(slot);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return slots;
    }

    public List<Booking> fetchBookedSlots(String email) {
        Connection connection = null;
        List<Booking> userBookings = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE customerEmail = ?";

        try {
            connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getString("bookingId"));
                booking.setSlotId(rs.getString("slotId"));
                booking.setGymId(rs.getString("gymId"));
                booking.setType(rs.getString("type"));
                booking.setDate(rs.getDate("date"));
                booking.setCustomerEmail(rs.getString("customerEmail"));
                userBookings.add(booking);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return userBookings;
    }



    public String bookSlots(String bookingId, String slotId, String gymId, String type, Date date, String customerEmail) {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // 1. Insert the new booking
            String insertBookingSQL = "INSERT INTO booking (bookingId, slotId, gymId, type, date, customerEmail) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertBookingSQL);
            insertStmt.setString(1, bookingId);
            insertStmt.setString(2, slotId);
            insertStmt.setString(3, gymId);
            insertStmt.setString(4, type);
            insertStmt.setDate(5, new java.sql.Date(date.getTime()));
            insertStmt.setString(6, customerEmail);
            insertStmt.executeUpdate();

            // 2. Update the seat count in the slot table
            String updateSlotSQL = "UPDATE slot SET numOfSeatsBooked = numOfSeatsBooked + 1 WHERE slotId = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSlotSQL);
            updateStmt.setString(1, slotId);
            updateStmt.executeUpdate();

            connection.commit();
            return bookingId;// Finalize the transaction

        } catch (SQLException e) {
            printSQLException(e);
            try {
                if (connection != null) connection.rollback(); // Rollback on error
            } catch (SQLException ex) {
                printSQLException(ex);
            }
        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException ex) {
                printSQLException(ex);
            }
        }
        return null;
    }

    public boolean isFull(String slotId, String date) {
        Connection connection = null;
        String query = "Select * slot where slotId=? and (numOfSeatsBooked>=numOfSeats)";
        try {connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, slotId);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e)
        {
            printSQLException(e);
        }

        return false;
    }

    public boolean alreadyBooked(String slotId, String email, String date) {
        Connection connection = null;
        String query = "select isVerified from Booking where slotId=? and customerEmail =  ?";
        try {connection = DBUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, slotId);
            preparedStatement.setString(2, email);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            printSQLException(e);
        }

        return false;
    }



    public boolean cancelBookingById(String bookingId) {
        Connection connection = null;
        boolean success = false;
        String fetchSlotIdSQL = "SELECT slotId FROM booking WHERE bookingId = ?";
        String deleteBookingSQL = "DELETE FROM booking WHERE bookingId = ?";
        String updateSlotSQL = "UPDATE slot SET numOfSeatsBooked = numOfSeatsBooked - 1 WHERE slotId = ?";

        try {
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // 1. Get the slotId from the booking before deleting it
            String slotId = null;
            PreparedStatement fetchStmt = connection.prepareStatement(fetchSlotIdSQL);
            fetchStmt.setString(1, bookingId);
            ResultSet rs = fetchStmt.executeQuery();
            if (rs.next()) {
                slotId = rs.getString("slotId");
            }

            // 2. Delete the booking
            PreparedStatement deleteStmt = connection.prepareStatement(deleteBookingSQL);
            deleteStmt.setString(1, bookingId);
            int rowsAffected = deleteStmt.executeUpdate();

            // 3. If deletion was successful and we have a slotId, update the slot table
            if (rowsAffected > 0 && slotId != null) {
                PreparedStatement updateStmt = connection.prepareStatement(updateSlotSQL);
                updateStmt.setString(1, slotId);
                updateStmt.executeUpdate();
                success = true;
            }

            connection.commit(); // Finalize the transaction

        } catch (SQLException e) {
            printSQLException(e);
            try {
                if (connection != null) connection.rollback(); // Rollback on error
            } catch (SQLException ex) {
                printSQLException(ex);
            }
        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException ex) {
                printSQLException(ex);
            }
        }
        return success;
    }

    public boolean checkSlotExists(String slotId, String gymId) {
        Connection connection = null;
        String query = "select isVerified from slot where slotId=? and gymId =  ?";
        try {connection = DBUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, slotId);
            preparedStatement.setString(2, gymId);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            printSQLException(e);
        }

        return false;
    }

    public boolean checkGymApprove(String gymId) {
        Connection connection = null;
        String query = "select isVerified from gym where gymId =  ?";
        try {connection = DBUtils.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gymId);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            printSQLException(e);
        }

        return false;
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

    public void editProfile(GymCustomer customer) {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // 1. Update the 'user' table
            String updateUserSQL = "UPDATE user SET password = ? WHERE email = ?";
            PreparedStatement updateUserStmt = connection.prepareStatement(updateUserSQL);
            updateUserStmt.setString(1, customer.getPassword());
            updateUserStmt.setString(2, customer.getEmail());
            updateUserStmt.executeUpdate();

            // 2. Update the 'customer' table
            String updateCustomerSQL = "UPDATE customer SET name = ?, phoneNumber = ?, age = ?, address = ? WHERE email = ?";
            PreparedStatement updateCustomerStmt = connection.prepareStatement(updateCustomerSQL);
            updateCustomerStmt.setString(1, customer.getName());
            updateCustomerStmt.setString(2, customer.getPhoneNumber());
            updateCustomerStmt.setInt(3, customer.getAge());
            updateCustomerStmt.setString(4, customer.getAddress());
            updateCustomerStmt.setString(5, customer.getEmail());
            updateCustomerStmt.executeUpdate();

            connection.commit(); // Finalize transaction

        } catch (SQLException e) {
            printSQLException(e);
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                printSQLException(ex);
            }
        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException e) {
                printSQLException(e);
            }
        }
    }

    public List<GymCentre> fetchGymsByDate(Date date) {
        Connection connection = null;
        List<GymCentre> gyms = new ArrayList<>();
        // This query finds all unique gym IDs that have slots on a given date,
        // and then joins with the gym table to get their details.
        String query = "SELECT g.* FROM gym g JOIN slot s ON g.gymId = s.gymId WHERE s.date = ? AND g.isVerified = 1 GROUP BY g.gymId";

        try {
            connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                GymCentre gym = new GymCentre();
                gym.setGymId(rs.getString("gymId"));
                gym.setGymName(rs.getString("gymName"));
                gym.setAddress(rs.getString("address"));
                gym.setOwnerEmail(rs.getString("ownerEmail"));
                gym.setVerified(rs.getBoolean("isVerified"));
                gyms.add(gym);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return gyms;
    }

    public void processPayment(Payment payment) {
        Connection connection = null;
        String query = "INSERT INTO payment (transactionId, bookingId, paymentStatus, paymentMethod, paymentMethodId) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = DBUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, payment.getTransactionId());
            statement.setString(2, payment.getBookingId());
            statement.setString(3, payment.getPaymentStatus());
            statement.setString(4, payment.getPaymentMethod());
            statement.setString(5, payment.getPaymentMethodId());
            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

}
