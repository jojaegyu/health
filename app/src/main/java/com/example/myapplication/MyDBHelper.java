package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {


    public MyDBHelper(Context context){
        super(context,"groupDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create table groupTBL before");
        db.execSQL("CREATE TABLE groupTBL ( gName char(20),gInt char(20));");
        db.execSQL("CREATE TABLE exercise ( gName char(20));");
        db.execSQL("CREATE TABLE routine ( gName char(20));");
        db.execSQL("CREATE TABLE routineInfo ( routineName char(20), exerciseName char(20),gNumber int, gSet int, gWeight);");
        db.execSQL("CREATE TABLE schedule (scheduleName char(20),startTime datetime,endTime datetime);");
        db.execSQL("CREATE TABLE calendar_schedule (gDATE date, gScheduleName char(20), gStartTime time, gendTime time);");
        db.execSQL("CREATE TABLE calendar_routine (gDATE date, gRoutineName char(20));");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS groupTBL");
        db.execSQL("DROP TABLE IF EXISTS exercise");
        db.execSQL("DROP TABLE IF EXISTS routine");
        db.execSQL("DROP TABLE IF EXISTS routineInfo");
        db.execSQL("DROP TABLE IF EXISTS schedule");
        db.execSQL("DROP TABLE IF EXISTS calendar_schedule");
        db.execSQL("DROP TABLE IF EXISTS calendar_routine");
        onCreate(db);
    }
}
