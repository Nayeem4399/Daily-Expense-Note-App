package com.example.dailyexpensenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dailyexpensenote.entity.Expense;
import com.example.dailyexpensenote.fragment.DashboardFragment;
import com.example.dailyexpensenote.fragment.ExpenseListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements ExpenseAdapter.ExpenseItemClickListener {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();

/*

        Intent intent=getIntent();
        if(intent.getAction().equals(Intent.ACTION_SEARCH)){
            String qq=intent.getStringExtra(SearchManager.QUERY);

            Toast.makeText(this, qq, Toast.LENGTH_SHORT).show();
        }
*/


        replace(new ExpenseListFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()){
                    case R.id.expenselist:
                        replace(new ExpenseListFragment());
                        return true;

                    case R.id.dashboard:
                        replace(new DashboardFragment());
                        return true;
                }


                return false;
            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        SearchManager manager= (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView= (SearchView) menu.findItem(R.id.itemsearch).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Type of expense");
         searchView.setBackgroundColor(R.drawable.add);
        return true;
    }






    private void replace(Fragment fragment) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.replace(R.id.fragmentContainer,fragment);
        ft.commit();
    }

    private void initial() {

        bottomNavigationView=findViewById(R.id.bottomNavigation);
    }

    private void mytoast() {
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.mytoast, (ViewGroup) findViewById(R.id.mytoast));
        Toast toast=new Toast(MainActivity.this);
        toast.setDuration(Toast.LENGTH_LONG);
        // toast.setGravity(Gravity.CENTER,0,-10);
        toast.setView(view);
        toast.show();

    }

    @Override
    public void onItemClicked(Expense expense2) {
       BottomSheet bottomSheet=new BottomSheet();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ex",expense2);
        bottomSheet.setArguments(bundle);
       bottomSheet.show(getSupportFragmentManager(),"bottom");
    }
}
