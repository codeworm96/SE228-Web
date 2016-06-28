<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bookstore.entity.Book" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored ="false" %>

<%@ include file="layouts/header.jsp" %>

<div class="container">
    <div class="row">
        <div id="el" class="col-md-10 col-md-offset-1">
            <h1 id="page-title"></h1>
            <ul id="book-list" class="clearfix">
                <li class="p-item"  v-for="book in books" v-on:click="book_detail($index)">
                    <img v-bind:src="'<%=ctx%>/cover?id=' + book.id" class="p-img">
                    <span class="p-name">{{book.name}}</span>
                    <span class="p-category">{{book.category}}</span>
                    <span class="p-price">￥{{book.price}}</span>
                </li>
            </ul>

            <div class="btn-group btn-group-justified" role="group" aria-label="...">
                <div class="btn-group" role="group">
                    <button id="next-page" type="button" class="btn btn-default" v-on:click="nextPage()">加载更多</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="book-detail" tabindex="-1" role="dialog"
     aria-labelledby="bookDetail" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    书籍详情
                </h4>
            </div>
            <div class="modal-body">
                <div id="alert-region"></div>
                <img src="<%=ctx%>/demo.jpg" id="detail-img">
                <div class="detail-section">
                    <p>名称: <span id="detail-name"></span></p>
                    <p>ISBN: <span id="detail-isbn"></span></p>
                    <p>类别: <span id="detail-category"></span></p>
                    <p>单价: <span id="detail-price"></span></p>

                    <label for="num">数量: </label>
                    <input type="hidden" id="bookid" name="bookid">
                    <input type="text" id="num" name="num" required="">
                    <button id="add-btn" class="btn btn-success" onclick="addCart()">加入购物车</button>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->

<script type="text/javascript" src="<%=ctx%>/js/vue.js"></script>
<script type="text/javascript">
    function getUrlParam(name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r!=null) {
            return decodeURIComponent(r[2]);
        } else {
            return "";
        }
    }

    function showDetail(id) {
        var url = "<%=ctx%>/shopping/detail?id=" + id;
        $.get(url, function(data, status) {
            var response = JSON.parse(data);

            $("#detail-img").attr('src', "<%=ctx%>/cover?id=" + response.id);
            $("#detail-name").html(response.name);
            $("#detail-isbn").html(response.isbn);
            $("#detail-category").html(response.category);
            $("#detail-price").html(response.price);
            $("#bookid").val(response.id);
            $('#num').val(1);
            $('#alert-region').html('');

            $('#book-detail').modal();
        });
    }

    function addCart() {
        var bookid = $('#bookid').val();
        var num = $('#num').val();
        var btn = document.getElementById("add-btn");
        btn.disabled = true;

        $.post('<%=ctx%>/cart/add', {bookid: bookid, num: num}, function(data, status) {
            var response = JSON.parse(data);
            if (response.success) {
                var btn = document.getElementById("add-btn");
                btn.disabled = false;
                $('#book-detail').modal('hide');
            } else {
                var btn = document.getElementById("add-btn");
                btn.disabled = false;
                $('#alert-region').html('<div class="alert alert-danger alert-dismissible" role="alert">' +
                    '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                    '<strong>Failed!</strong> ' + response.msg +
                    '</div>');
            }
        });
    }

    var vm = new Vue({
        el: '#el',
        data: {
            id: 1,
            size: 2,
            q: getUrlParam("q"),
            books: []
        },
        ready: function() {
            if (this.q == "") {
                $("#page-title").html("All books");
            } else {
                $("#page-title").html('Search result for "' + this.q + '"');
            }

            this.nextPage();
        },
        methods: {
            nextPage: function() {
                var btn = document.getElementById("next-page");
                btn.disabled = true;
                btn.innerHTML = "加载中...";

                var url = "<%=ctx%>/shopping/list?id=" + this.id + "&size=" + this.size;
                this.id += 1;
                if (this.q != "") {
                    url += "&q=" + this.q;
                }

                var self = this;
                $.get(url, function(data, status){
                    var response = JSON.parse(data);
                    if (response.incomplete) {
                        var btn = document.getElementById("next-page");
                        btn.disabled = false;
                        btn.innerHTML = "加载更多";
                    }　else {
                        var btn = document.getElementById("next-page");
                        btn.innerHTML = "没有了";
                    }

                    self.books = self.books.concat(response.items);
                });
            },
            book_detail: function (id) {
                showDetail(this.books[id].id);
            }
        }
    });
</script>

<%@ include file="layouts/footer.jsp" %>
