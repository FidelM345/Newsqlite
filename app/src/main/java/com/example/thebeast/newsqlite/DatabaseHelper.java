package com.example.thebeast.newsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper{
//sqlite db is not case sensitive

    static final String DATABASE_NAME="student.db";
    static final String TABLE_NAME="student_table";
    static final String COL_1="id";
    static final String COL_2="name";
    static final String COL_3="surname";
    static final String COL_4="marks";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //whenever the constructor is called the database is created


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table is created when the method is called

        db.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT, MARKS INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drops table if it exists in case of upgrading

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);//it will then create the table again

    }


    public boolean insertData(String name, String surname,int marks){
         SQLiteDatabase db=this.getWritableDatabase();//gets instance of the database

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);

        long result=db.insert(TABLE_NAME,null,contentValues);

        if(result==-1){

            return false;
        }
        else {


            return true;
        }

    }

    public Cursor getAllData (){
        SQLiteDatabase db=this.getWritableDatabase();//gets instance of the database
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);



        return res;

    }




    public long updateData(String id, String name, String surname,int marks){
        SQLiteDatabase db=this.getWritableDatabase();//gets instance of the database

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        long rows= db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});//return the number of rows affected




            return rows;

    }




    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();//gets instance of the database
       int rows= db.delete(TABLE_NAME,"ID=?",new String[]{id});

       return rows;


    }














}
