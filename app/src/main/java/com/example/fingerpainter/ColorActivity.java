package com.example.fingerpainter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.RED;

public class ColorActivity extends AppCompatActivity {

    /*
    * Declaring Views
    * */
    private Button colorRedBtn,colorGreenBtn,colorSelectBtn,
            colorYellowBtn,colorBlackBtn,colorBlueBtn;
    private View colorViewer;
    String color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        //Allocating Views
        colorGreenBtn = findViewById(R.id.colorGreenBtn);
        colorRedBtn = findViewById(R.id.colorRedBtn);
        colorBlackBtn = findViewById(R.id.colorBlackBtn);
        colorBlueBtn = findViewById(R.id.colorBlueBtn);
        colorYellowBtn = findViewById(R.id.colorYellowBtn);
        colorSelectBtn = findViewById(R.id.colorSelectBtn);
        colorViewer = findViewById(R.id.colorViewer);

        /*Handles Intent*/
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }

        color = extras.getString("COLOR");
        colorViewer.setBackgroundColor(Integer.parseInt(color));


        /*
        * Set Color Red
        * */
        colorRedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                colorViewer.setBackgroundColor(RED);
                color = String.valueOf(RED);
            }
        });

        /*
        * Set Color Black
        * */
        colorBlackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                colorViewer.setBackgroundColor(Color.BLACK);
                color = String.valueOf(Color.BLACK);
            }
        });

        /*
        * Set color Blue
        * */
        colorBlueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorViewer.setBackgroundColor(Color.BLUE);
                color = String.valueOf(Color.BLUE);
            }
        });

        /*
        * Set Color Green
        * */
        colorGreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorViewer.setBackgroundColor(Color.GREEN);
                color = String.valueOf(Color.GREEN);
            }
        });


        /*
        * Set Color Yellow
        * */
        colorYellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorViewer.setBackgroundColor(Color.YELLOW);
                color = String.valueOf(Color.YELLOW);
            }
        });



        /*
        * Passes color selected to MainActivity to be used by user
        * */
        colorSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent colorData = new Intent();

                colorData.putExtra("COLOR",color);

                setResult(RESULT_OK,colorData);

                finish();
            }
        });




    }
}
