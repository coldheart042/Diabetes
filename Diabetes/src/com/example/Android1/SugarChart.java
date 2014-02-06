package com.example.Android1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Richard on 2/5/14.
 */
public class SugarChart extends Activity {
  Paint paint;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chart);
//    requestWindowFeature(Window.FEATURE_NO_TITLE);
    initializeView();
  }


  public void initializeView(){
    paint = new Paint();
    paint.setColor(Color.BLACK);
    paint.setStrokeWidth( 1 );
    setContentView( new Panel(this) );
  }

  public ArrayList<String> logSplitter(){
    ArrayList<String> result = new ArrayList<String>();
    File f = new File("sugar_log.csv");

    try{
      FileInputStream fis = new FileInputStream(f);
      BufferedReader br = new BufferedReader(new InputStreamReader(fis));

      String line = null;
      while ((line = br.readLine()) != null){ //Sets variable AND checks if null - cool, huh?
        result.add(line);
      }

    } catch (Exception e){
      Toast.makeText(SugarChart.this, "Failed to load file.", Toast.LENGTH_SHORT).show();
    }

    return result;
  }

  class Panel extends View {
    public Panel(Context context){
      super(context);
    }

    @Override
    public void onDraw( Canvas canvas){
      ArrayList<Integer> bsList = new ArrayList<Integer>();
      // The setup:
      int originX = 10, originY = 800;

      canvas.drawColor(Color.WHITE);
      canvas.drawLine(originX , originY, 600, originY, paint);
      canvas.drawLine(originX , originY, originX, originY - 600, paint);

      // The take-down:

      for(Integer value : bsList){

      }
    }
  }

}
