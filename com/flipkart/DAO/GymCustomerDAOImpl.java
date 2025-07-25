package com.flipkart.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flipkart.bean.Booking;
import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymCustomer;
import com.flipkart.bean.Slot;
import com.flipkart.utils.DBUtils;

public class GymCustomerDAOImpl implements GymCustomerDAO {

	public List<GymCentre> fetchGymList() {
		Connection connection = null;
		List<GymCentre> gyms = new ArrayList<GymCentre>();
		String query = "select gymId, gymName, ownerEmail, address, slotCount, seatsPerSlotCount, isVerified from gym";
		try {connection = DBUtils.getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(query);
			System.out.println(statement);
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
				slot.setTrainer(rs.getString("trainer"));
				slot.setNumOfSeats(rs.getInt("numOfSeats"));
				slot.setNumOfSeatsBooked(rs.getInt("numOfSeatsBooked"));
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
	
	public void bookSlots(String bookingId, String slotId, String gymId, String type, Date date, String customerEmail) {
		Connection connection = null;
		String query = "INSERT INTO Booking (bookingId,slotId,gymId,type,date,customerEmail) values(?, ?, ?, ?, ?, ?)";
		try {connection = DBUtils.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, bookingId);
			statement.setString(2, slotId);
			statement.setString(3, gymId);
			statement.setString(4, type);
			statement.setDate(5, new java.sql.Date(date.getTime()));
			statement.setString(6, customerEmail);
			statement.executeUpdate();
			System.out.println("-----------------------------------------------");
		} catch (SQLException sqlExcep) {
			printSQLException(sqlExcep);
		}
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

//	public void cancelBooking(String slotId, String email, String date) {
//		Connection connection = null;
//		String query = "Delete from Booking where email = ? and slotId = ? and date = ?";
//		try {connection = DBUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query);
//			statement.setString(1, email);
//			statement.setString(2, slotId);
//			statement.setString(3, date);
//			statement.executeUpdate();
//			System.out.println("-----------------------------------------------");
//		} catch (SQLException sqlExcep) {
//			printSQLException(sqlExcep);
//		}
//	}

	public boolean cancelBookingById(String bookingId) {
		Connection connection = null;
		boolean success = false;
		String query = "DELETE FROM booking WHERE bookingId = ?";

		try {
			connection = DBUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, bookingId);

			// executeUpdate() returns the number of rows affected.
			// If it's more than 0, the deletion was successful.
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				success = true;
			}
		} catch (SQLException e) {
			printSQLException(e);
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

}