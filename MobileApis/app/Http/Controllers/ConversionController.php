<?php

namespace App\Http\Controllers;


use App\Models\Api\Conversion;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;

class ConversionController extends Controller
{
    //
    public function store(Request $request){

        //validate data in request
        try {
            $this->validate($request, [
                'amount' => 'required',
                'rate' => 'required',
                'currency' => 'required',
                'convert_to' => 'required'
            ]);
        }catch (ValidationException $exception){
            return response()->json([
                'code' => 200,
                'message' => $exception->errors(),
                'data' => null
            ]);
        }


        //check to what i should convert
        if ($request->convert_to == "USD"){
            $converted_amount = $request->amount / $request->rate;
        }else if ($request->convert_to == "LPB"){
            $converted_amount = $request->amount * $request->rate;
        }else{
            return response()->json([
                'code' => 400,
                'message' => 'Invalid converted_to.',
                'data' => null
            ]);
        }

        //store in db
        Conversion::create([
            'amount' => $request->amount,
            'rate' => $request->rate,
            'converted_amount' => $request->converted_amount,
            'currency' => $request->currency,
        ]);

        //return success response
        return response()->json([
            'code' => 200,
            'message' => 'Amount converted successfully',
            'data' => [
                'converted_amount' => $converted_amount
            ],
        ]);
    }
}
