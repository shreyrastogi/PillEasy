package com.mc2022.template.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mc2022.template.Adapters.PrescriptionAdapter;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.Models.PrescriptionModel;
import com.mc2022.template.R;
import com.mc2022.template.Utilities.SnackBarUtility;

public class Prescriptions_Activity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Button saveButton;
    SQLiteHelper helper;
    Bitmap photo;
    ImageView camera;
    String name_of_doctor;
    Button submit_btn;
    FloatingActionButton take_info;
    AlertDialog alertDialogPicture;
    EditText doctor_name;
    ActivityResultLauncher<Intent> some_activity;
    private RecyclerView objectRecyclerView;
    private Bitmap BitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptions);

        saveButton = findViewById(R.id.saveButton);
        take_info = findViewById(R.id.floating_btn);
        objectRecyclerView = findViewById(R.id.recyclerView_prescriptions);
        objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        helper = new SQLiteHelper(this);

        //INITIALIZING RECYCLER VIEW

        if (helper.getAllImagesData() != null) {
            PrescriptionAdapter objectRVAdapter = new PrescriptionAdapter(helper.getAllImagesData());
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setAdapter(objectRVAdapter);
        }

//        some_activity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == Activity.RESULT_OK) {
//                Intent data = result.getData();
//                assert data != null;
//                photo = (Bitmap) data.getExtras().get("data");
//                camera.setImageBitmap(photo);
//            }
//        });
        take_info.setOnClickListener(view -> choosePicture());
    }


    @SuppressLint("NewApi")
    private void choosePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Prescriptions_Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        doctor_name = dialogView.findViewById(R.id.doctor);
        submit_btn = dialogView.findViewById(R.id.submit_here);

        submit_btn.setOnClickListener(view -> {
            name_of_doctor = doctor_name.getText().toString();
            helper.storeImage(new PrescriptionModel(photo, name_of_doctor));
            PrescriptionAdapter adapter = new PrescriptionAdapter(helper.getAllImagesData());
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setAdapter(adapter);
            alertDialogPicture.cancel();
        });

        camera = dialogView.findViewById(R.id.camera);

        alertDialogPicture = builder.create();
        alertDialogPicture.show();
        alertDialogPicture.setCanceledOnTouchOutside(true);

        camera.setOnClickListener(view -> {

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            some_activity.launch(camera_intent);
                startActivityForResult(camera_intent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SnackBarUtility.ShowSnackbar("Camera permission granted", findViewById(R.id.prescription_layout));
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                SnackBarUtility.ShowSnackbar("Camera permission denied", findViewById(R.id.prescription_layout));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            camera.setImageBitmap(photo);
        }
    }
}