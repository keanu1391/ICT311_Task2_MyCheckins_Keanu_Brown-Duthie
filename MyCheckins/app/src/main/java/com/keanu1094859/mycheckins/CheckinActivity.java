package com.keanu1094859.mycheckins;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.UUID;

public class CheckinActivity extends SingleFragmentActivity {

    public static final String EXTRA_CHECKIN_ID = "com.keanu1094859.mycheckins.checkin_id";
    private static final int REQUEST_ERROR = 0;

    public static Intent newIntent(Context packageContext, UUID checkinId) {
        Intent intent = new Intent(packageContext, CheckinActivity.class);
        intent.putExtra(EXTRA_CHECKIN_ID, checkinId);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID checkinId = (UUID) getIntent().getSerializableExtra(EXTRA_CHECKIN_ID);

        return CheckinFragment.newInstance(checkinId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode, REQUEST_ERROR,
                    new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }

                    });
            errorDialog.show();
        }
    }
}
