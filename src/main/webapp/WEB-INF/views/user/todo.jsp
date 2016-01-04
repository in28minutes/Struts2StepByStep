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
