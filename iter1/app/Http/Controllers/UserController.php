<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use DB;

class UserController extends Controller
{
    //list all users
    public function index()
    {
        //check for admin
        $cur_user = session('user');
        if (is_null($cur_user) || !$cur_user->getIsAdmin()) {
            return redirect('/');
        }
        
        return view('user');
    }
    
    //return users' info in json
    public function fetch(Request $request)
    {
        $page = $request->input('page', 1);
        $rows = $request->input('rows', 10);
        $result = array();
        $result['total'] = DB::table('user')->count();
        $users = DB::table('user')->skip($rows * ($page - 1))->take($rows)->get();
        $result['rows'] = $users;
        return json_encode($result);
    }

    /**
     * Store a newly created user
     */
    public function store(Request $request)
    {
        DB::table('user')->insert(
            ['username'=>$request->input('username'), 'password'=>$request->input('password'), 'email'=>$request->input('email'), 'is_admin'=> $request->input('is_admin')]
        );
        
        return json_encode(array('success' => true));
    }

    /**
     * Update user
     */
    public function update(Request $request, $id)
    {
        DB::table('user')->where('username', $id)
            ->update(['username'=>$request->input('username'), 'password'=>$request->input('password'), 'email'=>$request->input('email'), 'is_admin'=> $request->input('is_admin')]);
            
        return json_encode(array('success' => true));
    }

    /**
     * Remove user
     */
    public function destroy($id)
    {
        DB::table('user')->where('username', '=', $id)->delete();
        return json_encode(array('success' => true));
    }
}
