@extends('layouts.app')

@section('content')
<div class="container">
<h2>订单详情</h2>
<h3>订单号: {{ $order->id }} 用户名: {{ $order->username }}</h3>
<form role="form" method="POST" action="{{ url('/orders', $order->id) }}">
<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <td>ISBN</td>
            <td>书名</td>
            <td>单价</td>
            <td>数量</td>
        </tr>
    </thead>
    <tbody>
    @foreach($items as $item)
        <tr>
            <td>{{ $item->ISBN }}</td>
            <td>{{ $item->name }}</td>
            <td>{{ $item->price }}</td>
            <td><input type="text" name="{{ $item->book_id }}" value="{{ $item->num }}"></td>
        </tr>
    @endforeach
    </tbody>
</table>
<button class="btn btn-large btn-info" type="submit">提交修改</button>
</form>
</div>
@endsection
