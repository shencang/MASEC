package com.shencangblue.jin.accesscontentdemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private EditText nameET,phoneNameET;
    private Button addBtn,queryBtn;
    private LinearLayout titleLL;
    private ListView contontLv;
    private MyListener myListener;
    private ContentResolver contentResolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET=(EditText)findViewById(R.id.nameET);
        phoneNameET=(EditText)findViewById(R.id.phoneNumberET);
        addBtn= (Button)findViewById(R.id.addBtn);
        queryBtn =(Button)findViewById(R.id.queryBtn);
        titleLL =(LinearLayout)findViewById(R.id.titleLL);
        contontLv = (ListView)findViewById(R.id.contentsLV);

        titleLL.setVerticalGravity(View.INVISIBLE);
        myListener = new MyListener();
        addBtn.setOnClickListener(myListener);
        queryBtn.setOnClickListener(myListener);

        contentResolver = getContentResolver();


    }

    private class MyListener implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.addBtn:{
                    if (ContextCompat.checkSelfPermission(MainActivity.this
                            , Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.WRITE_CONTACTS},
                                1);
                    } else {

                      addContents();
                    }
                    break;
                }
                case R.id.queryBtn:{
                    if (ContextCompat.checkSelfPermission(MainActivity.this
                            , Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                2);
                    } else {

                            titleLL.setVerticalGravity(View.VISIBLE);
                            ArrayList<Map<String,String>> persons = queryContents();
                            SimpleAdapter simpleAdapter = new SimpleAdapter(
                                    MainActivity.this,
                                    persons,
                                    R.layout.result,
                                    new String[]{"id","name","phoneNumber"},
                                    new int[]{R.id.idTv,R.id.nameTv,R.id.phoneNumberTv}
                                    );
                            contontLv.setAdapter(simpleAdapter);
                    }

                    break;
                }
                default:break;
            }
        }
    }

    private ArrayList<Map<String, String>> queryContents() {

    }


    private void addContents() {

        String nameStr = nameET.getText().toString().trim();
        String numberStr = phoneNameET.getText().toString().trim();

        ContentValues contentValues  = new ContentValues();
        Uri rawContactUri = contentResolver
                .insert(ContactsContract
                        .RawContacts.CONTENT_URI,contentValues);
        long contactId = ContentUris.parseId(rawContactUri);
        contentValues.clear();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID,contactId);
        contentValues.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,nameStr);
     //   contentValues.put(ContactsContract.CommonDataKinds.StructuredName.HAS_PHONE_NUMBER,numberStr);
        contentResolver.insert(ContactsContract.Data.CONTENT_URI,contentValues);
        contentValues.clear();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID,contactId);
        contentValues.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER,numberStr);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        contentResolver.insert(ContactsContract.Data.CONTENT_URI,contentValues);
        Toast.makeText(MainActivity.this,"添加联系人成功",Toast.LENGTH_LONG).show();

    }
}
