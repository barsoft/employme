package com.barsoft.employme.beans;

import com.barsoft.employme.dao.VacanciesDao;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

@ManagedBean
@SessionScoped
public class VacancyBean implements Serializable {
	private int vacancy_id;
	private int employer_id;
	private String name;
	private String description;
	private String education;
	private String area; 
	private String languages;
	private int age_min;
	private int age_max;
	private int experience_min;
	private int salary;
	private String place;
	private Date date;
	private ArrayList<VacancyBean> favorites;
	private String containingList;

	@PostConstruct
	public void init() {
		// VacancyBean v =
		// UsersDAO.getVacancy(SessionBean.getBean().getUserId());
		/*
		 * if (v != null) { this.setEmployer_id(v.getEmployer_id());
		 * this.setName(v.getName()); this.setDescription(v.getDescription());
		 * this.setEducation(v.getEducation());
		 * this.setPosition(v.getPosition()); this.setSkills(v.getSkills());
		 * this.setSocialskills(v.getSocialskills());
		 * this.setLanguages(v.getLanguages()); this.setAge_min(v.getAge_min());
		 * this.setAge_max(v.getAge_max());
		 * this.setExperience_min(v.getExperience_min());
		 * this.setSalary(v.getSalary()); }
		 */
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}
  
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEmployer_id() {
		return employer_id;
	}

	public void setEmployer_id(int employer_id) {
		this.employer_id = employer_id;
	}

	public int getAge_min() {
		return age_min;
	}

	public void setAge_min(int age_min) {
		this.age_min = age_min;
	}

	public int getAge_max() {
		return age_max;
	}

	public void setAge_max(int age_max) {
		this.age_max = age_max;
	}

	public int getExperience_min() {
		return experience_min;
	}

	public void setExperience_min(int experience_min) {
		this.experience_min = experience_min;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionShort() {
		int max = 400;
		int maxLength = (description.length() < max) ? description.length() : max;
		return description.substring(0, maxLength) + ((description.length() > max) ? " ..." : "");
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getVacancy_id() {
		return vacancy_id;
	}

	public void setVacancy_id(int vacancy_id) {
		this.vacancy_id = vacancy_id;
	}

	public ArrayList<VacancyBean> getVacancies() {
		return VacanciesDao.getVacancies();
	}
	
	public ArrayList<VacancyBean> getFilteredVacancies() {
		return VacanciesDao.getFilteredVacancies();
	}
	
	public ArrayList<VacancyBean> getMyVacancies() {
		return VacanciesDao.getMyVacancies();
	}

	public ArrayList<VacancyBean> getFavorites() {
		favorites = VacanciesDao.getFavorites();
		return favorites;
	}

	public String addToFavorites() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		VacanciesDao.addToFavorites(Integer.parseInt(params.get("vacancy_id")));
		return NavigationBean.getBean().getCurrentPage();
	}

	public String removeFromFavorites() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		VacanciesDao.removeFromFavorites(Integer.parseInt(params.get("vacancy_id")));
		getBean().getFavorites();
		return NavigationBean.getBean().getCurrentPage();
	}

	public boolean isInFavorites() {
		// Map<String, String> params =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		// VacanciesDao.removeFromFavorites(Integer.parseInt(params.get("vacancy_id")));

		for (VacancyBean vacancyBean : getBean().favorites) {
			if (vacancyBean.getVacancy_id() == getVacancy_id())
				return true;
		}
		return false;
	}

	public static VacancyBean getBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		VacancyBean bean = context.getApplication().evaluateExpressionGet(context, "#{vacancyBean}", VacancyBean.class);
		return bean;
	}

	public String goToVacancy() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		VacancyBean v = VacanciesDao.getVacancy(Integer.parseInt(params.get("vacancy_id")));
		String list = params.get("list");
		this.setVacancy_id(v.getVacancy_id()); 
		this.setEmployer_id(v.getEmployer_id());
		this.setName(v.getName());
		this.setDescription(v.getDescription());
		this.setEducation(v.getEducation());
		this.setArea(v.getArea()); 
		this.setLanguages(v.getLanguages());
		this.setAge_min(v.getAge_min());
		this.setAge_max(v.getAge_max());
		this.setExperience_min(v.getExperience_min());
		this.setSalary(v.getSalary());
		this.setPlace(v.getPlace());
		this.setDate(v.getDate());
		this.setContainingList(list);

		return "vacancy-detail?faces-redirect=true";
	}

	public String getContainingList() {
		return containingList;
	}

	public void setContainingList(String containingList) {
		this.containingList = containingList;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public String addVacancy(){
	
		if (VacanciesDao.addVacancy()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Vacancy was added!", ""));
			return "my-vacancies";
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "All fields are required!", ""));
			return "add-vacancy";
		}
	}
}
