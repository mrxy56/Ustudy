package sg.edu.nus.ustudy;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        double lng=getIntent().getDoubleExtra("lng", 0);
        double lat=getIntent().getDoubleExtra("lat", 0);
        int ID=getIntent().getIntExtra("ID",0);
        setUpMapIfNeeded();
        LatLng latLng = new LatLng(lat,lng);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        switch (ID){
            case 1:
                mMap.addMarker(new MarkerOptions().position(latLng).title("School of Computing").
                        snippet("Established in 1998."));
                break;
            case 2:
                mMap.addMarker(new MarkerOptions().position(latLng).title("University Town").
                        snippet("It provides accommodation primarily to the graduate community in NUS."));
                break;
            case 3:
                mMap.addMarker(new MarkerOptions().position(latLng).title("Central Library").
                        snippet("A multi-disciplinary library serving all NUS staff and students."));
                break;
            case 4:
                mMap.addMarker(new MarkerOptions().position(latLng).title("Business School").
                        snippet("It is one of the top business schools in Asia and is part of NUS."));
                break;
            case 5:
                mMap.addMarker(new MarkerOptions().position(latLng).title("Centre for Quantum Technologies").
                        snippet("The CQT in Singapore is a Research Centre of Excellence hosted by NUS."));
                break;
            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap(){
    }
}
