package com.example.mymunicipalityapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.example.mymunicipalityapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemTypesViewHolder extends RecyclerView.ViewHolder {
    // init the item view's
    @BindView(R.id.title)
    LoaderTextView title;
    @BindView(R.id.help)
    LoaderImageView help;
    @BindView(R.id.line)
    View line;

    private ItemTypesViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    @NonNull
    public static ItemTypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        // infalte the item Layout
        ItemTypesViewHolder vh = null;
        try {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_msc, parent, false);
            vh = new ItemTypesViewHolder(v); // pass the view to View Holder
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return vh;
    }

}
