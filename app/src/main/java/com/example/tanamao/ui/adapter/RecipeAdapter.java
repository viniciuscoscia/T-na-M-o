package com.example.tanamao.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanamao.R;
import com.example.tanamao.model.entity.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;
    private Context context;
    private RecipeClickListener recipeClickListener;

    public interface RecipeClickListener {
        void startRecipeActivity(Recipe recipe);
    }

    public RecipeAdapter(Context context, RecipeClickListener recipeClickListener) {
        recipeList = new ArrayList<>();
        this.context = context;
        this.recipeClickListener = recipeClickListener;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView recipePhoto;
        TextView recipeName;
        TextView servings;
        RatingBar ratingBar;
        TextView ingredientsQuant;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipePhoto = itemView.findViewById(R.id.iv_recipe_photo);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);
            servings = itemView.findViewById(R.id.tv_servings);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ingredientsQuant = itemView.findViewById(R.id.tv_ingredients_quantity);
        }

        public void bind(Recipe recipe) {
            Glide.with(context)
                    .load(recipe.getImagePath())
                    .into(recipePhoto);
            recipeName.setText(recipe.getRecipeName());
            servings.setText(context.getResources().getQuantityString(R.plurals.servings, recipe.getServings(), recipe.getServings()));
            ratingBar.setRating(recipe.getAverageRating());
            ingredientsQuant.setText(context.getString(R.string.ingredientsQuantity,
                    recipe.getIngredientsMatch(),
                    recipe.getIngredientsTags().size()));
        }

        @Override
        public void onClick(View v) {
            recipeClickListener.startRecipeActivity(recipeList.get(getAdapterPosition()));
        }
    }
}
