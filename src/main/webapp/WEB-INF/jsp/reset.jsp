<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login Page</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f0f8ff; /* Light blue background */
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.login-container {
	background-color: #ffffff; /* White background */
	border-radius: 12px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 30px;
	width: 350px;
	text-align: center;
}

.login-container h2 {
	margin-bottom: 20px;
	color: #333; /* Dark text color */
}

.login-form label {
	display: block;
	font-weight: bold;
	margin-bottom: 10px;
	color: #555; /* Medium text color */
}

.login-form input[type="text"], .login-form input[type="password"] {
	width: 100%;
	padding: 12px;
	margin-bottom: 20px;
	border: 1px solid #ccc;
	border-radius: 6px;
}

.login-form input[type="submit"] {
	background-color: #007bff;
	border: none;
	border-radius: 6px;
	color: white;
	padding: 12px 20px;
	cursor: pointer;
	font-weight: bold;
	transition: background-color 0.2s ease;
}

.login-form input[type="submit"]:hover {
	background-color: #0056b3;
}

.login-form a {
	display: block;
	margin-top: 20px;
	color: #007bff;
	text-decoration: none;
	font-weight: bold;
}

.login-form a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="login-container">
		<h2>Reset</h2>
		<form class="login-form" action="/reset" method="POST">
			${message} <label for="mail">Email</label> <input type="text"
				id="mail" name="mail" required> <input type="submit"
				name="submit" value="submit">
		</form>
	</div>
</body>
</html>
