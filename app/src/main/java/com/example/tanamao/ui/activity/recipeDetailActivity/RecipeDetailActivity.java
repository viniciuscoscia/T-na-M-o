package com.example.tanamao.ui.activity.recipeDetailActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.tanamao.R;
import com.example.tanamao.entity.recipe.Recipe;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class RecipeDetailActivity extends AppCompatActivity {

    private RecipeDetailsViewModel recipeDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        setupViewModel();
        setupLayoutInfo();
    }

    private void setupLayoutInfo() {
//        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.recipe_details_toolbar_layout);
//        Glide.with(this)
//                .load(recipeDetailsViewModel.getRecipe().getImagePath())
//                .into(new CustomViewTarget(collapsingToolbarLayout){
//                    @Override
//                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
//
//                    }
//
//                    @Override
//                    protected void onResourceCleared(@Nullable Drawable placeholder) {
//                        collapsingToolbarLayout.setBackground(placeholder);
//                    }
//                });

        Glide.with(this)
                .load(recipeDetailsViewModel.getRecipe().getImagePath())
                .into((ImageView)findViewById(R.id.iv_recipe));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void getIntentData() {
    }

    private void setupViewModel() {
        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getIntentData(getIntent());
    }


}
