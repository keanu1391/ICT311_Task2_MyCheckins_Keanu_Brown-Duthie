package com.keanu1094859.mycheckins;

import androidx.fragment.app.Fragment;

public class CheckinActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CheckinFragment();
    }
}
