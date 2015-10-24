package barqsoft.footballscores.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.Utilities;

public class WidgetIntentService extends IntentService {
    private static final String LOG_TAG = WidgetIntentService.class.getSimpleName();

    public static List<ContentValues> listItems = new ArrayList<>();

    public static final String ACTION_DATA_UPDATED = "barqsoft.footballscores.service.ACTION_DATA_UPDATED";

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public WidgetIntentService() {
        super("FootballWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG, "### onHandleIntent ###");
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Log.i(LOG_TAG, "  appWidgetId = " + appWidgetId);

        listItems.clear();

        //TODO update fetchData to only return today's result when populating the widget
        List<ContentValues> values = Utilities.fetchData("n2", getApplicationContext());

        if (values != null) {
            Log.i(LOG_TAG, "  found " + values.size() + " values");
            listItems.addAll(values);
            updateWidgets();
        } else {
            Log.w(LOG_TAG, "  no values found");
        }
    }

    private void updateWidgets() {
        Context context = getApplicationContext();
        // Setting the package ensures that only components in our app will receive the broadcast
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED)
//                .setPackage(context.getPackageName())
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        context.sendBroadcast(dataUpdatedIntent);
    }
}
