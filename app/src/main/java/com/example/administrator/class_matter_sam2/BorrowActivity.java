package com.example.administrator.class_matter_sam2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BorrowActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener{
    private Button btn_search;
    private EditText search_name;
    private Spinner spinner;
    private ListView listView;
    private String search_Borrow;
    private ArrayList<Article> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        listView = (ListView)findViewById(R.id.borrow_list);
        listView.setOnItemClickListener(this);
        search_name = (EditText)findViewById(R.id.search_name);
        spinner = (Spinner)findViewById(R.id.sp_Borrow);
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshData();
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    private void refreshData() {
        search_Borrow = spinner.getSelectedItem().toString();
        if(search_Borrow.equals("대여받음")) {
            search_Borrow = "IN";
        } else if(search_Borrow.equals("대여")) {
            search_Borrow = "OUT";
        } else {
            search_Borrow = "OUT|IN";
        }
        Dao dao = new Dao(getApplicationContext());
        dao.getArticleList("takeType="+ search_Borrow +"&name=" + search_name.getText().toString(), new DaoListResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, ArrayList<Article> articles) {
                articleList = articles;
                CustomAdaptor customAdaptor = new CustomAdaptor(BorrowActivity.this, R.layout.costom_list_row, articleList);
                listView.setAdapter(customAdaptor);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("test", "not In");
            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("err", "find err");
        Intent intent = new Intent(this, ReadActivity.class);
        intent.putExtra("number", articleList.get(position).getArticleNumber());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                refreshData();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        Log.d("test", "onCreateOptionsMenu - 최초 메뉴키를 눌렀을 때 호출됨");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴의 항목을 선택(클릭)했을 때 호출되는 콜백메서드
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("test", "onOptionsItemSelected - 메뉴항목을 클릭했을 때 호출됨");



        switch(item.getItemId()) {
            case R.id.btn_New:
                Intent intent = new Intent(this, NewActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
