@extends('layouts.app')

@section('content')
<div class="container">
<h2>购物车</h2>
<form role="form" method="POST" action="{{ url('/cash') }}">
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
            <td>{{ $item['ISBN'] }}</td>
            <td>{{ $item['name'] }}</td>
            <td>{{ $item['price'] }}</td>
            <td><input type="text" name="{{$item['id']}}" value="{{ $item['num'] }}"></td>
        </tr>
    @endforeach
    </tbody>
</table>
<button class="btn btn-large btn-success" type="submit">结算</button>
</form>
</div>
@endsection
