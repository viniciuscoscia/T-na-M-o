package com.example.tanamao.ui.activity.recipeDetailActivity;

import android.os.Bundle;
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

    private RecipeDetailsViewModel recipeDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        setupViewModel();
        setupLayoutInfo();
    }

    private void setupLayoutInfo() {
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.recipe_detail_menu);

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


}
