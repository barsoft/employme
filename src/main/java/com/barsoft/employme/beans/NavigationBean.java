package com.barsoft.employme.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@ManagedBean
@ApplicationScoped
public class NavigationBean implements Serializable {

	public String getURL() {
		String requestURI = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getRequestURI();
		return requestURI;
	}

	public String getCurrentPage() {
		String[] path = getPath();
		String currentPage = path[path.length - 1];
		return currentPage;
	}

	public String[] getPath() {
		String[] path = getURL().split("/");
		int i = 0;

		path[1] = path[1].replace(getProjectName(), "/");
		if (path[path.length - 1].lastIndexOf('.') > 0) {
			path[path.length - 1] = path[path.length - 1].substring(0, path[path.length - 1].lastIndexOf('.'));
		}
		return path;
	}

	public String getProjectName() {
		String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		String s[] = rootPath.replace("\\", "/").split("/");
		// System.out.println(s[s.length - 1]);
		return s[s.length - 1];
	}

	public static NavigationBean getBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		NavigationBean bean = context.getApplication().evaluateExpressionGet(context, "#{navigationBean}",
				NavigationBean.class);
		return bean;
	}
}
