package com.example.tjsdh.armanual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.HumanBird.ARUserManual.UnityPlayerActivity;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fhome, fhome2,fmenu, fsearch;

    private   FragmentManager fragmentManager;
    productPagerAdapter productPagerAdapter;
    ItemData input = new ItemData();
    ListAdapter adapter = new ListAdapter(input);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        fhome2 = new HomeLayout();
        fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_navigation,fhome2).commit();

       Toolbar toolbar= findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        productPagerAdapter=new productPagerAdapter(getSupportFragmentManager(),1);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       fragmentManager= getSupportFragmentManager();

        if (id == R.id.nav_home) { //홈
            if(fhome==null) {
                fhome = new HomeLayout();
                fragmentManager.beginTransaction().add(R.id.content_navigation,fhome).commit();
            }
            if(fhome !=null) fragmentManager.beginTransaction().show(fhome).commit();
            if(fmenu !=null) fragmentManager.beginTransaction().hide(fmenu).commit();
            if(fsearch !=null) fragmentManager.beginTransaction().hide(fsearch).commit();
        } else if (id == R.id.nav_manual) { //설명서 클릭시 fragment 달기
           if(fmenu==null) {
               Intent intent = new Intent(this, UnityPlayerActivity.class);
                 startActivity(intent);
           }
            if(fhome !=null) fragmentManager.beginTransaction().hide(fhome).commit();
           if(fmenu !=null){  Intent intent = new Intent(this, UnityPlayerActivity.class);
               startActivity(intent);}

           if(fsearch !=null) fragmentManager.beginTransaction().hide(fsearch).commit();


        } else if (id == R.id.nav_search) {
            if(fsearch==null) {
                fsearch = new ProductLayout();
                fragmentManager.beginTransaction().add(R.id.content_navigation,fsearch).commit();
            }
            if(fhome !=null) fragmentManager.beginTransaction().hide(fhome).commit();
            if(fmenu !=null) fragmentManager.beginTransaction().hide(fmenu).commit();
            if(fsearch !=null) fragmentManager.beginTransaction().show(fsearch).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onRefresh() {
        productPagerAdapter.notifyDataSetChanged();
    }
}
