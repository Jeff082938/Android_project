package com.example.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard2);
        // 找到按鈕 B1
        Button buttonB1 = findViewById(R.id.B1);
        buttonB1.setText("leave");
        // 設置按鈕的點擊事件
        buttonB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 彈出 Toast 顯示 "123"
                Toast.makeText(Dashboard2.this, "123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Dashboard2.this, MainActivity.class);
                startActivity(intent);
                // 結束當前 Activity
                finish();
            }
        });
        Button buttonB2 = findViewById(R.id.B2);
        buttonB2.setText("大安區");
        // 設置按鈕的點擊事件
        buttonB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 彈出 Toast 顯示 "123"
                Toast.makeText(Dashboard2.this, "123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Dashboard2.this, RadarChartView.class);
                startActivity(intent);
                // 結束當前 Activity
                finish();
            }
        });
    }
}