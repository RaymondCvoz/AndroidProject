package com.raymond.agenda.ui.timer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.raymond.agenda.databinding.FragmentTimerBinding;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TimerFragment extends Fragment
{
    TimerService timerService;
    boolean mBound = false;

    private FragmentTimerBinding binding;

    int calcRemain()
    {
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
        return result;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentTimerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Intent intent = new Intent(getActivity(),TimerService.class);
        getActivity().bindService(intent,connection,Context.BIND_AUTO_CREATE);

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                int remain = timerService.getRemaining();

                int hour = remain / 3600;
                remain -= hour * 3600;
                int minute = remain / 60;
                remain -= minute * 60;
                int second = remain;

                if(hour / 10 == 0)
                    binding.hoursLeft.setText("0" + hour + "");
                else
                    binding.hoursLeft.setText(hour + "");
                if(minute / 10 == 0)
                    binding.minutesLeft.setText("0" + minute + "");
                else
                    binding.minutesLeft.setText(minute + "");
                if(second / 10 == 0)
                    binding.secondsLeft.setText("0" + second + "");
                else
                    binding.secondsLeft.setText(second + "");

                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,300);


        binding.timerStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String hourS = binding.inputHours.getText().toString();
                if(hourS.equals(""))
                {
                    hourS = "0";
                }
                String minuteS = binding.inputMinutes.getText().toString();
                if(minuteS.equals(""))
                {
                    minuteS = "0";
                }
                String secondS = binding.inputSeconds.getText().toString();
                if(secondS.equals(""))
                {
                    secondS = "0";
                }


                int hour = Integer.parseInt(hourS);
                int minute = Integer.parseInt(minuteS);
                int second = Integer.parseInt(secondS);

                if(mBound)
                {
                    timerService.setRemaining(hour * 3600 + minute * 60 + second);
                    timerService.beginCount();
                    binding.timerStart.setEnabled(false);
                    binding.timerStop.setEnabled(true);
                }
            }
        });
        binding.timerStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mBound)
                {
                    timerService.stopCount();
                    binding.timerStart.setEnabled(true);
                    binding.timerStop.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onStop()
    {
        super.onStop();

    }

    @Override
    public void onDestroyView()
    {

        super.onDestroyView();
    }

    private ServiceConnection connection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            TimerService.LocalBinder binder = (TimerService.LocalBinder)service;
            timerService = binder.getService();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mBound = false;
        }
    };
}