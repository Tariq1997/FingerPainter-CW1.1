package com.example.fingerpainter;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{

    private FingerPainterView myFingerPainterView;
    private Button colorBtn,loadImageBtn,brushBtn,clearBtn;
    private ImageView brushShape;
    private Uri imageUri;

    //Intializing Requests for referencing
    private static final int COLOR_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int BRUSH_REQUEST = 3;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myFingerPainterView = findViewById(R.id.myFingerPainterViewId);
        brushShape = findViewById(R.id.brushShape);

        colorBtn = findViewById(R.id.colorMainBtn);
        loadImageBtn = findViewById(R.id.loadImageBtn);
        brushBtn = findViewById(R.id.brushBtn);
        clearBtn = findViewById(R.id.clearBtn);

        myFingerPainterView.setColour(Color.GREEN);
        myFingerPainterView.setBrush(Paint.Cap.ROUND);
        myFingerPainterView.setBrushWidth(75);

        brushShape.setImageResource(R.drawable.round_brush);
        brushShape.setImageTintList(ColorStateList.valueOf(myFingerPainterView.getColour()));
        setShapeSize(myFingerPainterView.getBrushWidth());

        //Get image from storage
        imageUri = getIntent().getData();
        if(imageUri != null){
            myFingerPainterView.load(imageUri);
        }

        /*
        * When Select Color Clicked it redirects user to ColorActivity
        * passing current color of painting
        * */
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent colorIntent = new Intent(getApplicationContext(),ColorActivity.class);
                colorIntent.putExtra("COLOR", String.valueOf(myFingerPainterView.getColour()));
                startActivityForResult(colorIntent,COLOR_REQUEST);
            }
        });

        /*
        * When load clicked it allows user to select image from gallery
        *
        * */
        loadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_REQUEST);
            }
        });

        /*
        * Sends user to BrushActivity to select brush shape and size
        * */
        brushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent colorIntent = new Intent(getApplicationContext(),BrushActivity.class);
                colorIntent.putExtra("BRUSH_TYPE", String.valueOf(myFingerPainterView.getBrush()));
                colorIntent.putExtra("BRUSH_WIDTH", String.valueOf(myFingerPainterView.getBrushWidth()));
                startActivityForResult(colorIntent,BRUSH_REQUEST);
            }
        });

        /*
        * Clear viewer */
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                myFingerPainterView.clearCanvas();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        * Retrieves color integer from ColorActivity then setColour
        * */
        if(requestCode == COLOR_REQUEST)
        {
            if(resultCode == RESULT_OK && data != null)
            {
                String color = data.getStringExtra("COLOR");

                myFingerPainterView.setColour(Integer.parseInt(color));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    brushShape.setImageTintList(ColorStateList.valueOf(myFingerPainterView.getColour()));
                }


            }
        }
        /*
        * Handles Request of selecting Image
        * */
        else if(requestCode == PICK_IMAGE_REQUEST)
        {
            if(resultCode == RESULT_OK && data != null){
                Uri uri = data.getData();

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    //Passes image selected to Finger Painter View
                    myFingerPainterView.setImage(bitmap);

                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
        /*
        * Handles brush request
        * */
        else if(requestCode == BRUSH_REQUEST)
        {
            if(resultCode == RESULT_OK && data != null)
            {
                String brushType = data.getStringExtra("BRUSH_TYPE");
                String brushWidth = data.getStringExtra("BRUSH_WIDTH");
                setShape(brushType);
                setShapeSize(Integer.parseInt(brushWidth));
                if(brushType.equals("SQUARE"))
                {
                    myFingerPainterView.setBrush(Paint.Cap.SQUARE);
                }
                else if(brushType.equals("ROUND"))
                {
                    myFingerPainterView.setBrush(Paint.Cap.ROUND);
                }

                myFingerPainterView.setBrushWidth(Integer.parseInt(brushWidth));

            }
        }
    }
    /*
     * Shows brush shape
     * */
    private void setShape(String shape){
        if(shape.equals("SQUARE")){
            brushShape.setImageResource(R.drawable.square_brush);
        }else if(shape.equals("ROUND")){
            brushShape.setImageResource(R.drawable.round_brush);
        }
    }

    /*Shape size*/
    private void setShapeSize(int size)
    {
        ViewGroup.LayoutParams layoutParams = brushShape.getLayoutParams();
        layoutParams.height = (int) (size*1.5);
        layoutParams.width = (int) (size*1.5);
        brushShape.setLayoutParams(layoutParams);
    }
}
