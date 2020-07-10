package com.alvindizon.basejavaapp.ui.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

    protected CompositeDisposable compositeDisposable;

    public BaseViewModel() {
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        compositeDisposable.clear();
    }
}
