package com.example.musicfirebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.musicfirebase.viewmodels.PlaylistViewModel;
import com.example.musicfirebase.viewmodels.SongViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hub);

        drawerLayout = findViewById(R.id.drawerLayout);

        SongViewModel songVM = new ViewModelProvider(this).get(SongViewModel.class);
        PlaylistViewModel playlistVM = new ViewModelProvider(this).get(PlaylistViewModel.class);

        songVM.getSongsFromDb();
        playlistVM.getPlaylistsFromDb(Query.Direction.ASCENDING);

        // Day UI
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE |
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR |
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

        Window window = getWindow();
        View decorView = window.getDecorView();
        window.setNavigationBarColor(getColor(R.color.white));
        window.setStatusBarColor(getColor(R.color.white));
        decorView.setSystemUiVisibility(uiOptions);

        BottomNavigationView bottomNav = findViewById(R.id.navBar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController());

        NavigationView navigationView = findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation item clicks here
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    /**
     * Hide the annoying keyboard without a stupid long method!
     *
     * @param v The current view
     */
    public static void hideKeyboardIn(View v) {
        ((InputMethodManager) v.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
