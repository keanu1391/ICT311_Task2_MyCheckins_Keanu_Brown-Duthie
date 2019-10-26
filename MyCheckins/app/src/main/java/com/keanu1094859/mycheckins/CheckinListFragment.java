package com.keanu1094859.mycheckins;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckinListFragment extends Fragment {

    private RecyclerView mCheckinRecyclerView;
    private CheckinAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkin_list, container, false);

        mCheckinRecyclerView = view.findViewById(R.id.checkin_recycler_view);
        mCheckinRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_checkin_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.new_checkin:
                Checkin checkin = new Checkin();
                MyCheckins.get(getActivity()).addCheckin(checkin);
                Intent newIntent = CheckinActivity.newIntent(getActivity(), checkin.getId());
                startActivity(newIntent);
                return true;
            case R.id.help_checkin:
//                * Get Working if time *
//                Intent helpIntent = CheckinHelpActivity.newIntent();
//                startActivity(helpIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        MyCheckins myCheckins = MyCheckins.get(getActivity());
        List<Checkin> checkins = myCheckins.getCheckins();

        if (mAdapter == null) {
            mAdapter = new CheckinAdapter(checkins);
            mCheckinRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCheckins(checkins);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CheckinHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        private Checkin mCheckin;
        private TextView mTitleTextView;
        private TextView mPlaceTextView;
        private TextView mDateTextView;

        public CheckinHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_checkin, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.checkin_title);
            mPlaceTextView = itemView.findViewById(R.id.checkin_place);
            mDateTextView = itemView.findViewById(R.id.checkin_date);
        }

        public void bind(Checkin checkin) {
            mCheckin = checkin;
            mTitleTextView.setText(mCheckin.getTitle());
            mPlaceTextView.setText(mCheckin.getPlace());
            mDateTextView.setText(mCheckin.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = CheckinActivity.newIntent(getActivity(), mCheckin.getId());
            startActivity(intent);
        }
    }

    private class CheckinAdapter extends RecyclerView.Adapter<CheckinHolder> {

        private List<Checkin> mCheckins;

        public CheckinAdapter(List<Checkin> checkins) {
            mCheckins = checkins;
        }

        @Override
        public CheckinHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CheckinHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CheckinHolder holder, int position){
            Checkin checkin = mCheckins.get(position);
            holder.bind(checkin);
        }

        @Override
        public int getItemCount() {
            return mCheckins.size();
        }

        public void setCheckins(List<Checkin> checkins) {
            mCheckins = checkins;
        }
    }
}
