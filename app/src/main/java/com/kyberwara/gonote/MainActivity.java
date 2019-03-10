package com.kyberwara.gonote;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    // Onclick action for floating action button in category fragment
    public void fabAction(View view) {
        Intent intent = new Intent(getApplicationContext(), AddNewNote.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the nav drawer button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);

        // Get drawer_layout from layout file
        drawerLayout = findViewById(R.id.drawer_layout);

        // Handle navigation click events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();
                        Fragment fragment = null;

                        switch (id) {
                            case R.id.add_new:
                                Intent intent = new Intent(getApplicationContext(), AddNewCategory.class);
                                startActivity(intent);
                                break;

                            default:
                                setTitle(menuItem.toString());

                                fragment = new CategoryFragment();
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.fragment_container, fragment);
                                ft.commit();
                        }

                        if (fragment != null) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fragment_container, fragment);
                            ft.commit();
                        } else {
                            Log.d("Error", "No valid fragment");
                        }

                        return true;
                    }
                });

        // Get menu of navigationView
        final Menu menu = navigationView.getMenu();

        // Get database
        final Database db = Room.databaseBuilder(getApplicationContext(), Database.class, "notesdb").allowMainThreadQueries().build();

        // Listen for drawer movement (must include all methods)
        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes

                        // Clear current menu to avoid duplication
                        menu.clear();

                        // Get categories from database and append to menu
                        List<CategoriesEntity> categories = db.AddNewCategoryDAO().getCategories();
                        for (CategoriesEntity c : categories) {
                            menu.add(c.getCategory());
                        }

                        // Append "Add Category" menu resource
                        MenuInflater inflater = getMenuInflater();
                        inflater.inflate(R.menu.drawer_view, menu);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        // Disable tint of icons in navigation drawer
        navigationView.setItemIconTintList(null);
    }

    // Open the drawer when the button is tapped
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}