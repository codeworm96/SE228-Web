<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use DB;

class OrderController extends Controller
{
    //list all orders
    public function index()
    {
        //check for admin
        $cur_user = session('user');
        if (is_null($cur_user) || !$cur_user->getIsAdmin()) {
            return redirect('/');
        }
        
        $orders = DB::table('order')->get();
        
        return view('order.index')->with('orders', $orders);
    }
    
    //delete an order
    public function destroy($id)
    {
        //check for admin
        $cur_user = session('user');
        if (is_null($cur_user) || !$cur_user->getIsAdmin()) {
            return redirect('/');
        }
        
        $order = DB::table('order')->where('id', $id)->delete();
        
        return redirect('orders');
    }
    
    //get order detail
    public function detail($id)
    {
        //check for admin
        $cur_user = session('user');
        if (is_null($cur_user) || !$cur_user->getIsAdmin()) {
            return redirect('/');
        }
        
        $order = DB::table('order')->where('id', $id)->first();
        $items = DB::table('order_item')->join('book', 'book.id', '=', 'order_item.book_id')->where('order_id', $order->id)->get();
        
        foreach ($items as $item) {
            $item->price = $item->price / 100;
        }
        
        return view('order.detail')->with('order', $order)->with('items', $items);
    }
    
    //Update order
    public function update(Request $request, $id)
    {
        //check for admin
        $cur_user = session('user');
        if (is_null($cur_user) || !$cur_user->getIsAdmin()) {
            return redirect('/');
        }
        
        $input = $request->all();
        foreach($input as $book_id => $num) {
            if ($num > 0) {
                DB::table('order_item')->where('order_id', $id)->where('book_id', $book_id)->update(['num'=>$num]);
            } else {
                DB::table('order_item')->where('order_id', $id)->where('book_id', $book_id)->delete();
            }
        }
        
        return redirect('orders');
    }
}
