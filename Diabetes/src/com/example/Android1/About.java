package com.example.Android1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Richard on 4/14/14.
 */
public class About extends Activity {
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

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about);
  }
  public void about_click(View view){
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://diabetes-about.azurewebsites.net/About.aspx"));
    //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")); - Used for testing the intent
    startActivity(browserIntent);
  }
  public void support_click(View view){
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://diabetes-support.azurewebsites.net/Support.aspx"));
    startActivity(browserIntent);
  }
}