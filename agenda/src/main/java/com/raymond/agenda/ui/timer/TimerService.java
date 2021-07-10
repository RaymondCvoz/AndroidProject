package com.raymond.agenda.ui.timer;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.media.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service
{
    private int remaining;
    private TimerBinder timerBinder = new TimerBinder(remaining);

    class TimerBinder extends Binder
    {
        private int remaining;
        public TimerBinder(int remaining)
        {
            this.remaining = remaining;
        }

        public int getRemaining()
        {
            return remaining;
        }

        public void setRemaining(int remaining)
        {
            this.remaining = remaining;
        }

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {

            @Override
            public void run()
            {
                remaining--;
                Log.i("count", "run: ");
            }
        };

        void count()
        {
            timer.schedule(timerTask,0,1000);
        }

        void countDone()
        {
            timer.cancel();
            timerTask.cancel();
            timer = null;
            timerTask = null;
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return timerBinder;
    }
}