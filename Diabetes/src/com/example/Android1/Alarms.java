package com.example.Android1;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by Richard on 3/11/14.
 */
public class Alarms extends Activity {

  private ArrayList<PendingIntent>pendingIntents = new ArrayList<PendingIntent>(10);
  // Daisy chained controls are the best I can do to clean up my code :(  I tried to set an ArrayList...
  // All ToggleButtons
  private ToggleButton tog1,tog2,tog3,tog4,tog5,tog6,tog7,tog8,tog9,tog10;
        // All DateTime Texts
  private EditText ea1,ea2,ea3,ea4,ea5,ea6,ea7,ea8,ea9,ea10;
        // All Note Texts
  private EditText en1,en2,en3,en4,en5,en6,en7,en8,en9,en10;
        // All CheckBoxes
  private CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10;
  // Whew, now that's over, time to set them...

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
    // Set EditTexts
    ea1 = (EditText)findViewById(R.id.txtAlarm1);ea2 = (EditText)findViewById(R.id.txtAlarm2);ea3 = (EditText)findViewById(R.id.txtAlarm3);ea4 = (EditText)findViewById(R.id.txtAlarm4);ea5 = (EditText)findViewById(R.id.txtAlarm5);ea6 = (EditText)findViewById(R.id.txtAlarm6);ea7 = (EditText)findViewById(R.id.txtAlarm7);ea8 = (EditText)findViewById(R.id.txtAlarm8);ea9 = (EditText)findViewById(R.id.txtAlarm9);ea10 = (EditText)findViewById(R.id.txtAlarm10);
    en1 = (EditText)findViewById(R.id.txtNotes1);en2 = (EditText)findViewById(R.id.txtNotes2);en3 = (EditText)findViewById(R.id.txtNotes3);en4 = (EditText)findViewById(R.id.txtNotes4);en5 = (EditText)findViewById(R.id.txtNotes5);en6 = (EditText)findViewById(R.id.txtNotes6);en7 = (EditText)findViewById(R.id.txtNotes7);en8 = (EditText)findViewById(R.id.txtNotes8);en9 = (EditText)findViewById(R.id.txtNotes9);en10 = (EditText)findViewById(R.id.txtNotes10);
    // Set ToggleButtons
    tog1 = (ToggleButton)findViewById(R.id.togAlarm1); tog2 = (ToggleButton)findViewById(R.id.togAlarm2);tog3 = (ToggleButton)findViewById(R.id.togAlarm3);tog4 = (ToggleButton)findViewById(R.id.togAlarm4);tog5 = (ToggleButton)findViewById(R.id.togAlarm5);tog6 = (ToggleButton)findViewById(R.id.togAlarm6);tog7 = (ToggleButton)findViewById(R.id.togAlarm7);tog8 = (ToggleButton)findViewById(R.id.togAlarm8);tog9 = (ToggleButton)findViewById(R.id.togAlarm9);tog10 = (ToggleButton)findViewById(R.id.togAlarm10);
    // Set Checkboxes
    cb1 = (CheckBox)findViewById(R.id.chkAlarm1);cb2 = (CheckBox)findViewById(R.id.chkAlarm2);cb3 = (CheckBox)findViewById(R.id.chkAlarm3);cb4 = (CheckBox)findViewById(R.id.chkAlarm4);cb5 = (CheckBox)findViewById(R.id.chkAlarm5);cb6 = (CheckBox)findViewById(R.id.chkAlarm6);cb7 = (CheckBox)findViewById(R.id.chkAlarm7);cb8 = (CheckBox)findViewById(R.id.chkAlarm8);cb9 = (CheckBox)findViewById(R.id.chkAlarm9);cb10 = (CheckBox)findViewById(R.id.chkAlarm10);
    // Set onClick listeners for toggleButtons
    tog1.setOnClickListener(togOCL);
    tog2.setOnClickListener(togOCL);
    tog3.setOnClickListener(togOCL);
    tog4.setOnClickListener(togOCL);
    tog5.setOnClickListener(togOCL);
    tog6.setOnClickListener(togOCL);
    tog7.setOnClickListener(togOCL);
    tog8.setOnClickListener(togOCL);
    tog9.setOnClickListener(togOCL);
    tog10.setOnClickListener(togOCL);
    // Set onClick listeners for EditTexts

  }

  // OnClickListener for ToggleButtons
  View.OnClickListener togOCL = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      ToggleButton toggleButton = (ToggleButton)view;
      if (toggleButton.isChecked()){
        createAlarm();
      }
      else{
        disposeAlarm();
      }
    }
  };

  public void createAlarm(){
    // Code to add alarm
  }

  public void disposeAlarm(){
    // Code to remove alarm
  }
}
