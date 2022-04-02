<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class RateController extends Controller
{
    public function getRate(){

        try {
            $url = "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t20224221";
            $lira = collect(Http::get($url)->json('buy')); //if the api was block, 23500 by default
            $rate = $lira->last()[1] ?? 23500;
        }catch (\Throwable $exception){
            response()->json([
                'code' => 200,
                'message' => 'An error occurred',
                'data' => null
            ]);
        }

        return response()->json([
            'code' => 200,
            'message' => 'Rate was retrieved successfully',
            'rate' => $rate,
        ]);
    }
}
