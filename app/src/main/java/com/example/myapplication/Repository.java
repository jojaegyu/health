package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Exercise.Exercise;
import com.example.myapplication.Routine.Routine;
import com.example.myapplication.RoutineInfo.RoutineInfo;
import com.example.myapplication.Schedule.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Repository {


    public void init(MyDBHelper myDBHelper){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        myDBHelper.onUpgrade(sqlDB,1,2);
        sqlDB.close();
    }

    public void insert_exercise(MyDBHelper myDBHelper, Exercise exercise){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO exercise (gName) VALUES ('" + exercise.getName().toString() + "');");
        sqlDB.close();
    }

    public ArrayList<Exercise> select_exercise(MyDBHelper myDBHelper){
        ArrayList<Exercise> tmp = new ArrayList<Exercise>();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM exercise;",null);

        while (cursor.moveToNext()){
            tmp.add(new Exercise(cursor.getString(0)));
        }

        cursor.close();
        sqlDB.close();

        return tmp;
    }

    public ArrayList<Exercise> search_exercise(MyDBHelper myDBHelper, String keyword){
        ArrayList<Exercise> tmp = new ArrayList<Exercise>();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM exercise WHERE gName = '" + keyword + "';",null);

        while (cursor.moveToNext()){
            tmp.add(new Exercise(cursor.getString(0)));
        }

        cursor.close();
        sqlDB.close();

        return tmp;
    }

    public void update_exercise(MyDBHelper myDBHelper,Exercise old_exercise,Exercise new_exercise){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("update exercise set gName = '" + new_exercise.getName() + "' where gName = " + "'" + old_exercise.getName() + "';");
        sqlDB.close();
    }

    public void delete_exercise(MyDBHelper myDBHelper,Exercise exercise){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("DELETE FROM exercise where gName = '" + exercise.getName() + "'");
        sqlDB.close();
    }

    public void insert_routine(MyDBHelper myDBHelper, Routine routine){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO routine (gName) VALUES ('" + routine.getName().toString() + "');");
        sqlDB.close();
    }

    public ArrayList<Routine> select_routine(MyDBHelper myDBHelper){
        ArrayList<Routine> tmp = new ArrayList<Routine>();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM routine;",null);

        while (cursor.moveToNext()){
            tmp.add(new Routine(cursor.getString(0)));
        }

        cursor.close();
        sqlDB.close();

        return tmp;
    }

    public void update_routine(MyDBHelper myDBHelper,Routine old_routine, Routine new_routine){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("update exercise set gName = '" + old_routine.getName() + "' where gName = " + "'" + new_routine.getName() + "';");
        sqlDB.close();
    }

    public void delete_routine(MyDBHelper myDBHelper,Routine routine){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("DELETE FROM exercise where gName = '" + routine.getName() + "'");
        sqlDB.close();
    }

    public void insert_routineInfo(MyDBHelper myDBHelper,RoutineInfo routineInfo){
        routineInfo.print();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        String query = String.format("INSERT INTO routineInfo (routineName,exerciseName,gNumber,gSet,gWeight) " +
                        "VALUES(\"%s\",\"%s\",%d,%d,%d);",routineInfo.getRoutineName(),
                routineInfo.getExerciseName(),routineInfo.getNumber(),routineInfo.getSet(),routineInfo.getWeight());
        System.out.println(query);
        sqlDB.execSQL(query);
        sqlDB.close();
    }

    public ArrayList<RoutineInfo> select_routineInfo(MyDBHelper myDBHelper, String routineName){
        ArrayList<RoutineInfo> tmp = new ArrayList<RoutineInfo>();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM routineInfo WHERE routineName = '" + routineName + "';", null);
        } catch (Exception e){
            return tmp;
        }
        while (cursor.moveToNext()){
            tmp.add(new RoutineInfo(cursor.getString(0)
                    ,cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            ));
        }

        cursor.close();
        sqlDB.close();

        return tmp;
    }

    public void insert_schedule(MyDBHelper myDBHelper, Schedule schedule){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL(String.format("INSERT INTO schedule (scheduleName, startTime, endTIme) VALUES('%s','%s','%s')",
                schedule.getScheduleName()
                ,schedule.getStartTime().toString()
                ,schedule.getEndTime().toString()));
        sqlDB.close();
    }

    public ArrayList<Schedule> select_schedule(MyDBHelper myDBHelper){
        ArrayList<Schedule> tmp = new ArrayList<Schedule>();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM schedule;", null);
        } catch (Exception e){
            return tmp;
        }

        while (cursor.moveToNext()){
            System.out.println(cursor.getString(0));
            System.out.println(cursor.getString(1));
            System.out.println(cursor.getString(2));

            tmp.add(new Schedule(cursor.getString(0)
                    ,LocalTime.parse(cursor.getString(1))
                    ,LocalTime.parse(cursor.getString(2))
            ));
        }

        cursor.close();
        sqlDB.close();

        return tmp;
    }

    public void update_Schedule(MyDBHelper myDBHelper, Schedule new_schedule,Schedule old_schedule){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("UPDATE Schedule SET scheduleName = '" + new_schedule.getScheduleName()
                + "', startTime = '" + new_schedule.getStartTime().toString()
                + "', endTime = '" + new_schedule.getEndTime().toString()
                + "' WHERE scheduleName = '" + old_schedule.getScheduleName() + "';");
        sqlDB.close();
    }

    public void delete_Schedule(MyDBHelper myDBHelper, Schedule schedule){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("DELETE FROM schedule where scheduleName = '" + schedule.getScheduleName() + "';");
        sqlDB.close();
    }

    public ArrayList<Schedule> search_Schedule(MyDBHelper myDBHelper, String keyword){
        ArrayList<Schedule> tmp = new ArrayList<Schedule>();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM schedule WHERE scheduleName LIKE '%" + keyword + "%';", null);
        } catch (Exception e){
            return tmp;
        }

        while (cursor.moveToNext()){
            System.out.println(cursor.getString(0));
            System.out.println(cursor.getString(1));
            System.out.println(cursor.getString(2));

            tmp.add(new Schedule(cursor.getString(0)
                    ,LocalTime.parse(cursor.getString(1))
                    ,LocalTime.parse(cursor.getString(2))
            ));
        }

        cursor.close();
        sqlDB.close();

        return tmp;
    }

    public void insert_calendar_routine(MyDBHelper myDBHelper, Routine routine, LocalDate localDate){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO calendar_routine (gRoutineName,gDATE) VALUES ('" + routine.getName().toString() + "', '" + localDate.toString() + "');");
        sqlDB.close();

    }

    public ArrayList<Routine> select_calendar_routine(MyDBHelper myDBHelper, LocalDate localDate){
        ArrayList<Routine> tmp = new ArrayList<Routine>();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM calendar_routine WHERE gDATE = '" + localDate.toString() + "'",null);

        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            System.out.println(name);
            tmp.add(new Routine(name));
        }

        cursor.close();
        sqlDB.close();

        return tmp;
    }

    public void insert_calendar_schedule(MyDBHelper myDBHelper, Schedule schedule, LocalDate localDate){
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO calendar_schedule (gScheduleName,gDATE,gStartTime,gendTime) VALUES ('"
                + schedule.getScheduleName().toString()
                + "', '" + localDate.toString() + "'"
                + ", '" + schedule.getStartTime().toString() + "',"
                + "'" + schedule.getEndTime().toString() + "');");
        sqlDB.close();
    }

    public ArrayList<Schedule> select_calendar_schedule(MyDBHelper myDBHelper, LocalDate localDate){
        ArrayList<Schedule> tmp = new ArrayList<Schedule>();
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        try {
            cursor = sqlDB.rawQuery("SELECT * FROM calendar_schedule WHERE gDATE = '" + localDate.toString() + "';", null);
        } catch (Exception e){
            return tmp;
        }

        while (cursor.moveToNext()){
            System.out.println(cursor.getString(0));
            System.out.println(cursor.getString(1));
            System.out.println(cursor.getString(2));

            tmp.add(new Schedule(
                    cursor.getString(1)
                    ,LocalTime.parse(cursor.getString(2))
                    ,LocalTime.parse(cursor.getString(3))
            ));
        }

        cursor.close();
        sqlDB.close();

        return tmp;

    }

}
