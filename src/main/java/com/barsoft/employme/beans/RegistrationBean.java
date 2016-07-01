package com.barsoft.employme.beans;

import com.barsoft.employme.dao.UsersDAO;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ManagedBean
@RequestScoped
public class RegistrationBean implements Serializable {

	private String username;
	private String password;
	private String repassword;
	private String md5Password;

	@PostConstruct
	public void init() {
	}

	public boolean validateAuthenticationData() {
		boolean error = false;
		if (getUsername().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("registerForm:username",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is empty", ""));
			error = true;
		}
		if (getPassword().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("registerForm:password",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password is empty", ""));
			error = true;
		}
		if (!getPassword().equals(getRepassword())) {
			FacesContext.getCurrentInstance().addMessage("registerForm:repassword",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", ""));
			error = true;
		}
		return error;
	}

	// register employee
	public String registerEmployee() {

		if (validateAuthenticationData()) {
			return "register";
		} else {
			try {
				MessageDigest mdEnc;
				String md5;
				mdEnc = MessageDigest.getInstance("MD5");
				mdEnc.update(getPassword().getBytes(), 0, getPassword().length());
				md5 = new BigInteger(1, mdEnc.digest()).toString(16);
				setMd5Password(md5);
			} catch (NoSuchAlgorithmException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "System error!!!", "Try again later!"));
				e.printStackTrace();
				return "register";
			}
			FacesContext context = FacesContext.getCurrentInstance();
			EmployeeBean bean = context.getApplication().evaluateExpressionGet(context, "#{employeeBean}",
					EmployeeBean.class);
			if (UsersDAO.register(this, bean)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Registration successful", "Please enter username and password"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"User with name " + getUsername() + " already exists", ""));
				return "register";
			}
		}
		return "login";
	}

	// register employee
	public String registerEmployer() {
		if (validateAuthenticationData()) {
			return "register";
		} else {
			try {
				MessageDigest mdEnc;
				String md5;
				mdEnc = MessageDigest.getInstance("MD5");
				mdEnc.update(getPassword().getBytes(), 0, getPassword().length());
				md5 = new BigInteger(1, mdEnc.digest()).toString(16);
				setMd5Password(md5);
			} catch (NoSuchAlgorithmException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "System error!!!", "Try again later!"));
				e.printStackTrace();
				return "register";
			}
			FacesContext context = FacesContext.getCurrentInstance();
			EmployerBean bean = context.getApplication().evaluateExpressionGet(context, "#{employerBean}",
					EmployerBean.class);
			if (UsersDAO.register(this, bean)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Registration successful", "Please enter username and password"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"User with name " + getUsername() + " already exists", ""));
				return "register";
			}
		}
		return "login";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getMd5Password() {
		return md5Password;
	}

	public void setMd5Password(String md5Password) {
		this.md5Password = md5Password;
	}
}
