package com.example.fasalmandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFrag;
    private OrderFragment ordersFrag;
    private ProfileFragment profileFrag;
    private BottomNavigationView nav_bar;
    private FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame=findViewById(R.id.frame_container);
        nav_bar=findViewById(R.id.nav_bar);
        nav_bar.setItemBackgroundResource(R.color.design_default_color_secondary);
        homeFrag=new HomeFragment();
        ordersFrag=new OrderFragment();
        profileFrag= new ProfileFragment();
        setFragment(homeFrag);


        nav_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home :
                        //nav_bar.setItemBackgroundResource(R.color.design_default_color_secondary);
                        setFragment(homeFrag);
                        return true;
                    case R.id.nav_orders:
                        //nav_bar.setItemBackgroundResource(R.color.design_default_color_secondary);
                        setFragment(ordersFrag);
                        return true;
                    case R.id.nav_profile:
                        //  nav_bar.setItemBackgroundResource(R.color.design_default_color_secondary);
                        setFragment(profileFrag);
                        return true;

                    default:
                        return false;
                }
            }
        });


    }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();
    }
}