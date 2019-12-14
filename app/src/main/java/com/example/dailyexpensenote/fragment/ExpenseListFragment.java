package com.example.dailyexpensenote.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dailyexpensenote.AddExpenseActivity;
import com.example.dailyexpensenote.ExpenseAdapter;
import com.example.dailyexpensenote.R;
import com.example.dailyexpensenote.db.ExpenseDatabase;
import com.example.dailyexpensenote.entity.Expense;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseListFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Expense> expenseList;
    private ExpenseAdapter adapter;
    private FloatingActionButton fab;

    private SearchView searchView;

    private Spinner spinner;
    private TextInputEditText dateEt1,dateEt2;
    private List<String> typelist=new ArrayList<>();


    public ExpenseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_list, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateEt1=view.findViewById(R.id.dateEdTId2);
        dateEt2=view.findViewById(R.id.dateEdtId3);
        spinner=view.findViewById(R.id.spinnerId2);

        searchView=view.findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);
        
        fab=view.findViewById(R.id.floatingActionBt);
        recyclerView=view.findViewById(R.id.recyclerViewId);
        GridLayoutManager manager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(manager);


        expenseList= ExpenseDatabase.getObject(getActivity()).getExpenseDao().getAllExpense();

           if(expenseList!=null){

               adapter=new ExpenseAdapter(getActivity(),expenseList);
               recyclerView.setAdapter(adapter);
               adapter.notifyDataSetChanged();

           }

        typelist.add("Select expense Type ");
        typelist.add("Electricity Bill");
        typelist.add("Transport Cost");
        typelist.add("Bus Bill");
        typelist.add("Train Bill ");
        typelist.add("Breakfast ");
        typelist.add("Lunch ");
        typelist.add(" Dinner");
        typelist.add("Shopping Bill ");
        typelist.add("Home Rent ");
        typelist.add("Utilities");
        typelist.add("Others Expense");

        final ArrayAdapter<String> adapter=new ArrayAdapter<> (getActivity(),R.layout.support_simple_spinner_dropdown_item,typelist);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {





            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dateEt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePicker datePicker = new DatePicker(getActivity());
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        String currentDate = (day + "/" + (month + 1) + "/" + year);
                        DateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");

                        Date date = null;


                        try {
                            date = dformat.parse(currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        dateEt1.setText(dformat.format(date));


                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });




        dateEt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePicker datePicker = new DatePicker(getActivity());
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        String currentDate = (day + "/" + (month + 1) + "/" + year);
                        DateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");

                        Date date = null;


                        try {
                            date = dformat.parse(currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        dateEt2.setText(dformat.format(date));


                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity() ,AddExpenseActivity.class));
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
/*
                String type=selectEdt.getText().toString().trim();
                String datefrom=dateEt1.getText().toString().trim();
                String dateto=dateEt2.getText().toString().trim();
                searchView.setQuery(type,true);
                searchView.setQuery(datefrom+dateto,true);*/

                //long ml=dateEt1.getDrawingTime();
             //   Toast.makeText(getActivity(),"ml"+ml, Toast.LENGTH_SHORT).show();

                List<Expense> search= new ArrayList<>();

                for(Expense  x :expenseList){
                    if(x.getExpenseType().toLowerCase().contains(s.toLowerCase())|| x.getExpenseDate().contains(s))
                        search.add(x);

                }




                // ((ProductAdapter)recyclerView.getAdapter()).update(search);

                ((ExpenseAdapter)recyclerView.getAdapter()).update(search);


                return false;
            }
        });


    }


}
