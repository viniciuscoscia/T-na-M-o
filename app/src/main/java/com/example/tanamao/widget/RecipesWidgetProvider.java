package com.example.tanamao.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.tanamao.R;
import com.example.tanamao.ui.activity.mainActivity.MainActivity;
import com.example.tanamao.ui.activity.recipeDetailActivity.RecipeDetailsActivity;

public class RecipesWidgetProvider extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews rv = getRecipesRemoteView(context);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipes_view);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getRecipesRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
        Intent intent = new Intent(context, RecipeListService.class);

        views.setRemoteAdapter(R.id.lv_recipes, intent);

        Intent startActivityIntent = new Intent(context, RecipeDetailsActivity.class);
        PendingIntent startActivityPendingIntent  = PendingIntent.getActivity(context, 0, startActivityIntent , PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lv_recipes, startActivityPendingIntent);
        views.setEmptyView(R.id.lv_recipes, R.id.empty_view);

        return views;
    }

    public static void updateRecipesWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateRecipesWidgets(context, appWidgetManager, appWidgetIds);
    }
}
