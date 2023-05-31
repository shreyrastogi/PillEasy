package com.mc2022.template.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.mc2022.template.Models.Dosage;
import com.mc2022.template.Models.Medicine;
import com.mc2022.template.Models.PrescriptionModel;
import com.mc2022.template.Models.UserBPSugar;
import com.mc2022.template.Utilities.Utilities;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mc_project_new1.db";

    /* MEDICINE TABLE */
    public static final String TABLE_NAME_MEDICINE = "MEDICINE";
    public static final String COLUMN_MEDICINE_NAME = "MEDICINE_NAME";
    public static final String COLUMN_MEDICINE_TYPE = "MEDICINE_TYPE";
    public static final String COLUMN_STARTDATE = "STARTDATE";
    public static final String COLUMN_ENDDATE = "ENDDATE";
    public static final String COLUMN_DURATION = "DURATION";
    public static final String COLUMN_NOTIFICATIONS = "NOTIFICATIONS";

    /*DOSAGE TABLE*/
    public static final String TABLE_NAME_DOSAGE = "DOSAGE";
    public static final String COLUMN_MEDICINE_NAME1 = "MEDICINE_NAME";
    public static final String COLUMN_TIMING = "TIMING";
    public static final String COLUMN_DOSE = "DOSE";

    /*SPECIFIC DAY TABLE*/
    public static final String TABLE_NAME_MEDICINEDAY = "SPECIFIC_DAY_MEDICINES";
    public static final String COLUMN_MEDICINE_NAME2 = "MEDICINE_NAME";
    public static final String COLUMN_DAY_OF_WEEK = "DAY_OF_WEEK";

    /*USER TABLE*/
    public static final String TABLE_NAME_USER = "USER";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";


    Context context;
    private SQLiteDatabase database;
    private byte[] imageInBytes;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table;

        create_table = "CREATE TABLE " + TABLE_NAME_MEDICINE + " ( " +
                COLUMN_MEDICINE_NAME + " VARCHAR PRIMARY KEY ," +
                COLUMN_MEDICINE_TYPE + " VARCHAR , " +
                COLUMN_STARTDATE + " DATE , " +
                COLUMN_ENDDATE + " DATE , " +
                COLUMN_DURATION + " INTEGER , " +
                COLUMN_NOTIFICATIONS + " INTEGER);";

        Log.e("Medicine table", create_table);
        db.execSQL(create_table);

        create_table = "CREATE TABLE " + TABLE_NAME_DOSAGE + " ( " +
                COLUMN_MEDICINE_NAME1 + " VARCHAR ," +
                COLUMN_TIMING + " TIME , " +
                COLUMN_DOSE + " INTEGER);";

        Log.e("Dosage table", create_table);
        db.execSQL(create_table);

        create_table = "CREATE TABLE " + TABLE_NAME_MEDICINEDAY + " ( " +
                COLUMN_MEDICINE_NAME2 + " VARCHAR ," +
                COLUMN_DAY_OF_WEEK + " VARCHAR);";

        Log.e("Specific Day table", create_table);
        db.execSQL(create_table);

        create_table = "CREATE TABLE users(email TEXT PRIMARY KEY, name TEXT, password TEXT)";
        Log.e("User table", create_table);
        db.execSQL(create_table);

        create_table = "CREATE TABLE imageInfo (id INTEGER PRIMARY KEY AUTOINCREMENT" + ",image BLOB, doc_name TEXT)";
        Log.e("Image table", create_table);
        db.execSQL(create_table);

        create_table = "CREATE TABLE firstTime (id INTEGER);";
        db.execSQL(create_table);

        create_table = "create table daily_detail (weight TEXT, bp TEXT, sugar TEXT, dateMain TEXT)";
        db.execSQL(create_table);

        // INSERTION QUERIES
