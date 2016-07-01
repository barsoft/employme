package com.barsoft.employme.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@SessionScoped
public class SearchBean implements Serializable {
	private String text;
	private String education;
	private String years_of_experience;
	private String area;
	private String languages;
	private int age;
	private int salary_min;
	private int salary_max;
	private String place;
	private Date date_from;
	private Date date_to;
	private boolean extended_search_available;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getYears_of_experience() {
		return years_of_experience;
	}

	public void setYears_of_experience(String years_of_experience) {
		this.years_of_experience = years_of_experience;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSalary_min() {
		return salary_min;
	}

	public void setSalary_min(int salary_min) {
		this.salary_min = salary_min;
	}

	public int getSalary_max() {
		return salary_max;
	}

	public void setSalary_max(int salary_max) {
		this.salary_max = salary_max;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public boolean isExtended_search_available() {
		return extended_search_available;
	}

	public void setExtended_search_available(boolean extended_search_available) {
		this.extended_search_available = extended_search_available;
	}

	public void disableExtendedSearch() {
		this.extended_search_available = false;
	}

	public void enableExtendedSearch() {
		this.extended_search_available = true;
	}

	public void clearText() {
		this.text = "";
	}

	public static SearchBean getBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		SearchBean bean = context.getApplication().evaluateExpressionGet(context, "#{searchBean}", SearchBean.class);
		return bean;
	}

}
