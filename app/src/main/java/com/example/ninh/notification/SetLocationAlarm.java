package com.example.ninh.notification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ninh on 25/03/2015.
 */
public class SetLocationAlarm extends FragmentActivity implements OnMapReadyCallback{

    GoogleMap mMap;
    Boolean changeCamera = false;
    Boolean hasMarker = false;
    LinearLayout optionsbar;
    Marker marker;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String LAT = "LAT";
    public static final String LNG = "LNG";

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location_alarm);

        optionsbar = (LinearLayout)findViewById(R.id.optionsbar);

        GetLocation gl = new GetLocation(this);
        if(!gl.canGetLocation()) gl.showSettingsAlert();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMap = mapFragment.getMap();
        mMap.setMyLocationEnabled(true);

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(21.027134, 105.782986));
        mMap.moveCamera(center);

        if (mMap != null) {


            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location arg0) {
                    // TODO Auto-generated method stub
                    if(!changeCamera) {

                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude()))
                                .title(getString(R.string.title_marker)));


                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(arg0.getLatitude(), arg0.getLongitude()));
                        mMap.moveCamera(center);
                        changeCamera = true;
                    }

                    Log.i("CURRENTLOC",arg0.getLatitude() + ";" + arg0.getLongitude());

                }
            });

        }



        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.getPosition();
                Log.i("POSST",""+marker.getPosition());
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.i("POSEN",""+marker.getPosition());
            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(hasMarker == false) {
                    marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Marker")
                            .draggable(true)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    hasMarker = true;

                    optionsbar.setVisibility(View.VISIBLE);
                }

                else
                    marker.setPosition(latLng);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {

    }

    public void cancel(View v)
    {
        marker.remove();
        hasMarker = false;
        optionsbar.setVisibility(View.INVISIBLE);
    }

    public void accept(View v) {

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(LAT, (float)marker.getPosition().latitude);
        editor.putFloat(LNG, (float)marker.getPosition().longitude);
        editor.commit();

        startService(new Intent(getBaseContext(), MyService.class));

    }
}


