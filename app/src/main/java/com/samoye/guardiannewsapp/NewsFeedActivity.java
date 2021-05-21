package com.samoye.guardiannewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsFeed>> {

    private static final String LOG_TAG = NewsFeedActivity.class.getName();

    /**
     * URL for earthquake data from the GUARDIAN dataset
     */
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search";

    /**
     * Constant value for earthquake loader ID
     */
    private static final int NEWSFEED_LOADER_ID = 1;

    /**
     * Adapter for the list of earthquakes
     */
    private NewsFeedAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    private TextView mInternetErrorStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView newsFeedListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsFeedListView.setEmptyView(mEmptyStateTextView);

        mInternetErrorStateTextView = (TextView) findViewById(R.id.internet_error_view);
        newsFeedListView.setEmptyView(mInternetErrorStateTextView);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(NEWSFEED_LOADER_ID, null, this);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsFeedAdapter(this, new ArrayList<NewsFeed>());


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsFeedListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        newsFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                NewsFeed currentNewsFeedList = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsFeedUri = Uri.parse(currentNewsFeedList.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsFeedUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);

                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {

                }
            }
        });


    }

//    @Override
//    // This method initialize the contents of the Activity's options menu.
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the Options Menu we specified in XML
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            Intent settingsIntent = new Intent(this, SettingsActivity.class);
//            startActivity(settingsIntent);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    // onCreateLoader instantiates and returns a new Loader for the given ID
    public Loader<List<NewsFeed>> onCreateLoader(int i, Bundle bundle) {

//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//
//        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
//        String minMagnitude = sharedPrefs.getString(
//                getString(R.string.settings_min_magnitude_key),
//                getString(R.string.settings_min_magnitude_default));
//
//        String orderBy  = sharedPrefs.getString(
//                getString(R.string.settings_order_by_key),
//                getString(R.string.settings_order_by_default)
//        );

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter("q", "debate");
        uriBuilder.appendQueryParameter("tag", "politics/politics");
        uriBuilder.appendQueryParameter("from-date", "2014-01-01");
        uriBuilder.appendQueryParameter("api-key", "test");

        // Return the completed uri `https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test'
        return new NewsFeedLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(android.content.Loader<List<NewsFeed>> loader, List<NewsFeed> newsFeedList) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No newsFeedList found."
        mEmptyStateTextView.setText(R.string.no_newsFeed);

        // Set internet error empty state text to display "Can't load data at the moment, check internet connection."
        mEmptyStateTextView.setText(R.string.no_internet_connection);

        // Clear the adapter of previous newsFeed data
        mAdapter.clear();

        // If there is a valid list of {@link NewsFeed}s, then add them to the adapter's
        // newsFeedList set. This will trigger the ListView to update.
        if (newsFeedList != null && !newsFeedList.isEmpty()) {
            mAdapter.addAll(newsFeedList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<NewsFeed>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}