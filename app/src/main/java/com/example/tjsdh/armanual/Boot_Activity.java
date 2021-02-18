package com.example.tjsdh.armanual;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Window;

public class Boot_Activity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_acitivity);

        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                startActivity(new Intent(Boot_Activity.this,NavigationActivity.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,1500);
    }
}
