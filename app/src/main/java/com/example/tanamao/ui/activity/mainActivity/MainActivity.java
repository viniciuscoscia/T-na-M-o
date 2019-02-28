package com.example.tanamao.ui.activity.mainActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tanamao.R;
import com.example.tanamao.entity.recipe.Ingredient;
import com.example.tanamao.repository.FirebaseUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;

import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ChipGroup availableIngredientsChipGroup;
    private ChipGroup userIngredientsChipGroup;
    private MainViewModel viewModel;
    private FirebaseUtils firebaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        firebaseUtils = new FirebaseUtils(this);
        firebaseUtils.insertRecipe();

        findViews();
        setupInterfaceComponents();

        setupViewModel();


        populateChipGroups();
    }

    private void populateChipGroups() {
        executePopulate(viewModel.getAvailableIngredients(), availableIngredientsChipGroup);
        executePopulate(viewModel.getUserIngredients(), userIngredientsChipGroup);
    }

    private void executePopulate(MutableLiveData<List<Ingredient>> ingredientList,
                                 ChipGroup chipGroup) {
        ingredientList.observe(this, ingredients -> {
            chipGroup.removeAllViews();
            for (Ingredient ingredient : ingredients) {
                Chip chip = new Chip(this);
                chip.setText(ingredient.getName());
                chip.setClickable(true);
                chip.setOnClickListener(view -> {
                    viewModel.ingredientClickEvent(ingredient);
                    chipGroup.removeView(view);
                });
                chipGroup.addView(chip);
            }
        });
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void findViews() {
        availableIngredientsChipGroup = findViewById(R.id.cg_available_ingredients);
        userIngredientsChipGroup = findViewById(R.id.cg_user_ingredients);
    }

    private void setupInterfaceComponents() {
        setupToolbarDrawer();
        setupNavigationView();
        setupBottomSheet();
    }

    private void setupBottomSheet() {
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_layout));
    }

    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupToolbarDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_favorites) {
            // Handle the camera action
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
