package com.example.Android1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Richard on 4/20/14.
 */
public class Preferences extends Activity {
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

  EditText txtDocEmail;
  EditText txtUserEmail;
  EditText txtSubject;
  EditText txtEmailNotes;
  EditText txtLogStart;
  EditText txtLogEnd;
  RadioButton rdoPrefADAG;
  RadioButton rdoPrefDCCT;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.preferences);
    initialize();
  }

  private void initialize() {
    txtDocEmail = (EditText)findViewById(R.id.txtDocEmail);
    txtUserEmail = (EditText)findViewById(R.id.txtUserEmail);
    txtSubject = (EditText)findViewById(R.id.txtSubject);
    txtEmailNotes = (EditText)findViewById(R.id.txtEmailNotes);
    txtLogStart = (EditText)findViewById(R.id.txtLogStart);
    txtLogEnd = (EditText)findViewById(R.id.txtLogEnd);
    rdoPrefADAG = (RadioButton)findViewById(R.id.rdoPrefADAG);
    rdoPrefDCCT = (RadioButton)findViewById(R.id.rdoPrefDCCT);

    SharedPreferences settings = getSharedPreferences("diabetesPref", Context.MODE_PRIVATE);
    txtDocEmail.setText(settings.getString("DoctorEmail",""));
    txtUserEmail.setText(settings.getString("UserEmail",""));
    txtSubject.setText(settings.getString("EmailSubject", "n/a"));
    txtEmailNotes.setText(settings.getString("EmailNotes", "n/a"));
    txtLogStart.setText(settings.getString("LogStart", ""));
    txtLogEnd.setText(settings.getString("LogEnd", ""));
    rdoPrefADAG.setChecked(settings.getBoolean("ADAG", false));
    rdoPrefDCCT.setChecked(settings.getBoolean("DCCT", false));
  }

  public void emailClick(View view){
    // Determine if there is an email address to send to first...
    if(txtDocEmail.getText().toString() != ""){
      sendEmail(txtDocEmail.getText().toString(), txtSubject.getText().toString(), txtEmailNotes.getText().toString());
    }
    else if (txtUserEmail.getText().toString() != ""){
      sendEmail(txtUserEmail.getText().toString(), txtSubject.getText().toString(), txtEmailNotes.getText().toString());
    }
    else{
      Toast.makeText(this,"No Email Specified", Toast.LENGTH_LONG).show();
    }

  }

  private void sendEmail(String to, String subject, String body) {  // NOTE: This may only work correctly on a physical device! Source: http://www.mkyong.com/android/how-to-send-email-in-android/
    Intent email = new Intent(Intent.ACTION_SEND);
    email.putExtra(Intent.EXTRA_EMAIL, to);
    email.putExtra(Intent.EXTRA_SUBJECT, subject);
    email.putExtra(Intent.EXTRA_TEXT, body);
    email.putExtra(Intent.EXTRA_STREAM, "sugar_log.csv");
    email.setType("message/rfc822");
    startActivity(Intent.createChooser(email, "Choose an email client: "));
  }

  public void saveClick(View view){
    SharedPreferences settings = getSharedPreferences("diabetesPref", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("DoctorEmail", txtDocEmail.getText().toString() );
    editor.putString("UserEmail", txtUserEmail.getText().toString());
    editor.putString("EmailSubject", txtSubject.getText().toString());
    editor.putString("EmailNotes", txtEmailNotes.getText().toString());
    editor.putString("LogStart", txtLogStart.getText().toString());
    editor.putString("LogEnd", txtLogEnd.getText().toString());
    editor.putBoolean("ADAG", rdoPrefADAG.isChecked());
    editor.putBoolean("DCCT", rdoPrefDCCT.isChecked());
    editor.apply();
    Toast.makeText(this,"Saved!",Toast.LENGTH_LONG).show();
  }

  public void setField(View view){
    final Calendar c = Calendar.getInstance();
    final EditText editText = (EditText)view;
    final DatePickerDialog d = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        editText.setText((datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear());
      }
    },c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    d.show();
  }
}