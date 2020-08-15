package com.example.mymunicipalityapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.addComplaintActivity.AddComplaintActivity;
import com.example.mymunicipalityapp.addComplaintActivity.ComplaintMapFragment;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintTypeModel;
import com.example.mymunicipalityapp.viewModel.DialogViewModel;
import java.util.ArrayList;

public class ComplaintTypeAdapter extends RecyclerView.Adapter<ItemTypesViewHolder>{

    private ArrayList<ComplaintTypeModel> complaintTypeModel;
    private Context context;


    public ComplaintTypeAdapter(Context context, ArrayList<ComplaintTypeModel> complaintTypeModel) {
        this.context = context;
        this.complaintTypeModel = new ArrayList<>(complaintTypeModel);
    }


    @NonNull
    @Override
    public ItemTypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infalte the item Layout
         return  ItemTypesViewHolder.onCreateViewHolder(parent );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTypesViewHolder holder, int position) {
        try {
            if(!complaintTypeModel.get(position).getTitle().equals(""))
            {
                holder.title.setText(complaintTypeModel.get(position).getTitle());

                if(position == complaintTypeModel.size() -1){
                    holder.line.setVisibility(View.GONE);
                }

                holder.help.setImageResource(R.drawable.ic_help_outline_black_24dp);
                holder.help.setOnClickListener(v -> new DialogViewModel().helpDialogComplaint(context , complaintTypeModel.get(position).getTitle() , complaintTypeModel.get(position).getInfo() , complaintTypeModel.get(position).getId()  ));


                holder.itemView.setOnClickListener(view -> {
                    // Set data to pass
                    ComplaintTypeModel c =  new ComplaintTypeModel(context);
                    c.AllClear();
                    c.putData(complaintTypeModel.get(position).getTitle(), complaintTypeModel.get(position).getId()) ;
                    AddComplaintActivity.getInstance().update_view(R.layout.toolbar_complaint_map, new ComplaintMapFragment());
                });
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void setItems(ArrayList<ComplaintTypeModel> complaintTypeModel) {
        this.complaintTypeModel = complaintTypeModel;
    }

    @Override
    public int getItemCount() {
        return complaintTypeModel.size();
    }

}
