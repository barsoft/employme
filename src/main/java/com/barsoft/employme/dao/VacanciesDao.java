package com.barsoft.employme.dao;

import com.barsoft.employme.DataConnect;
import com.barsoft.employme.beans.SearchBean;
import com.barsoft.employme.beans.SessionBean;
import com.barsoft.employme.beans.VacancyBean;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VacanciesDao {

	public static ArrayList<VacancyBean> getVacancies() {
		ArrayList<VacancyBean> vacancyBeans = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			if (con == null)
				return null;
			ps = con.prepareStatement("SELECT * FROM em_vacancies v ");
			/*
			 * + "LEFT JOIN em_employers e ON e.employer_id=v.employer_id " +
			 * " WHERE  e.user_id = ?");
			 */
			// ps.setInt(1, SessionBean.getBean().getUserId());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// SessionBean.getBean().getSession().setAttribute("user_id",
				// rs.getInt("user_id"));
				VacancyBean v = new VacancyBean();

				if (v != null) {
					v.setVacancy_id(rs.getInt("vacancy_id"));
					v.setEmployer_id(rs.getInt("employer_id"));
					v.setName(rs.getString("name"));
					v.setDescription(rs.getString("description"));
					v.setEducation(rs.getString("education"));
					v.setArea(rs.getString("area"));
					v.setLanguages(rs.getString("languages"));
					v.setAge_min(rs.getInt("age_min"));
					v.setAge_max(rs.getInt("age_max"));
					v.setExperience_min(rs.getInt("experience_min"));
					v.setSalary(rs.getInt("salary"));
					v.setPlace(rs.getString("place"));
					v.setDate(rs.getDate("date"));
				}

				vacancyBeans.add(v);
			}
			return vacancyBeans;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Vacancies list retrieve error -->" + ex.getMessage());
			return null;
		} finally {
			DataConnect.close(con);
		}
	}

	public static ArrayList<VacancyBean> getFavorites() {
		ArrayList<VacancyBean> vacancyBeans = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			if (con == null)
				return null;
			String sql = "SELECT * FROM em_vacancies v ";
			sql += " LEFT JOIN em_employees_vacancies ev ";
			sql += " ON  v.vacancy_id=ev.vacancy_id ";
			sql += " LEFT JOIN em_employees e ";
			sql += " ON e.employee_id=ev.employee_id ";
			sql += " WHERE  e.user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, SessionBean.getBean().getUserId());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VacancyBean v = new VacancyBean();

				if (v != null) {
					v.setVacancy_id(rs.getInt("vacancy_id"));
					v.setEmployer_id(rs.getInt("employer_id"));
					v.setName(rs.getString("name"));
					v.setDescription(rs.getString("description"));
					v.setEducation(rs.getString("education"));
					v.setArea(rs.getString("area"));
					v.setLanguages(rs.getString("languages"));
					v.setAge_min(rs.getInt("age_min"));
					v.setAge_max(rs.getInt("age_max"));
					v.setExperience_min(rs.getInt("experience_min"));
					v.setSalary(rs.getInt("salary"));
					v.setPlace(rs.getString("place"));
					v.setDate(rs.getDate("date"));
				}

				vacancyBeans.add(v);
			}
			return vacancyBeans;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Favorites list retrieve error -->" + ex.getMessage());
			return null;
		} finally {
			DataConnect.close(con);
		}
	}

	public static boolean addToFavorites(int vacancyId) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();

			String sql = "";
			sql += "INSERT INTO em_employees_vacancies ";
			sql += "( employee_id, vacancy_id  ";
			sql += " )";
			sql += "VALUES ";
			sql += " (";
			sql += "?, ? ";
			sql += " )";

			ps = con.prepareStatement(sql);

			ps.setInt(1, UsersDAO.getEmployeeId(SessionBean.getBean().getUserId()));
			ps.setInt(2, vacancyId);
			int executeUpdate = ps.executeUpdate();

			if (executeUpdate == 0) {
				System.out.println("Add to favorites error --> executeUpdate=0");
				return false;
			}

			return true;
		} catch (SQLException ex) {
			System.out.println("Add to favorites error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
	}

	public static boolean removeFromFavorites(int vacancyId) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();

			String sql = "";
			sql += "DELETE FROM em_employees_vacancies ";
			sql += "WHERE ";
			sql += " employee_id=? AND vacancy_id= ? ";

			ps = con.prepareStatement(sql);

			ps.setInt(1, UsersDAO.getEmployeeId(SessionBean.getBean().getUserId()));
			ps.setInt(2, vacancyId);
			int executeUpdate = ps.executeUpdate();

			if (executeUpdate == 0) {
				System.out.println("Remove from favorites error --> executeUpdate=0");
				return false;
			}

			return true;
		} catch (SQLException ex) {
			System.out.println("Remove from favorites error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
	}

	public static VacancyBean getVacancy(int vacancy_id) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			if (con == null)
				return null;
			ps = con.prepareStatement("SELECT * FROM em_vacancies v WHERE vacancy_id =?");

			ps.setInt(1, vacancy_id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				VacancyBean v = new VacancyBean();

				if (v != null) {
					v.setVacancy_id(rs.getInt("vacancy_id"));
					v.setEmployer_id(rs.getInt("employer_id"));
					v.setName(rs.getString("name"));
					v.setDescription(rs.getString("description"));
					v.setEducation(rs.getString("education"));
					v.setArea(rs.getString("area"));
					v.setLanguages(rs.getString("languages"));
					v.setAge_min(rs.getInt("age_min"));
					v.setAge_max(rs.getInt("age_max"));
					v.setExperience_min(rs.getInt("experience_min"));
					v.setSalary(rs.getInt("salary"));
					v.setPlace(rs.getString("place"));
					v.setDate(rs.getDate("date"));
				}
				return v;
			}
			return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Vacancies list retrieve error -->" + ex.getMessage());
			return null;
		} finally {
			DataConnect.close(con);
		}
	}

	public static ArrayList<VacancyBean> getFilteredVacancies() {
		ArrayList<VacancyBean> vacancyBeans = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;

		if (SearchBean.getBean().getDate_to()!=null && SearchBean.getBean().getDate_from()!=null){
			if (SearchBean.getBean().getDate_to().before(SearchBean.getBean().getDate_from() ))
			{
				SearchBean.getBean().setDate_to(SearchBean.getBean().getDate_from());
			}
		}
		
		try {
			con = DataConnect.getConnection();
			if (con == null)
				return null;
			String sql = "SELECT * FROM em_vacancies v ";
			sql += "WHERE ";
			if (SearchBean.getBean().getText() != null && !SearchBean.getBean().getText().trim().isEmpty()) {
				sql += "( name LIKE '%" + SearchBean.getBean().getText().trim() + "%'";
				sql += " OR description LIKE '%" + SearchBean.getBean().getText().trim() + "%')";
				sql += " AND ";
			} else {
			}
			if (SearchBean.getBean().isExtended_search_available()) {
				if (SearchBean.getBean().getAge() > 0) {
					sql += " (age_min<=0 ||  age_min <='" + SearchBean.getBean().getAge() + "')";
					sql += " AND (age_max<=0 ||  age_max >='" + SearchBean.getBean().getAge() + "')";
					sql += " AND ";
				}
				if (SearchBean.getBean().getDate_from() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
					sql += " (date >='" + dateFormat.format(SearchBean.getBean().getDate_from()) + "')";
					sql += " AND ";
				}
				if (SearchBean.getBean().getDate_to() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
					sql += " (date <='" + dateFormat.format(SearchBean.getBean().getDate_to()) + "')";
					sql += " AND ";
				}
				if (SearchBean.getBean().getPlace() != null) {
					sql += " (place LIKE '%" + SearchBean.getBean().getPlace() + "%')";
					sql += " AND ";
				}
			}

			if (sql.trim().endsWith("AND")) {
				sql = new StringBuilder(sql).replace(sql.lastIndexOf("AND"), sql.lastIndexOf("AND") + 3, "").toString();
			}

			if (sql.trim().endsWith("WHERE")) {
				sql = new StringBuilder(sql).replace(sql.lastIndexOf("WHERE"), sql.lastIndexOf("WHERE") + 5, "")
						.toString();
			}
			System.out.println(sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VacancyBean v = new VacancyBean();

				if (v != null) {
					v.setVacancy_id(rs.getInt("vacancy_id"));
					v.setEmployer_id(rs.getInt("employer_id"));
					v.setName(rs.getString("name"));
					v.setDescription(rs.getString("description"));
					v.setEducation(rs.getString("education"));
					v.setArea(rs.getString("area"));
					v.setLanguages(rs.getString("languages"));
					v.setAge_min(rs.getInt("age_min"));
					v.setAge_max(rs.getInt("age_max"));
					v.setExperience_min(rs.getInt("experience_min"));
					v.setSalary(rs.getInt("salary"));
					v.setPlace(rs.getString("place"));
					v.setDate(rs.getDate("date"));
				}

				vacancyBeans.add(v);
			}
			return vacancyBeans;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Vacancies list retrieve error -->" + ex.getMessage());
			return null;
		} finally {
			DataConnect.close(con);
		}
	}

	public static ArrayList<VacancyBean> getMyVacancies() {
		ArrayList<VacancyBean> vacancyBeans = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			if (con == null)
				return null;
			String sql = "SELECT * FROM em_vacancies v ";
			sql += " LEFT JOIN em_employers e ";
			sql += " ON e.employer_id=v.employer_id ";
			sql += " WHERE  e.user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, SessionBean.getBean().getUserId());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VacancyBean v = new VacancyBean();

				if (v != null) {
					v.setVacancy_id(rs.getInt("vacancy_id"));
					v.setEmployer_id(rs.getInt("employer_id"));
					v.setName(rs.getString("name"));
					v.setDescription(rs.getString("description"));
					v.setEducation(rs.getString("education"));
					v.setArea(rs.getString("area"));
					v.setLanguages(rs.getString("languages"));
					v.setAge_min(rs.getInt("age_min"));
					v.setAge_max(rs.getInt("age_max"));
					v.setExperience_min(rs.getInt("experience_min"));
					v.setSalary(rs.getInt("salary"));
					v.setPlace(rs.getString("place"));
					v.setDate(rs.getDate("date"));
				}

				vacancyBeans.add(v);
			}
			return vacancyBeans;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Favorites list retrieve error -->" + ex.getMessage());
			return null;
		} finally {
			DataConnect.close(con);
		}
	}

	public static boolean addVacancy() {
		Connection con = null;
		PreparedStatement ps = null;
		
		VacancyBean v = VacancyBean.getBean();
		try {
			con = DataConnect.getConnection();

			String sql = "";
			sql += "INSERT INTO em_vacancies ";
			sql += "( employer_id,"
					+ " name,"
					+ " description,"
					+ " education,"
					+ " languages,"
					+ " age_min,"
					+ " age_max,"
					+ " area,"
					+ " experience_min,"
					+ " salary,"
					+ " place,"
					+ " date";
			sql += " )";
			sql += "VALUES ";
			sql += " (";
			sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ";
			sql += " )";

			ps = con.prepareStatement(sql);

			ps.setInt(1, UsersDAO.getEmployerId(SessionBean.getBean().getUserId()));
			ps.setString(2, v.getName());
			ps.setString(3, v.getDescription());
			ps.setString(4, v.getEducation());
			ps.setString(5, v.getLanguages());
			ps.setInt(6, v.getAge_min());
			ps.setInt(7, v.getAge_max());
			ps.setString(8, v.getArea());
			ps.setInt(9, v.getExperience_min());
			ps.setInt(10, v.getSalary());
			ps.setString(11, v.getPlace());
			ps.setDate(12, new Date(Calendar.getInstance().getTime().getTime()));
			int executeUpdate = ps.executeUpdate();

			if (executeUpdate == 0) {
				System.out.println("Add vacancy error --> executeUpdate=0");
				return false;
			}

			return true;
		} catch (SQLException ex) {
			System.out.println("Add vacancy error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
	}

}
