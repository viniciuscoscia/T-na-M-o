package com.example.tanamao.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.tanamao.R;

import androidx.annotation.Nullable;

public class RecipesChangeService extends IntentService {

    public static String ACTION_UPDATE_RECIPE_LIST = "updateRecipeList";

    public RecipesChangeService() {
        super("RecipesChangeService");
    }

    public static void changeIngredientsListWidget(Context context) {
        Intent intent = new Intent(context, RecipesChangeService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_LIST);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipesWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_recipes);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String data = intent.getAction();
        if (data.equals(ACTION_UPDATE_RECIPE_LIST)) {
            handleRecipesUpdate();
        }
    }

    private void handleRecipesUpdate() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipesWidgetProvider.class));

        RecipesWidgetProvider.updateRecipesWidgets(this, appWidgetManager, appWidgetIds);
    }
}
