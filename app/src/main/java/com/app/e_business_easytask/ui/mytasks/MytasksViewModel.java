package com.app.e_business_easytask.ui.mytasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MytasksViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MytasksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mytasks fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}