package com.raymond.agenda.ui.timer;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.raymond.agenda.databinding.FragmentTimerBinding;

import java.util.Timer;
import java.util.TimerTask;

public class TimerFragment extends Fragment
{
    private int bindStatus = 0;
    private int timerRunning = 0;
    private FragmentTimerBinding binding;
    private TimerService.TimerBinder timerBinder;
    private Timer timer;
    private int secondLeft;
    private Handler mHandler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.equals(1))
            {
                binding.secondsLeft.setText(secondLeft);
            }
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            timerBinder = (TimerService.TimerBinder) service;
            String hours = binding.inputHours.getText().toString();
            if (hours.equals(""))
            {
                hours = "0";
            }
            String minutes = binding.inputMinutes.getText().toString();
            if (minutes.equals(""))
            {
                minutes = "0";
            }
            String second = binding.inputSeconds.getText().toString();
            if (second.equals(""))
            {
                second = "0";
            }
            Integer htos = Integer.parseInt(hours) * 3600;
            Integer mtos = Integer.parseInt(minutes) * 60;
            Integer sec = Integer.parseInt(second);
            Integer result = htos + mtos + sec;
            timerBinder.setRemaining(result);
            timerBinder.count();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentTimerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void run()
            {
                if (bindStatus == 1)
                {
                    secondLeft = timerBinder.getRemaining();
                    Message message = new Message();
                    message.what = 1;
                    mHandler.handleMessage(message);
                }
            }
        };

        timer.schedule(timerTask, 0, 1000);
        timerRunning = 1;
        binding.timerStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), TimerService.class);
                getContext().startService(intent);
                getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                bindStatus = 1;
            }
        });

        binding.timerStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (bindStatus == 1)
                {
                    Intent intent = new Intent(getContext(), TimerService.class);
                    getContext().unbindService(serviceConnection);
                    getContext().stopService(intent);
                    timerBinder.countDone();
                    bindStatus = 0;
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}