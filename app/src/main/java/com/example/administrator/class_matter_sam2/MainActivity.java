package com.example.administrator.class_matter_sam2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    private Button btn_New;
    private Button btn_NowList;
    private Button btn_BorrowList;
    private Button btn_BrokenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_New = (Button)findViewById(R.id.btn_New);
        btn_New.setOnClickListener(this);
        Button btn_NowList = (Button)findViewById(R.id.btn_NowList);
        btn_NowList.setOnClickListener(this);
        Button btn_BorrowList = (Button)findViewById(R.id.btn_BorrowList);
        btn_BorrowList.setOnClickListener(this);
        Button btn_BrokenList = (Button)findViewById(R.id.btn_BrokenList);
        btn_BrokenList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_New:
                Intent intent = new Intent(this, NewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_NowList:
                Intent intent1 = new Intent(this, ListActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_BorrowList:
                Intent intent2 = new Intent(this, BorrowActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_BrokenList:
                Intent intent3 = new Intent(this, BrokenActivity.class);
                startActivity(intent3);
                break;
        }

    }
}
