package com.in28minutes.struts;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.in28minutes.model.Todo;
import com.in28minutes.todo.service.TodoService;

@Namespace("/user")
@ResultPath(value = "/WEB-INF/views/")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lang.Exception", result = "error") })
public class ListTodosAction {
	private List<Todo> todos = new ArrayList<Todo>();

	public List<Todo> getTodos() {
		return todos;
	}

	@Action(value = "list-todos", results = {
			@Result(name = "success", location = "list-todos.jsp"),
			@Result(name = "error", location = "customized-error.jsp") })
	public String execute() {
		todos = new TodoService().retrieveTodos("in28Minutes");
		return "success";
	}
}
