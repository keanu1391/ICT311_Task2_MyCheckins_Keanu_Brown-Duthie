package com.keanu1094859.mycheckins;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keanu1094859.database.CheckinBaseHelper;
import com.keanu1094859.database.CheckinCursorWrapper;
import com.keanu1094859.database.CheckinDbSchema.CheckinTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyCheckins {
    private static MyCheckins sMyCheckins;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MyCheckins get(Context context) {
        if (sMyCheckins == null) {
            sMyCheckins = new MyCheckins(context);
        }
        return sMyCheckins;
    }

    private MyCheckins(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CheckinBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addCheckin(Checkin c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CheckinTable.NAME, null, values);
    }

    public List<Checkin> getCheckins() {
        List<Checkin> checkins = new ArrayList<>();

        CheckinCursorWrapper cursor = queryCheckins(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                checkins.add(cursor.getCheckin());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return checkins;
    }

    public Checkin getCheckin(UUID id) {
        CheckinCursorWrapper cursor = queryCheckins(
                CheckinTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCheckin();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Checkin checkin) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, checkin.getPhotoFilename());
    }

    public void updateCheckin(Checkin checkin) {
        String uuidString = checkin.getId().toString();
        ContentValues values = getContentValues(checkin);

        mDatabase.update(CheckinTable.NAME, values,
                CheckinTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private CheckinCursorWrapper queryCheckins(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CheckinTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CheckinCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Checkin checkin) {
        ContentValues values = new ContentValues();
        values.put(CheckinTable.Cols.UUID, checkin.getId().toString());
        values.put(CheckinTable.Cols.TITLE, checkin.getTitle());
        values.put(CheckinTable.Cols.DETAILS, checkin.getDetails());
        values.put(CheckinTable.Cols.PLACE, checkin.getPlace());
        values.put(CheckinTable.Cols.DATE, checkin.getDate().getTime());
        values.put(CheckinTable.Cols.LATITUDE, checkin.getLatitude());
        values.put(CheckinTable.Cols.LONGITUDE, checkin.getLongitude());

        return values;
    }
}
