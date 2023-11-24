package com.app.e_business_easytask.ui.suche;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SucheViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SucheViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is suche fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}