//        db.execSQL("insert into medicine values( 'Medicine1', 'Pills', '2022-03-20', '2022-03-30', 1, 0)");
//        db.execSQL("insert into medicine values( 'Medicine2', 'Injection', '2022-03-01', '2023-03-01', 1, 0)");
//        db.execSQL("insert into medicine values( 'EyeDrop', 'Drops', '2022-03-01', '2022-04-30', 4, 0)");
//        db.execSQL("insert into medicine values( 'Asthama Inhaler', 'Inhaler', '2022-03-01', '2022-04-30', -1, 0)");
//
//        db.execSQL("insert into dosage values ('Medicine1', '09:00', 1)");
//        db.execSQL("insert into dosage values ('Medicine1', '18:00', 2)");
//        db.execSQL("insert into dosage values ('Medicine2', '09:00', 1)");
//        db.execSQL("insert into dosage values ('Medicine2', '15:00', 1)");
//        db.execSQL("insert into dosage values ('EyeDrop', '08:00', 2)");
//        db.execSQL("insert into dosage values ('EyeDrop', '20:00', 2)");
//        db.execSQL("insert into dosage values ('Asthama Inhaler', '09:45', 1)");
//        db.execSQL("insert into dosage values ('Asthama Inhaler', '21:45', 1)");
//
//        db.execSQL("insert into specific_day_medicines values ( 'Asthama Inhaler', 2)");
//        db.execSQL("insert into specific_day_medicines values ( 'Asthama Inhaler', 4)");
//        db.execSQL("insert into specific_day_medicines values ( 'Asthama Inhaler', 6)");

        db.execSQL("INSERT INTO firstTime values (0)");

