package com.learn.architecture.usecontext;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ViewModelWithCtx extends AndroidViewModel {

    private final MutableLiveData<String> dirName = new MutableLiveData<>();

    public ViewModelWithCtx(@NonNull Application application) {
        super(application);
        // use the application context to get the data dir
        // but it can be used to get services like location service to play with GPS etc
        // application.getSystemService(Context.LOCATION_SERVICE);
        dirName.setValue(application.getDataDir().toString());
    }

    public LiveData<String> getDirName() {
        return dirName;
    }
}
