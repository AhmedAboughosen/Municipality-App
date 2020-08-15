package com.example.mymunicipalityapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.addSuggestionActivity.AddSuggestionActivity;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionTypeModel;
import com.example.mymunicipalityapp.municipalityFragment.MunicipalityFragment;
import com.example.mymunicipalityapp.viewModel.DialogViewModel;

import java.util.ArrayList;

public class SuggestionTypeAdapter extends RecyclerView.Adapter<ItemTypesViewHolder>{


    private ArrayList<SuggestionTypeModel> suggestionTypeModels;
    private Context context;


    public SuggestionTypeAdapter(Context context, ArrayList<SuggestionTypeModel> suggestionTypeModels) {
        this.context = context;
        this.suggestionTypeModels = new ArrayList<>(suggestionTypeModels);
    }


    @NonNull
    @Override
    public ItemTypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infalte the item Layout
        return ItemTypesViewHolder.onCreateViewHolder(parent);
    }


    public void setItems(ArrayList<SuggestionTypeModel> suggestionTypeModels){
        this.suggestionTypeModels = new ArrayList<>(suggestionTypeModels);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemTypesViewHolder holder, int position) {
        try {
            if(!suggestionTypeModels.get(position).getTitle().equals(""))
            {
                holder.title.setText(suggestionTypeModels.get(position).getTitle());

                if(position == suggestionTypeModels.size() -1){
                    holder.line.setVisibility(View.GONE);
                }

                holder.help.setImageResource(R.drawable.ic_help_outline_black_24dp);
                holder.help.setOnClickListener(v -> new DialogViewModel().helpDialogSuggestion(context ,suggestionTypeModels.get(position).getTitle() +"", suggestionTypeModels.get(position).getInfo()+"" , suggestionTypeModels.get(position).getId()  ));


                holder.itemView.setOnClickListener(view -> {

                    SuggestionTypeModel suggestionTypeModel = new SuggestionTypeModel(context);
                    suggestionTypeModel.AllClear();
                    suggestionTypeModel.putData( suggestionTypeModels.get(position).getTitle() , suggestionTypeModels.get(position).getId()) ;

                    AddSuggestionActivity.getInstance().update(R.layout.toolbar_municipality_type, new MunicipalityFragment(false));
                });
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return suggestionTypeModels.size();
    }

}
