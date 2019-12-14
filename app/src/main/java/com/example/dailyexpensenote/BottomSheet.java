package com.example.dailyexpensenote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dailyexpensenote.entity.Expense;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomSheet extends BottomSheetDialogFragment {
    private TextView typeTv,amountTv,dateTv,timeTv;
    private ImageView detImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_design, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        typeTv=view.findViewById(R.id.detType);
        amountTv=view.findViewById(R.id.detAmount);
        dateTv=view.findViewById(R.id.detDate);
        timeTv=view.findViewById(R.id.detTime);
        detImage=view.findViewById(R.id.detImage);


        Bundle bundle = getArguments();
        if (bundle != null) {
            final Expense expense = (Expense) bundle.getSerializable("ex");

            typeTv.setText(expense.getExpenseType());
            amountTv.setText("Tk : "+expense.getExpenseAmount());
            dateTv.setText(expense.getExpenseDate());
            timeTv.setText(expense.getExpenseTime());


            if(expense.getDocument()!=null) {

                String input = expense.getDocument();

                Bitmap bitmap = decodeBase64(input);
                detImage.setImageBitmap(bitmap);

            }





        }
    }


    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
