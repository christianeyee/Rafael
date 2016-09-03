package com.wy.rafael.adapters;

/**
 * Created by christianeyee on 02/09/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wy.rafael.R;
import com.wy.rafael.models.Buddy;

import java.util.List;

public class BuddyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Buddy> buddies;
    private LayoutInflater inflater;

    public BuddyAdapter(Context context, List<Buddy> buddies) {
        this.context = context;
        this.buddies = buddies;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.buddy_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Buddy current = buddies.get(position);
        ((MyViewHolder) holder).setData(current, position);
    }

    @Override
    public int getItemCount() {
        return buddies.size();
    }

    public String getItemName(int position) {
        return buddies.get(position).getName();
    }

    public String getItemMobile(int position) {
        return buddies.get(position).getMobile();
    }

    public void removeItem(int position) {
        buddies.remove(position);
        notifyItemRemoved(position);
        // without this, inconsistent index
        notifyItemRangeChanged(position, buddies.size());

        // no animation since all views are redrawn
        // notifyDataSetChanged();
    }

    public void addItem(int position, Buddy Buddy) {
        buddies.add(position, Buddy);
        notifyItemInserted(position);
        // without this, inconsistent index
        notifyItemRangeChanged(position, buddies.size());

        // no animation since all views are redrawn
        // notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, mobile;
        ImageView img;
        int position;
        Buddy current;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.mobile = (TextView) itemView.findViewById(R.id.mobile);
            this.img = (ImageView) itemView.findViewById(R.id.img);
        }

        public void setData(Buddy current, int position) {
            this.name.setText(current.getName());
            this.mobile.setText(current.getMobile());
            this.img.setImageResource(current.getImgId());
            this.position = position;
            this.current = current;
        }

    }
}
