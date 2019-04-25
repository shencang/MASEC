package com.shencangblue.jin.notebooks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDataBaseHelper";
    final String CREATE_TABLE_SQL ="create table notepad_tb(_id integer primary key autoincrement,theme,content,date)";
    public MyDataBaseHelper(
            @Nullable Context context,
            @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"onUpgrade:"+"-----------"+oldVersion+"-------------"+newVersion);

    }
}
