package com.example.Android1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.Android1.R.id.btnAddAlarm;

/**
 * Created by Richard on 3/11/14.
 */
public class Alarms extends Activity {
  private Button btnAlarm;

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
//        Intent switchToAlarms = new Intent();
//        startActivity(switchToAlarms);
        break;
      default:break;
    }
    return true;
  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarms);

    initializeView();
  }

  private void initializeView(){

    // Get button and set on-click listener...
    btnAlarm = (Button) findViewById(btnAddAlarm);
    btnAlarm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    // Add more Stuff here!!!
  }
}
