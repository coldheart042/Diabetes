package com.example.Android1;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Richard on 5/21/14.
 */
public class Map extends Activity implements LocationListener{

  private GoogleMap googleMap;
  private Location myLocation;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map);
    setLocation();
  }

  private void setPlaces() {
    StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
    stringBuilder.append("location=" + myLocation.getLatitude() + "," + myLocation.getLongitude());
    stringBuilder.append("&radius=5000");
    stringBuilder.append("&types=hospital");
    stringBuilder.append("&sensor=true");
    //stringBuilder.append("&key=AIzaSyCAbyj3H0o30LQloOafZWhvkpn2-PzJ6VA");// ← This is the Android Key - Gonna Try the browser key below:
    stringBuilder.append("&key=AIzaSyAKj_kDYLFAbHZsVt9zsXDt9pzCRiE2k_I");
    PlacesTask placesTask = new PlacesTask();
    placesTask.execute(stringBuilder.toString());
  }

  private void setLocation() {
    googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.the_map)).getMap(); //← Tested, no Crash!
    // Get the location coordinates
    LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    Criteria criteria = new Criteria();
    String provider = locationManager.getBestProvider(criteria, true);
    myLocation = locationManager.getLastKnownLocation(provider);
    LatLng myPosition = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
    //Toast.makeText(Map.this, myLocation.getLatitude() + "," + myLocation.getLongitude(), Toast.LENGTH_LONG).show(); // Displays coordinates in a Toast
    Float zoomLevel = (float) 14;
    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, zoomLevel));
    //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, zoomLevel)); //← Didn't want to use.
    googleMap.addMarker(new MarkerOptions().position(myPosition).title("You are here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    setPlaces();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.action_main:
        Intent switchToMain = new Intent(this, MyActivity.class);
        startActivity(switchToMain);
        break;
      case R.id.action_sugar:
        Intent switchToSugar = new Intent(this, SugarLogger.class);
        startActivity(switchToSugar);
        break;
      case R.id.action_alarm:
        Intent switchToAlarms = new Intent();
        startActivity(switchToAlarms);
        break;
      case R.id.action_preferences:
        Intent switchtosettings = new Intent(this,Preferences.class);
        startActivity(switchtosettings);
        break;
      case R.id.action_map:
        Intent switchToMap = new Intent(this, Map.class);
        startActivity(switchToMap);
        break;
      default:break;
    }
    return true;
  }

  private String downloadUrl(String strUrl) throws IOException {
    String data = "";
    InputStream iStream = null;
    HttpURLConnection urlConnection = null;
    try{
      URL url = new URL(strUrl);

      // Creating an http connection to communicate with url
      urlConnection = (HttpURLConnection) url.openConnection();

      // Connecting to url
      urlConnection.connect();

      // Reading data from url
      iStream = urlConnection.getInputStream();

      BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

      StringBuffer sb  = new StringBuffer();

      String line = "";
      while( ( line = br.readLine())  != null){
        sb.append(line);
      }

      data = sb.toString();

      br.close();

    }catch(Exception e){
      Log.d( "Exception while downloading url", e.toString());
    }finally{
      iStream.close();
      urlConnection.disconnect();
    }

    return data;
  }

  @Override
  public void onLocationChanged(Location location) {

  }

  @Override
  public void onStatusChanged(String s, int i, Bundle bundle) {

  }

  @Override
  public void onProviderEnabled(String s) {

  }

  @Override
  public void onProviderDisabled(String s) {

  }

  /** A class, to download Google Places */
  private class PlacesTask extends AsyncTask<String, Integer, String>{

    String data = null;

    // Invoked by execute() method of this object
    @Override
    protected String doInBackground(String... url) {
      try{
        data = downloadUrl(url[0]);
      }catch(Exception e){
        Toast.makeText(Map.this,"Error downloading: " + e.getMessage(),Toast.LENGTH_LONG).show();
      }
      return data;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(String result){
      ParserTask parserTask = new ParserTask();

      // Start parsing the Google places in JSON format
      // Invokes the "doInBackground()" method of the class ParseTask
      parserTask.execute(result);
    }

  }

  /** A class to parse the Google Places in JSON format */
  private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

    JSONObject jObject;

    // Invoked by execute() method of this object
    @Override
    protected List<HashMap<String,String>> doInBackground(String... jsonData) {

      List<HashMap<String, String>> places = null;
      PlaceJSONParser placeJsonParser = new PlaceJSONParser();

      try{
        jObject = new JSONObject(jsonData[0]);

        /** Getting the parsed data as a List construct */
        places = placeJsonParser.parse(jObject);

      }catch(Exception e){
        Log.d("Exception", e.toString());
      }
      return places;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(List<HashMap<String,String>> list){

      // Clears all the existing markers
      //googleMap.clear();

      for(int i=0;i<list.size();i++){

        // Creating a marker
        // Getting a place from the places list
        HashMap<String, String> hmPlace = list.get(i);

        // Getting latitude of the place
        double lat = Double.parseDouble(hmPlace.get("lat"));

        // Getting longitude of the place
        double lng = Double.parseDouble(hmPlace.get("lng"));

        // Getting name
        String name = hmPlace.get("place_name");

        LatLng latLng = new LatLng(lat, lng);

        // Placing a marker on the touched position
        googleMap.addMarker(new MarkerOptions()
          .position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
      }
    }

  public class PlaceJSONParser {

    /** Receives a JSONObject and returns a list */
    public List<HashMap<String,String>> parse(JSONObject jObject){

      JSONArray jPlaces = null;
      try {
        /** Retrieves all the elements in the 'places' array */
        jPlaces = jObject.getJSONArray("results");
      } catch (JSONException e) {
        e.printStackTrace();
      }
      /** Invoking getPlaces with the array of json object
       * where each json object represent a place
       */
      return getPlaces(jPlaces);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jPlaces){
      int placesCount = jPlaces.length();
      List<HashMap<String, String>> placesList = new ArrayList<HashMap<String,String>>();
      HashMap<String, String> place = null;

      /** Taking each place, parses and adds to list object */
      for(int i=0; i<placesCount;i++){
        try {
          /** Call getPlace with place JSON object to parse the place */
          place = getPlace((JSONObject)jPlaces.get(i));
          placesList.add(place);

        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      return placesList;
    }

    /** Parsing the Place JSON object */
    private HashMap<String, String> getPlace(JSONObject jPlace){

      HashMap<String, String> place = new HashMap<String, String>();
      String placeName = "-NA-";
      String vicinity="-NA-";
      String latitude="";
      String longitude="";

      try {
        // Extracting Place name, if available
        if(!jPlace.isNull("name")){
          placeName = jPlace.getString("name");
        }

        // Extracting Place Vicinity, if available
        if(!jPlace.isNull("vicinity")){
          vicinity = jPlace.getString("vicinity");
        }

        latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
        longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");

        place.put("place_name", placeName);
        place.put("vicinity", vicinity);
        place.put("lat", latitude);
        place.put("lng", longitude);

      } catch (JSONException e) {
        e.printStackTrace();
      }
      return place;
    }
  }
  }
}