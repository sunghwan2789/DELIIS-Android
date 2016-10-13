package com.example.administrator.class_matter_sam2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class BrokenActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener{
    private Spinner spinner;
    private EditText search_name;
    private Button btn_search;
    private ArrayList<Article> articleList;
    private ListView listView;
    private   String search_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broken);


        spinner = (Spinner) findViewById(R.id.sp_Broken);
        listView = (ListView)findViewById(R.id.broken_list) ;
        listView.setOnItemClickListener(this);

        search_name = (EditText)findViewById(R.id.search_name);
        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        refreshData();
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshData();
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    private void refreshData() {
        search_item = spinner.getSelectedItem().toString();
        if(search_item.equals("전체")) {
            search_item = "";
        }
        Dao dao = new Dao(getApplicationContext());
        dao.getArticleList("broken="+ 1 +"&division=" + search_item + "&name=" + search_name.getText().toString(), new DaoListResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, ArrayList<Article> articles) {
                articleList = articles;
                CustomAdaptor customAdaptor = new CustomAdaptor(BrokenActivity.this, R.layout.costom_list_row, articleList);
                listView.setAdapter(customAdaptor);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("test", "not In");
            }

        });
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("err", "find err");
        Intent intent = new Intent(this, ReadActivity.class);
        intent.putExtra("number", articleList.get(position).getArticleNumber());
        startActivity(intent);
    }
}
