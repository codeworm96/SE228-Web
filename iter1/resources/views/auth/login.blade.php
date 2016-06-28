@extends('layouts.app')

@section('extra_style')
<style>
.login-form {
    max-width: 450px;
    margin: auto;
}
.login-title {
   font-size: 40px;
}
</style>
@section('content')
<div class="container">
    <div class="row">
        <div class="">
                    <form class="login-form" role="form" method="POST" action="{{ url('/login') }}">
                    <h2 class="login-title">登录</h2>
                    <label for="username" class="sr-only">用户名</label>
                    <input type="text" name="username" class="form-control" placeholder="用户名" required="" autofocus="">
                    <label for="password" class="sr-only">密码</label>
                    <input type="password" name="password" class="form-control" placeholder="密码" required="">
                    <button class="btn btn-large btn-primary btn-block" type="submit">登录</button>
                    </form>
        </div>
    </div>
</div>
@endsection
