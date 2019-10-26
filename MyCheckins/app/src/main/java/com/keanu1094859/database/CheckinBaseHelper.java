package com.keanu1094859.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.keanu1094859.database.CheckinDbSchema.CheckinTable;

public class CheckinBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "checkinBase.db";

    public CheckinBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CheckinTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CheckinTable.Cols.UUID + ", " +
                CheckinTable.Cols.TITLE + ", " +
                CheckinTable.Cols.DATE + ", " +
                CheckinTable.Cols.PLACE + ", " +
                CheckinTable.Cols.DETAILS + ", " +
                CheckinTable.Cols.LATITUDE + ", " +
                CheckinTable.Cols.LONGITUDE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
