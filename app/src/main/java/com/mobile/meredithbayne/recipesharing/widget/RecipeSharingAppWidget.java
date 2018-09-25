package com.mobile.meredithbayne.recipesharing.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;
import com.mobile.meredithbayne.recipesharing.ui.activity.RecipeListActivity;

public class RecipeSharingAppWidget extends AppWidgetProvider {
    private static final String PREFS_NAME = "prefs";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        Recipe recipe = getRecipe(context);
        if (recipe != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, RecipeListActivity.class), 0);

            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_sharing_appwidget_list);

            views.setTextViewText(R.id.appwidget_recipe_name, recipe.getName());
            views.setOnClickPendingIntent(R.id.appwidget_recipe_name, pendingIntent);

            Intent intent = new Intent(context, RecipeSharingAppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            // Bind the remote adapter
            views.setRemoteAdapter(R.id.appwidget_ingredient_list, intent);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
                    R.id.appwidget_ingredient_list);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static Recipe getRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(context.getString(R.string.prefs_recipe_key), "");
        return gson.fromJson(json, Recipe.class);
    }

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefs.putString(context.getString(R.string.prefs_recipe_key), json);
        prefs.apply();
    }
}
