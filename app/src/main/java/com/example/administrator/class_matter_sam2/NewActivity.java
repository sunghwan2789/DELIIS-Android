package com.example.administrator.class_matter_sam2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class NewActivity extends AppCompatActivity implements OnClickListener{

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHOTO_ALBOM:
                if(grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_PHOTO_ALBOM);
                }
                break;
        }
    }

    private static final int REQUEST_PHOTO_ALBUM = 1;

    private EditText Name;
    private Spinner sp_Division;
    private CheckBox btn_Broken;
    private Spinner sp_Borrow;
    private EditText Date;
    private EditText Codename;
    private EditText Story;
    private ImageButton btn_Newimage;
    private Button btn_Save;
    public String taketype;
    public int artiNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        btn_Save = (Button)findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(this);
        Name = (EditText)findViewById(R.id.Name);
        sp_Division = (Spinner)findViewById(R.id.sp_Division);
        btn_Broken = (CheckBox)findViewById(R.id.btn_Broken);
        sp_Borrow = (Spinner)findViewById(R.id.sp_Borrow);
        Date = (EditText)findViewById(R.id.Date);
        Codename = (EditText)findViewById(R.id.Codename);
        Story = (EditText)findViewById(R.id.Story);
        btn_Newimage = (ImageButton)findViewById(R.id.btn_Newimage);
        btn_Newimage.setOnClickListener(this);


        if(getIntent().getExtras() != null) {
            String articleNumber = getIntent().getExtras().getString("number");
            artiNum = Integer.parseInt(articleNumber);

            Dao dao = new Dao(getApplicationContext());
            dao.getArticleByArticleNumber(Integer.parseInt(articleNumber), new DaoResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, Article article) {
                    Name.setText(article.getName());
                    //sp_Division.setText(article.getDivision());
                    Codename.setText(article.getCodename());
                    Story.setText(article.getContent());
                    Date.setText(article.getDate());
                    fileName=article.getImageOriginal();
                    if (article.getBroken() == 1) {
                        btn_Broken.setChecked(true);
                    } else {
                        btn_Broken.setChecked(false);
                    }

                    if (article.getBorrow().equals("OUT")) {
                        Log.i("test", "대여");
                        sp_Borrow.setSelection(0);
                    } else if (article.getBorrow().equals("IN")) {
                        Log.i("test", "대여받음");
                        sp_Borrow.setSelection(1);
                    } else {
                        Log.i("test", "없음");
                        sp_Borrow.setSelection(2);
                    }

                    Log.i("test", "getView: " + article.getImgName());
                    Picasso.with(NewActivity.this)
                            .load(article.getImgName())
                            .placeholder(android.R.drawable.sym_def_app_icon)
                            .into(btn_Newimage);
                }
            });
        }
    }

    private String filePath;
    private String fileName;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(requestCode ==REQUEST_PHOTO_ALBOM) {
                Uri uri = getRealPathUri(data.getData());
                filePath = uri.toString();
                fileName = uri.getLastPathSegment();

                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                btn_Newimage.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            Log.e("test", "onActivityResult ERROR : " + e);
        }
    }

    private Uri getRealPathUri(Uri uri) {
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null,null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePathUri = Uri.parse(cursor.getString(column_index));
            }
        }
        return filePathUri;
    }

    private static final int REQUEST_PHOTO_ALBOM = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 7;

    private ProgressDialog progressDialog;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Newimage: {
                ActivityCompat.requestPermissions(NewActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PHOTO_ALBUM);
                break;
            }

            case R.id.btn_Save: {
                final Handler handler = new Handler();

                progressDialog = ProgressDialog.show(this, "", "업로드 중입니다.");
                /*String ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA).format(new Date());*/

                if (sp_Borrow.getSelectedItem().equals("대여")) {
                    Log.i("test", "OUT");
                    taketype="OUT";
                } else if (sp_Borrow.getSelectedItem().equals("대여받음")) {
                    Log.i("test", "대여받음");
                    taketype = "IN";
                } else {
                    Log.i("test", "없음");
                    taketype = "NO";
                }

                Article article = new Article(0,
                        Name.getText().toString(),
                        sp_Division.getSelectedItem().toString(),
                        btn_Broken.isChecked() ? 1 : 0,
                        Codename.getText().toString(),
                        Story.getText().toString(),
                        taketype.toString(),
                        Date.getText().toString(),
                        fileName);
                Log.i("TAG", "onClick: " + article.toString());
                if(artiNum == 0) {
                    ProxyUp.uploadArticle(article, filePath, new AsyncHttpResponseHandler() {
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

                        @Override
                        public boolean getUseSynchronousMode() {
                            return false;
                        }
                    });
                } else {
                    ProxyUp.ReloadArticle(article, filePath, new AsyncHttpResponseHandler() {
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

                        @Override
                        public boolean getUseSynchronousMode() {
                            return false;
                        }
                    }, artiNum);
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("작성을 취소하시겠습니까?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewActivity.this.finish();
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
