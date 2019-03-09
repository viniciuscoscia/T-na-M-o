package com.example.tanamao.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.tanamao.R;
import com.example.tanamao.database.AppDataBase;
import com.example.tanamao.model.entity.recipe.Recipe;
import com.example.tanamao.ui.activity.recipeDetailActivity.RecipeDetailsActivity;

import java.util.List;

public class RecipeListService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeListViewsFactory(getApplicationContext());
    }
}

class RecipeListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeListViewsFactory(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipeList = AppDataBase.getInstance(context).favoritesDao().loadFavoriteRecipes();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Recipe recipe = recipeList.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_for_widget);
        views.setTextViewText(R.id.tv_recipe, recipe.getRecipeName());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(Recipe.RECIPE_KEY, recipe);
        views.setOnClickFillInIntent(R.id.tv_recipe, fillInIntent);

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, fillInIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setOnClickPendingIntent(R.id.tv_recipe, pendingIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
