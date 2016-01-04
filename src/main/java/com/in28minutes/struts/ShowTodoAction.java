package com.in28minutes.struts;

import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.in28minutes.model.Todo;
import com.in28minutes.todo.service.TodoService;

@Namespace("/user")
@ResultPath(value = "/WEB-INF/views/")
public class ShowTodoAction {
	private int id = 0;

	private Todo todo = new Todo(0, "in28Minutes", "Learn Spring MVC",
			new Date(), false);

	@Action(value = "show-todo", results = { @Result(name = "success", location = "todo.jsp") })
	public String execute() {
		if (id > 0) {
			todo = new TodoService().retrieveTodo(id);
		}
		return "success";
	}

	public Todo getTodo() {
		return todo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
