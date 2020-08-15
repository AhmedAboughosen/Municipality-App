package com.example.mymunicipalityapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.addComplaintActivity.AddComplaintActivity;
import com.example.mymunicipalityapp.addComplaintActivity.ComplaintDetailsFragment;
import com.example.mymunicipalityapp.addSuggestionActivity.AddSuggestionActivity;
import com.example.mymunicipalityapp.addSuggestionActivity.SuggestionDetailsFragment;
import com.example.mymunicipalityapp.model.MunicipalityModel;
import com.example.mymunicipalityapp.municipalityFragment.MunicipalityFragment;
import com.example.mymunicipalityapp.viewModel.DialogViewModel;

import java.util.ArrayList;
import java.util.List;

public class MunicipalityAdapter extends RecyclerView.Adapter<ItemTypesViewHolder> implements Filterable {


    private ArrayList<MunicipalityModel> municipalityModel;
    private ArrayList<MunicipalityModel> municipalityModelSearch;
    private Context context;
    private boolean iscomplaint;


    public MunicipalityAdapter(Context context, ArrayList<MunicipalityModel> municipality , boolean iscomplaint) {
        this.context = context;
        this.municipalityModel = new ArrayList<>(municipality);
        this.iscomplaint = iscomplaint;
    }


    public void setItems(ArrayList<MunicipalityModel> municipalityModel) {
        this.municipalityModel.clear();
        this.municipalityModel = municipalityModel;
        this.municipalityModelSearch = new ArrayList<>( municipalityModel);
    }


    @NonNull
    @Override
    public ItemTypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ItemTypesViewHolder.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTypesViewHolder holder, int i) {
        try {
            if (!municipalityModel.get(i).getMunicipality_name().equals(""))
            {
                holder.title.setText(municipalityModel.get(i).getMunicipality_name());

                if (i == municipalityModel.size() - 1) {
                    holder.line.setVisibility(View.GONE);
                }
                else {
                    holder.line.setVisibility(View.VISIBLE);
                }

                holder.help.setImageResource(R.drawable.ic_help_outline_black_24dp);
                holder.help.setOnClickListener(v -> new DialogViewModel().mapDialog(context , municipalityModel.get(i).getLatitude() ,municipalityModel.get(i).getLongitude() , true ));


                holder.itemView.setOnClickListener(view -> {
                    // Set data to pass

                    try {
                        MunicipalityModel m = new MunicipalityModel(context);
                        m.AllClear();
                        m.putData(municipalityModel.get(i).getMunicipality_name() , municipalityModel.get(i).getId());


                        if(iscomplaint)
                        {
                            AddComplaintActivity.getInstance().update_view(R.layout.toolbar_complaint_details, new ComplaintDetailsFragment());
                        }
                        else {
                            AddSuggestionActivity.getInstance().update(R.layout.toolbar_suggestion_details , new SuggestionDetailsFragment());
                        }

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
        return municipalityModel.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            FilterResults results = new FilterResults();

            try {

                results.values =Filter(constraint);

            }catch (Exception ex){
                ex.printStackTrace();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                municipalityModel.clear();
                municipalityModel.addAll((List) results.values);
                if(municipalityModel.size() == 0){
                    Toast.makeText(context ,  "البلدية غير موجوده"  ,Toast.LENGTH_LONG).show();
                    return;
                }
                MunicipalityFragment.getInstance().customAdapter.notifyDataSetChanged();
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    };

    public List<MunicipalityModel> Filter(CharSequence constraint){
        List<MunicipalityModel> filteredList = new ArrayList<>();

        try {
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(municipalityModelSearch);
            } else {
                String filterPattern = constraint.toString();

                for (MunicipalityModel item : municipalityModelSearch) {
                    String mun = item.getMunicipality_name();

                    if (mun.contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return filteredList;
    }

}
