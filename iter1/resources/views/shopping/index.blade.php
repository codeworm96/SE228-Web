@extends('layouts.app')

@section('content')
<div class="container">
<h2>书籍列表</h2>
<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <td>ISBN</td>
            <td>书名</td>
            <td>价格</td>
            <td>操作</td>
        </tr>
    </thead>
    <tbody>
    @foreach($books as $book)
        <tr>
            <td>{{ $book->ISBN }}</td>
            <td>{{ $book->name }}</td>
            <td>{{ $book->price }}</td>
            <td>
            <a class="btn btn-info" href="{{ url('/shopping', $book->id) }}">详情</a>
            </td>
        </tr>
    @endforeach
    </tbody>
</table>
</div>
@endsection
