package com.example.Android1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MyActivity extends Activity {
  private EditText averageGlucose;
  private EditText a1c;
  private RadioButton rdoADAG;
  private RadioButton rdoDCCT;
  private Button btnSwitch;
  private Button btnSwitchToAlarm;
  private Button btnSwitchToCalc;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    initializeApp();
  }
  private void initializeApp(){
    averageGlucose = (EditText) findViewById(R.id.averageGlucose);
    a1c = (EditText) findViewById(R.id.a1c);
    rdoADAG = (RadioButton) findViewById(R.id.rdoADAG);
    rdoDCCT = (RadioButton) findViewById(R.id.rdoDCCT);
    btnSwitch = (Button) findViewById(R.id.btnSwitch);
    btnSwitchToAlarm = (Button) findViewById(R.id.btnSwitchToAlarm);

    // Event Handlers: onFocus
    averageGlucose.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (!averageGlucose.hasFocus()){
          averageGlucose.setText("");
        }
      }
    });

    a1c.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (!a1c.hasFocus()){
          a1c.setText("");
        }
      }
    });

    averageGlucose.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        double eAG = Double.parseDouble(String.valueOf(averageGlucose.getText()));
        double solution = 0;
        boolean handled = false;
        if (rdoADAG.isChecked()){
          // Formula to derive A1C
          solution = (((eAG/18.05) + 2.52) / 1.583);
          handled = true;
        }
        if (rdoDCCT.isChecked()){
          solution = (eAG + 77.3)/35.6;
          handled = true;
        }
        DecimalFormat df = new DecimalFormat("#.#");
        a1c.setText(df.format(solution));
        return handled;
      }
    });

    a1c.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handled = false;
        double a1C = Double.parseDouble(String.valueOf(a1c.getText()));
        double solution = 0;
        if (rdoADAG.isChecked()){
          // Formula to derive eAG
          solution = ((1.583 * a1C) - 2.52) * 18.05;
          handled = true;
        }
        if (rdoDCCT.isChecked()){
          solution = ((a1C * 35.6)-77.3);
          handled = true;
        }
        DecimalFormat df = new DecimalFormat("#");
        averageGlucose.setText(df.format(solution));
        return handled;
      }
    });

    btnSwitch.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){
        Intent switchActivity = new Intent(v.getContext() ,SugarLogger.class);
        startActivity(switchActivity);
      }
    }
    );

    btnSwitchToAlarm.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){
        Intent switchActivity = new Intent(v.getContext() , SugarAlarm.class);
        startActivity(switchActivity);
      }
    });
  }
}
