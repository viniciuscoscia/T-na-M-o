package com.example.tanamao.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.tanamao.R;

public class RecipesWidgetProvider extends AppWidgetProvider {

    public static void updateRecipesWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews rv = getRecipesRemoteView(context);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipes_view);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getRecipesRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
        Intent intent = new Intent(context, RecipeListService.class);

        views.setRemoteAdapter(R.id.lv_recipes, intent);

        //TODO Open Recipe
//        Intent appIntent = new Intent(context, MainActivity.class);
//        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setPendingIntentTemplate(R.id.ingredients_view, appPendingIntent);
//        views.setEmptyView(R.id.lv_ingredients, R.id.empty_view);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateRecipesWidgets(context, appWidgetManager, appWidgetIds);
    }
}
