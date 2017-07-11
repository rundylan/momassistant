package cn.bingoogolapple.qrcode.zbardemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;

public class ShowActivity extends AppCompatActivity {
    private TextView tv_res;
    private String result;
    private String  res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        result= getIntent().getStringExtra("result");//将结果从intent里取出来

        res =result.substring(9,11);
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
        tv_res= (TextView) findViewById(R.id.show_res);
        tv_res.setText("排卵期可能性"+res+"%");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
