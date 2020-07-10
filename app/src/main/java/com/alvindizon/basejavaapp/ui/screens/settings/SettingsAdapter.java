package com.alvindizon.basejavaapp.ui.screens.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alvindizon.basejavaapp.R;

import java.util.ArrayList;
import java.util.List;


public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
    private final OnSettingsItemClickListener mListener;

    // Store a member variable for the list of settings' names
    private List<Integer> settingsNames;

    public SettingsAdapter(OnSettingsItemClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTextView = itemView.findViewById(R.id.settings_item_name);
        }
    }

    public void bindSettingsList(List<Integer> items) {
        settingsNames = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_settings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int settingName = settingsNames.get(position);
        viewHolder.mTextView.setText(settingName);

        viewHolder.itemView.setOnClickListener(v -> mListener.onItemClick(settingName));
    }

    @Override
    public int getItemCount() {
        return settingsNames.size();
    }

}
