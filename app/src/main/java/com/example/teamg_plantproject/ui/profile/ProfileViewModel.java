package com.example.teamg_plantproject.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProfileViewModel() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String profileText = auth.getCurrentUser().getDisplayName()
                + "\n\n"
                + auth.getCurrentUser().getEmail();
        mText = new MutableLiveData<>();
        mText.setValue(profileText);
    }

    public LiveData<String> getText() {
        return mText;
    }
}