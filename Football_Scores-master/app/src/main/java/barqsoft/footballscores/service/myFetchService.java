package barqsoft.footballscores.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.Utilities;

public class MyFetchService extends IntentService
{
    public static final String LOG_TAG = "MyFetchService";
    public MyFetchService()
    {
        super("MyFetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        getData("n2");
        getData("p2");

        return;
    }

    private void getData (String timeFrame) {
        final Context context = getApplicationContext();

        final List<ContentValues> values = Utilities.fetchData(timeFrame, context);
        if (values != null) {
            ContentValues[] insert_data = new ContentValues[values.size()];
            values.toArray(insert_data);
            final int inserted_data = context.getContentResolver().bulkInsert(
                    DatabaseContract.BASE_CONTENT_URI, insert_data);
            Log.v(LOG_TAG, "Succesfully Inserted : " + String.valueOf(inserted_data));
        }

    }
}

