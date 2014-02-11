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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

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
      canvas.drawColor(Color.WHITE);

      // Set center coordinates (0,0)
      int centerX = 40, centerY = canvas.getHeight() - (canvas.getHeight() / 8);
      drawGrid(canvas, centerX, centerY);
      ArrayList<Integer> bs = getBS();
      int size = bs.size();
      if (size > 0){
        ArrayList<Integer> placeholder;     // This needs to be set initially as point A
        int diff = canvas.getWidth() / size;

        /////////////// Iterates through Blood Sugar (bs) ArrayList \\\\\\\\\\\\\\\
        for (int i = 0; i < size; i++){
          // Generate xy of point A (ArrayList<Integer>(x,y))
          // Generate xy of point B (ArrayList<Integer>(x,y))
          // Set placeholder to point B
        }
      }
    }

    /////////////// This sets up the graph \\\\\\\\\\\\\\\
    public void drawGrid( Canvas canvas, int centerX, int centerY){
      Paint axis = new Paint(Color.BLACK);
      Paint marker = new Paint(Color.BLUE);

      /////////////// Y- Axis \\\\\\\\\\\\\\\
      canvas.drawLine(centerX, centerY, centerX, centerY - 800, axis); // Draws the Y-Axis
      canvas.drawLine(centerX, centerY - 800, centerX + 10, centerY - 795, axis); // Y-Axis Arrow (Right)
      canvas.drawLine(centerX, centerY - 800, centerX - 10, centerY - 795, axis); // Y-Axis Arrow (Left)
      canvas.drawText("Blood Sugar (Rounded to nearest 5)", centerX, centerY - 810, axis);

      /////////////// X- Axis \\\\\\\\\\\\\\\
      canvas.drawLine(centerX, centerY, centerX + (canvas.getWidth() - 60),centerY, axis); // Draws the X-Axis
      canvas.drawLine(centerX + (canvas.getWidth() - 60),centerY,centerX + (canvas.getWidth() - 70), centerY + 10, axis); // Draws the X-Axis Arrow (Bottom)
      canvas.drawLine(centerX + (canvas.getWidth() - 60),centerY,centerX + (canvas.getWidth() - 70), centerY - 10, axis); // Draws the X-Axis Arrow (Top)

      /////////////// Y-Axis Grid markers \\\\\\\\\\\\\\\
      for(int i = 20; i < 800; i += 20){
        String bs = String.valueOf((i / 4));
        canvas.drawLine(centerX - 5, centerY - i, centerX + 5, centerY - i, marker);

          Paint danger = new Paint(Color.RED);
          canvas.drawText(bs, centerX - 30, (centerY -i)+5, danger);
      }
  }

    /////////////// This sets the array up \\\\\\\\\\\\\\\
    public ArrayList<Integer> getBS(){
      ArrayList<Integer> bs = new ArrayList<Integer>();
      File f = new File("sugar_log.csv");
      try{
        FileInputStream fis = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while ( (line = br.readLine()) != null){
          String[] rowData = line.split(",");
          bs.add(Integer.parseInt(rowData[4]));
        }
      } catch (Exception e){
        Toast.makeText(SugarChart.this, "Failed to load file.", Toast.LENGTH_SHORT).show();
      }
      return bs;
    }

    /////////////// This sets up the points and lines on the graph \\\\\\\\\\\\\\\
    public ArrayList<Integer> plotPoints(Canvas canvas, ArrayList<Integer> pointA, ArrayList<Integer> pointB ){
      // Draw Circle at pointA
      // Draw Circle at pointB
      // Draw Line from pointA to pointB
      return pointB;
    }
  }

}
