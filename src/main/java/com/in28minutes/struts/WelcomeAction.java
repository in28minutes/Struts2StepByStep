package com.in28minutes.struts;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

@Namespace("/user")
@ResultPath(value = "/WEB-INF/views/")
public class WelcomeAction {
	@Action(value = "welcome", results = { @Result(name = "success", location = "welcome.jsp") })
	public String execute() {
		return "success";
	}
}
