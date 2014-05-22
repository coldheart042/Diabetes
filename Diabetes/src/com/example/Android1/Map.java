package com.example.Android1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by Richard on 5/21/14.
 */
public class Map extends Activity {

  private GoogleMap googleMap;
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map);

    try {
      // Loading map
      initilizeMap();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initilizeMap() {
    if (googleMap == null) {
      googleMap = ((MapFragment) getFragmentManager().findFragmentById(
          R.id.gmap)).getMap();

      // check if map is created successfully or not
      if (googleMap == null) {
        Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
      }
    }
  }
}