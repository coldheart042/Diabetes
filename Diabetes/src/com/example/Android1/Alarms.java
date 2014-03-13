package com.example.Android1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
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
    Toast.makeText(Alarms.this, R.string.alarms_load, Toast.LENGTH_LONG);

    // Get button and set on-click listener...
    btnAlarm = (Button) findViewById(btnAddAlarm);
    btnAlarm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // Call custom dialog to set alarm
        dialogBuilder(Alarms.this, 0);

      }
      // Alarm will be referenced as a button, added dynamically - onclick will show second dialog
    });

    // Alarms programming.
  }
  private void dialogBuilder(Context context,final int selector){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = getLayoutInflater();
    builder.setTitle(R.string.add_alarm)
        .setView(inflater.inflate(R.layout.alarm_dialog, null))
        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            if (selector == 0){
              // Add Alarm to activity (and save to file) from dialog
              Toast.makeText(Alarms.this,"Alarm will be created!", Toast.LENGTH_LONG).show();
            }
            else {
              // Get alarm from activity and modify from dialog
              Toast.makeText(Alarms.this,"Alarm will be saved!", Toast.LENGTH_LONG).show();
            }
          }
        })
        .setNegativeButton(R.string.back, new DialogInterface.OnClickListener(){

          // Do nothing.
          @Override
        public void onClick(DialogInterface dialogInterface, int i){
            if (selector == 0){
              Toast.makeText(Alarms.this,"Alarm will not be created!", Toast.LENGTH_LONG).show();
            }
            else {
              Toast.makeText(Alarms.this,"Alarm will not be saved!", Toast.LENGTH_LONG).show();
            }
          }
        });
    if (selector != 0){
      builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          // Delete alarm from array
          // Update the file
        }
      });
    }
    builder.show();
  }
}
