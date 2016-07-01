package com.barsoft.employme.dao;

import com.barsoft.employme.DataConnect;
import com.barsoft.employme.beans.EmployeeBean;
import com.barsoft.employme.beans.EmployerBean;
import com.barsoft.employme.beans.RegistrationBean;
import com.barsoft.employme.beans.SessionBean;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {

	public static boolean validate(String username, String password) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			if (con == null)
				return false;
			ps = con.prepareStatement(
					"SELECT username, password, user_id FROM em_users WHERE username = ? AND password = ? AND enabled=1");
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				SessionBean.getBean().getSession().setAttribute("user_id", rs.getInt("user_id"));
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
		return false;
	}

	public static boolean register(RegistrationBean register, Object userBean) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT username, password FROM em_users where username = ?");
			ps.setString(1, register.getUsername());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("User -->" + register.getUsername() + " already exists");
				return false;
			}

			ps = con.prepareStatement("INSERT INTO em_users" + "(username, password) values" + "(?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, register.getUsername());
			ps.setString(2, register.getMd5Password());

			int executeUpdate = ps.executeUpdate();

			if (executeUpdate == 0) {
				System.out.println("Register error --> executeUpdate=0");
				return false;
			}
			ResultSet generatedKeys = ps.getGeneratedKeys();
			int lastId;
			if (generatedKeys.next()) {
				lastId = generatedKeys.getInt(1);
			} else {
				return false;
			}
			if (userBean instanceof EmployeeBean) {
				String sql = "";
				sql += "INSERT INTO em_employees ";
				sql += "(user_id, ";
				sql += "age, ";
				sql += "years_of_experience, ";
				sql += "education, ";
				sql += "area, "; 
				sql += "languages,";
				sql += "place )";
				sql += "VALUES ";
				sql += " (";
				sql += "?,?,?,?,?,?,?";
				sql += " )";

				ps = con.prepareStatement(sql);
				ps.setInt(1, lastId);

				EmployeeBean employeeBean = (EmployeeBean) userBean;
				ps.setInt(2, employeeBean.getAge());
				ps.setInt(3, employeeBean.getYearsOfExperience()); 
				ps.setString(4, employeeBean.getEducation()); 
				ps.setString(5, employeeBean.getArea()); 
				ps.setString(6, employeeBean.getLanguages());
				ps.setString(7, employeeBean.getPlace());

				executeUpdate = ps.executeUpdate();

				if (executeUpdate == 0) {
					System.out.println("Register error --> executeUpdate=0");
					return false;
				}

				sql = "";
				sql += "INSERT INTO em_user_roles ";
				sql += "(user_id, ";
				sql += "role  ";
				sql += " )";
				sql += "VALUES ";
				sql += " (";
				sql += "?,? ";
				sql += " )";

				ps = con.prepareStatement(sql);
				ps.setInt(1, lastId);
				ps.setString(2, "ROLE_EMPLOYEE");

				executeUpdate = ps.executeUpdate();

				if (executeUpdate == 0) {
					System.out.println("Register error --> executeUpdate=0");
					return false;
				}
			}
			if (userBean instanceof EmployerBean) {
				String sql = "";
				sql += "INSERT INTO em_employers ";
				sql += "(user_id  ";
				sql += "  )";
				sql += "VALUES ";
				sql += " (";
				sql += "? ";
				sql += " )";

				ps = con.prepareStatement(sql);
				ps.setInt(1, lastId);

				executeUpdate = ps.executeUpdate();

				if (executeUpdate == 0) {
					System.out.println("Register error --> executeUpdate=0");
					return false;
				}

				sql = "";
				sql += "INSERT INTO em_user_roles ";
				sql += "(user_id, ";
				sql += "role  ";
				sql += " )";
				sql += "VALUES ";
				sql += " (";
				sql += "?,? ";
				sql += " )";

				ps = con.prepareStatement(sql);
				ps.setInt(1, lastId);
				ps.setString(2, "ROLE_EMPLOYER");

				executeUpdate = ps.executeUpdate();

				if (executeUpdate == 0) {
					System.out.println("Register error --> executeUpdate=0");
					return false;
				}
			}
			return true;
		} catch (SQLException ex) {
			System.out.println("Register error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
	}

	public static EmployeeBean getEmployee(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		EmployeeBean e = null;
		try {
			e = new EmployeeBean();
			con = DataConnect.getConnection();
			String sql = "";
			sql += "SELECT ";
			sql += "age, ";
			sql += "years_of_experience, ";
			sql += "education, ";
			sql += "area, "; 
			sql += "languages, ";
			sql += "place "; 
			sql += "FROM em_employees ";
			sql += "WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				e.setAge(rs.getInt("age"));
				e.setYearsOfExperience(rs.getInt("years_of_experience")); 
				e.setEducation(rs.getString("education"));
				e.setArea(rs.getString("area"));
				e.setLanguages(rs.getString("languages"));
				e.setPlace(rs.getString("place"));
				return e;
			}
			return null;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DataConnect.close(con);
		}
		return e;
	}

	public static int getEmployeeId(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DataConnect.getConnection();
			String sql = "";
			sql += "SELECT ";
			sql += "employee_id  ";
			sql += "FROM em_employees ";
			sql += "WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("employee_id");
			}
			return -1;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DataConnect.close(con);
		}
		return -1;
	}

	public static EmployerBean getEmployer(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		EmployerBean e = null;
		try {
			e = new EmployerBean();
			con = DataConnect.getConnection();
			String sql = "";
			sql += "SELECT ";
			sql += "FROM em_employers ";
			sql += "WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return e;
			}
			return null;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DataConnect.close(con);
		}
		return e;
	}

	public static int getEmployerId(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DataConnect.getConnection();
			String sql = "";
			sql += "SELECT ";
			sql += "employer_id  ";
			sql += "FROM em_employers ";
			sql += "WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("employer_id");
			}
			return -1;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DataConnect.close(con);
		}
		return -1;
	}
	public static String getUserRole(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		String r = null;
		try {

			con = DataConnect.getConnection();
			String sql = "";
			sql += "SELECT role ";
			sql += "FROM em_user_roles ";
			sql += "WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("role");
			}
			return null;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DataConnect.close(con);
		}
		return null;
	}

	public static boolean update(int userId, Object userBean) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			if (userBean instanceof EmployeeBean) {
				EmployeeBean employeeBean = (EmployeeBean) userBean;

				String sql = "UPDATE em_employees SET ";
				sql += "age=?, ";
				sql += "years_of_experience=?, ";
				sql += "education=?, ";
				sql += "area=?, "; 
				sql += "languages=?, ";
				sql += "place=? ";
				sql += "WHERE user_id = ? ";

				ps = con.prepareStatement(sql);
				ps.setInt(1, employeeBean.getAge());
				ps.setInt(2, employeeBean.getYearsOfExperience()); 
				ps.setString(3, employeeBean.getEducation()); 
				ps.setString(4, employeeBean.getArea()); 
				ps.setString(5, employeeBean.getLanguages());
				ps.setString(6, employeeBean.getPlace());
				ps.setInt(7, userId);

				int rs = ps.executeUpdate();
				if (rs > 0) {
					return true;
				}
				return false;
			}
			if (userBean instanceof EmployerBean) {
				EmployerBean employerBean = (EmployerBean) userBean;
				return true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DataConnect.close(con);
		}
		return false;
	}

}