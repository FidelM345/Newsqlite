package com.example.thebeast.newsqlite;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name,surname,marks,userid;
    Button save,view,update,delete,service;
    DatabaseHelper  databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name =findViewById(R.id.id_name);
        surname =findViewById(R.id.id_surname);
        marks=findViewById(R.id.id_marks);
        save=findViewById(R.id.id_save);
        view=findViewById(R.id.id_view);
        update=findViewById(R.id.id_update);
        delete=findViewById(R.id.id_delete);
        userid=findViewById(R.id.id_userid);
        service=findViewById(R.id.id_service);


        databaseHelper=new DatabaseHelper(MainActivity.this);

        addData();
        viewData();
        dataUpdate();
        dataDelete();

        serviceActivity();
    }


    public void serviceActivity(){
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ServiceAPP.class);
                startActivity(intent);
            }
        });


    }
    public void addData(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString();
                String Surname=surname.getText().toString();
                int Marks=Integer.parseInt(marks.getText().toString());

                if(Name.isEmpty()|| Surname.isEmpty()||marks.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ensure all fields have been filled",Toast.LENGTH_LONG).show();


                }else{

                    boolean value=databaseHelper.insertData(Name,Surname,Marks);


                    if(value){
                        Toast.makeText(getApplicationContext(),"Data has been saved",Toast.LENGTH_LONG).show();
                        name.setText(null);
                        surname.setText(null);
                        marks.setText(null);


                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Data is not saved",Toast.LENGTH_LONG).show();

                    }
                }



            }
        });


    }

    public void viewData(){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Cursor res= databaseHelper.getAllData();

               if(res.getCount()==0){
                  // Toast.makeText(getApplicationContext(),"No data is available",Toast.LENGTH_LONG).show();
                   showMessage("Error","No Data found");

                   return;

               }else{

                   StringBuffer stringBuffer=new StringBuffer();
                   while (res.moveToNext()){

                       stringBuffer.append("ID: "+res.getString(0)+"\n");
                       stringBuffer.append("NAME: "+res.getString(1)+"\n");
                       stringBuffer.append("SURNAME: "+res.getString(2)+"\n");
                       stringBuffer.append("MARKS: "+res.getString(3)+"\n\n");




                   }


                   showMessage("Students results",stringBuffer.toString());


               }

            }
        });


    }

    public void showMessage(String title, String Message){


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(true);
        builder1.setTitle(title);
        builder1.setMessage(Message);
        builder1.setIcon(R.mipmap.ic_launcher);


        builder1.setNegativeButton(
                "Close interface",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


       builder1.show();




    }

    public void dataUpdate(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString();
                String Surname=surname.getText().toString();
                int Marks=Integer.parseInt(marks.getText().toString());
                String Userid=userid.getText().toString();
                long isUpdated=databaseHelper.updateData(Userid,Name,Surname,Marks);



                if(isUpdated==0){


                    Toast.makeText(getApplicationContext(),"Data has not been updated",Toast.LENGTH_LONG).show();



                }else {



                    Toast.makeText(getApplicationContext(),"Data has been updated",Toast.LENGTH_LONG).show();
                    name.setText(null);
                    surname.setText(null);
                    marks.setText(null);

                }


            }
        });


    }

    public void dataDelete(){

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Userid=userid.getText().toString();


                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("Delete data");
                builder1.setMessage("Are you sure you want to delete data");
                builder1.setCancelable(true);
                builder1.setIcon(R.mipmap.ic_launcher);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {

                               int isDeleted= databaseHelper.deleteData(Userid);


                                if(isDeleted==0){


                                    Toast.makeText(getApplicationContext(),"Data has not been deleted",Toast.LENGTH_LONG).show();



                                }else {

                                    Toast.makeText(getApplicationContext(),"Data has been successfully deleted",Toast.LENGTH_LONG).show();

                                }



                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();









            }
        });

    }




}
