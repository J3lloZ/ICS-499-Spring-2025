package com.example.ics449app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteHelper sqLiteHelper;
    private static final String DB_Name = "Study_DB";
    private static final int DB_Version = 1;




    public SQLiteHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }
    public static SQLiteHelper instanceOfDatabase(Context context){
        if(sqLiteHelper == null){
            sqLiteHelper = new SQLiteHelper(context);
        }
        return sqLiteHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSchoolsTable = "CREATE TABLE Schools (" +
                "SchoolCode VARCHAR(255) PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "address VARCHAR(255), " +
                "contact_email VARCHAR(255) UNIQUE);";
        db.execSQL(createSchoolsTable);
        String createSchoolusersSQL = "CREATE TABLE Users ("
                + "userID VARCHAR(255) PRIMARY KEY, "
                + "Fname VARCHAR(255), "
                + "Lname VARCHAR(255), "
                + "email VARCHAR(255) UNIQUE, "
                + "password VARCHAR(255), "
                + "role VARCHAR(50), "
                + "SchoolCode VARCHAR(255),"
                + "FOREIGN KEY (SchoolCode) REFERENCES Schools(SchoolCode));";
        db.execSQL(createSchoolusersSQL);
        String createSubjectSQL = "CREATE TABLE subjects ("
                + "SubjectID VARCHAR(255) PRIMARY KEY, "
                + "SchoolCode VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (SchoolCode) REFERENCES Schools(SchoolCode)"
                + ");";
        db.execSQL(createSubjectSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addSchool(School school) {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
        // SQL statement to insert a new school
        String insertSchoolQuery = "INSERT INTO Schools (SchoolCode, name, address, contact_email) " +
                "VALUES (?,?, ?, ?)";

        // Prepare the SQLite statement
        SQLiteStatement stmt = db.compileStatement(insertSchoolQuery);
        stmt.bindString(1, school.getSchoolCode());
        stmt.bindString(2, school.getSchoolname());
        stmt.bindString(3, school.getAddress());
        stmt.bindString(4, school.getContact_email());

        // Execute the insert statement
        stmt.executeInsert();

    }catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding school: ", e);
        } finally {
            // Always close the database connection to avoid memory leaks
            db.close();
        }
    }
    public void addStudent(Student user) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // SQL statement to add a new user
            String insertUserQuery = "INSERT INTO users (userID, Fname, Lname, email, password, role, SchoolCode) " +
                    "VALUES (?, ?, ?, ?, ?, ?,?)";

            // Prepare the SQLite statement
            SQLiteStatement stmt = db.compileStatement(insertUserQuery);

            // Bind the user data to the statement
            stmt.bindString(1, user.getUserID());
            stmt.bindString(2, user.getFname());
            stmt.bindString(3, user.getLname());
            stmt.bindString(4, user.getEmail());
            stmt.bindString(5, user.getPassword());
            stmt.bindString(6, user.getRole());
            stmt.bindString(7, user.getSchoolCode());


            // Execute the insert statement
            stmt.executeInsert();
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding school: ", e);
        } finally {
            // Always close the database connection to avoid memory leaks
            db.close();

        }
    }
    public void addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {


        // SQL statement to insert a new subject
        String insertSubjectQuery = "INSERT INTO subjects (SubjectID, SchoolCode) " +
                "VALUES (?, ?)";

        // Prepare the SQLite statement
        SQLiteStatement stmt = db.compileStatement(insertSubjectQuery);

        // Bind the subject data to the statement
        stmt.bindString(1, subject.getSubjectID());
        stmt.bindString(2, subject.getSchoolCode());

        // Execute the insert statement
        stmt.executeInsert();
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding school: ", e);
        } finally {
            // Always close the database connection to avoid memory leaks
            db.close();
        }
    }

}