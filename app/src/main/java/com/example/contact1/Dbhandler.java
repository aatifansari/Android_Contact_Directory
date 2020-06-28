package com.example.contact1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class Dbhandler extends SQLiteOpenHelper {

    private static final String DB_NAME="usersbd";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="userdetails";
    private static final String COL_1="id";
    private static final String COL_2="name";
    private static final String COL_3="pnum";

    public Dbhandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT,"
                + COL_3 + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion, int newversion){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertUserDetails( String name,String pnum){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, pnum);
        long newRowID=db.insert(TABLE_NAME,null,contentValues);
        db.close();

    }

    public ArrayList<HashMap<String, String>> getUsers(){
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userlist=new ArrayList<>();
        String query="SELECT name, pnum FROM "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        while(cursor.moveToNext()){
            HashMap<String,String> users=new HashMap<>();
            users.put("name",cursor.getString(cursor.getColumnIndex(COL_2)));
            users.put("pnum",cursor.getString(cursor.getColumnIndex(COL_3)));
            userlist.add(users);

        }
        return userlist;
    }
    public ArrayList<HashMap<String,String>> GetUserByUserId(int userid){
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<HashMap<String,String>> userlist=new ArrayList<>();
        String query="SELECT name, pnum FROM "+TABLE_NAME;
        Cursor cursor=db.query(TABLE_NAME, new String[]{COL_2, COL_3}, COL_1+"=?",new String[]{String.valueOf(userid)},
                null,null,null,null);
        if (cursor.moveToNext()){
            HashMap<String,String> users=new HashMap<>();
            users.put("name",cursor.getString(cursor.getColumnIndex(COL_2)));
            users.put("pnum",cursor.getString(cursor.getColumnIndex(COL_3)));
            userlist.add(users);
        }
        return userlist;
    }
    public void DeleteUser(int userid){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,COL_1+" = ? ",new String[]{String.valueOf(userid)});
        db.close();
    }
    public int UpdateUserDetails(String pnum,int id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_3,pnum);
        int count=db.update(TABLE_NAME, contentValues ,COL_1+" = ? ",new String[]{String.valueOf(id)});
        return count;
    }
}
