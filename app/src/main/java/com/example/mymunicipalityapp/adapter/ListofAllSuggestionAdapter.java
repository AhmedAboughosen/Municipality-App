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
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionItem;
import com.example.mymunicipalityapp.searchFragment.DisplayComplaintActivity;
import com.example.mymunicipalityapp.searchFragment.DisplaySuggestionActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListofAllSuggestionAdapter extends RecyclerView.Adapter<ItemStateViewHolder>  {



    private ArrayList<SuggestionItem> suggestionItems;
    private Context context;


    public ListofAllSuggestionAdapter(Context context, ArrayList<SuggestionItem> suggestionItems) {
        this.context = context;
        this.suggestionItems = new ArrayList<>(suggestionItems);
    }


    public void setItems(ArrayList<SuggestionItem> suggestionItems){
        this.suggestionItems = suggestionItems;
    }

    @NonNull
    @Override
    public ItemStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infalte the item Layout
       return ItemStateViewHolder.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemStateViewHolder holder, int i) {
        try {

            if(suggestionItems.get(i).getNumber() != 0){
                ViewGroup.LayoutParams params = holder.state.getLayoutParams();
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.state.setLayoutParams(params);


                holder.state.setText(suggestionItems.get(i).getState());
                holder.state.setBackgroundResource(suggestionItems.get(i).getBackgroundColor());


                holder.number.setText(  "رقم المقترح: " + suggestionItems.get(i).getNumber());
                holder.detail.setText(suggestionItems.get(i).getDetails());
                holder.date_text.setText("تاريخ الارسال :");
                holder.date_card.setText(suggestionItems.get(i).getDate());


                holder.cardView.setOnClickListener(view -> {
                    // Set data to pass
                    //   Log.e("alert" , "i am here");
                    Intent intent   = new Intent(context, DisplaySuggestionActivity.class);
                    intent.putExtra("state_suggestion_text", suggestionItems.get(i).getState()+"");
                    intent.putExtra("state_suggestion_background", suggestionItems.get(i).getBackgroundColor());
                    intent.putExtra("number_suggestion", suggestionItems.get(i).getNumber());
                    intent.putExtra("detail_suggestion", suggestionItems.get(i).getDetails());
                    intent.putExtra("date_card", suggestionItems.get(i).getDate());
                    intent.putExtra("type", suggestionItems.get(i).getType());
                    intent.putExtra("municipality",  suggestionItems.get(i).getMunicipality());

                    context.startActivity(intent);
                });

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return suggestionItems.size();
    }

}
