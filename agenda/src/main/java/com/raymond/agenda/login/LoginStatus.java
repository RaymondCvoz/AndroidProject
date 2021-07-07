package com.raymond.agenda.login;

public class LoginStatus
{
    private Integer usernameErr;
    private Integer passwordErr;


    public Integer checkUsername(String username)
    {
        if(username.length() < 5)
        {
            return 1;
        }
        return 0;
    }

    public Integer checkPassword(String username,String password)
    {
        if(username != password)
        {
            return 1;
        }
        return 0;
    }

    public boolean checkValid(String username,String password)
    {
        return (checkUsername(username) == 0 && checkPassword(username,password) == 0);
    }
}
