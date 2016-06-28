<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ page import="bookstore.user.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>书店 ~ A book store</title>

    <!-- Icon -->
     <link rel="shortcut icon" href="../../../favicon.ico">
    <!-- Styles -->
    <link rel="stylesheet" type="text/css" href="../../../js/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../../js/themes/icon.css">
    <link href="../../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../../css/style.css" rel="stylesheet">
</head>
<body id="app-layout">
    <div class="container">
         <nav class="navbar navbar-inverse navbar-embossed">
            <div class="navbar-header">

                <!-- Collapsed Hamburger -->
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#app-navbar-collapse">
                    <span class="sr-only">Toggle Navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>

                <!-- Branding Image -->
                <a class="navbar-brand" href="/">
                    书店
                </a>
            </div>

            <div class="collapse navbar-collapse" id="app-navbar-collapse">
                <!-- Left Side Of Navbar -->
                <ul class="nav navbar-nav">
                    <li><a href="/shopping">购书</a></li>
                </ul>

                <!-- Right Side Of Navbar -->
                <ul class="nav navbar-nav navbar-right">
                    <!-- Authentication Links -->
                    <% User user = (User)request.getAttribute("user"); %>
                    <% if (user == null) { %>
                        <li><a href="/register">注册</a></li>
                        <li><a href="/login">登录</a></li>
                    <% } else { %>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                <%= user.getUsername() %> <span class="caret"></span>
                            </a>

                            <ul class="dropdown-menu" role="menu">
                                <li><a href="/cart">购物车</a></li>
                                <li><a href="/order/my">我的订单</a></li>
                                <li><a href="/logout">登出</a></li>
                                <% if (user.isAdmin()) { %>
                                <li><a href="/admin/user/index.jsp">用户管理</a></li>
                                <li><a href="/admin/book/index.jsp">图书管理</a></li>
                                <li><a href="/stat.jsp">销售统计</a></li>
                                <% } %>
                            </ul>
                        </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>
