package com.example.tanamao.ui.activity.mainActivity;

import android.os.Build;
import android.os.Bundle;

import com.example.tanamao.R;
import com.example.tanamao.entity.recipe.Category;
import com.example.tanamao.entity.recipe.Ingredient;
import com.example.tanamao.entity.recipe.Recipe;
import com.example.tanamao.entity.recipe.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String RECIPE_PATH = "recipe";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        testFirebase();
        retrieveDataFromFirebase();
    }

    private void retrieveDataFromFirebase() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            db.collection(RECIPE_PATH)
                    .whereEqualTo("category/categoryName", "Carne")
                    .get()
                    .addOnCompleteListener(result -> result.getResult()
                            .forEach(action -> Log.d("RESULTADOS", action.getData().toString())));
        }
    }

    private void testFirebase() {

        Recipe recipe = new Recipe();
        recipe.setAverageRating(3.5F);
        recipe.setCategory(new Category("1", "Carne", null));
        recipe.setImagePath("caminhoImagem");
        recipe.setIngredients(new ArrayList<Ingredient>(){{
                add(new Ingredient("0", "Sal"));
            }});
        recipe.setRecipeId("1");
        recipe.setVideos(new ArrayList<Video>(){{
            add(new Video("www.exemplo.com/idVideo2", "exemplo.com"));
        }});
        recipe.setServings(2);
        recipe.setRecipeName("RecipeName2");
        recipe.setTags(new ArrayList<String>(){{
            add("Carne");
        }});
        recipe.setSteps(new ArrayList<String>(){{
            add("Passo 1");
        }});

        db.collection(RECIPE_PATH)
                .add(recipe)
                .addOnSuccessListener(response -> Log.d(MainActivity.class.getName(), response.getId()))
                .addOnFailureListener(failure -> Log.d(MainActivity.class.getName(), failure.getMessage()));

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
