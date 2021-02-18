package com.native_to_flutter_programming

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private  val CHANNEL="samples.flutter.dev/battery"
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger,CHANNEL).setMethodCallHandler{call, result ->
               call.arguments
            if(call.method.equals("getBatteryLevel")){
               // result.success("Hi I am Android "+ call.argument<String>("first_name"))
                /*to pass "Hi I am Android " to flutter and call.argument<String>("first_name") send data flutter and recive in android   */
                //callIntent("https://flutter.dev")// open intent in native(android plate form )
                result.success(getBatteryLevel().toString());
            }

        }

    }

    /*fun  callIntent(url:String){
        intent =  Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent)
    }*/

private  fun  getBatteryLevel():Int{
    val batteryLevel: Int
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    } else {
        val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    }

    return batteryLevel
    }
}
