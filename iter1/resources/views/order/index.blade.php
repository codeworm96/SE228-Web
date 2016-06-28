@extends('layouts.app')

@section('content')
<div class="container">
<h2>订单列表</h2>
<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <td>订单号</td>
            <td>用户名</td>
            <td>操作</td>
        </tr>
    </thead>
    <tbody>
    @foreach($orders as $order)
        <tr>
            <td>{{ $order->id }}</td>
            <td>{{ $order->username }}</td>
            <td>
            <a class="btn btn-info" href="{{ url('/orders', $order->id) }}">详情</a>
            <form style="display:inline" role="form" method="POST" action="{{ url('/orders', $order->id) }}">
            {{ method_field('DELETE') }}
            <button class="btn btn-danger" type="submit">删除</button>
            </form>
            </td>
        </tr>
    @endforeach
    </tbody>
</table>
</div>
@endsection
