package com.mc2022.template.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.textfield.TextInputLayout;
import com.mc2022.template.Adapters.adapter_medicine_intake;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.Models.Dosage;
import com.mc2022.template.Models.Medicine;
import com.mc2022.template.Models.helper_medicine_intake;
import com.mc2022.template.R;
import com.mc2022.template.Utilities.SnackBarUtility;
import com.mc2022.template.Utilities.Utilities;
import com.mc2022.template.Utilities.date_picker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

public class AddMedicine extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener, adapter_medicine_intake.ListItemClickListener, AdapterView.OnItemSelectedListener {

    private static final int REQUEST_CAMERA_CODE = 100;
    static int dose_count = 0;
    RecyclerView medRecycler;
    Adapter adapter;
    CardView cardView1;
    TextView my_week, day_interval, my_day, start_d, end_d, time_info;
    AppCompatEditText med_name;
    TextInputLayout outlinedTextField1;
    ImageView img_btn, cancel_btn;
    EditText dose_info;
    LinearLayout layout;
    Button add_btn, save_btn;
    TimePickerDialog timePickerDialog;
    Spinner spinner;
    ArrayList<helper_medicine_intake> medicine_intake_type_list = new ArrayList<>();
    ArrayList<String> time_stamps = new ArrayList<>();
    ArrayList<String> my_doses = new ArrayList<>();
    String intake_type = "", current, days, time1, dose1, month1, day1, previous_medicine_name;
    String[] options;
    Bitmap photo;
    int interval = -10, year, update_flag = 0, showNumberPickerFlag = 0;
    SQLiteHelper helper;

    // For CV
    Bitmap bitmap;
    Uri uri;
    private ArrayList<String> days_of_week, medicine_intake_mappings;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        helper = new SQLiteHelper(this);

        my_week = findViewById(R.id.textView6);
        day_interval = findViewById(R.id.textView5);
        layout = findViewById(R.id.linear_list);
        add_btn = findViewById(R.id.set_btn);
        img_btn = findViewById(R.id.imageButton);
        medRecycler = findViewById(R.id.medicine_intake_type_recycler_id);
        cardView1 = findViewById(R.id.medicine_intake_card_id);
        start_d = findViewById(R.id.textView3);
        end_d = findViewById(R.id.textView4);
        med_name = findViewById(R.id.medi_name);
        outlinedTextField1 = findViewById(R.id.medicinename_outlinedTextField1);
        save_btn = findViewById(R.id.save_button);

        spinner = findViewById(R.id.interval_days_spinner);
        spinner.setOnItemSelectedListener(this);
        options = new String[]{"Specific Days of Week", "Interval"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, options);
        adapter1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        medicine_intake_mappings = new ArrayList<>();
        medicine_intake_mappings.add("Pills");
        medicine_intake_mappings.add("Injection");
        medicine_intake_mappings.add("Spray");
        medicine_intake_mappings.add("Drops");
        medicine_intake_mappings.add("Inhaler");
        medicine_intake_mappings.add("Powder");
        medicine_intake_mappings.add("Solution");
        medicine_intake_mappings.add("Cream");

