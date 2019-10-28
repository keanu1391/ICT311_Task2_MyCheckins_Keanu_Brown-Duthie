package com.keanu1094859.mycheckins;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CheckinFragment extends Fragment {

    private static final String ARG_CHECKIN_ID = "checkin_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,};

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_PERMISSIONS = 0;

    private Checkin mCheckin;
    private File mPhotoFile;

    private EditText mTitleField;
    private EditText mPlaceField;
    private EditText mDetailsField;
    private TextView mLocationView;

    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    private Button mDateButton;
    private Button mShareButton;
    private Button mShowMapButton;
    private Button mDeleteButton;

    private GoogleApiClient mGoogleApiClient;
    public LocationListener mLocationListener;

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

        setHasOptionsMenu(true);
        UUID checkinId = (UUID) getArguments().getSerializable(ARG_CHECKIN_ID);

        mCheckin = MyCheckins.get(getActivity()).getCheckin(checkinId);
        mPhotoFile = MyCheckins.get(getActivity()).getPhotoFile(mCheckin);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mCheckin.setLatitude(location.getLatitude());
                mCheckin.setLongitude(location.getLongitude());
                mLocationView.setText(
                        String.format(
                                "Latitude: %s     Longitude: %s",
                                mCheckin.getLatitude(),
                                mCheckin.getLongitude()
                        )
                );
            }
        };

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

                    @Override
                    public void onConnected(@Nullable final Bundle bundle) {
                        final LocationRequest request = LocationRequest.create();

                        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        request.setNumUpdates(1);
                        request.setInterval(0);
                        requestPermissions(LOCATION_PERMISSIONS, REQUEST_PERMISSIONS);

                        if (ContextCompat.checkSelfPermission(
                                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }

                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, mLocationListener);
                    }

                    @Override
                    public void onConnectionSuspended(int i) { }

                })
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }


    @Override
    public void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();

        MyCheckins.get(getActivity()).updateCheckin(mCheckin);
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
                mCheckin.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mPlaceField = v.findViewById(R.id.checkin_place);
        mPlaceField.setText(mCheckin.getPlace());
        mPlaceField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCheckin.setPlace(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mDetailsField = v.findViewById(R.id.checkin_details);
        mDetailsField.setText(mCheckin.getDetails());
        mDetailsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCheckin.setDetails(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mDateButton = v.findViewById(R.id.checkin_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCheckin.getDate());
                dialog.setTargetFragment(CheckinFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mShareButton = v.findViewById(R.id.btn_share);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCheckinSummary());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.checkin_summary));
                i = Intent.createChooser(i, getString(R.string.checkin_send_summary));
                startActivity(i);
            }
        });

        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = v.findViewById(R.id.checkin_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.keanu1094859.mycheckins.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = v.findViewById(R.id.checkin_photo);
        updatePhotoView();

        mLocationView = v.findViewById(R.id.checkin_location);

        final Intent showMap = new Intent(getActivity(), CheckinMapsActivity.class);
        mShowMapButton = v.findViewById(R.id.btn_show_map);
        mShowMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showMap.putExtra("Latitude", mCheckin.getLatitude());
                showMap.putExtra("Longitude", mCheckin.getLongitude());
                startActivity(showMap);
            }
        });

        if (packageManager.resolveActivity(showMap,
                PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mShowMapButton.setEnabled(false);
        }

        mDeleteButton = v.findViewById(R.id.btn_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyCheckins.get(getActivity()).deleteCheckIn(mCheckin.getId());
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {

            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCheckin.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.keanu1094859.mycheckins.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }

    private void updateDate() {
        mDateButton.setText(mCheckin.getFormattedDate(null));
    }

    private String getCheckinSummary() {
        String summary = getString(
                R.string.checkin_summary,
                mCheckin.getTitle(),
                "\n",
                mCheckin.getFormattedDate(null),
                mCheckin.getPlace(),
                mCheckin.getDetails()
        );

        return summary;
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
