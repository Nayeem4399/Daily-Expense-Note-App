package com.example.dailyexpensenote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyexpensenote.db.ExpenseDatabase;
import com.example.dailyexpensenote.entity.Expense;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    private Context context;
    private List<Expense> expenseList;
    private ExpenseItemClickListener listener;

    private AlertDialog.Builder alertdialogBuilder;




    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;

        listener= (ExpenseItemClickListener) context;
    }

    public void update(List<Expense> results){

        expenseList=new ArrayList<>();
        expenseList.addAll(results);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_model_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Expense expense=expenseList.get(position);

        holder.expenseTypeTv.setText(expense.getExpenseType());
        holder.expenseDateTv.setText(expense.getExpenseDate());
        holder.expenseTimeTv.setText(expense.getExpenseTime());
        holder.expenseAmountTv.setText("Tk: "+expense.getExpenseAmount());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onItemClicked(expense);
            }
        });



        holder.menuBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(context,view);
                popupMenu.inflate(R.menu.pop_up_menu);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){

                            case R.id.updateId:
                            {

                                String id=""+expense.getId();
                                String type=expense.getExpenseType();
                                String date=expense.getExpenseDate();
                                String time=expense.getExpenseTime();
                                String amount=expense.getExpenseAmount()+"";

                                Intent intent=new Intent(context,AddExpenseActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("type",type);
                                intent.putExtra("date",date);
                                intent.putExtra("time",time);
                                intent.putExtra("amount",amount);


                                if(expense.getDocument()!=null) {
                                    String image = expense.getDocument();
                                    intent.putExtra("image",image);

                                }


                                context.startActivity(intent);

                                return true;
                            }

                            case R.id.deleteId:
                            {


                                alertdialogBuilder=new AlertDialog.Builder(context);
                                alertdialogBuilder.setTitle("Are You Sure ??");
                                alertdialogBuilder.setMessage("Do you Want to Delete this Event ?");
                                alertdialogBuilder.setIcon(R.drawable.alert4);


                                alertdialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });

                                alertdialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.cancel();

                                    }
                                });

                                alertdialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        ExpenseDatabase.getObject(context).getExpenseDao().deleteExpense(expense);
                                        expenseList.remove(expense);
                                        notifyDataSetChanged();
                                        //Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                                        StyleableToast.makeText(context, "Delete Successful ", Toast.LENGTH_LONG, R.style.mytoast).show();

                                    }
                                });

                                AlertDialog alertDialog=alertdialogBuilder.create();
                                alertDialog.show();
                                return true;
                            }

                        }



                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView expenseTypeTv,expenseDateTv,expenseTimeTv,expenseAmountTv,menuBt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            expenseTypeTv=itemView.findViewById(R.id.row_expenseTypeTv);
            expenseDateTv=itemView.findViewById(R.id.row_expenseDateTv);
            expenseTimeTv=itemView.findViewById(R.id.row_expenseTimeTv);
            expenseAmountTv=itemView.findViewById(R.id.row_expenseAmountTv);
            menuBt=itemView.findViewById(R.id.menuBtTv);
        }
    }

    public interface ExpenseItemClickListener{
        void onItemClicked(Expense expense);
    }
}
