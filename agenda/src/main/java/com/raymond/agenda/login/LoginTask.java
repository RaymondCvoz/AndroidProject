package com.raymond.agenda.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.raymond.agenda.MainFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

public class LoginTask extends AsyncTask<String,Integer,Boolean>
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://106.13.85.56:3306/AndroidLoginDB?useSSL=false";
    static final String DBUserName = "rdsroot";
    static final String DBPassword = "password1";
    static Connection connection;
    static ResultSet resultSet;
    static PreparedStatement preparedStatement;
    static String sql;


    public interface AsyncResponse
    {
        void processFinished(Boolean output);
    }
    public AsyncResponse asyncResponse = null;

    public LoginTask(AsyncResponse asyncResponse)
    {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean)
    {
        //super.onPostExecute(aBoolean);
        asyncResponse.processFinished(aBoolean);
        System.out.println(aBoolean);
    }

    @Override
    protected Boolean doInBackground(String... strings)
    {
        String uname = strings[0];
        String pwd = strings[1];
        try
        {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DBUserName,DBPassword);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            sql = "SELECT * FROM USER_TABLE";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
            sql = "SELECT * FROM USER_TABLE WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,uname);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                sql = "INSERT INTO USER_TABLE (username,password) VALUES (?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,uname);
                preparedStatement.setString(2,pwd);
                preparedStatement.execute();
                return true;
            }
            else
            {
                resultSet.beforeFirst();
                while(resultSet.next())
                {
                    String cntPwd = resultSet.getString(2);
                    if(!cntPwd.equals(pwd))
                    {
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
