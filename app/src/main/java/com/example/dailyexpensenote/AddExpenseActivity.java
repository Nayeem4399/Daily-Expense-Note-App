package com.example.dailyexpensenote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dailyexpensenote.db.ExpenseDatabase;
import com.example.dailyexpensenote.entity.Expense;
import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.ByteArrayOutputStream;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static android.graphics.Bitmap.CompressFormat.PNG;


public class AddExpenseActivity extends AppCompatActivity {
    private TextInputEditText expenseDateEdt,expenseTimeEdt,expenseAmountEdt,selectEdt;
    private ImageView documentImageSet;
    private TextView addDocumentTv,updateDocumentTv;

    private Spinner spinner;
    private Button addBt,updateBt;



    private DatePicker datePicker;
    private DatePickerDialog datePickerDialog;






    private String expenseType,expenseDate,expenseTime,expenseImage=null;
    public String expenseAmount;

    private List<String> expenseTypeList=new ArrayList<>();

    private int id=0;

    private android.app.AlertDialog.Builder alertdialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        initila();

        setTitle("Save Your Daily Expense");

        expenseTypeList.add("");
        expenseTypeList.add("Electricity Bill");
        expenseTypeList.add("Transport Cost");
        expenseTypeList.add("Bus Bill");
        expenseTypeList.add("Train Bill ");
        expenseTypeList.add("Breakfast ");
        expenseTypeList.add("Lunch ");
        expenseTypeList.add(" Dinner");
        expenseTypeList.add("Shopping Bill ");
        expenseTypeList.add("Home Rent ");
        expenseTypeList.add("Utilities");
        expenseTypeList.add("Others Expense");

