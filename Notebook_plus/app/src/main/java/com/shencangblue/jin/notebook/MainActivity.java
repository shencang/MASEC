package com.shencangblue.jin.notebook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText themeET, contentET, dateET;
    private Button chooseDateBtn, addBtn, queryBtn;
    private ListView reslutLV;
    private LinearLayout titleLL;
    private MyDataBaseHelper myDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themeET = (EditText) findViewById(R.id.themeET);
        contentET = (EditText) findViewById(R.id.contentET);
        dateET = (EditText) findViewById(R.id.dateET);
        chooseDateBtn = (Button) findViewById(R.id.chooseDateBtn);
        addBtn = (Button) findViewById(R.id.addBtn);
        queryBtn = (Button) findViewById(R.id.queryBtn);
        reslutLV = (ListView) findViewById(R.id.resultLV);
        titleLL = (LinearLayout) findViewById(R.id.titleLL);


        chooseDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(
                        MainActivity.this
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        dateET.setText(String.format("%d-%d-%d", new Object[]{year, month, dayOfMonth}));
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        MyListener myListener = new MyListener();
        addBtn.setOnClickListener(myListener);
        queryBtn.setOnClickListener(myListener);

    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            myDataBaseHelper = new MyDataBaseHelper(
                    MainActivity.this
                    , "notepad.db"
                    , null
                    , 1);
            SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getReadableDatabase();
            String themeStr = themeET.getText().toString().trim();
            String contentStr = contentET.getText().toString().trim();
            String dateStr = dateET.getText().toString().trim();
            switch (v.getId()) {
                case R.id.addBtn: {
                    titleLL.setVisibility(View.INVISIBLE);
                    addData(sqLiteDatabase, themeStr, contentStr, dateStr);
                    Toast.makeText(MainActivity.this,
                            "添加成功", Toast.LENGTH_LONG).show();
                    reslutLV.setAdapter(null);


                    //额外添加

                    break;
                }
                case R.id.queryBtn: {
                    titleLL.setVisibility(View.VISIBLE);
                    Cursor cursor = queryData(sqLiteDatabase,themeStr, contentStr, dateStr);
                    SimpleCursorAdapter simpleCursorAdapter =
                            new SimpleCursorAdapter(
                                    MainActivity.this
                                    ,R.layout.list_panel
                                    ,cursor
                                    ,new String[]{"_id,theme,content,date"}
                                    ,new int[]{R.id.idTV,R.id.themeTV,R.id.contentTV,R.id.dateTV}
                            );
                    reslutLV.setAdapter(simpleCursorAdapter);

                    break;
                }
            }
        }

    }




//    private Cursor queryData(String themeStr, String contentStr, String dateStr) {
//
//    }

    private void addData(SQLiteDatabase sqLiteDatabase, String themeStr, String contentStr, String dateStr) {
        ContentValues values = new ContentValues();
        values.put("theme", themeStr);
        values.put("content", contentStr);
        values.put("date", dateStr);
        sqLiteDatabase.insert("notepad_tb", null, values);
    }

}

