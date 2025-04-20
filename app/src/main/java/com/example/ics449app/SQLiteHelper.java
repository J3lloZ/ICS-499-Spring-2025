package com.example.ics449app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import java.util.List;

import java.util.ArrayList;


public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteHelper sqLiteHelper;
    private static final String DB_Name = "StudyDataBase";
    private static final int DB_Version = 2;


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
        // School table
        String createSchoolsTable = "CREATE TABLE Schools (" +
                "SchoolCode VARCHAR(255) PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "address VARCHAR(255), " +
                "contact_email VARCHAR(255) UNIQUE);";
        db.execSQL(createSchoolsTable);

        // User table
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

        // Teacher table
        String createTeacherSQL = "CREATE TABLE Teacher ("
                + "TeacherID VARCHAR(255) PRIMARY KEY, "
                + "userID VARCHAR(255),"

                + "FirstName VARCHAR(255), "
                + "LastName VARCHAR(255), "
                + "SchoolCode VARCHAR(255),"
                + "FOREIGN KEY (userID) REFERENCES Users(userID), "
                + "FOREIGN KEY (SchoolCode) REFERENCES Schools(SchoolCode));";
        db.execSQL(createTeacherSQL);

        // Parent table
        String createParentSQL = "CREATE TABLE Parent ("
                + "ParentID VARCHAR(255) PRIMARY KEY, "
                + "userID VARCHAR(255),"
                + "FirstName VARCHAR(255), "
                + "LastName VARCHAR(255), "
                + "SchoolCode VARCHAR(255),"
                + "FOREIGN KEY (userID) REFERENCES Users(userID), "
                + "FOREIGN KEY (SchoolCode) REFERENCES Schools(SchoolCode));";
        db.execSQL(createParentSQL);

        // Student table
        String createStudentSQL = "CREATE TABLE Student ("
                + "StudentID VARCHAR(255) PRIMARY KEY, "
                + "userID VARCHAR(255),"
                + "FirstName VARCHAR(255), "
                + "LastName VARCHAR(255), "
                + "SchoolCode VARCHAR(255),"
                + "FOREIGN KEY (userID) REFERENCES Users(userID),"
                + "FOREIGN KEY (SchoolCode) REFERENCES Schools(SchoolCode));";
        db.execSQL(createStudentSQL);

        // Subject table
        String createSubjectSQL = "CREATE TABLE subjects ("
                + "SubjectID VARCHAR(255) PRIMARY KEY, "
                + "SubjectName VARCHAR(255),"
                + "SchoolCode VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (SchoolCode) REFERENCES Schools(SchoolCode)"
                + ");";
        db.execSQL(createSubjectSQL);

        // Classes table
        String createClassesTable = "CREATE TABLE Classes ("
                + "ClassID VARCHAR(255) PRIMARY KEY, "
                + "SubjectID VARCHAR(255), "
                + "teacherID VARCHAR(255), "
                + "ClassName VARCHAR(255), "
                + "TimeSlot VARCHAR(255), "
                + "FOREIGN KEY (SubjectID) REFERENCES subjects(SubjectID), "
                + "FOREIGN KEY (teacherID) REFERENCES Teacher(teacherID));";
        db.execSQL(createClassesTable);

        // StudentClasses table
        String createStudentClassesTable = "CREATE TABLE StudentClasses ("
                + "StudentID VARCHAR(255), "
                + "teacherID VARCHAR(255), "
                + "PRIMARY KEY (StudentID, teacherID), "
                + "FOREIGN KEY (StudentID) REFERENCES Users(userID), "
                + "FOREIGN KEY (teacherID) REFERENCES Users(userID));";
        db.execSQL(createStudentClassesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Teacher");
            db.execSQL("DROP TABLE IF EXISTS Parent");
            db.execSQL("DROP TABLE IF EXISTS Student");
            onCreate(db);
        }
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
            stmt.bindString(2, school.getSchoolName());
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
    public void addUser(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Insert into Users table
            String insertQuery = "INSERT INTO Users (userID, FirstName, LastName, email, password, role, SchoolCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement stmt = db.compileStatement(insertQuery);
            stmt.bindString(1, teacher.getUserID());
            stmt.bindString(2, teacher.getFirstName());
            stmt.bindString(3, teacher.getLastName());
            stmt.bindString(4, teacher.getEmail());
            stmt.bindString(5, teacher.getPassword());
            stmt.bindString(6, "Teacher");
            stmt.bindString(7, teacher.getSchoolCode());
            stmt.executeInsert();

            // Insert into Teacher table
            String insertTeacherQuery = "INSERT INTO Teacher (TeacherID, userID, FirstName, LastName, SchoolCode) VALUES (?, ?, ?, ?, ?)";
            SQLiteStatement stmtTeacher = db.compileStatement(insertTeacherQuery);
            stmtTeacher.bindString(1, teacher.getTeacherId());
            stmtTeacher.bindString(2, teacher.getUserID());
            stmtTeacher.bindString(3, teacher.getFirstName());
            stmtTeacher.bindString(4, teacher.getLastName());
            stmtTeacher.bindString(5, teacher.getSchoolCode());
            stmtTeacher.executeInsert();

        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding Teacher", e);
        } finally {
            db.close();
        }
    }

    public void addUser(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Insert into Users table
            String insertQuery = "INSERT INTO Users (userID, FirstName, LastName, email, password, role, SchoolCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement stmt = db.compileStatement(insertQuery);
            stmt.bindString(1, student.getUserID());
            stmt.bindString(2, student.getFirstName());
            stmt.bindString(3, student.getLastName());
            stmt.bindString(4, student.getEmail());
            stmt.bindString(5, student.getPassword());
            stmt.bindString(6, "Student");
            stmt.bindString(7, student.getSchoolCode());
            stmt.executeInsert();

            // Insert into Student table
            String insertTeacherQuery = "INSERT INTO Student (StudentID, userID, FirstName, LastName, SchoolCode) VALUES (?, ?, ?, ?, ?)";
            SQLiteStatement stmtStudent = db.compileStatement(insertTeacherQuery);
            stmtStudent.bindString(1, student.getStudentId());
            stmtStudent.bindString(2, student.getUserID());
            stmtStudent.bindString(3, student.getFirstName());
            stmtStudent.bindString(4, student.getLastName());
            stmtStudent.bindString(5, student.getSchoolCode());
            stmtStudent.executeInsert();
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding Student", e);
        } finally {
            db.close();
        }
    }

    public void addUser(Parent parent) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Insert into Users table
            String insertQuery = "INSERT INTO Users (userID, FirstName, LastName, email, password, role, SchoolCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement stmt = db.compileStatement(insertQuery);
            stmt.bindString(1, parent.getUserID());
            stmt.bindString(2, parent.getFirstName());
            stmt.bindString(3, parent.getLastName());
            stmt.bindString(4, parent.getEmail());
            stmt.bindString(5, parent.getPassword());
            stmt.bindString(6, "Parent");
            stmt.bindString(7, parent.getSchoolCode());
            stmt.executeInsert();

            // Insert into Parent table
            String insertTeacherQuery = "INSERT INTO Parent (ParentID, userID, FirstName, LastName, SchoolCode) VALUES (?, ?, ?, ?, ?)";
            SQLiteStatement stmtParent = db.compileStatement(insertTeacherQuery);
            stmtParent.bindString(1, parent.getParentId());
            stmtParent.bindString(2, parent.getUserID());
            stmtParent.bindString(3, parent.getFirstName());
            stmtParent.bindString(4, parent.getLastName());
            stmtParent.bindString(5, parent.getSchoolCode());
            stmtParent.executeInsert();
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error adding Parent", e);
        } finally {
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
            String query = "SELECT * FROM Users WHERE LOWER(email) = LOWER(?) AND password = ?";
            cursor = db.rawQuery(query, new String[]{email.toLowerCase(), password});

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

    // Search for Student
    public List<Student> getStudentsBySchool(String schoolCode) {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE role = 'Student' AND SchoolCode = ?", new String[]{schoolCode});

        if (cursor.moveToFirst())   {
            do {
                String studentID = cursor.getString(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String email = cursor.getString(3);
                String password = cursor.getString(4);
                String school = cursor.getString(5);

                students.add(new Student(studentID, firstName, lastName, email, password, "Student", school));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }

    public boolean addStudentToClass(String email, String teacherSchoolCode) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get student ID by email
        String studentIdQuery = "SELECT StudentID FROM Student Where email = ?";
        Cursor cursor = db.rawQuery(studentIdQuery, new String[] {email});

        if (cursor.moveToFirst()) {
            int studentIdColumnIndex = cursor.getColumnIndex("StudentID");

            // Ensure column index is valid
            if (studentIdColumnIndex >= 0) {
                String studentId = cursor.getString(studentIdColumnIndex);  // Safely access the student ID
                cursor.close();

                // Get teacher id by school code
                String classIdQuery = "SELECT TeacherID FROM StudentClasses WHERE SchoolCode = ?";
                cursor = db.rawQuery(classIdQuery, new String[]{teacherSchoolCode});

                if (cursor.moveToFirst()) {
                    int classIdColumnIndex = cursor.getColumnIndex("TeacherID");

                    // Ensure column index is valid
                    if (classIdColumnIndex >= 0) {
                        String teacherID = cursor.getString(classIdColumnIndex);
                        cursor.close();

                        // Insert into student classes table to link student with teacher
                        ContentValues values = new ContentValues();
                        values.put("StudentID", studentId);
                        values.put("TeacherID", teacherSchoolCode);

                        long result = db.insert("StudentClasses", null, values);
                        return result != -1;    // Return true if insertion was successful
                    }
                }
            }
        }
        return false;   // Return false if any query fails or if column index is invalid
    }

    public String getFirstNameByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String firstName = "";

        try {
            String query = "SELECT firstName FROM users WHERE LOWER(email) = LOWER(?)";
            cursor = db.rawQuery(query, new String[]{email});
            if (cursor.moveToFirst()) {
                firstName = cursor.getString(0);
            }
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error fetching first name by email", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return firstName;
   }
}
