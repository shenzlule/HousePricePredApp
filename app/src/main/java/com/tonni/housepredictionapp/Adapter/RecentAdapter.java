package com.tonni.housepredictionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonni.housepredictionapp.R;
import com.tonni.housepredictionapp.models.PredictedModel;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {

    private ArrayList<PredictedModel> predictedModel_list = new ArrayList<PredictedModel>();
    Context context;
    private int expandedPosition = -1;  // Variable to keep track of the currently expanded position

    public RecentAdapter(ArrayList<PredictedModel> predictedModel_list, Context context) {
        this.predictedModel_list = predictedModel_list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new RecentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {

        PredictedModel predictedModel=predictedModel_list.get(holder.getAdapterPosition());



        holder.p1.setText(String.valueOf(predictedModel.getPosted_by()));
        holder.p2.setText(String.valueOf(predictedModel.getRera()));
        holder.p3.setText(String.valueOf(predictedModel.getBhk()));
        holder.p4.setText(String.valueOf(predictedModel.getBhk_rk()));
        holder.p5.setText(String.valueOf(predictedModel.getArea()));
        holder.p6.setText(String.valueOf(predictedModel.getResale()));
        holder.p7.setText(String.valueOf(predictedModel.getLongi()));
        holder.p8.setText(String.valueOf(predictedModel.getLati()));
        holder.price.setText(String.valueOf(predictedModel.getPrice()));
        holder.date_.setText(String.valueOf(predictedModel.get_date_()));

        // Set visibility based on the expanded position
        if (holder.getAdapterPosition() == expandedPosition) {
            holder.expandView.setVisibility(View.VISIBLE);
            holder.down_k.setVisibility(View.GONE);
            holder.up_k.setVisibility(View.VISIBLE);
        } else {
            holder.expandView.setVisibility(View.GONE);
            holder.down_k.setVisibility(View.VISIBLE);
            holder.up_k.setVisibility(View.GONE);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousExpandedPosition = expandedPosition;
                if (holder.getAdapterPosition() == expandedPosition) {
                    expandedPosition = -1;
                } else {
                    expandedPosition = holder.getAdapterPosition();
                }
                // Notify the adapter to refresh the views
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(expandedPosition);
            }
        });




    }

    @Override
    public int getItemCount() {
        return predictedModel_list.size();
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder{

        TextView p1,p2,p3,p4,p5,p6,p7,p8,price,date_;
        ImageView up_k,down_k;
        RelativeLayout relativeLayout;
        LinearLayout expandView;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);


            p1=itemView.findViewById(R.id.p1_);
            p2=itemView.findViewById(R.id.p2_);
            p3=itemView.findViewById(R.id.p3_);
            p4=itemView.findViewById(R.id.p4_);
            p5=itemView.findViewById(R.id.p5_);
            p6=itemView.findViewById(R.id.p6_);
            p7=itemView.findViewById(R.id.p7_);
            p8=itemView.findViewById(R.id.p8_);
            price=itemView.findViewById(R.id.price_);
            date_=itemView.findViewById(R.id.date_predicted);
            up_k=itemView.findViewById(R.id.key_up);
            down_k=itemView.findViewById(R.id.key_down);
            relativeLayout=itemView.findViewById(R.id.tap_on_view);
            expandView=itemView.findViewById(R.id.expand_view);
        }
    }
}
