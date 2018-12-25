package com.mywill.soundrecorder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.billingclient.api.SkuDetails;
import com.mywill.soundrecorder.R;

import java.util.List;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.ViewHolder> {

    private List<SkuDetails> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public PurchaseListAdapter(Context context, List<SkuDetails> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_purchase, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      final SkuDetails skuDetails = mData.get(position);
        holder.tvTitle.setText(skuDetails.getTitle());
        holder.tvContent.setText(skuDetails.getDescription());
        holder.tvPrice.setText(skuDetails.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (mClickListener!= null){
              mClickListener.onItemClick(skuDetails);
            }
          }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvPrice;

        ViewHolder(View itemView) {
            super(itemView);
          tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
          tvContent = (TextView)itemView.findViewById(R.id.tvContent);
          tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
        }

    }

    // convenience method for getting data at click position
    SkuDetails getItem(int pos) {
        return mData.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(SkuDetails item);
    }
}