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
    <link rel="stylesheet" type="text/css" href="css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="css/demo.css" />
    <link rel="stylesheet" type="text/css" href="css/component.css" />
    <script src="js/modernizr.custom.js"></script>
</head>
<body>
<div class="container">
    <ul id="gn-menu" class="gn-menu-main">
        <li class="gn-trigger">
            <a class="gn-icon gn-icon-menu"><span>菜单</span></a>
            <nav class="gn-menu-wrapper">
                <div class="gn-scroller">
                    <ul class="gn-menu">
                        <li class="gn-search-item">
                            <input placeholder="Search" type="search" class="gn-search">
                            <a class="gn-icon gn-icon-search"><span>搜索</span></a>
                        </li>
                        <li>
                            <a class="gn-icon gn-icon-download">下载</a>
                        </li>
                        <li><a class="gn-icon gn-icon-cog">设置</a></li>
                        <li><a class="gn-icon gn-icon-help">帮助</a></li>
                        <li>
                            <a class="gn-icon gn-icon-archive">Archives</a>
                            <ul class="gn-submenu">
                                <li><a class="gn-icon gn-icon-article">文章</a></li>
                                <li><a class="gn-icon gn-icon-pictures">图片</a></li>
                                <li><a class="gn-icon gn-icon-videos">视频</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /gn-scroller -->
            </nav>
        </li>
        <li><a href="">Admim</a></li>

        <li><a class="codrops-icon codrops-icon-prev" href=""><span>管理</span></a></li>

        <li><a class="codrops-icon codrops-icon-drop" href=""><span>关于我们</span></a></li>
    </ul>
    <header>
        <h1>Andx3 Web Server <span>网站服务 <a href="">Andx3</a> 框架</span></h1>
    </header>
</div><!-- /container -->
<script src="js/classie.js"></script>
<script src="js/gnmenu.js"></script>
<script>
    new gnMenu( document.getElementById( 'gn-menu' ) );
</script>
</body>
</html>