package com.keanu1094859.mycheckins;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyCheckins {
    private static MyCheckins sMyCheckins;

    private List<Checkin> mCheckins;

    public static MyCheckins get(Context context) {
        if (sMyCheckins == null) {
            sMyCheckins = new MyCheckins(context);
        }
        return sMyCheckins;
    }

    private MyCheckins(Context context) {
        mCheckins = new ArrayList<>();
        // remove later
        for (int i = 0; i < 10; i++) {
            Checkin checkin = new Checkin();
            checkin.setmTitle("Checkin #" + i);
            mCheckins.add(checkin);
        }
    }

    public List<Checkin> getCheckins() {
        return mCheckins;
    }

    public Checkin getCheckin(UUID id) {
        for (Checkin checkin : mCheckins) {
            if (checkin.getId().equals(id)) {
                return checkin;
            }
        }

        return null;
    }
}
