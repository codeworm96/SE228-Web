<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use App\User;

class AuthController extends Controller
{
    //show login page
    public function login()
    {
        //your should logout first
        if (session('user')) {
            return redirect('/');
        }
        
        return view('auth.login');
    }
    
    //check login request
    public function check_login(Request $request)
    {
        //your should logout first
        if (session('user')) {
            return redirect('/');
        }
        
        $user = User::where('username', '=', $request->input('username'))->first();
        if ($user && $user->password == $request->input('password')) {
             session(['user'=> $user]);
             return redirect('/');
        } else {
             return redirect('/login');
        }
    }
    
    //logout
    public function logout()
    {
        session(['user'=> NULL]);
        return redirect('/login');
    }
}
