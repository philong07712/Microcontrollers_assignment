<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<form action="hello-servlet" method="get">
    <label for="fname">So 1:</label><br>
    <input type="text" id="fname" name="num1"><br>
    <label for="lname">So 2:</label><br>
    <input type="text" id="lname" name="num2">
    <button type="submit" name="your_name" value="Send" class="btn-link">Go</button>
</form>
<a href="hello-servlet?num1=3&num2=4">Send</a>
</body>
</html>