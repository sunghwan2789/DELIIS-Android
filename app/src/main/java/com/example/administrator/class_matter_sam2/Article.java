package com.example.administrator.class_matter_sam2;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-10-12.
 */

public class Article {

    private int articleNumber;
    private String Name;
    private String Division;
    private int Broken;
    private String Codename;
    private String content;
    private String Date;
    private String Borrow;
    private String imgName;

    public Article(int number, String name, String division, int broken, String codename, String content,String takeType, String takePeriod, String imgName) {
        this.articleNumber = number;
        this.Name = name;
        this.Division = division;
        this.Broken = broken;
        this.Codename = codename;
        this.content = content;
        this.Borrow = takeType;
        this.Date = takePeriod;
        this.imgName = imgName;
    }

    public static Article fromJSONObject(JSONObject jobj) throws JSONException {
        return new Article(jobj.getInt("number"),
                            jobj.getString("name"),
                            jobj.getString("division"),
                            jobj.getInt("broken"),
                            jobj.getString("codename"),
                            jobj.getString("content"),
                            jobj.getString("takeType"),
                            jobj.getString("takePeriod"),
                            jobj.getString("image"));
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public String getName() {
        return Name;
    }

    public String getDivision() {
        return Division;
    }

    public int getBroken() {
        return Broken;
    }

    public String getCodename() {
        return Codename;
    }

    public String getContent() {
        return content;
    }

    public String getBorrow() {
        return Borrow;
    }

    public String getDate() {
        return Date;
    }

    public String getImgName() {
        return "http://bloodcat.com:5844/image/" + imgName;
    }

    public String getImageOriginal() {
        return imgName;
    }
}

