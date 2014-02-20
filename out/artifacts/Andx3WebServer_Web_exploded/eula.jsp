<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();//项目名称
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";// 获得项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量
    pageContext.setAttribute("basePath",basePath);// 将 "项目路径basePath" 放入pageContext中，待以后用EL表达式读出。
%>

<!DOCTYPE html>
<html lang="zh_CN" class="no-js">
<head>
    <base href="<%=basePath %>">
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Andx3 Web Server</title>
    <meta name="description" content="Andx3 Web Server" />
    <meta name="keywords" content="Andx3 Web Server" />
    <meta name="author" content="Winnid" />
    <link rel="shortcut icon" href="img/favicon.ico">
</head>
<body>

</body>
</html>