package com.example.automaticappareloutliner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

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
                    //TODO: Use an intent to allow the user to upload a photo from the local device gallery.
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fashionImage.setImageBitmap(imageBitmap);
            segmentedImage.setImageBitmap(imageBitmap);
            outlinedImage.setImageBitmap(imageBitmap);
        }
    }
}