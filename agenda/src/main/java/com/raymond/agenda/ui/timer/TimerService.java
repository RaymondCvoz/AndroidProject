package com.raymond.agenda.ui.timer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.raymond.agenda.MainFrame;
import com.raymond.agenda.R;
import com.raymond.agenda.databinding.FragmentTimerBinding;

public class TimerService extends Service
{

    private static final String CHANNEL_DEFAULT_IMPORTANCE = "service";
    private static final String CHANNEL_ID = "default";
    private int remaining;
    private final IBinder binder = new LocalBinder();
    private int signal;
    private Handler handler;
    private Runnable runnable;
    private int kill = 0;
    private FragmentTimerBinding binding;
    public class LocalBinder extends Binder
    {
        TimerService getService()
        {
            System.out.println("localBinder constructed");
            return TimerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return binder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    public void setRemaining(int remaining)
    {
        this.remaining = remaining;
    }

    public int getRemaining()
    {
        return remaining;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    void showNotification()
    {
        final String CHANNEL_ID = "channel_id_1";
        final String CHANNEL_NAME = "channel_name_1";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        Intent intent = new Intent(this, MainFrame.class);
        intent.putExtra("navTo",3);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        builder.setSmallIcon(R.drawable.ic_dashboard_black_24dp)
                .setContentTitle("定时事件")
                .setContentText("时间到了")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build();

        notificationManager.notify(1, builder.build());
    }


    public void beginCount()
    {
        kill = 0;
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run()
            {
                if(kill == 0)
                {
                    if (remaining > 0)
                    {
                        remaining--;
                    } else
                    {
                        showNotification();
                        stopCount();
                    }
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void stopCount()
    {
        kill = 1;
        handler.removeCallbacks(runnable);
    }
}