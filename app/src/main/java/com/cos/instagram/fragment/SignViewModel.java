package com.cos.instagram.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cos.instagram.model.User;

public class SignViewModel extends ViewModel {

    private final MutableLiveData<User> selected = new MutableLiveData<User>();

    public void select(User user) {
        selected.setValue(user);
    }

    public LiveData<User> getSelected() { return selected; }
    // TODO: Implement the ViewModel
}