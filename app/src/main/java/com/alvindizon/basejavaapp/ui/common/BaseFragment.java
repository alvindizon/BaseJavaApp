package com.alvindizon.basejavaapp.ui.common;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
    }

    public BaseFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    protected abstract void performDependencyInjection();

    protected NavController getNavController(View view) {
        return Navigation.findNavController(view);
    }

    protected NavController getNavController(Fragment fragment) {
        return NavHostFragment.findNavController(fragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        performDependencyInjection();
        super.onAttach(context);
    }


}
