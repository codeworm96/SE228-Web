<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    protected $table = 'user';
    protected $primaryKey = 'username';
    public $incrementing = false;
    public $timestamps = false;
    
    public function getIsAdmin()
    {
        if ($this->is_admin == 'Y') {
            return true;
        } else {
            return false;
        }
    }
    
    public function setIsAdmin($value)
    {
        if ($value) {
            $this->attributes['is_admin'] = 'Y';
        } else {
            $this->attributes['is_admin'] = 'N';
        }
    }
}
