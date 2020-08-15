package com.example.mymunicipalityapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintItem;
import com.example.mymunicipalityapp.searchFragment.DisplayComplaintActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListofAllComplaintsAdapter extends RecyclerView.Adapter<ItemStateViewHolder> {


    private ArrayList<ComplaintItem> complaintItems;
    private Context context;


    public ListofAllComplaintsAdapter(Context context, ArrayList<ComplaintItem> compalintTypeModel) {
        this.context = context;
        this.complaintItems = new ArrayList<>(compalintTypeModel);
    }


    public void setItems(ArrayList<ComplaintItem> complaintItems){
        this.complaintItems = complaintItems;
    }


    @NonNull
    @Override
    public ItemStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // infalte the item Layout
        return ItemStateViewHolder.onCreateViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemStateViewHolder compalintViewHolder, int i) {
        try {

            if(complaintItems.get(i).getNumber() != 0){
              //  compalintViewHolder.state_complaint.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
           ViewGroup.LayoutParams params = compalintViewHolder.state.getLayoutParams();
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                compalintViewHolder.state.setLayoutParams(params);


                compalintViewHolder.state.setText(complaintItems.get(i).getState());
                compalintViewHolder.state.setBackgroundResource(complaintItems.get(i).getBackgroundColor());


                compalintViewHolder.number.setText(  "رقم الشكوى: " + complaintItems.get(i).getNumber());
                compalintViewHolder.detail.setText(complaintItems.get(i).getDetails());
                compalintViewHolder.date_text.setText("تاريخ الارسال :");
                compalintViewHolder.date_card.setText(complaintItems.get(i).getDate());


                compalintViewHolder.cardView.setOnClickListener(view -> {
                    // Set data to pass
                    //   Log.e("alert" , "i am here");
                    try {
                        Intent intent   = new Intent(context, DisplayComplaintActivity.class);
                        intent.putExtra("state_complaint_text", complaintItems.get(i).getState()+"");
                        intent.putExtra("state_complaint_background", complaintItems.get(i).getBackgroundColor());
                        intent.putExtra("number_complaint", complaintItems.get(i).getNumber());
                        intent.putExtra("detail_complaint", complaintItems.get(i).getDetails());
                        intent.putExtra("date_card", complaintItems.get(i).getDate());
                        intent.putExtra("type", complaintItems.get(i).getType());
                        intent.putExtra("municipality",  complaintItems.get(i).getMunicipality());

                        context.startActivity(intent);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                });

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return complaintItems.size();
    }

}
