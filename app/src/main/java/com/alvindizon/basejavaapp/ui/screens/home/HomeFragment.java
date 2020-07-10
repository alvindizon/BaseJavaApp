package com.alvindizon.basejavaapp.ui.screens.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alvindizon.basejavaapp.R;
import com.alvindizon.basejavaapp.databinding.FragmentHomeBinding;
import com.alvindizon.basejavaapp.ui.common.BaseViewBindingFragment;

public class HomeFragment extends BaseViewBindingFragment<FragmentHomeBinding> {

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    protected FragmentHomeBinding bindLayout(View view) {
        return FragmentHomeBinding.bind(view);
    }

    @Override
    protected void performDependencyInjection() {
        // TODO add inject method to ActivityComponent as needed
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.includeToolbar.toolbarTitle.setText(R.string.title_home);
        binding.includeToolbar.toolbar.inflateMenu(R.menu.menu_settings);
        binding.includeToolbar.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_settings:
                    getNavController(this).navigate(R.id.action_home_dest_to_settings_menu_dest);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        });
    }

}
