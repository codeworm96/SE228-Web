<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use DB;

class BookController extends Controller
{
    //show book list
    public function index()
    {
        //check for admin
        $cur_user = session('user');
        if (is_null($cur_user) || !$cur_user->getIsAdmin()) {
            return redirect('/');
        }
        
        return view('book');
    }
    
    //return books' info in json
    public function fetch(Request $request)
    {
        $page = $request->input('page', 1);
        $rows = $request->input('rows', 10);
        $result = array();
        $result['total'] = DB::table('book')->count();
        $books = DB::table('book')->skip($rows * ($page - 1))->take($rows)->get();
        foreach ($books as $book) {
            $book->price = $book->price / 100;
        }
        $result['rows'] = $books;
        return json_encode($result);
    }

    /**
     * Store a newly created book
     */
    public function store(Request $request)
    {
        DB::table('book')->insertGetId(
            ['ISBN'=>$request->input('ISBN'), 'name'=>$request->input('name'), 'price'=> $request->input('price') * 100]
        );
        
        return json_encode(array('success' => true));
    }

    /**
     * Update book
     */
    public function update(Request $request, $id)
    {
        DB::table('book')->where('id', $id)
            ->update(['ISBN'=>$request->input('ISBN'), 'name'=>$request->input('name'), 'price'=> $request->input('price') * 100]);
            
        return json_encode(array('success' => true));
    }

    /**
     * Remove book
     */
    public function destroy($id)
    {
        DB::table('book')->where('id', '=', $id)->delete();
        return json_encode(array('success' => true));
    }
}
