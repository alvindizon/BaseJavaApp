package com.alvindizon.basejavaapp.ui.screens.settings;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alvindizon.basejavaapp.R;
import com.alvindizon.basejavaapp.databinding.FragmentSettingsMenuBinding;
import com.alvindizon.basejavaapp.di.Injector;
import com.alvindizon.basejavaapp.ui.common.BaseDataBindingFragment;
import com.alvindizon.basejavaapp.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class SettingsMenuFragment extends BaseDataBindingFragment<FragmentSettingsMenuBinding>
        implements OnSettingsItemClickListener {

    private List<Integer> mSettingsItems;
    private SettingsViewModel viewModel;
    // TODO add more settings menu items here
    private Integer[] settingsCategories = {
            R.string.settings_comm
    };

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void performDependencyInjection() {
        Injector.getActivityComponent(requireActivity()).inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings_menu;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(SettingsViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSettingsItems = getSettingsNames();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.includeToolbar.toolbarTitle.setText(R.string.title_settings);
        binding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_back);
        // go back to previous screen on backpress/ up icon press
        // TODO study if there's a way to specify this behavior in the nav graph xml
        binding.includeToolbar.toolbar.setNavigationOnClickListener(v -> getNavController(view).navigateUp());
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getNavController(SettingsMenuFragment.this).navigateUp();
            }
        });

        mSettingsItems = getSettingsNames();
        initMenu();
    }

    @Override
    public void onItemClick(int settingName) {
        // TODO add cases for additional categories as needed
        switch (settingName) {
            case R.string.settings_comm:
                getNavController(this).navigate(R.id.action_settings_menu_dest_to_comm_settings_dest);
                break;
            default:
                break;
        }
    }

    private void initMenu() {
        SettingsAdapter mAdapter = new SettingsAdapter(this);
        mAdapter.bindSettingsList(mSettingsItems);
        RecyclerView mRecyclerView = getView().findViewById(R.id.rv_settings_main);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.recycler_horizontal_bottom_border));
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private ArrayList<Integer> getSettingsNames() {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, settingsCategories);
        return list;
    }

}
