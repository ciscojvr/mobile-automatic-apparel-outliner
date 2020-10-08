package com.example.automaticappareloutliner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PROVIDE_IMAGE_FROM_GALLERY = 2;

    private String Document_img1="";

    ImageView fashionImage;
    ImageView segmentedImage;
    ImageView outlinedImage;

    Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fashionImage = (ImageView) findViewById(R.id.imageView_fashionImage);
        segmentedImage = (ImageView) findViewById(R.id.imageView_segmentedImage);
        outlinedImage = (ImageView) findViewById(R.id.imageView_outlinedImage);

        uploadButton = (Button) findViewById(R.id.button_upload);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add photo!");
        alertDialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Take Photo")) {
                    dispatchTakePictureIntent();
                } else if (options[which].equals("Choose from Gallery")) {
                    dispatchChoosePictureIntent();
                } else if (options[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        alertDialogBuilder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Activity Not Found Exception.", Toast.LENGTH_SHORT).show();
        }
    }

    private void dispatchChoosePictureIntent() {
        Intent choosePictureIntent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            startActivityForResult(choosePictureIntent, PROVIDE_IMAGE_FROM_GALLERY);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Activity Not Found Exception.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fashionImage.setImageBitmap(imageBitmap);
            segmentedImage.setImageBitmap(imageBitmap);
            outlinedImage.setImageBitmap(imageBitmap);
        } else if (requestCode == PROVIDE_IMAGE_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            fashionImage.setImageURI(selectedImage);
            segmentedImage.setImageURI(selectedImage);
            outlinedImage.setImageURI(selectedImage);

//            String[] filePath = { MediaStore.Images.Media.DATA };
//            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
//            c.moveToFirst();
//            int columnIndex = c.getColumnIndex(filePath[0]);
//            String picturePath = c.getString(columnIndex);
//            c.close();
//            Log.w("path of image from gallery......******************.........", picturePath+"");
//            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//            thumbnail=getResizedBitmap(thumbnail, 400);
//            Log.w("path of image from gallery......******************.........", picturePath+"");
//            fashionImage.setImageBitmap(thumbnail);
//            segmentedImage.setImageBitmap(thumbnail);
//            outlinedImage.setImageBitmap(thumbnail);
//            BitMapToString(thumbnail);
        }
    }

//    public String BitMapToString(Bitmap userImage1) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
//        byte[] b = baos.toByteArray();
//        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
//        return Document_img1;
//    }
//
//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float)width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }
}