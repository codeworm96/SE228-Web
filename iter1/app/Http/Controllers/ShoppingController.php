<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use DB;

class ShoppingController extends Controller
{
    //list all books that you can buy
    public function index()
    {
        $books = DB::table('book')->get();
        foreach ($books as $book) {
            $book->price = $book->price / 100;
        }
        
        return view('shopping.index')->with('books', $books);
    }
    
    //should a book's detail
    public function detail($id)
    {
        $book = DB::table('book')->where('id', $id)->first();
        $book->price = $book->price / 100;
        
        return view('shopping.detail')->with('book', $book);
    }
    
    //add books
    public function add_cart(Request $request, $id)
    {
        //check for login
        $cur_user = session('user');
        if (is_null($cur_user)) {
            return redirect('/login');
        }
        
        $cart = session('cart', array());
        if (array_key_exists($id, $cart)) {
            $cart[$id] = $cart[$id] + intval($request->input('num'));
        } else {
            $cart[$id] = intval($request->input('num'));
        }
        
        session(['cart'=>$cart]);
        return redirect('shopping');
    }
    
    //show cart
    public function cart()
    {
        //check for login
        $cur_user = session('user');
        if (is_null($cur_user)) {
            return redirect('/login');
        }
        
        $cart = session('cart', array());
        $books = DB::table('book')->whereIn('id', array_keys($cart))->get();
        
        $items = array();
        foreach($books as $book) {
            $item = array();
            $item['id'] = $book->id;
            $item['ISBN'] = $book->ISBN;
            $item['name'] = $book->name;
            $item['price'] = $book->price / 100;
            $item['num'] = $cart[$book->id];
            if ($item['num'] > 0) {
                array_push($items, $item);
            }
        }
        
        return view('shopping.cart')->with('items', $items);
    }
    
    //cash to create an order
    public function cash(Request $request)
    {
        //check for login
        $cur_user = session('user');
        if (is_null($cur_user)) {
            return redirect('/login');
        }
        
        $order_id = DB::table('order')->insertGetId(['username'=>$cur_user->username]);
        
        $input = $request->all();
        foreach($input as $book_id => $num) {
            if ($num > 0) {
                DB::table('order_item')->insert(['order_id'=>$order_id, 'book_id'=>$book_id, 'num'=>$num]);
            }
        }
        
        session(['cart'=>array()]); //clean cart
        return redirect('shopping');
    }
}
