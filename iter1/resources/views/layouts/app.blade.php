<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>书店 ~ A book store</title>

    <!-- Icon -->
     <link rel="shortcut icon" href="{{ asset('favicon.ico') }}">
    <!-- Styles -->
    <link rel="stylesheet" type="text/css" href="{{ asset('js/themes/bootstrap/easyui.css') }}">
    <link rel="stylesheet" type="text/css" href="{{ asset('js/themes/icon.css') }}">
    <link href="{{ asset('css/bootstrap.min.css') }}" rel="stylesheet">
    <link href="{{ asset('css/style.css') }}" rel="stylesheet">
    @yield('extra_style', '')
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
                <a class="navbar-brand" href="{{ url('/') }}">
                    书店
                </a>
            </div>

            <div class="collapse navbar-collapse" id="app-navbar-collapse">
                <!-- Left Side Of Navbar -->
                <ul class="nav navbar-nav">
                    <li><a href="{{ url('/shopping') }}">购书</a></li>
                </ul>

                <!-- Right Side Of Navbar -->
                <ul class="nav navbar-nav navbar-right">
                    <!-- Authentication Links -->
                    @if (is_null(session('user')))
                        <li><a href="{{ url('/login') }}">登录</a></li>
                    @else
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                {{ session('user')->username }} <span class="caret"></span>
                            </a>

                            <ul class="dropdown-menu" role="menu">
                                <li><a href="{{ url('/cart') }}"><i class="fa fa-btn fa-sign-out"></i>购物车</a></li>
                                <li><a href="{{ url('/logout') }}"><i class="fa fa-btn fa-sign-out"></i>登出</a></li>
                                @if (session('user')->getIsAdmin())
                                <li><a href="{{ url('/users') }}"><i class="fa fa-btn fa-sign-out"></i>用户管理</a></li>
                                <li><a href="{{ url('/books') }}"><i class="fa fa-btn fa-sign-out"></i>图书管理</a></li>
                                <li><a href="{{ url('/orders') }}"><i class="fa fa-btn fa-sign-out"></i>订单管理</a></li>
                                @endif
                            </ul>
                        </li>
                    @endif
                </ul>
            </div>
        </div>
    </nav>

    @yield('content')

    <!-- JavaScripts -->
    <script src="{{ asset('js/jquery.min.js') }}"></script>
    <script type="text/javascript" src="{{ asset('js/jquery.easyui.min.js') }}"></script>
    <script src="{{ asset('js/bootstrap.min.js') }}"></script>
    
</body>
</html>
