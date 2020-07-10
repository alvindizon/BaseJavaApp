package com.alvindizon.basejavaapp.ui.screens.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alvindizon.basejavaapp.databinding.ActivityMainBinding;
import com.alvindizon.basejavaapp.di.Injector;
import com.alvindizon.basejavaapp.ui.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Injector.getActivityComponent(this).inject(this);
        Timber.d("onCreate()");
    }
}