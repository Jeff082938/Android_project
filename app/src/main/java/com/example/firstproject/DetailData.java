package com.example.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        // 找到按鈕 B1

        ImageButton buttonB2 = findViewById(R.id.back_but);
        // 設置按鈕的點擊事件
        buttonB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 彈出 Toast 顯示 "123"
                //Toast.makeText(DetailData.this, "123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailData.this,RadarChartView.class);
                startActivity(intent);
                // 結束當前 Activity
                finish();
            }
        });
    }
}