        final ArrayAdapter<String> adapter=new ArrayAdapter<> (AddExpenseActivity.this,R.layout.support_simple_spinner_dropdown_item,expenseTypeList);
        spinner.setAdapter(adapter);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                selectEdt.setText(adapterView.getItemAtPosition(i).toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        expenseDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                datePicker = new DatePicker(AddExpenseActivity.this);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();


                datePickerDialog = new DatePickerDialog(AddExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        String currentDate=( day+"/"+ (month+1)+"/"+year);
                        DateFormat dformat=new SimpleDateFormat("dd/MM/yyyy");

                        Date date=null;


                        try {
                            date=dformat.parse(currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        expenseDateEdt.setText(dformat.format(date));



                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });



        expenseTimeEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AddExpenseActivity.this);

                View view1=getLayoutInflater().inflate(R.layout.custom_time_picker,null);
                Button buttonok=view1.findViewById(R.id.ookBt);
                Button cancelBt=view1.findViewById(R.id.cancelBt);
                final TimePicker timePicker=view1.findViewById(R.id.timePickerId);




                builder.setView(view1);

                final Dialog dialog=builder.create();

                dialog.show();
                cancelBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                buttonok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm:ss aa");
                        int hour,min;
                        hour = timePicker.getHour();
                        min=timePicker.getMinute();

                        Time time=new Time(hour,min,0);
                        expenseTimeEdt.setText(timeFormat.format(time));
                        dialog.dismiss();



                    }
                });

            }
        });




        addDocumentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectPhoto();

            }
        });


        updateDocumentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectPhoto();

            }
        });




        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectEdt.getText().toString().isEmpty()) {
                    StyleableToast.makeText(AddExpenseActivity.this, "Please Select any Type of Expense !!", Toast.LENGTH_LONG, R.style.mytoast).show();
                   }


                 else if (expenseAmountEdt.getText().toString().isEmpty()) {
                    StyleableToast.makeText(AddExpenseActivity.this, "Please Provide Amount !!", Toast.LENGTH_LONG, R.style.mytoast).show();



                } else if (expenseAmountEdt.getText().toString().length() >10) {
                    StyleableToast.makeText(AddExpenseActivity.this, "Invalid Amount !!", Toast.LENGTH_LONG, R.style.mytoast).show();


                } else if (expenseDateEdt.getText().toString().isEmpty()) {
                    StyleableToast.makeText(AddExpenseActivity.this, "Please Provide Date !!", Toast.LENGTH_LONG, R.style.mytoast).show();

                } else {


                     expenseType=selectEdt.getText().toString().trim();
                    expenseAmount =expenseAmountEdt.getText().toString().trim();
                    expenseDate = expenseDateEdt.getText().toString().trim();
                    expenseTime = expenseTimeEdt.getText().toString().trim();


                    Expense expense = new Expense(expenseType, expenseDate, expenseTime, expenseAmount, expenseImage);

                    long rowId = ExpenseDatabase.getObject(AddExpenseActivity.this).getExpenseDao().insertNewExpense(expense);

                    if (rowId > 0) {
                        mytoast(rowId);
                        Intent intent=new Intent(AddExpenseActivity.this,MainActivity.class);
                        startActivity(intent);

                        resetViews();



                    } else {
                        Toast.makeText(AddExpenseActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(AddExpenseActivity.this,MainActivity.class);
                        startActivity(intent);
                        resetViews();

                    }



                }

            }



            private void resetViews() {

               selectEdt.setText("");
                expenseAmountEdt.setText("");
                expenseDateEdt.setText("");
                expenseTimeEdt.setText("");
               documentImageSet.setImageResource(R.drawable.null_image3);


            }
        });


        getDataForUpdate();




    }

    private void selectPhoto() {



        alertdialogBuilder=new android.app.AlertDialog.Builder(AddExpenseActivity.this);
        alertdialogBuilder.setTitle("Select Photo");
        alertdialogBuilder.setMessage("Choice any Option !! ");
        alertdialogBuilder.setIcon(R.drawable.camera);


        alertdialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        alertdialogBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,0);


                dialogInterface.dismiss();
            }
        });

        alertdialogBuilder.setPositiveButton("Gallary", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                startActivityForResult(intent,9);
                dialogInterface.dismiss();
            }
        });

        android.app.AlertDialog alertDialog=alertdialogBuilder.create();
        alertDialog.show();



    }


    public void initila() {

        expenseAmountEdt=findViewById(R.id.amountEdTId);
        expenseDateEdt=findViewById(R.id.dateEdTId);
        expenseTimeEdt=findViewById(R.id.timeEdTId);
        documentImageSet=findViewById(R.id.documentImaId);

        addDocumentTv=findViewById(R.id.addDocumentTvId);
        updateDocumentTv=findViewById(R.id.updateDocumentTvId);

        addBt=findViewById(R.id.addBtId);
        updateBt=findViewById(R.id.updateBtId);
        spinner=findViewById(R.id.spinnerId);
        selectEdt=findViewById(R.id.selectTypeEd);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode==RESULT_OK){
            if(requestCode==0){
                Bundle bundle=data.getExtras();
              Bitmap  bitmap= (Bitmap) bundle.get("data");
                documentImageSet.setImageBitmap(bitmap);

                expenseImage=encodeToBase64(bitmap,PNG,100);


            }


            else if(requestCode ==9){
                Uri uri= data.getData();
                documentImageSet.setImageURI(uri);
             /*

                InputStream pictureInput;
                try {
                    pictureInput=getContentResolver().openInputStream(uri);
                    Bitmap  bitmap2=BitmapFactory.decodeStream(pictureInput);

                    // documentImageSet.setImageBitmap(bitmap);
                    expenseImage=encodeToBase64(bitmap2,PNG,100);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                */


                }





            }

        }



    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }


    private void mytoast(long rowId) {
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.mytoast, (ViewGroup) findViewById(R.id.mytoast));
        Toast toast=new Toast(AddExpenseActivity.this);
        toast.setDuration(Toast.LENGTH_LONG);
        TextView textView=view.findViewById(R.id.toastId);
        textView.setText("# "+rowId);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(view);
        toast.show();

    }

    private void mytoast2(long rowId) {
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.mytoast2, (ViewGroup) findViewById(R.id.mytoast2));
        Toast toast=new Toast(AddExpenseActivity.this);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView textView=view.findViewById(R.id.toastId);
        textView.setText("# "+rowId);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(view);
        toast.show();

    }


    private void getDataForUpdate() {



        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {


            id = Integer.parseInt(getIntent().getStringExtra("id"));
            expenseType = bundle.getString("type");
            expenseDate = bundle.getString("date");
            expenseTime = bundle.getString("time");




            expenseAmount=bundle.getString("amount");


            addBt.setVisibility(View.GONE);
            addDocumentTv.setVisibility(View.GONE);

            updateBt.setVisibility(View.VISIBLE);
            updateDocumentTv.setVisibility(View.VISIBLE);


            selectEdt.setText(expenseType+"");
            expenseAmountEdt.setText(expenseAmount+"");

            expenseDateEdt.setText(expenseDate);
            expenseTimeEdt.setText(expenseTime);


            if(bundle.getString("image")!=null){
                expenseImage = bundle.getString("image");
                Bitmap bitmap = decodeBase64(expenseImage);

                documentImageSet.setImageBitmap(bitmap);
            }



            updateBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(selectEdt.getText().toString().isEmpty()){
                        StyleableToast.makeText(AddExpenseActivity.this, "Please Select any Type of Expense !!", Toast.LENGTH_LONG, R.style.mytoast).show();
                    }



                    else if(expenseAmountEdt.getText().toString().isEmpty()){
                        StyleableToast.makeText(AddExpenseActivity.this, "Please Provide Amount !!", Toast.LENGTH_LONG, R.style.mytoast).show();

                    }



                    else if(expenseAmountEdt.getText().toString().length()>10){
                        StyleableToast.makeText(AddExpenseActivity.this, "Invalid Amount !!", Toast.LENGTH_LONG, R.style.mytoast).show();

                    }

                    else if(expenseDateEdt.getText().toString().isEmpty()){
                        StyleableToast.makeText(AddExpenseActivity.this, "Please Provide Date !!", Toast.LENGTH_LONG, R.style.mytoast).show();

                    }

                    else
                    {


                        expenseType=selectEdt.getText().toString();
                        expenseAmount=expenseAmountEdt.getText().toString().trim();
                        expenseDate=expenseDateEdt.getText().toString().trim();
                        expenseTime=expenseTimeEdt.getText().toString().trim();




                        Expense expense=new Expense(id,expenseType,expenseDate,expenseTime,expenseAmount,expenseImage);

                        int rowId= ExpenseDatabase.getObject(AddExpenseActivity.this).getExpenseDao().updateExpese(expense);


                       if(rowId>0) {


                           Intent intent=new Intent(AddExpenseActivity.this,MainActivity.class);
                           startActivity(intent);

                           mytoast2(id);






                       }
                       else {

                           Intent intent=new Intent(AddExpenseActivity.this,MainActivity.class);
                           startActivity(intent);

                           Toast.makeText(AddExpenseActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();

                       }





                        updateBt.setVisibility(View.GONE);
                        updateDocumentTv.setVisibility(View.GONE);
                        addBt.setVisibility(View.VISIBLE);
                        addDocumentTv.setVisibility(View.VISIBLE);

                        resetViews();


                    }


                }
            });
        }




    }



    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    public void resetViews(){
        expenseTimeEdt.setText("");
        expenseDateEdt.setText("");
        expenseAmountEdt.setText("");
        documentImageSet.setImageResource(R.drawable.nulll_image);
        selectEdt.setText("");

    }


}
