package com.in28minutes.struts;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.in28minutes.model.Todo;
import com.in28minutes.todo.service.TodoService;

@Namespace("/user")
@ResultPath(value = "/WEB-INF/views/")
public class EditAddTodoAction {
	private Todo todo;

	@Action(value = "edit-todo", results = { @Result(name = "success", type = "redirect", location = "list-todos") })
	public String execute() {
		TodoService todoService = new TodoService();
		if (todo.getId() > 0) {
			todo.setUser("in28Minutes");
			todoService.updateTodo(todo);
		} else {
			todoService.addTodo("in28Minutes", todo.getDesc(),
					todo.getTargetDate(), false);

		}
		return "success";
	}

	public Todo getTodo() {
		return todo;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}
}