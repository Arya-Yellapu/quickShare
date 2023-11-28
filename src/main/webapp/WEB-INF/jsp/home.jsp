<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
/* Add CSS styles here */
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f0f0f0;
}

.container {
	position: relative;
	background-color: #fff;
	border: 1px solid #ccc;
	padding: 20px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin: 20px;
	animation: fadeIn 1s ease-in-out; /* Add a fade-in animation */
}

@
keyframes fadeIn {from { opacity:0;
	
}

to {
	opacity: 1;
}

}
.image {
	border: 5px solid black;
	margin: 10px;
	display: block;
	max-width: 100%;
	position: absolute;
	top: 10px;
	right: 10px;
}

.logout-link {
	position: absolute;
	top: 90px;
	right: 10px;
}

.content {
	margin-top: 90px;
	/* Adjust the margin to create space below the image and logout link */
}

form {
	background-color: #fff;
	border: 1px solid #ccc;
	padding: 20px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin-top: 20px; /* Move the form down below the content */
	animation: fadeIn 1s ease-in-out;
	/* Add a fade-in animation to the form */
}

input[type="file"] {
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<div class="container">
		<img src="${path}" alt="Smiley face" class="image">
		<div class="logout-link">
			<a href="/logout">Logout</a>
		</div>
		<div class="content">Hello ${name}! Space Available on your
			drive: ${actualSpace}GB/5GB</div>
		<form action="upload" method="post" enctype="multipart/form-data">
			<input type="hidden" name="mail" value="${mail}"> Select
			File:<input type="file" name="fname" multiple /><br /> <input
				type="submit" name="upload" value="Upload">
		</form>
		<c:forEach items="${list}" var="finalSlots">
			<c:out value="${finalSlots}"></c:out>
			<a href="/download?mail=${mail}&file=${finalSlots}">Download</a>
			<a href="/delete?mail=${mail}&file=${finalSlots}">Delete</a>
			<br>
		</c:forEach>
	</div>
</body>
</html>
