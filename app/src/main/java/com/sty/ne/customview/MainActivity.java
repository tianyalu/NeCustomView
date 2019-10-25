package com.sty.ne.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(new CarView(this));

        CustomToolBar customToolBar = findViewById(R.id.toolbar);
        customToolBar.setTitle("这是自定义组合toolbar");
        customToolBar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击返回", Toast.LENGTH_SHORT).show();
            }
        });
        customToolBar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击右键", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
