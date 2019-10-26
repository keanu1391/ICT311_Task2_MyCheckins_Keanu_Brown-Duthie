package com.keanu1094859.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.keanu1094859.database.CheckinDbSchema.CheckinTable;
import com.keanu1094859.mycheckins.Checkin;

import java.util.Date;
import java.util.UUID;

public class CheckinCursorWrapper extends CursorWrapper {
    public CheckinCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Checkin getCheckin() {
        String uuidString = getString(getColumnIndex(CheckinTable.Cols.UUID));
        String title = getString(getColumnIndex(CheckinTable.Cols.TITLE));
        String details = getString(getColumnIndex(CheckinTable.Cols.DETAILS));
        String place = getString(getColumnIndex(CheckinTable.Cols.PLACE));
        Long date = getLong(getColumnIndex(CheckinTable.Cols.DATE));

        Checkin checkin = new Checkin(UUID.fromString(uuidString));
        checkin.setTitle(title);
        checkin.setDetails(details);
        checkin.setPlace(place);
        checkin.setDate(new Date(date));

        return checkin;
    }
}
