package com.example.demo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityModel extends ViewModel
{
    private MutableLiveData<Integer> status;
    public MutableLiveData<Integer> getCurrentStatus()
    {
        if(status == null)
        {
            status = new MutableLiveData<Integer>(0);
        }
        return status;
    }
}
