package com.cos.instagram.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cos.instagram.model.User;

public class MainViewModel extends ViewModel {
    public User user;
    private final MutableLiveData<User> selected = new MutableLiveData<User>();

    public void select(User user) {
        selected.setValue(user);
        this.user = user;
    }

    public LiveData<User> getSelected() { return selected; }
}