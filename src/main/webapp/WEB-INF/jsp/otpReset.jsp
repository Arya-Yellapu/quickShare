<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OTP Verification</title>
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

.container {
	background-color: #ffffff;
	border-radius: 12px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	text-align: center;
}

h2 {
	margin-bottom: 20px;
	color: #333;
}

form {
	margin-top: 20px;
}

input[type="text"] {
	width: 100%;
	padding: 12px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 6px;
}

input[type="submit"] {
	background-color: #007bff;
	border: none;
	border-radius: 6px;
	color: white;
	padding: 12px 20px;
	cursor: pointer;
	font-weight: bold;
	transition: background-color 0.2s ease;
}

input[type="submit"]:hover {
	background-color: #0056b3;
}

.resend-button {
	margin-top: 10px;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Please enter the OTP sent to the registered Mail Id</h2>
		<form action="otpResetValidation" method='POST'>
			${message} <input type="hidden" name="Mail" value="${Mail}">
			<input type="text" name="otp" placeholder="Enter OTP" required>
			<input type="submit" value="Submit">
		</form>
		<div class="resend-button">
			<form action="/reset" method='POST'>
				<input type="hidden" name="mail" value="${Mail}"> <input
					type="submit" value="Resend OTP">
			</form>
		</div>
	</div>
</body>
</html>
