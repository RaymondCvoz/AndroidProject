package com.raymond.agenda.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginActivityModel extends ViewModel
{
    MutableLiveData<LoginStatus> loginFormStatusMutableLiveData;
    public MutableLiveData<LoginStatus> getLoginFormStatusMutableLiveData()
    {
        if(loginFormStatusMutableLiveData == null)
        {
            return new MutableLiveData<LoginStatus>(new LoginStatus(1,1));
        }
        return loginFormStatusMutableLiveData;
    }
}
