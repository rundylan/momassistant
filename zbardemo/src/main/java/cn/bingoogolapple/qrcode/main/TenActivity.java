package cn.bingoogolapple.qrcode.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import cn.bingoogolapple.qrcode.zbardemo.R;


/**
 * Created by Administrator on 2015/4/20.
 */

public class TenActivity extends Activity {
	private Handler handler;
	SharedPreferences sp;
	
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        
        sp = getSharedPreferences("mensttuation", MODE_PRIVATE);
        setHandler();
    }

	private void setHandler() {
		handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            	Intent intent = new Intent();
            	intent.setClass(GuideActivity.this, sp.getBoolean("menstruation", false) ? FragMainActivity.class : SetMenstruationDate.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
