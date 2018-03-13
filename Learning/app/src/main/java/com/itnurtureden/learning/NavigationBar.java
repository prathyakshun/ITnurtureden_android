package com.itnurtureden.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class NavigationBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CourseListFragment.OnFragmentInteractionListener, LessonFragment.OnFragmentInteractionListener, LessonInFragment.OnFragmentInteractionListener, LessonContentFragment.OnFragmentInteractionListener, WebLessonFragment.OnFragmentInteractionListener, AllCourseListFragment.OnFragmentInteractionListener, CategoryFragment.OnFragmentInteractionListener, CategoryCourseFragment.OnFragmentInteractionListener, ChatFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener, AssessmentFragment.OnFragmentInteractionListener
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("navactivity","navactivity");
        setTitle("LEARN WITH US");
        final SharedPreferences pref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView tvEmailNavBar = (TextView)header.findViewById(R.id.tvEmailNavBar);
        TextView tvNameNavBar = (TextView)header.findViewById(R.id.tvNameNavBar);
        ImageView ivProfile = (ImageView)header.findViewById(R.id.ivProfile);
        Log.e("email",pref.getString("email_val",""));
        Log.e("profile_image",pref.getString("userpic_val",""));
        Picasso.with(getApplicationContext()).load(pref.getString("userpic_val","")).into(ivProfile);
        tvEmailNavBar.setText(pref.getString("email_val",""));
        tvNameNavBar.setText(pref.getString("username_val",""));
        FloatingActionButton fabChat = (FloatingActionButton) findViewById(R.id.fabChat);
        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
                ChatFragment chatFragment = new ChatFragment();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layout_fragment, chatFragment, chatFragment.getTag()).commit();
            }
        });

        DashboardFragment dashboardFragment = new DashboardFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.relative_layout_fragment, dashboardFragment, dashboardFragment.getTag()).commit();
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.relative_layout_fragment, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
            DashboardFragment dashboardFragment = new DashboardFragment();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, dashboardFragment, dashboardFragment.getTag()).commit();
        } else if (id == R.id.nav_course) {
            Toast.makeText(this, "Courses", Toast.LENGTH_SHORT).show();
            CourseListFragment courseListFragment = new CourseListFragment();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, courseListFragment, courseListFragment.getTag()).commit();
        } else if (id == R.id.nav_all_courses) {
            Toast.makeText(this, "All Courses", Toast.LENGTH_SHORT).show();
            AllCourseListFragment allCourseListFragment = new AllCourseListFragment();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, allCourseListFragment, allCourseListFragment.getTag()).commit();
        } else if (id == R.id.nav_categories) {
            Toast.makeText(this, "Categories", Toast.LENGTH_SHORT).show();
            CategoryFragment categoryFragment = new CategoryFragment();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, categoryFragment, categoryFragment.getTag()).commit();

        } else if (id == R.id.nav_chat) {
            Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
            ChatFragment chatFragment = new ChatFragment();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, chatFragment, chatFragment.getTag()).commit();

        } else if (id == R.id.nav_logout) {
            getApplicationContext().getSharedPreferences("userInfo", 0).edit().clear().apply();
            Intent intent = new Intent(NavigationBar.this, LoginActivity.class);
            startActivity(intent);
            finish();  // This call is missing.

        } 
        else if (id == R.id.nav_assessment) {
            Toast.makeText(this, "Assessment", Toast.LENGTH_SHORT).show();
            AssessmentFragment assessmentFragment = new AssessmentFragment();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment, assessmentFragment, assessmentFragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
