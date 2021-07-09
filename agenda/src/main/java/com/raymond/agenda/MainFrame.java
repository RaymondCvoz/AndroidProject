package com.raymond.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.raymond.agenda.databinding.ActivityMainFrameBinding;
import com.raymond.agenda.ui.note.NoteFragment;

public class MainFrame extends AppCompatActivity
{

    private ActivityMainFrameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainFrameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_frame);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        Intent intent = getIntent();
        Integer navTo = intent.getIntExtra("navTo",1);
        if(navTo == 1)
        {
            navController.navigate(R.id.navigation_home);
        }
        else if(navTo == 2)
        {
            navController.navigate(R.id.navigation_dashboard);
        }
        else if(navTo == 3)
        {
            navController.navigate(R.id.navigation_notifications);
        }
        Log.i("main", "onCreate: success");
    }

}