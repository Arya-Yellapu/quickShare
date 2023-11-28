<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Form</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f0f0f0;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

form {
	background-color: #ffffff;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
	padding: 20px;
	border-radius: 10px;
	width: 300px;
}

label {
	display: block;
	margin-top: 10px;
	font-weight: bold;
}

input[type="text"], input[type="password"] {
	width: 100%;
	padding: 10px;
	margin-top: 5px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

input[type="submit"] {
	background-color: #007BFF;
	color: #fff;
	border: none;
	border-radius: 5px;
	padding: 10px 20px;
	cursor: pointer;
	margin-top: 10px;
}

input[type="submit"]:hover {
	background-color: #0056b3;
}
</style>
</head>
<body>
	<form action="/register" method="POST">
		${message} <label for="firstName">First Name</label> <input
			type="text" id="firstName" name="firstName"> <label
			for="lastName">Last Name</label> <input type="text" id="lastName"
			name="lastName"> <label for="Mail">Mail</label> <input
			type="text" id="Mail" name="Mail"> <label for="password">Password</label>
		<input type="password" id="password" name="password"> <input
			type="submit" value="Register">
	</form>
</body>
</html>
