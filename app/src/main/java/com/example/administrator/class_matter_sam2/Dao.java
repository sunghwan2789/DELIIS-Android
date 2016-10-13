package com.example.administrator.class_matter_sam2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-10-12.
 */

public class Dao {
    private Context context;

    public Dao(Context context) {
        this.context = context;
    }

    private static AsyncHttpClient client = new AsyncHttpClient();
    public void getArticleList(String search, DaoListResponseHandler responseHandler) {
        client.get("http://bloodcat.com:5844/list?" +search, responseHandler);
    }

    public void getArticleByArticleNumber(int articleNumber, DaoResponseHandler responseHandler) {
        client.get("http://bloodcat.com:5844/item/" + articleNumber, responseHandler);
    }

    public void delArticleByArticleNumber(int articleNumber, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        client.delete("http://bloodcat.com:5844/item/" + articleNumber, asyncHttpResponseHandler);
    }

}
