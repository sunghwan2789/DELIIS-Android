package com.example.administrator.class_matter_sam2;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016-10-13.
 */

public class ProxyUp {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void uploadArticle(Article article, String filePath, AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.setForceMultipartEntityContentType(true);
        params.put("name", article.getName());
        params.put("division", article.getDivision());
        params.put("broken", article.getBroken());
        params.put("codename", article.getCodename());
        params.put("content", article.getContent());
        params.put("takeType", article.getBorrow());
        params.put("takePeriod", article.getDate());

        params.put("image", article.getImageOriginal());

        try {
            params.put("imageFile", new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        client.post("http://bloodcat.com:5844/upload/", params, responseHandler);
    }

    public static void ReloadArticle(Article article, String filePath, AsyncHttpResponseHandler responseHandler, int arnum) {
        RequestParams params = new RequestParams();
        params.setForceMultipartEntityContentType(true);
        params.put("name", article.getName());
        params.put("division", article.getDivision());
        params.put("broken", article.getBroken());
        params.put("codename", article.getCodename());
        params.put("content", article.getContent());
        params.put("takeType", article.getBorrow());
        params.put("takePeriod", article.getDate());

        params.put("image", article.getImageOriginal());


        try {
            params.put("imageFile", new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        client.put("http://bloodcat.com:5844/item/" + arnum, params, responseHandler);
    }
}
