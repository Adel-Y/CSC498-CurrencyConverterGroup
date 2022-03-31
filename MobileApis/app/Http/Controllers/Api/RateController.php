<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class RateController extends Controller
{
    public function getRate(){
        $url = "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t202233121";
        $lira = Http::get($url)->json();

        return $lira;
    }
}
