<%@ page language="java"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, webProject.model.UserPost, webProject.model.dao.Dao"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
	<div class="center">
		<h1 class="head">Login</h1>
		<p>${ error }</p>
		<form method="POST" action="/webProject/LoginServlet">
			<p>Username: </p><input type="text" name="user" /><br>
			<p>Password: </p><input type="password" name="pass" /><br><br>
			<input type="submit" value="Login" name="submit" />
			<input type="submit" value="Register" name="submit" />
		</form>
	</div>
</t:layout>