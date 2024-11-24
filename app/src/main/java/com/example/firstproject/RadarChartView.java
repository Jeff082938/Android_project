package com.example.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RadarChartView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radar);
        // 找到按鈕 B1
        Button buttonB1 = findViewById(R.id.btn_person);
        // 設置按鈕的點擊事件
        buttonB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 彈出 Toast 顯示 "123"
                Toast.makeText(RadarChartView.this, "123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RadarChartView.this,DetailData.class);
                startActivity(intent);
                // 結束當前 Activity
                finish();
            }
        });
        ImageButton buttonB2 = findViewById(R.id.back_button);
        // 設置按鈕的點擊事件
        buttonB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 彈出 Toast 顯示 "123"
                //Toast.makeText(RadarChartView.this, "123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RadarChartView.this,Dashboard2.class);
                startActivity(intent);
                // 結束當前 Activity
                finish();
            }
        });
    }
}