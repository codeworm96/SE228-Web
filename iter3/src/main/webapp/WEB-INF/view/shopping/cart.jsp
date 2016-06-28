<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layouts/header.jsp" %>

<div id='cart' class="container">
    <div v-if="msg" v-bind:class="'alert alert-' + msg.type" role="alert">
        <button type="button" class="close" aria-label="Close" v-on:click="clear_msg()">
            <span aria-hidden="true">&times;</span>
        </button>
        {{msg.text}}
    </div>
    <h2>我的购物车</h2>
    <form id="cart-form" role="form" method="POST" action="<%=ctx%>/cart/checkout">
        <div v-if="items.length > 0">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <td>
                        <button type="button" class="btn btn-default btn-sm" v-if="all_checked" v-on:click="uncheck_all()">全不选</button>
                        <button type="button" class="btn btn-default btn-sm" v-else v-on:click="check_all()">全选</button>
                    </td>
                    <td>封面</td>
                    <td>书名</td>
                    <td>单价</td>
                    <td>数量</td>
                    <td>小计</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="item in items">
                        <td class="vmid"><input type="checkbox" v-model="item.checked"></td>
                        <td><img v-bind:src="'<%=ctx%>/cover?id=' + item.bookId" class="cart-cover"></td>
                        <td class="vmid">{{item.book.name}}</td>
                        <td class="vmid">{{item.book.price}}</td>
                        <td class="vmid">
                            <div class="input-group cart-num">
                                <span class="input-group-btn">
                                    <button class="btn btn-default" type="button" v-on:click="sub_one($index)">-</button>
                                </span>
                                <input type="text" class="form-control" v-bind:name="item.checked ? item.bookId : null" v-model="item.num" number>
                                <span class="input-group-btn">
                                    <button class="btn btn-default" type="button" v-on:click="add_one($index)">+</button>
                                </span>
                            </div>
                        </td>
                        <td class="vmid">{{item.book.price * item.num}}</td>
                        <td class="vmid">
                            <button class="btn btn-danger" v-on:click="remove_item($index)">
                                <span class="glyphicon glyphicon-trash"></span>
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="clearfix">
                <a href="<%=ctx%>/index" class="btn btn-info pull-left">返回购书</a>
                <button class="btn btn-success pull-left" v-on:click="save_cart()">暂存</button>
                <button class="btn btn-primary pull-right" v-bind:disabled="!allow_checkout" v-on:click="checkout()">提交订单</button>
                <p class="cart-sum pull-right">合计: {{total}}元</p>
            </div>
        </div>
        <div v-else>
            您的购物车还是空的！
            <a href="<%=ctx%>/index" class="btn btn-info">马上去购书</a>
        </div>
    </form>
</div>

<script type="text/javascript" src="<%=ctx%>/js/vue.js"></script>
<script type="text/javascript">
    $.get('<%=ctx%>/cart/data', function(data, status) {
        items = JSON.parse(data);
        items.forEach(function (item) {
            item.checked = true;
        });

        var vm = new Vue({
            el: '#cart',
            data: {
                items: items,
                allow_checkout: true,
                msg: null
            },
            computed: {
                total: function () {
                    var res = 0;
                    this.items.forEach(function(item) {
                        if (item.checked) {
                            res += item.book.price * item.num;
                        }
                    });
                    return res;
                },
                all_checked: function () {
                    return this.items.every(function (item) {
                        return item.checked;
                    });
                }
            },
            methods: {
                remove_item: function (id) {
                    this.items.splice(id, 1);
                },
                add_one: function (id) {
                    this.items[id].num += 1;
                },
                sub_one: function (id) {
                    if (this.items[id].num > 1) {
                        this.items[id].num -= 1;
                    }
                },
                check_all: function () {
                    this.items.forEach(function (item) {
                        item.checked = true;
                    });
                },
                uncheck_all: function () {
                    this.items.forEach(function (item) {
                        item.checked = false;
                    });
                },
                clear_msg: function() {
                    this.msg = null;
                },
                encode_cart: function () {
                    var res = {};
                    this.items.forEach(function(item) {
                        res[item.bookId] = item.num;
                    });
                    return res;
                },
                save_cart: function () {
                    var self = this;
                    $.post('<%=ctx%>/cart/save', self.encode_cart(), function(data, status) {
                        var response = JSON.parse(data);
                        if (response.success) {
                            self.msg = {
                                type: 'success',
                                text: '暂存成功'
                            };
                        } else {
                            self.msg = {
                                type: 'danger',
                                text: '暂存失败'
                            };
                        }
                    });
                },
                checkout: function () {
                    console.log("called");
                    this.allow_checkout = false;
                    var cart = {};
                    this.items.forEach(function(item) {
                        if (!item.checked) {
                            cart[item.bookId] = item.num;
                        }
                    });

                    var self = this;
                    $.post('<%=ctx%>/cart/save', cart, function(data, status) {
                        var response = JSON.parse(data);
                        if (response.success) {
                            document.getElementById("cart-form").submit();
                        } else {
                            self.allow_checkout = true;
                            self.msg = {
                                type: 'danger',
                                text: '提交失败'
                            };
                        }
                    });
                }
            }
        });
    });
</script>
<%@ include file="../layouts/footer.jsp" %>