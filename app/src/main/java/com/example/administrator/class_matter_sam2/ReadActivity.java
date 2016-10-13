package com.example.administrator.class_matter_sam2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import cz.msebera.android.httpclient.Header;

import static com.example.administrator.class_matter_sam2.R.id.image_view;
import static com.example.administrator.class_matter_sam2.R.id.read_Image;

public class ReadActivity extends AppCompatActivity implements OnClickListener{
    private ImageView Read_image;
    private TextView Name;
    private TextView read_Division;
    private CheckBox btn_Broken;
    private TextView Borrow;
    private TextView read_Date;
    private TextView Codename;
    private TextView Story;
    public  String imgeName;

    //private ArrayList<Article> articleList;
    public int articleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        Read_image = (ImageView) findViewById(read_Image);
        Read_image.setOnClickListener(this);
        Name = (TextView) findViewById(R.id.Name);
        read_Division = (TextView) findViewById(R.id.read_Division);
        btn_Broken = (CheckBox) findViewById(R.id.btn_Broken);
        Borrow = (TextView) findViewById(R.id.Borrow);
        read_Date = (TextView) findViewById(R.id.read_Date);
        Codename = (TextView) findViewById(R.id.Codename);
        Story = (TextView) findViewById(R.id.Story);

        articleNumber = getIntent().getExtras().getInt("number");

        starter();

    }

    public void starter() {

        Dao dao = new Dao(getApplicationContext());
        dao.getArticleByArticleNumber(articleNumber, new DaoResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, Article article) {
                Name.setText(article.getName());
                read_Division.setText(article.getDivision());
                Codename.setText(article.getCodename());
                Story.setText(article.getContent());
                read_Date.setText(article.getDate());

                if(article.getBroken()==1) {
                    btn_Broken.setChecked(true);
                } else {
                    btn_Broken.setChecked(false);
                }

                if (article.getBorrow().equals("OUT")) {
                    Log.i("test", "대여");
                    Borrow.setText("대여");
                } else if (article.getBorrow().equals("IN")) {
                    Log.i("test", "대여받음");
                    Borrow.setText("대여받음");
                } else {
                    Log.i("test","없음");
                    Borrow.setText(" ");
                }

                imgeName = article.getImgName();
                Log.i("test", "getView: " + article.getImgName());
                Picasso.with(ReadActivity.this)
                        .load(imgeName)
                        .placeholder(android.R.drawable.sym_def_app_icon)
                        .into(Read_image);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        starter();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_menu, menu);
        Log.d("test", "onCreateOptionsMenu - 최초 메뉴키를 눌렀을 때 호출됨");
        return super.onCreateOptionsMenu(menu);
    }

    private ProgressDialog progressDialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴의 항목을 선택(클릭)했을 때 호출되는 콜백메서드
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("test", "onOptionsItemSelected - 메뉴항목을 클릭했을 때 호출됨");

        switch(item.getItemId()) {
            case R.id.brn_Re:
                Log.i("err", "put redata");
                Intent intent = new Intent(this, NewActivity.class);
                intent.putExtra("number", articleNumber + "");
                startActivity(intent);
                break;
            case R.id.btn_del:
                deletdata();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_Image:
                Intent intent = new Intent(this, ImageViewActivity.class);
                intent.putExtra("image", imgeName);
                startActivity(intent);
                break;
        }
    }


    public void deletdata() {
        new AlertDialog.Builder(this)
                .setMessage("데이터를 삭제하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = ProgressDialog.show(ReadActivity.this, "", "서버 갱신 중...");
                        Log.i("test", "delete data");
                        Dao dao =new Dao(ReadActivity.this);
                        dao.delArticleByArticleNumber(articleNumber, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Log.e("TAG", "onSuccess: " + statusCode);
                                progressDialog.cancel();
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Log.e("TAG", "onFailure: " + statusCode);
                                progressDialog.cancel();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