//        db.execSQL("INSERT INTO daily_detail VALUES (10, 20, 30, '2022-03-15')");
//        db.execSQL("INSERT INTO daily_detail VALUES (14, 45, 35, '2022-03-16')");
//        db.execSQL("INSERT INTO daily_detail VALUES (16, 37, 14, '2022-03-17')");
//        db.execSQL("INSERT INTO daily_detail VALUES (73, 76, 45, '2022-03-18')");
//        db.execSQL("INSERT INTO daily_detail VALUES (19, 28, 34, '2022-03-19')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEDICINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DOSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEDICINEDAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS imageInfo");
        db.execSQL("DROP TABLE IF EXISTS daily_detail");
        db.execSQL("DROP TABLE IF EXISTS firstTime");
        onCreate(db);
    }

    public String addrecord(String weight, String bp, String sugar, String dateMain) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("weight", weight);
        cv.put("bp", bp);
        cv.put("sugar", sugar);
        cv.put("dateMain", dateMain);
        int res = (int) db.insert("daily_detail", null, cv);

        if (res == -1) {
            return "Failed!";
        } else {
            return "Successfully Inserted!";
        }
    }

    public boolean checkDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM daily_detail WHERE dateMain = ?", new String[]{date});
        return cursor.getCount() > 0;
    }

    public ArrayList<UserBPSugar> readalldata() {
        ArrayList<UserBPSugar> dataholder;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM daily_detail ORDER BY dateMain DESC";
        Cursor cursor = db.rawQuery(qry, null);
        dataholder = new ArrayList<>();

        while (cursor.moveToNext()) {
            UserBPSugar userBPSugar = new UserBPSugar(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataholder.add(userBPSugar);
        }
        return dataholder;
    }

    public ArrayList<UserBPSugar> readalldatainRange(String startDate, String endDate) {
        ArrayList<UserBPSugar> dataholder;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM daily_detail WHERE dateMain BETWEEN '" + startDate + "' AND '" + endDate + "' ORDER BY dateMain DESC";
        Cursor cursor = db.rawQuery(qry, null);
        dataholder = new ArrayList<>();

        while (cursor.moveToNext()) {
            UserBPSugar userBPSugar = new UserBPSugar(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataholder.add(userBPSugar);
        }
        return dataholder;
    }

    public boolean checkFirst() {
        database = this.getReadableDatabase();
        String query = "SELECT * FROM firstTime WHERE id = 0";
        @SuppressLint("Recycle") Cursor c = database.rawQuery(query, null);
        int count = c.getCount();
        database.close();
        return count == 1;
    }

    public void markFirst() {
        database = this.getReadableDatabase();
        String query = "UPDATE firstTime SET id = 1 WHERE id = 0";
        database.execSQL(query);
        database.close();
    }

    public String[] getDateRange() {
        String[] dates = {"", ""};
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT MIN(startdate), MAX(enddate) FROM medicine;", null);
        Log.e("count of rows", String.valueOf(c.getCount()));
        c.moveToFirst();
        dates[0] = c.getString(0);
        dates[1] = c.getString(1);
        if (dates[0] == null && dates[1] == null) {
            dates = null;
        } else {
            Log.e("getDateRange:first", dates[0]);
            Log.e("getDateRange:last", dates[1]);
            c.close();
        }
        database.close();
        return dates;
    }

    public ArrayList<Medicine> getMedicines(String date) {
        database = this.getReadableDatabase();

        String query = "SELECT md.medicine_name, md.medicine_type, dose.timing, dose.dose \n" +
                "FROM medicine md\n" +
                "INNER JOIN dosage dose\n" +
                "ON md.medicine_name = dose.medicine_name\n" +
                "WHERE '" + date + "' BETWEEN md.startdate AND md.enddate\n" +
                "AND md.duration != -1 AND ((JulianDay('" + date + "') - JulianDay(md.startdate)) % md.duration) = 0\n" +
                "UNION\n" +
                "SELECT md.medicine_name, md.medicine_type, dose.timing, dose.dose \n" +
                "FROM medicine md\n" +
                "INNER JOIN dosage dose\n" +
                "ON md.medicine_name = dose.medicine_name\n" +
                "INNER JOIN specific_day_medicines sdm\n" +
                "ON md.medicine_name = sdm.medicine_name\n" +
                "WHERE '" + date + "' BETWEEN md.startdate AND md.enddate\n" +
                "AND md.duration = -1\n" +
                "AND sdm.day_of_week = strftime('%w', '" + date + "');";
        Cursor cursor = database.rawQuery(query, null);
        Log.e("count of medicines", String.valueOf(cursor.getCount()));
        ArrayList<Medicine> medicines = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                medicines.add(new Medicine(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            }
        }
        cursor.close();
        database.close();
        return medicines;
    }

    public boolean medicineAlreadyPresent(String medicine_name) {
        database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_MEDICINE + " WHERE medicine_name = '" + medicine_name + "';";
        Cursor c = database.rawQuery(query, null);
        int count = c.getCount();
        database.close();
        return count == 0;
    }

    public void deleteMedicine(String medicine_name) {
        database = this.getReadableDatabase();

        // Deleting from the medicine table
        String query = "DELETE FROM " + TABLE_NAME_MEDICINE + " WHERE medicine_name = '" + medicine_name + "';";
        Log.e("deleteMedicine:medicine", query);
        database.execSQL(query);

        //Deleting from the Dosage table
        query = "DELETE FROM " + TABLE_NAME_DOSAGE + " WHERE medicine_name = '" + medicine_name + "';";
        Log.e("deleteMedicine:dosage", query);
        database.execSQL(query);

        //Deleting from the Specific_day_medicine table
        query = "DELETE FROM " + TABLE_NAME_MEDICINEDAY + " where medicine_name = '" + medicine_name + "';";
        Log.e("deleteMedicine:specific", query);
        database.execSQL(query);
        database.close();
    }

    public void addMedicine(Medicine medicine) {
        database = this.getReadableDatabase();
        int notify = medicine.getNotifications() ? 1 : 0;
        String query = "INSERT INTO " + TABLE_NAME_MEDICINE + " VALUES (" +
                "'" + medicine.getMedicine_name() + "'," +
                "'" + medicine.getMedicine_type() + "'," +
                "'" + medicine.getStartdate() + "'," +
                "'" + medicine.getEnddate() + "'," +
                medicine.getDuration() + "," +
                notify + ");";
        Log.e("addMedicine-medicine", query);
        database.execSQL(query);

        ArrayList<Dosage> dosages = medicine.getDoses();
        for (int i = 0; i < dosages.size(); i++) {
            Dosage dosage = dosages.get(i);
            query = "INSERT INTO " + TABLE_NAME_DOSAGE + " VALUES (" +
                    "'" + dosage.getMedicine_name() + "'," +
                    "'" + dosage.getTiming() + "'," +
                    dosage.getDose() + ");";
            Log.e("addMedicine-dosage", query);
            database.execSQL(query);
        }

        if (medicine.getDuration() == (-1)) {
            ArrayList<String> days = medicine.getDays();
            for (int i = 0; i < days.size(); i++) {
                query = "INSERT INTO " + TABLE_NAME_MEDICINEDAY + " VALUES (" +
                        "'" + medicine.getMedicine_name() + "'," +
                        Utilities.convertDay(days.get(i)) + ");";
                Log.e("addMedicine-Specific", query);
                database.execSQL(query);
            }
        }
    }

    public Medicine getMedicine(String medicineName) {
        database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_MEDICINE + " WHERE medicine_name = '" + medicineName + "';";
        Log.e("getMedicine:medicine", query);

        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        Medicine medicine = new Medicine();
        medicine.setMedicine_name(c.getString(0));
        medicine.setMedicine_type(c.getString(1));
        medicine.setStartdate(c.getString(2));
        medicine.setEnddate(c.getString(3));
        medicine.setDuration(c.getInt(4));
        medicine.setNotifications(c.getInt(5) == 1);
        Log.e("Before dosage", String.valueOf(medicine));


        query = "SELECT * FROM " + TABLE_NAME_DOSAGE + " WHERE medicine_name = '" + medicineName + "';";
        Log.e("getMedicine:dosage", query);
        c = database.rawQuery(query, null);
        ArrayList<Dosage> dosages = new ArrayList<>();

        if (c.getCount() > 0) {
            for (int i = 0; i < c.getCount(); i++) {
                c.moveToNext();
                Dosage dosage = new Dosage(c.getString(0), c.getString(1), c.getInt(2));
                dosages.add(dosage);
            }
        }

        medicine.setDoses(dosages);
        Log.e("After dosage", String.valueOf(medicine));

        ArrayList<String> days = new ArrayList<>();
        if (medicine.getDuration() == (-1)) {
            query = "SELECT * FROM " + TABLE_NAME_MEDICINEDAY + " WHERE medicine_name = '" + medicineName + "';";
            Log.e("getMedicine:days", query);
            c = database.rawQuery(query, null);

            for (int i = 0; i < c.getCount(); i++) {
                c.moveToNext();
                days.add(c.getString(1));
            }
        }
        medicine.setDays(days);
        return medicine;
    }

    public boolean insertData(String email, String name, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);
        return result != -1;
    }

    public boolean checkusername(String name) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = myDB.rawQuery("select * from users where name=?", new String[]{name});
        return cursor.getCount() > 0;
    }

    public boolean checkemailnamepassword(String email, String name, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = myDB.rawQuery("select * from users where email=? and name=? and password=?", new String[]{email, name, password});
        return cursor.getCount() > 0;
    }

    public void storeImage(PrescriptionModel prescriptionModel) {
        try {
            SQLiteDatabase objectSqLiteDatabase = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = prescriptionModel.getImage();
            String name = prescriptionModel.getDoctor_name();

            ByteArrayOutputStream objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();

            objectContentValues.put("doc_name", name);
            objectContentValues.put("image", imageInBytes);
            long checkIfQueryRuns = objectSqLiteDatabase.insert("imageInfo", null, objectContentValues);
            if (checkIfQueryRuns != -1) {
                Toast.makeText(context, "Data added into the table", Toast.LENGTH_SHORT).show();
                objectSqLiteDatabase.close();
            } else {
                Toast.makeText(context, "Fails to add data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<PrescriptionModel> getAllImagesData() {
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            ArrayList<PrescriptionModel> prescriptionModels = new ArrayList<>();

            Cursor objectCursor = database.rawQuery("select * from imageInfo ", null);
            if (objectCursor.getCount() != 0) {
                while (objectCursor.moveToNext()) {
                    byte[] imageBytes = objectCursor.getBlob(1);
                    String name = objectCursor.getString(2);
                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    prescriptionModels.add(new PrescriptionModel(objectBitmap, name));
                }
                return prescriptionModels;
            } else {
                Toast.makeText(context, "Please add your prescriptions here", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
