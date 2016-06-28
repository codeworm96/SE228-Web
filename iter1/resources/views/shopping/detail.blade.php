@extends('layouts.app')

@section('content')
<div class="container">
<div class="col-md-offset-3 col-md-6">
<h2>书籍详情</h2>
<p><b>ISBN: </b>{{ $book->ISBN }}</p>
<p><b>名称: </b>{{ $book->name }}</p>
<p><b>单价: </b>{{ $book->price }}</p>

<form role="form" method="POST" action="{{ url('/cart/add', $book->id) }}">
    <div class="form-group">
        <label for="num">数量</label>
        <input type="text" name="num" class="form-control" value=1 required="">
    </div>    
    <button class="btn btn-large btn-primary" type="submit">加入购物车</button>
</form>

</div>
</div>
@endsection
