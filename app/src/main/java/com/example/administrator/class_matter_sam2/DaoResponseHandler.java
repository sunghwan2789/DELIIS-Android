package com.example.administrator.class_matter_sam2;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-10-13.
 */

public abstract class DaoResponseHandler extends AsyncHttpResponseHandler {
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String jsonData = new String(responseBody);
        Log.i("test", "jsonData : " + jsonData);

        Article rtn = null;
        try {
            rtn = Article.fromJSONObject(new JSONObject(jsonData));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onSuccess(statusCode, headers, rtn);
    }

    public abstract void onSuccess(int statusCode, Header[] headers, Article article);

    @Override
    public boolean getUseSynchronousMode() {
        return false;
    }
}

