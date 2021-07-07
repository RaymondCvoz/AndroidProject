package com.raymond.agenda.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginActivityModel extends ViewModel
{
    String email;
    String password;
    MutableLiveData<LoginStatus> loginFormStatusMutableLiveData;
    public MutableLiveData<LoginStatus> getLoginFormStatusMutableLiveData()
    {
        if(loginFormStatusMutableLiveData == null)
        {
            return new MutableLiveData<LoginStatus>(new LoginStatus(false,false));
        }
        return loginFormStatusMutableLiveData;
    }
}
