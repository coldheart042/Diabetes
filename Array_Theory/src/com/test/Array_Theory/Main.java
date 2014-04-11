package com.test.Array_Theory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Main extends Activity
{
  public ArrayList<Button>buttons = new ArrayList<Button>();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      //initialize_app();
    }
  public void initialize_app(){
   for (int i=1;i<=2;i++){
     Button button = (Button)findViewById(getResId("button" + String.valueOf(i),Button.class));
     buttons.add(button);
   }
  }
  public void onClick(View view ){
    Toast.makeText(Main.this,"Button Was clicked!", Toast.LENGTH_LONG).show();
  }

  /*Taken from a Stack overflow post... parses a string into a resource id (Cool!!!!)*/
  public static int getResId(String varName, Class<?> c){
    try{
      Field idField = c.getDeclaredField(varName);
      return idField.getInt(idField);
    } catch (Exception e){
      e.printStackTrace();
      return -1;
    }
  }
}
