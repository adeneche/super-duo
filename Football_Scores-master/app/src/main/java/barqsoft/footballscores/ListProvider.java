package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.service.WidgetIntentService;

import static barqsoft.footballscores.DatabaseContract.scores_table.AWAY_COL;
import static barqsoft.footballscores.DatabaseContract.scores_table.AWAY_GOALS_COL;
import static barqsoft.footballscores.DatabaseContract.scores_table.HOME_COL;
import static barqsoft.footballscores.DatabaseContract.scores_table.HOME_GOALS_COL;

/**
 *
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private static final String LOG_TAG = ListProvider.class.getSimpleName();

    private List<ContentValues> listItemList = new ArrayList();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        Log.i(LOG_TAG, "### populate list item ###");
        listItemList = new ArrayList<>(WidgetIntentService.listItems);
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Similar to getView of Adapter where instead of View
     * we return RemoteViews
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_row);

        ContentValues content = listItemList.get(position);

        remoteView.setTextViewText(R.id.home_name, content.getAsString(HOME_COL));
        remoteView.setTextViewText(R.id.away_name, content.getAsString(AWAY_COL));
        remoteView.setTextViewText(R.id.score_textview,
                Utilities.getScores(content.getAsInteger(HOME_GOALS_COL), content.getAsInteger(AWAY_GOALS_COL)));

        return remoteView;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
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
    public boolean hasStableIds() {
        return true;
    }

}
