package com.example.tanamao.ui.activity.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tanamao.R;
import com.example.tanamao.model.RecipeSearch;
import com.example.tanamao.model.entity.recipe.Ingredient;
import com.example.tanamao.model.entity.recipe.Recipe;
import com.example.tanamao.ui.activity.recipeDetailActivity.RecipeDetailsActivity;
import com.example.tanamao.ui.adapter.RecipeAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecipeAdapter.RecipeClickListener {

    private ChipGroup availableIngredientsChipGroup;
    private ChipGroup userIngredientsChipGroup;
    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private TextView noIngredients;
    private TextView bottomSheetTitle;
    private ProgressBar progressBar;
    private ImageView arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        findViews();
        setupInterfaceComponents();
        setupViewModel();
        setupReciclerView();
        getRecipes();

        populateChipGroups();

    }

    private void setupReciclerView() {
        recipeAdapter = new RecipeAdapter(this, this);

        recyclerView = findViewById(R.id.rv_recipes);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getRecipes() {
        viewModel.getRecipeList().observe(this, recipes -> {
            showLoading(true);
            List<Ingredient> persistedUserIngredients = viewModel.getPersistedUserIngredients(this);

            if (persistedUserIngredients.size() == 0) {
                showNoIngredientsMessage(true);
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (recipes.size() == 0) {
                return;
            }

            showNoIngredientsMessage(false);

            resetIngredientsMatch(recipes);
            RecipeSearch recipeSearch = new RecipeSearch();
            recipes = recipeSearch.recipeFilter(recipes, persistedUserIngredients);
            recipeAdapter.setRecipeList(recipes);
            showLoading(false);
        });
        viewModel.loadRecipes();
    }

    private void showLoading(boolean b) {
        progressBar.setVisibility(b ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(b ? View.INVISIBLE : View.VISIBLE);
    }

    private void showNoIngredientsMessage(boolean b) {
        noIngredients.setVisibility(b ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(b ? View.INVISIBLE : View.VISIBLE);
    }

    private void resetIngredientsMatch(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            recipe.setIngredientsMatch(0);
        }
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
                    getRecipes();
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
        recyclerView = findViewById(R.id.rv_recipes);
        noIngredients = findViewById(R.id.tv_no_ingredients);
        progressBar = findViewById(R.id.progressBar);
        arrow = findViewById(R.id.iv_arrow);
        bottomSheetTitle = findViewById(R.id.tv_bottom_sheet_title);
    }

    private void setupInterfaceComponents() {
        setupToolbarDrawer();
        setupNavigationView();
        setupBottomSheet();
    }

    private void setupBottomSheet() {
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_layout));
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        arrow.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        arrow.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
                        break;
                    }
                    case BottomSheetBehavior.STATE_DRAGGING:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float newState) {

            }
        });

        bottomSheetTitle.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                arrow.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
            } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                arrow.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
            }
        });
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
            viewModel.loadFavorites(this);
            return true;
        } else if (id == R.id.home) {
            viewModel.loadRecipes();
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startRecipeActivity(Recipe recipe, View v) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Recipe.RECIPE_KEY, recipe);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}