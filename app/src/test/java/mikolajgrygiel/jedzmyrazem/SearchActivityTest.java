package mikolajgrygiel.jedzmyrazem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.gms.ShadowGooglePlayServicesUtil;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SearchActivityTest {
    private SearchActivity activity;
    private GPSTracker gpsTrackerMock;
    @Before
    public void setup() {

        //Stworzenie mocka dla Google Play Services, aby można było uruchomić testy
        //bez korzystania z zewnętrznego narzędzia
        ShadowApplication shadowApplication = Shadows.shadowOf(RuntimeEnvironment.application);
        shadowApplication.declareActionUnbindable("com.google.android.gms.analytics.service.START");
        ShadowGooglePlayServicesUtil.setIsGooglePlayServicesAvailable(ConnectionResult.API_UNAVAILABLE);

        activity = Robolectric.setupActivity(SearchActivity.class);
        //Stworzenie mocka dla klasy GPSTracker
        gpsTrackerMock = Mockito.mock(GPSTracker.class);
        activity.gps = gpsTrackerMock;
    }

    //test sprawdzający, czy lokalizacja jest pobierana z modułu gps, jeśli użytkownik jej nie wprowadził
    @Test
    public void searchCallGetLocationIfStarLocationIsNotSetted()
    {
        activity.search(null);
        Mockito.verify(gpsTrackerMock, Mockito.times(1)).getLocation();
    }
    //test sprawdzający, czy lokalizacja nie jest pobierana z modułu gps, jeśli użytkownik ją wprowadził
    @Test
    public void searchNotCallGetLocationIfStartLocationIsSetted()
    {
        activity.mAutocompleteTextViewSrc.setText("start");
        activity.start_location = new LatLng(52.00, 17.00);
        activity.search(null);
        Mockito.verify(gpsTrackerMock, Mockito.times(0)).getLocation();
    }
}
