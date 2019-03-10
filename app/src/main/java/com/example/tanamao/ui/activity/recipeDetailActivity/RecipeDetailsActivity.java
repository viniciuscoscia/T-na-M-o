package com.example.tanamao.ui.activity.recipeDetailActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanamao.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class RecipeDetailsActivity extends AppCompatActivity {

    //Muito bom implementar a Toolbar.
    //Sugiro implementar também Up Navigation pois o usuário deveria conseguir voltar sem precisar utilizar o botão "físico" de voltar do android.

    private RecipeDetailsViewModel recipeDetailsViewModel;
    private MenuItem favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        setupViewModel();
        setupLayoutInfo();
    }

    private void setupLayoutInfo() {
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //Image
        Glide.with(this)
                .load(recipeDetailsViewModel.getRecipe().getImagePath())
                .into((ImageView)findViewById(R.id.iv_recipe));

        //Title
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(recipeDetailsViewModel.getRecipe().getRecipeName());
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        TextView recipeIngredients = findViewById(R.id.tv_ingredients);
        for (String ingredient : recipeDetailsViewModel.getRecipe().getIngredients()) {
            recipeIngredients.append(" - "+ ingredient + "\n");
        }

        TextView instructions = findViewById(R.id.tv_instructions);
        instructions.setText(recipeDetailsViewModel.getRecipe().getRecipeInstructions());

    }

    private void setupViewModel() {
        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getIntentData(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_detail_menu, menu);

        favorite = menu.findItem(R.id.item_favorite);

        recipeDetailsViewModel.getIsRecipeFavorite().observe(this, isFavorite -> {
            if (isFavorite) {
                favorite.setIcon(R.drawable.red_heart);
            } else {
                favorite.setIcon(R.drawable.grey_heart);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
            case R.id.item_favorite:
                recipeDetailsViewModel.favoriteUnfavoriteRecipe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
