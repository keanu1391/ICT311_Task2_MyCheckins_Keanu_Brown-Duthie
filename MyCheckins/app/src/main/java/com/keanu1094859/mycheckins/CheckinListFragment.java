package com.keanu1094859.mycheckins;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CheckinListFragment extends Fragment {

    private RecyclerView mCheckinRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkin_list, container, false);

        mCheckinRecyclerView = view.findViewById(R.id.checkin_recycler_view);
        mCheckinRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private class CheckinHolder extends RecyclerView.ViewHolder {
        public CheckinHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_checkin, parent, false));
        }
    }
}
