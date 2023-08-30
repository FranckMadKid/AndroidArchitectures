package com.learn.architecture.viewmodelstate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class StateViewModel extends ViewModel {
    private static final String COUNTER_KEY = "COUNTER";

    private final SavedStateHandle savedstate;

    private MutableLiveData<Integer> counter;


    public StateViewModel(SavedStateHandle savedStateHandle) {
        savedstate = savedStateHandle;
        counter = savedStateHandle.getLiveData(COUNTER_KEY);
        // Demonstrate this first
        // counter = new MutableLiveData<>(0);
    }


    public LiveData<Integer> getCounter() {
        return counter;
    }


    public void incrementCounter() {
        Integer currCount = counter.getValue();
        counter.setValue( currCount == null ? 0 : currCount.intValue() + 1);
    }


}
