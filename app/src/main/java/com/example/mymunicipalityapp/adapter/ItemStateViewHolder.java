package com.example.mymunicipalityapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymunicipalityapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemStateViewHolder extends RecyclerView.ViewHolder  {

    // init the item view's
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.date_card)
    TextView date_card;
    @BindView(R.id.date_text)
    TextView date_text;
    @BindView(R.id.card_view)
    CardView cardView;

    public ItemStateViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }


    @NonNull
    public static ItemStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup) {
        // infalte the item Layout
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_complaint_suggestion_state, viewGroup, false);
        ItemStateViewHolder vh = null;
        try {
            vh = new ItemStateViewHolder(v); // pass the view to View Holder
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return vh;
    }

}
