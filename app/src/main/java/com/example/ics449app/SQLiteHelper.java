package com.example.ics449app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.security.spec.ECField;


public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "Study_DB";
    private static final int DB_Version = 2;
    private static SQLiteHelper instance;

    // Table name and columns
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "userID";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_SCHOOL_CODE = "schoolCode";

    // Create Table SQL query
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_ROLE + " TEXT, " +
                    COLUMN_SCHOOL_CODE + " TEXT);";
    public SQLiteHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    // Singleton pattern
    public static synchronized SQLiteHelper instanceOfDatabase(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add new columns to the users table if they don't exist already
            try {
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_FIRST_NAME + " TEXT;");
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_LAST_NAME + " TEXT;");
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_EMAIL + " TEXT;");
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_PASSWORD + " TEXT;");
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ROLE + " TEXT;");
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_SCHOOL_CODE + " TEXT;");
            } catch (Exception e) {
                Log.e("SQLiteHelper", "Error adding columns: ", e);
            }
        }
    }

    public void addSchool(School school) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
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
        }

    }
    public void addStudent(Student user) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Log user data
            Log.d("SQLiteHelper", "Adding user: " + user.getUserID() + ", " + user.getFirstName() + " " + user.getLastName());

            // SQL statement to add a new user
            String insertUserQuery = "INSERT INTO users (userID, firstName, lastName, email, password, role, SchoolCode) " +
                        "VALUES (?, ?, ?, ?, ?, ?,?)";

            // Prepare the SQLite statement
            SQLiteStatement stmt = db.compileStatement(insertUserQuery);

            // Bind the user data to the statement
            stmt.bindString(1, user.getUserID());
            stmt.bindString(2, user.getFirstName());
            stmt.bindString(3, user.getLastName());
            stmt.bindString(4, user.getEmail());
            stmt.bindString(5, user.getPassword());
            stmt.bindString(6, user.getRole());
            stmt.bindString(7, user.getSchoolCode());


            // Execute the insert statement
            stmt.executeInsert();
            Log.d("SQLiteHelper", "User added successfully");
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding student: ", e);
        }
    }
    public void addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

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
            Log.e("SQLiteHelper", "Error adding subject: ", e);
        }
    }

    // Check if User exist method
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
            cursor = db.rawQuery(query, new String[]{email, password});

            return cursor.moveToFirst();
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error, cannot validate user", e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
    }

    // Check if school exists
    public boolean schoolExists(String schoolCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM Schools Where SchoolCode = ?";
            cursor = db.rawQuery(query, new String[]{schoolCode});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("SQLiteHelp", "Error, Cannot check if school exists", e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
    }

    // Check if user exists
    public boolean userExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM Users Where email = ?";
            cursor = db.rawQuery(query, new String[]{email});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("SQLiteHelp", "Error, cannot check if user exists", e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
    }

    // Check if subject exists
    public boolean subjectExists(String subjectID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM subjects Where subjectID = ?";
            cursor = db.rawQuery(query, new String[]{subjectID});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("SQLiteHelp", "Error, cannot check if subject exists", e);
            return false;
        } finally {
            if (cursor != null) {
            cursor.close();
            }

        }
    }

}