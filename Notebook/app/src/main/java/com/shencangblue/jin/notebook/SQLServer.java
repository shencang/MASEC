package com.shencangblue.jin.notebook;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class SQLServer {
    private static final String TABLE_NOTEPAD ="notepad_tb";

    private MyDataBaseHelper helper;
    public SQLServer(Context context) {
        helper=new MyDataBaseHelper(
                context
                ,TABLE_NOTEPAD
                , null
                , 1);
    }
    /**
     * 添加
     * @param note
     * @return
     */
    public boolean add(Note note){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("theme", note.getTheme());
        values.put("content", note.getContent());
        values.put("date",note.getDate());
        long insert = db.insert(TABLE_NOTEPAD, null, values);
        db.close();
        if(insert !=-1){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        SQLiteDatabase db = helper.getWritableDatabase();
//        db.execSQL("delete notepad_tb where id =?",new String[]{id});
        db.delete(TABLE_NOTEPAD, "id=?", new String[]{id});
        db.close();
    }
    /**
     * 修改
     * @param id
     * @param theme
     * @param content
     * @param date
     */
    public void update(String id ,String theme, String content ,String date){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update "+TABLE_NOTEPAD+" set theme=?,content=?,date=? where id=?",new Object[]{theme,content,date});
        db.close();
    }
    /**
     *查找ID
     * @param note
     * @return
     */
    public Note findName(Note note ){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NOTEPAD+" where id=?", new String[]{note.getId()});
        Note notes = new Note();
        while(cursor.moveToNext()){
//            String name = cursor.getString(1);
            notes.setTheme(note.getTheme());
            notes.setContent(note.getContent());
            notes.setDate(note.getDate());
        }
        cursor.close();
        db.close();
        return notes;
    }


    //查询所有学生
    public List<Note> findAll(){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Note> list =new ArrayList<Note>();
        Cursor cursor = db.query(TABLE_NOTEPAD
                , null
                , null
                , null
                , null
                , null
                , null);
        while(cursor.moveToNext()){
            //创建表对象
            Note note = new Note();
            String idStr = cursor.getString(1);
            String themeStr = cursor.getString(2);
            String contentStr = cursor.getString(3);
            String dateStr = cursor.getColumnName(4);
            //添加到记事bean里面
            note.setId(idStr);
            note.setTheme(themeStr);
            note.setContent(contentStr);
            note.setDate(dateStr);
            list.add(note);
        }
        db.close();
        cursor.close();
        return list;

    }
}