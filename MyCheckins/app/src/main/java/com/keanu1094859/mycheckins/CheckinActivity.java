package com.keanu1094859.mycheckins;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class CheckinActivity extends SingleFragmentActivity {

    public static final String EXTRA_CHECKIN_ID =
            "com.keanu1094859.mycheckins.checkin_id";

    public static Intent newIntent(Context packageContext, UUID checkinId) {
        Intent intent = new Intent(packageContext, CheckinActivity.class);
        intent.putExtra(EXTRA_CHECKIN_ID, checkinId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID checkinId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CHECKIN_ID);
        return CheckinFragment.newInstance(checkinId);
    }
}
