package com.example.fingerpainter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class BrushActivity extends AppCompatActivity
{
    private ImageView roundImage,squareImage,brushImage;
    private EditText brushSize;
    private TextView textViewBrush;
    private Button applyShapeBtn;
    private String brushShape,brushWidth,shapeSize;
    private SeekBar seekBarSize;

    int min = 50, current = 100, max = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        roundImage = findViewById(R.id.roundBrushImage);
        squareImage = findViewById(R.id.squareBrushImage);
        brushImage = findViewById(R.id.brushImage);

        brushSize = findViewById(R.id.brushSize);
        applyShapeBtn = findViewById(R.id.applyShapeBtn);
        seekBarSize = findViewById(R.id.seekBsrBrushSize);
        textViewBrush = findViewById(R.id.textViewBrush);


        //Hide change size options onCreate of Activity
        hideSizeOption();

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }

        //Gets current brush Size and brush Shape
        shapeSize = extras.getString("BRUSH_WIDTH");

        //set the value to current brush size
        current = Integer.parseInt(shapeSize);
        brushSize.setText(shapeSize);

        seekBarSize.setMax(max - min);//set the maximum length of the bar
        seekBarSize.setProgress(current - min);//set the current position of the bar

        seekBarSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                current = progress + min;
                brushSize.setText(""+current);
                changeShapeSize(current);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        /*
        * Set brush to square shape
        * */
        squareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                brushShape = "SQUARE";
                showSizeOption();
                changeShape(brushShape);
            }
        });

        /*
         * Set brush to round shape
         * */
        roundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                brushShape = "ROUND";
                showSizeOption();
                changeShape(brushShape);
            }
        });

        /*
        * Change brush Size when user inputs value
        * */
        brushSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(brushSize.getText().length() != 0){
                    current = Integer.parseInt(brushSize.getText().toString());
                    if(current > max){
                        current = max;

                    }else if(current < min){
                        current = min;
                    }
                    seekBarSize.setProgress(current - min);
                    changeShapeSize(current);

                }
            }
        });


        /*
        * Apply changes to brush in MainActivity
        * */
        applyShapeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                brushWidth = String.valueOf(current);
                Intent brushData = new Intent();

                brushData.putExtra("BRUSH_TYPE",brushShape);
                brushData.putExtra("BRUSH_WIDTH",brushWidth);

                setResult(RESULT_OK,brushData);
                finish();
            }
        });

    }

    /*
    * Change Size of brush in app according to values provided
    * */
    private void changeShapeSize(int size)
    {
        ViewGroup.LayoutParams layoutParams = brushImage.getLayoutParams();
        layoutParams.height = (int) (size*1.5);
        layoutParams.width = (int) (size*1.5);
        brushImage.setLayoutParams(layoutParams);
    }

    /*
    * Hide all options for changing brush size until user selects shape
    * */
    private void hideSizeOption()
    {
        textViewBrush.setVisibility(View.INVISIBLE);
        seekBarSize.setVisibility(View.INVISIBLE);
        applyShapeBtn.setVisibility(View.INVISIBLE);
        brushSize.setVisibility(View.INVISIBLE);
    }

    /*
    * Displays all options to change brush size after user selects brush shape
    * */
    private void showSizeOption()
    {
        textViewBrush.setVisibility(View.VISIBLE);
        seekBarSize.setVisibility(View.VISIBLE);
        applyShapeBtn.setVisibility(View.VISIBLE);
        brushSize.setVisibility(View.VISIBLE);
        changeShapeSize(current);

    }

    /*
    * Keeps only selected brush shape and removes the other option
    * */
    private void changeShape(String shape){
        if(shape.equals("SQUARE")){
            brushImage.setImageResource(R.drawable.square_brush);
        }else if(shape.equals("ROUND")){
            brushImage.setImageResource(R.drawable.round_brush);
        }
        brushImage.setVisibility(View.VISIBLE);
        hideShape();
    }


    private void hideShape()
    {
     roundImage.setVisibility(View.GONE);
     squareImage.setVisibility(View.GONE);
    }


}