        if (ContextCompat.checkSelfPermission(AddMedicine.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddMedicine.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            update_flag = 1;
            String medicine_name = extras.getString("MedicineName");
            previous_medicine_name = medicine_name;

            Log.e("Medicine name", medicine_name);
            Medicine medicine = helper.getMedicine(medicine_name);
            Log.e("AddMedicine", String.valueOf(medicine));

            med_name.setText(medicine.getMedicine_name());
            phoneRecycler(medicine.getMedicine_type());

            ArrayList<Dosage> dosages = medicine.getDoses();
            for (Dosage dosage : dosages) {
                add_view(dosage.getTiming(), String.valueOf(dosage.getDose()));
                my_doses.add(String.valueOf(dosage.getDose()));
                time_stamps.add(dosage.getTiming());
                dose_count = dose_count + 1;
            }

            start_d.setText(medicine.getStartdate());
            end_d.setText(medicine.getEnddate());


            if (medicine.getDuration() == -1) {
                day_interval.setVisibility(View.INVISIBLE);
                my_week.setVisibility(View.VISIBLE);
                spinner.setSelection(0);
                String[] d1 = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                ArrayList<String> days = medicine.getDays();
                Collections.sort(days);
                String all_days = "";
                int count = 0;
                for (String day : days) {
                    if (count == 0) {
                        all_days = all_days + d1[Integer.parseInt(day)];
                    } else {
                        all_days = all_days + ", " + d1[Integer.parseInt(day)];
                    }
                    count++;
                }
                all_days = "Every " + all_days;
                my_week.setText(all_days);
                days_of_week = medicine.getDays();

                days_of_week = Utilities.convertAllDays(days_of_week);

            } else {
                day_interval.setVisibility(View.VISIBLE);
                my_week.setVisibility(View.INVISIBLE);
                spinner.setSelection(1);
                day_interval.setText("Every " + medicine.getDuration() + " days");
                interval = medicine.getDuration();
                show(medicine.getDuration());
            }

            save_btn.setText("UPDATE");

        } else {
            update_flag = 0;
            add_view("", "");
            dose_count = dose_count + 1;
            //call to recycler view from here
            phoneRecycler("");
        }

        med_name.setOnFocusChangeListener((view, b) -> {
            if (med_name.getText().toString().isEmpty()) {
                outlinedTextField1.setErrorEnabled(true);
                outlinedTextField1.setError("*Required");
            } else {
                outlinedTextField1.setErrorEnabled(false);
            }
        });


        med_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (med_name.getText().toString().isEmpty()) {
                    outlinedTextField1.setErrorEnabled(true);
                    outlinedTextField1.setError("*Required");
                } else {
                    outlinedTextField1.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        day_interval.setOnClickListener(view -> {
            //my_week.setFocusable(false);
            my_week.setVisibility(View.INVISIBLE);
            show(-1);
        });

        my_week.setOnClickListener(view -> {
            day_interval.setVisibility(View.INVISIBLE);
            DayPicker_dialog();
        });

        add_btn.setOnClickListener(view -> {
            if (dose_count > 0) {
                String dose_num = dose_info.getText().toString();
                String time_num = time_info.getText().toString();
                Log.d("TIME", time_num);
                Log.d("DOSE", dose_num);
                if (time_num.length() == 0) {
                    SnackBarUtility.ShowSnackbar("Please Enter a Time!", findViewById(R.id.add_medicine_layout));
                } else if (dose_num.length() == 0) {
                    SnackBarUtility.ShowSnackbar("Please Enter a Dose!", findViewById(R.id.add_medicine_layout));
                } else {
                    //Changed from here
                    if (!time_stamps.contains(time_num)) {
                        time_stamps.add(time_num);
                        my_doses.add(dose_num);
                    } else {
                        SnackBarUtility.ShowSnackbar("Please Set reminder for some other time", findViewById(R.id.add_medicine_layout));
                    }
                    // my_doses.add(dose_num);
                    // Changed until here
                    dose_count = dose_count + 1;
                    add_view("", "");
                }
            } else {
                dose_count = dose_count + 1;
                add_view("", "");
            }
        });

//        ActivityResultLauncher<Intent> some_activity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == Activity.RESULT_OK) {
//                Intent data = result.getData();
//                assert data != null;
//                photo = (Bitmap) data.getExtras().get("data");
//                img_btn.setImageBitmap(photo);
//            }
//        });

        img_btn.setOnClickListener(view -> {
//            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            some_activity.launch(camera_intent);
            if (CropImage.isExplicitCameraPermissionRequired(this)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
            } else {
                CropImage.startPickImageActivity(AddMedicine.this);
            }
        });
    }

    public void on_click_save_button(View view) {
        int duration;
        String medicine = med_name.getText().toString();
        String starting = start_d.getText().toString();
        String ending = end_d.getText().toString();

        if (spinner.getSelectedItemPosition() == 0) {
            duration = -1;
        } else {
            duration = interval;
        }

        // Capture Last Dose
        String dose_num = dose_info.getText().toString();
        String time_num = time_info.getText().toString();
        Log.d("TIME", time_num);
        Log.d("DOSE", dose_num);
        if (time_num.length() == 0) {
            SnackBarUtility.ShowSnackbar("Please Enter a Time!", findViewById(R.id.add_medicine_layout));
        } else if (dose_num.length() == 0) {
            SnackBarUtility.ShowSnackbar("Please Enter a Dose!", findViewById(R.id.add_medicine_layout));
        } else {
            //Changed from here
            if (!time_stamps.contains(time_num)) {
                time_stamps.add(time_num);
                my_doses.add(dose_num);
            } else {
                SnackBarUtility.ShowSnackbar("Please Set reminder for some other time", findViewById(R.id.add_medicine_layout));
            }
            // my_doses.add(dose_num);
            // time_stamps.add(time_num);
            // Changed until here

            dose_count = dose_count + 1;
        }

        ArrayList<Dosage> dosages = new ArrayList<>();
        for (int x = 0; x < my_doses.size(); x++) {
            dosages.add(new Dosage(medicine, time_stamps.get(x), Integer.parseInt(my_doses.get(x))));
        }

        Log.e("medicine_name", medicine);
        Log.e("Intake Type", intake_type);
        Log.e("starting", starting);
        Log.e("ending", ending);
        Log.e("duration", String.valueOf(duration));
        Log.e("interval", String.valueOf(interval));
        Log.e("Dosages", String.valueOf(dosages.size()));
        Log.e("Days", String.valueOf(days_of_week == null));


        if (medicine.isEmpty() || starting.isEmpty() || ending.isEmpty() || intake_type.isEmpty() || dosages.size() == 0 || (duration == -1 && days_of_week == null) || (duration == (-10))) {
            SnackBarUtility.ShowSnackbar("All Fields are neccesary", findViewById(R.id.add_medicine_layout));
        } else {
            if (ending.equals("Endless")) {
                ending = "";
                year = year + 1;
                ending = ending + year + "-" + month1 + "-" + day1;
                end_d.setText(ending);
            }

            Medicine temp = new Medicine(medicine, intake_type, duration, dosages, starting, ending, false, days_of_week);
            if (update_flag == 1) {
                helper.deleteMedicine(previous_medicine_name);
                helper.addMedicine(temp);
                SnackBarUtility.ShowSnackbar("Medicine Updated Successfully", findViewById(R.id.add_medicine_layout));
            } else {
                if (!helper.medicineAlreadyPresent(temp.getMedicine_name())) {
                    SnackBarUtility.ShowSnackbar("Medicine Already Present", findViewById(R.id.add_medicine_layout));
                } else {
                    helper.addMedicine(temp);
                    SnackBarUtility.ShowSnackbar("Medicine Added Successfully", findViewById(R.id.add_medicine_layout));
                }
            }

            Utilities.setAlarms(this, 1);
            Intent intent = new Intent(AddMedicine.this, MainActivity.class);
            startActivity(intent);
            finish();

//            if (!helper.medicineAlreadyPresent(medicine)) {
//                SnackBarUtility.ShowSnackbar("Medicine Already Present", findViewById(R.id.add_medicine_layout));
//            } else {
//
//                // Reading image
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                Log.e("photo", String.valueOf(photo));
//                if (photo != null) {
//                    photo.compress(Bitmap.CompressFormat.PNG, 0, stream);
//                    byte[] picture = stream.toByteArray();
////                    Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
////                    FileUtilities.saveMedicineImage(this, bitmap, medicine+".jpg");
//                }
//
//                Medicine temp = new Medicine(medicine, intake_type, duration, dosages, starting, ending, false, days_of_week);
//
//                if (update_flag == 1) {
//                    helper.deleteMedicine(previous_medicine_name);
//                    helper.addMedicine(temp);
//                    SnackBarUtility.ShowSnackbar("Medicine Updated Successfully", findViewById(R.id.add_medicine_layout));
//                } else {
//                    helper.addMedicine(temp);
//                    SnackBarUtility.ShowSnackbar("Medicine Added Successfully", findViewById(R.id.add_medicine_layout));
//                }
//
//                Intent intent = new Intent(AddMedicine.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
        }
    }

    public void DayPicker_dialog() {

        if (days_of_week == null) {
            days_of_week = new ArrayList<>();
        }

        // Set days item
        boolean[] d2 = new boolean[]{false, false, false, false, false, false, false};
        for (int i = 0; i < days_of_week.size(); i++) {
            d2[Utilities.convertDay(days_of_week.get(i))] = true;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("On specific days of the week");

        Log.e("Days option", Arrays.toString(d2));
        builder.setMultiChoiceItems(R.array.Days, d2, (dialogInterface, i, b) -> {
            String[] my_day = getResources().getStringArray(R.array.Days);
            if (b)       //if some item is checked
            {
                days_of_week.add(my_day[i]);
            } else days_of_week.remove(my_day[i]);
        });

        builder.setPositiveButton("Set", (dialogInterface, i) -> {
            String this_day = "";
            int count = 0;
            Log.e("Modified days", String.valueOf(days_of_week));
            for (String d : days_of_week) {
                if (count == 0) {
                    this_day = this_day + d;
                } else {
                    this_day = this_day + ", " + d;
                }
                count = count + 1;
            }
            this_day = "Every " + this_day;
            my_week.setText(this_day);
        });
        builder.create();
        builder.show();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Log.i("value is", "" + i1);
    }

    public void show(int value) {
        final Dialog dia = new Dialog(AddMedicine.this);
        dia.setContentView(R.layout.dialog);
        Button set_button = dia.findViewById(R.id.set_it);
        final NumberPicker numberPicker = dia.findViewById(R.id.num_pick);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(this);
        set_button.setOnClickListener(v -> {
            interval = numberPicker.getValue();
            days = String.valueOf(numberPicker.getValue());
            days = "Every " + days + " days";
            day_interval.setText(days);
            dia.dismiss();
        });
        if (interval != -1) {
            Log.e("Numberpicker value", String.valueOf(value));
            numberPicker.setValue(interval);
        }
        if (showNumberPickerFlag != 0) {
            dia.show();
            Window window = dia.getWindow();
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        }
        showNumberPickerFlag = 1;
    }

    private void phoneRecycler(String selectedMedicineType) {

        medRecycler.setHasFixedSize(true);
        medRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        medicine_intake_type_list.add(new helper_medicine_intake(R.drawable.pills, "Pills"));
        medicine_intake_type_list.add(new helper_medicine_intake(R.drawable.syringe, "Injection"));
        medicine_intake_type_list.add(new helper_medicine_intake(R.drawable.spray, "Spray"));
        medicine_intake_type_list.add(new helper_medicine_intake(R.drawable.dropper, "Drops"));
        medicine_intake_type_list.add(new helper_medicine_intake(R.drawable.inhaler, "Inhaler"));
        medicine_intake_type_list.add(new helper_medicine_intake(R.drawable.powder, "Powder"));
        medicine_intake_type_list.add(new helper_medicine_intake(R.drawable.med_solution, "Solution"));
        medicine_intake_type_list.add(new helper_medicine_intake(R.drawable.cream_gel_ointment, "Cream"));
        adapter = new adapter_medicine_intake(medicine_intake_type_list, this);
        medRecycler.setAdapter(adapter);

        if (!selectedMedicineType.equals("")) {
            new Handler().postDelayed(() -> medRecycler.findViewHolderForAdapterPosition(medicine_intake_mappings.indexOf(selectedMedicineType)).itemView.performClick(), 1);
        }
    }

    @Override
    public void onphoneListClick(int clickedItemIndex) {
        intake_type = medicine_intake_type_list.get(clickedItemIndex).getTitle();
        Log.d("my_med", intake_type);
    }

    public void date_click(View view) {
        if (view.getId() == R.id.textView3) {
            my_day = findViewById(R.id.textView3);
            DialogFragment date_pick = new date_picker();
            date_pick.show(getSupportFragmentManager(), "date_picker");
        } else {
            my_day = findViewById(R.id.textView4);
            DialogFragment date_pick = new date_picker();
            date_pick.show(getSupportFragmentManager(), "date_picker");
        }
    }

    public void add_view(String time, String dose) {

        Log.e("New Slot added", time_stamps == null ? "Empty" : time_stamps.toString());
        Log.e("New Slot added", my_doses == null ? "Empty" : my_doses.toString());

        @SuppressLint("InflateParams") final View time_list = getLayoutInflater().inflate(R.layout.time_dose, null, false);
        time_info = time_list.findViewById(R.id.time);
        dose_info = time_list.findViewById(R.id.dose);

        if (!time.equals("") && !dose.equals("")) {
            time_info.setText(time);
            dose_info.setText(dose);
        }

        ImageView cancel_btn = time_list.findViewById(R.id.cancel);
        time_info.setOnClickListener(view -> {
            timePickerDialog = new TimePickerDialog(AddMedicine.this, (timePicker, i, i1) -> {
//                String hour = Integer.toString(i);
//                String minutes = Integer.toString(i1);
                int hour1 = i;
                int minutes1 = i1;
                //TIME FOR EACH DOSE IS HERE
                String my_time_is_here = (hour1 < 10 ? "0" + hour1 : hour1) + ":" + (minutes1 < 10 ? "0" + minutes1 : minutes1);

                if (!time_stamps.contains(my_time_is_here)) {
                    // time_stamps.add(my_time_is_here);
                    time_info.setText(my_time_is_here);
                } else {
                    SnackBarUtility.ShowSnackbar("Please Set reminder for some other time", findViewById(R.id.add_medicine_layout));
                }
            }, 0, 0, false);
            timePickerDialog.show();
        });

        cancel_btn.setOnClickListener(view -> remove_dose(time_list));
        layout.addView(time_list);
    }

    public void remove_dose(View view) {
        TextView my_time = view.findViewById(R.id.time);
        EditText my_dose = view.findViewById(R.id.dose);
        time1 = my_time.getText().toString();
        Log.d("Error_here", time1);
        dose_count--;
        dose1 = my_dose.getText().toString();

        if ((!dose1.equals("Dose")) && (!time1.equals("Time")) && time1.length() != 0 && dose_count != 0) {
            int index = time_stamps.indexOf(time1);
            time_stamps.remove(index);
            my_doses.remove(index);
        }
        layout.removeView(view);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, i2);
        cal.set(Calendar.YEAR, i);
        cal.set(Calendar.MONTH, i1);
        i1 = i1 + 1;
        year = i;
        month1 = i1 < 10 ? "0" + i1 : String.valueOf(i1);
        day1 = i2 < 10 ? "0" + i2 : String.valueOf(i2);
        current = i + "-" + month1 + "-" + day1;
        Log.e("Date", current);
        my_day.setText(current);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            day_interval.setVisibility(View.INVISIBLE);
            my_week.setVisibility(View.VISIBLE);
        } else {
            day_interval.setVisibility(View.VISIBLE);
            my_week.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Activity Result", "Here");
        Log.e("Data", String.valueOf(data));
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.e("Image Picker", "here");
            Uri ImageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(AddMedicine.this, ImageUri)) {
                Log.e("Image Picker", "here1");
                uri = ImageUri;
                ActivityCompat.requestPermissions(AddMedicine.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                CropImage.activity(ImageUri).setGuidelines(CropImageView.Guidelines.ON)
                        .setMultiTouchEnabled(true)
                        .start(AddMedicine.this);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.e("Image Cropper", "Cropper here");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri resultUri = result.getUri();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                getTextFromImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(this);
            } else {
                SnackBarUtility.ShowSnackbar("Cancelling, required permissions are not granted", findViewById(R.id.add_medicine_layout));
            }
        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (uri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                        .setMultiTouchEnabled(true)
                        .start(AddMedicine.this);
            } else {
                SnackBarUtility.ShowSnackbar("Cancelling, required permissions are not granted", findViewById(R.id.add_medicine_layout));
            }
        }
    }

    private void getTextFromImage(Bitmap bitmap) {
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (!recognizer.isOperational()) {
            SnackBarUtility.ShowSnackbar("Error Occurred while processing image", findViewById(R.id.add_medicine_layout));
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < textBlockSparseArray.size(); i++) {
                TextBlock temp = textBlockSparseArray.get(i);
                builder.append(temp.getValue());
            }
            img_btn.setImageBitmap(bitmap);
            med_name.setText(builder.toString());
        }
    }
}