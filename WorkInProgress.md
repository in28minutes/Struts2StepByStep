## Files List
### pom.xml
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.in28minutes</groupId>
	<artifactId>in28Minutes-struts</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<dependencies>
		<dependency>
			<groupId>javax</groupId>
			<artifactId>javaee-web-api</artifactId>
			<version>6.0</version>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.apache.struts</groupId>
			<artifactId>struts2-core</artifactId>
			<version>2.3.24.1</version>
		</dependency>

		<!-- For Annotation Based Approach -->
		<dependency>
			<groupId>org.apache.struts</groupId>
			<artifactId>struts2-convention-plugin</artifactId>
			<version>2.3.24.1</version>
		</dependency>

		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
			<version>1.2</version>
		</dependency>

		<dependency>
			<groupId>org.webjars</groupId>
			<artifactId>bootstrap</artifactId>
			<version>3.3.6</version>
		</dependency>
		<dependency>
			<groupId>org.webjars</groupId>
			<artifactId>jquery</artifactId>
			<version>1.9.1</version>
		</dependency>

		<dependency>
			<groupId>org.webjars</groupId>
			<artifactId>bootstrap-datepicker</artifactId>
			<version>1.0.1</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>5.0.2.Final</version>
		</dependency>

		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>1.2.17</version>
		</dependency>
	</dependencies>

	<build>
		<pluginManagement>
			<plugins>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-compiler-plugin</artifactId>
					<version>3.2</version>
					<configuration>
						<verbose>true</verbose>
						<source>1.8</source>
						<target>1.8</target>
						<showWarnings>true</showWarnings>
					</configuration>
				</plugin>
				<plugin>
					<groupId>org.apache.tomcat.maven</groupId>
					<artifactId>tomcat7-maven-plugin</artifactId>
					<version>2.2</version>
					<configuration>
						<path>/</path>
						<contextReloadable>true</contextReloadable>
					</configuration>
				</plugin>
			</plugins>
		</pluginManagement>
	</build>
</project>
```
### src/main/java/com/in28minutes/model/Todo.java
```
package com.in28minutes.model;

import java.util.Date;

import javax.validation.constraints.Size;

public class Todo {
	private int id;

	private String user;

	@Size(min = 10, message = "Enter atleast 10 Characters.")
	private String desc;

	private Date targetDate;
	private boolean isDone;

	public Todo() {
		super();
	}

	public Todo(int id, String user, String desc, Date targetDate,
			boolean isDone) {
		super();
		this.id = id;
		this.user = user;
		this.desc = desc;
		this.targetDate = targetDate;
		this.isDone = isDone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Todo [id=%s, user=%s, desc=%s, targetDate=%s, isDone=%s]", id,
				user, desc, targetDate, isDone);
	}

}
```
### src/main/java/com/in28minutes/struts/EditAddTodoAction.java
```
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
```
### src/main/java/com/in28minutes/struts/ListTodosAction.java
```
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
```
### src/main/java/com/in28minutes/struts/ShowTodoAction.java
```
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
```
### src/main/java/com/in28minutes/struts/WelcomeAction.java
```
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
```
### src/main/java/com/in28minutes/todo/service/TodoService.java
```
package com.in28minutes.todo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.in28minutes.model.Todo;

public class TodoService {
	private static List<Todo> todos = new ArrayList<Todo>();
	private static int todoCount = 3;

	static {
		todos.add(new Todo(1, "in28Minutes", "Learn Spring MVC", new Date(),
				false));
		todos.add(new Todo(2, "in28Minutes", "Learn Struts", new Date(), false));
		todos.add(new Todo(3, "in28Minutes", "Learn Hibernate", new Date(),
				false));
	}

	public List<Todo> retrieveTodos(String user) {
		List<Todo> filteredTodos = new ArrayList<Todo>();
		for (Todo todo : todos) {
			if (todo.getUser().equals(user))
				filteredTodos.add(todo);
		}
		return filteredTodos;
	}

	public Todo retrieveTodo(int id) {
		for (Todo todo : todos) {
			if (todo.getId() == id)
				return todo;
		}
		return null;
	}

	public void updateTodo(Todo todo) {
		todos.remove(todo);
		todos.add(todo);
	}

	public void addTodo(String name, String desc, Date targetDate,
			boolean isDone) {
		todos.add(new Todo(++todoCount, name, desc, targetDate, isDone));
	}

	public void deleteTodo(int id) {
		Iterator<Todo> iterator = todos.iterator();
		while (iterator.hasNext()) {
			Todo todo = iterator.next();
			if (todo.getId() == id) {
				iterator.remove();
			}
		}
	}
}
```
### src/main/resources/log4j.properties
```
log4j.rootLogger=DEBUG, Appender1
 
