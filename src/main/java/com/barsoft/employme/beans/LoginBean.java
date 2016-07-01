package com.barsoft.employme.beans;

import com.barsoft.employme.dao.UsersDAO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {
 
	private String password;
	private String message;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// validate login
	public String login() {
		String md5;
		try {
			MessageDigest mdEnc;
			mdEnc = MessageDigest.getInstance("MD5");
			mdEnc.update(getPassword().getBytes(), 0, getPassword().length());
			md5 = new BigInteger(1, mdEnc.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "System error!!!", "Try again later!"));
			e.printStackTrace();
			return "login";
		}
		boolean valid = UsersDAO.validate(getUsername(), md5);
		if (valid) {
			HttpSession session = SessionBean.getBean().getSession();
			session.setAttribute("username", getUsername());
			return "profile?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter correct username and password", ""));
			return "login";
		}
	}

	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionBean.getBean().getSession();
		session.invalidate();
		return "login?faces-redirect=true";
	}

}