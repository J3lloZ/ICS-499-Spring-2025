package com.example.ics449app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteHelper sqLiteHelper;
    private static final String DB_Name = "StudyDataBase";
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
                + "FirstName VARCHAR(255), "
                + "LastName VARCHAR(255), "
                + "email VARCHAR(255) UNIQUE, "
                + "password VARCHAR(255), "
                + "role VARCHAR(50), "
                + "SchoolCode VARCHAR(255),"
                + "FOREIGN KEY (SchoolCode) REFERENCES Schools(SchoolCode));";
        db.execSQL(createSchoolusersSQL);
        String createSubjectSQL = "CREATE TABLE subjects ("
                + "SubjectID VARCHAR(255) PRIMARY KEY, "
                + "SubjectName VARCHAR(255),"
                + "SchoolCode VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (SchoolCode) REFERENCES Schools(SchoolCode)"
                + ");";
        db.execSQL(createSubjectSQL);
        String createClassesTable = "CREATE TABLE Classes ("
                + "ClassID VARCHAR(255) PRIMARY KEY, "
                + "SubjectID VARCHAR(255), "
                + "teacherID VARCHAR(255), "
                + "ClassName VARCHAR(255), "
                + "TimeSlot VARCHAR(255), "
                + "FOREIGN KEY (SubjectID) REFERENCES subjects(SubjectID), "
                + "FOREIGN KEY (teacherID) REFERENCES Users(userID));";
        db.execSQL(createClassesTable);
        String createStudentClassesTable = "CREATE TABLE StudentClasses ("
                + "StudentID VARCHAR(255) PRIMARY KEY, "
                + "teacherID VARCHAR(255), "
                + "FOREIGN KEY (StudentID) REFERENCES Users(userID), "
                + "FOREIGN KEY (teacherID) REFERENCES Users(userID));";
        db.execSQL(createStudentClassesTable);
        String createParentsTable = "CREATE TABLE Parents ("
                + "parentCode VARCHAR(255) PRIMARY KEY, "
                + "firstName VARCHAR(255), "
                + "lastName VARCHAR(255), "
                + "contactEmail VARCHAR(255), "
                + "ParentID VARCHAR(255), "
                + "FOREIGN KEY (ParentID) REFERENCES Users(userID));";
        //db.execSQL(createParentsTable);
        String createParentChildTable = "CREATE TABLE ParentChild ("
                + "ParentCode VARCHAR(255), "
                + "ChildID VARCHAR(255), "
                + "PRIMARY KEY (ParentCode, ChildID), "
                + "FOREIGN KEY (ParentCode) REFERENCES Parents(parentCode), "
                + "FOREIGN KEY (ChildID) REFERENCES Users(userID));";
        //db.execSQL(createParentChildTable);
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
            String insertUserQuery = "INSERT INTO Users (userID, FirstName, LastName, email, password, role, SchoolCode) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

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

            Log.d("SQLiteHelper", "User added successfully: " + user.getUserID());
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding user: ", e);
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
            String insertSubjectQuery = "INSERT INTO subjects (SubjectID, SubjectName, SchoolCode) " +
                    "VALUES (?, ?, ?)";

            // Prepare the SQLite statement
            SQLiteStatement stmt = db.compileStatement(insertSubjectQuery);

            // Bind the subject data to the statement
            stmt.bindString(1, subject.getSubjectID());
            stmt.bindString(2, subject.getSubjectName());
            stmt.bindString(3, subject.getSchoolCode());

            // Execute the insert statement
            stmt.executeInsert();
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding school: ", e);
        } finally {
            // Always close the database connection to avoid memory leaks
            db.close();
        }
    }
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