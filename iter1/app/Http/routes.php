<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::group(['middleware' => ['web']], function () {
    //index
    Route::get('/', function () {
        return view('welcome');
    });
    
    //Auth
    Route::get('login', 'AuthController@login');
    Route::post('login', 'AuthController@check_login');
    Route::get('logout', 'AuthController@logout');
    
    //User CRUD
    Route::get('users', 'UserController@index');
    Route::get('users/list', 'UserController@fetch');
    Route::post('users', 'UserController@store');
    Route::delete('users/{id}', 'UserController@destroy');
    Route::post('users/{id}', 'UserController@update');
    
    //Book CRUD
    Route::get('books', 'BookController@index');
    Route::get('books/list', 'BookController@fetch');
    Route::post('books', 'BookController@store');
    Route::delete('books/{id}', 'BookController@destroy');
    Route::post('books/{id}', 'BookController@update');
    
    //Shopping logic
    Route::get('shopping', 'ShoppingController@index');
    Route::get('shopping/{id}', 'ShoppingController@detail');
    Route::post('cart/add/{id}', 'ShoppingController@add_cart');
    Route::get('cart', 'ShoppingController@cart');
    Route::post('cash', 'ShoppingController@cash');
    
    //Order manage
    Route::get('orders', 'OrderController@index');
    Route::delete('orders/{id}', 'OrderController@destroy');
    Route::get('orders/{id}', 'OrderController@detail');
    Route::post('orders/{id}', 'OrderController@update');
});