log4j.appender.Appender1=org.apache.log4j.ConsoleAppender
log4j.appender.Appender1.layout=org.apache.log4j.PatternLayout
log4j.appender.Appender1.layout.ConversionPattern=%-7p %d [%t] %c %x - %m%n
 
```
### src/main/resources/messages_en.properties
```
welcome.message=Welcome in English
todo.caption= Todo Caption in English
```
### src/main/resources/messages_fr.properties
```
welcome.message=Welcome in French
todo.caption= Todo Caption in French
```
### src/main/resources/struts.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!-- https://struts.apache.org/docs/struts-defaultxml.html -->
<!DOCTYPE struts PUBLIC
    "-//Apache Software Foundation//DTD Struts Configuration 2.3//EN"
    "http://struts.apache.org/dtds/struts-2.3.dtd">
<struts>
	<package name="default" extends="struts-default">
		<global-results>
			<result name="error">/Error.jsp</result>
		</global-results>

		<global-exception-mappings>
			<exception-mapping exception="java.lang.Exception"
				result="error" />
		</global-exception-mappings>
	</package>

</struts>
```
### src/main/webapp/WEB-INF/views/common/footer.jspf
```

<script src="../webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="../webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="../webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"></script>

</body>
</html>
```
### src/main/webapp/WEB-INF/views/common/header.jspf
```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Todos Application</title>
<link href="../webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.label{
	font-size: 16px;
	color : black;
}
.table-nonfluid {
   width: auto !important;
}
</style>	

</head>

<body>
```
### src/main/webapp/WEB-INF/views/common/navigation.jspf
```
<nav role="navigation" class="navbar navbar-default">

	<div class="">
		<a href="http://www.in28minutes.com" class="navbar-brand">in28Minutes</a>
	</div>

	<div class="navbar-collapse">
		<ul class="nav navbar-nav">
			<li class="active"><a href="/user/welcome.action">Home</a></li>
			<li><a href="/user/list-todos.action">Todos</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="/logout">Logout</a></li>
		</ul>
	</div>
</nav>
```
### src/main/webapp/WEB-INF/views/error.jsp
```
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	Application has encountered an error. Please contact support on ...
</div>

<%@ include file="common/footer.jspf"%>
```
### src/main/webapp/WEB-INF/views/user/customized-error.jsp
```
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>
<div class="container">Application has encountered an error.</div>
<div class="container">Exception name: ${exception}</div>
<div class="container">Exception stack trace: ${exceptionStack}</div>

<%@ include file="../common/footer.jspf"%>
```
### src/main/webapp/WEB-INF/views/user/list-todos.jsp
```
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">
	<table class="table table-striped">
		<caption>TODO Caption</caption>
		<thead>
			<tr>
				<th>Description</th>
				<th>Date</th>
				<th>Completed</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.desc}</td>
					<td><fmt:formatDate pattern="dd/MM/yyyy"
							value="${todo.targetDate}" /></td>
					<td>${todo.done}</td>
					<td><a type="button" class="btn btn-primary"
						href="/user/show-todo.action?id=${todo.id}">Edit</a> <a type="button"
						class="btn btn-warning" href="/delete-todo?id=${todo.id}">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div>
		<a type="button" class="btn btn-success" href="/user/show-todo.action">Add</a>
	</div>
</div>
<%@ include file="../common/footer.jspf"%>
```
### src/main/webapp/WEB-INF/views/user/todo.jsp
```
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>
<div class="container">

	<s:form action="edit-todo" cssClass="table table-striped table-nonfluid">

		<s:hidden name="todo.id"/>

		<s:textfield label="Description" name="todo.desc" />
		<s:textfield label="Target Date" name="todo.targetDate" />

		<s:submit class="btn btn-success" />

	</s:form>

</div>

<%@ include file="../common/footer.jspf"%>

<script>
	$('#edit-todo_todo_targetDate').datepicker({
		format : 'mm/dd/yy'
	});
</script>
```
### src/main/webapp/WEB-INF/views/user/welcome.jsp
```
<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>
<div class="container">
	Welcome
</div>

<%@ include file="../common/footer.jspf"%>
```
### src/main/webapp/WEB-INF/web.xml
```
<web-app xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	version="3.0">

	<display-name>To do List</display-name>

	<filter>
		<filter-name>struts2</filter-name>
		<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
	</filter>

	<filter-mapping>
		<filter-name>struts2</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

	<error-page>
		<location>/WEB-INF/views/error.jsp</location>
	</error-page>
</web-app>
```
