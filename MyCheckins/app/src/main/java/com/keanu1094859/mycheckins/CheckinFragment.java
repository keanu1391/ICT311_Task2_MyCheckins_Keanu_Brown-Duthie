package com.keanu1094859.mycheckins;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class CheckinFragment extends Fragment {

    private static final String ARG_CHECKIN_ID = "checkin_id";

    private Checkin mCheckin;
    private EditText mTitleField;
    private Button mDateButton;

    public static CheckinFragment newInstance(UUID checkinId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHECKIN_ID, checkinId);

        CheckinFragment fragment = new CheckinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID checkinId = (UUID) getArguments().getSerializable(ARG_CHECKIN_ID);
        mCheckin = MyCheckins.get(getActivity()).getCheckin(checkinId);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle saveInstanceState
    ) {
        View v = inflater.inflate(R.layout.fragment_checkin, container, false);

        mTitleField = v.findViewById(R.id.checkin_title);
        mTitleField.setText(mCheckin.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCheckin.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mDateButton = v.findViewById(R.id.checkin_date);
        mDateButton.setText(mCheckin.getDate().toString());
        mDateButton.setEnabled(false);

        return v;
    }
}
