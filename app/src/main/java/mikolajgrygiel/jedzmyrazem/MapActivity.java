package mikolajgrygiel.jedzmyrazem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import mikolajgrygiel.jedzmyrazem.enums.RestApiUrl;


public class MapActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextViewSrc;
    private AutoCompleteTextView mAutocompleteTextViewDst;
    private TextView mTextViewDate;
    private TextView mTextViewTime;
    private TextView mAttTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private LatLng start_location, finish_location;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(51.043317, 16.803714), new LatLng(51.219122, 17.199909));

    private Calendar calendar;
    private int year, month, day;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mGoogleApiClient = new GoogleApiClient.Builder(MapActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextViewSrc = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextViewSrc);
        mAutocompleteTextViewSrc.setThreshold(3);

        mAttTextView = (TextView) findViewById(R.id.att);
        mAutocompleteTextViewSrc.setOnItemClickListener(mAutocompleteClickListenerStart);

        mAutocompleteTextViewDst = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextViewDst);
        mAutocompleteTextViewDst.setThreshold(3);

        mAttTextView = (TextView) findViewById(R.id.att);
        mAutocompleteTextViewDst.setOnItemClickListener(mAutocompleteClickListenerFinish);

        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);

        mAutocompleteTextViewSrc.setAdapter(mPlaceArrayAdapter);
        mAutocompleteTextViewDst.setAdapter(mPlaceArrayAdapter);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);

        mTextViewDate = (TextView) findViewById(R.id
                .textViewDate);
        showDate(year, month, day);

        mTextViewTime = (TextView) findViewById(R.id
                .textViewTime);
        showTime(hour, minute);

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListenerStart
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallbackStart);
        }

        private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallbackStart
                = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    Log.e(LOG_TAG, "Place query did not complete. Error: " +
                            places.getStatus().toString());
                    return;
                }
                // Selecting the first object buffer.
                final Place place = places.get(0);
                start_location = place.getLatLng();
            }
        };
    };

    private AdapterView.OnItemClickListener mAutocompleteClickListenerFinish
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallbackFinish);
        }

        private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallbackFinish
                = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    Log.e(LOG_TAG, "Place query did not complete. Error: " +
                            places.getStatus().toString());
                    return;
                }
                // Selecting the first object buffer.
                final Place place = places.get(0);
                finish_location = place.getLatLng();
            }
        };
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @SuppressWarnings("deprecation")
    public void setTime(View view) {
        showDialog(1000);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        else if (id == 1000) {
            return new TimePickerDialog(this, mTimeSetListener, hour, minute,
                    DateFormat.is24HourFormat(this));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    showTime(hourOfDay, minute);

                }
            };

    private void showDate(int year, int month, int day) {
        mTextViewDate.setText(String.format("%04d-%02d-%02d", year, month, day));
    }

    private void showTime(int hour, int minute) {
        mTextViewTime.setText(String.format("%02d:%02d", hour, minute));
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    public void search(View view)
    {
        SearchTask st = new SearchTask();
        st.execute();
    }

    private class SearchTask extends AsyncTask<Void, Double, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject json = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            try {
                paramsJson.put("date", mTextViewDate.getText());
                paramsJson.put("start_time", mTextViewTime.getText());
                paramsJson.put("start_lat", start_location.latitude);
                paramsJson.put("start_lng", start_location.longitude);
                paramsJson.put("finish_lat", finish_location.latitude);
                paramsJson.put("finish_lng", finish_location.longitude);
                json.put("params", paramsJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(RestApiUrl.SEARCH.getUrl(), ServiceHandler.POST, json);
            return null;
        }
    }
}