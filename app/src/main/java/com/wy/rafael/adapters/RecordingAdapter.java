package com.wy.rafael.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wy.rafael.R;
import com.wy.rafael.models.Recording;

import java.util.List;

/**
 * Created by christianeyee on 02/09/2016.
 */

public class RecordingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private List<Recording> recordings;

    private MediaPlayer mediaPlayer;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text;
        int position;
        Recording current;

        private ClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        public void setData(Recording current, int position) {
            this.text.setText(current.getText());
            this.position = position;
            this.current = current;
        }

        public interface ClickListener {
            void onClick(View v, int position, boolean isLongClick);
        }

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition(), false);
        }

    }

    public RecordingAdapter(Context context, List<Recording> recordings) {
        this.context = context;
        this.recordings = recordings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.recording_item, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Recording current = recordings.get(position);
        ((ViewHolder) holder).setData(current, position);
        ((ViewHolder) holder).setClickListener(new RecordClickListener());
    }

    @Override
    public int getItemCount() {
        return recordings.size();
    }

    public String getText(int position) {
        return recordings.get(position).getText();
    }

    public void removeItem(int position) {
        recordings.remove(position);
        notifyItemRemoved(position);
        // without this, inconsistent index
        notifyItemRangeChanged(position, recordings.size());

        // no animation since all views are redrawn
        // notifyDataSetChanged();
    }

    public void addItem(int position, Recording recording) {
        recordings.add(position, recording);
        notifyItemInserted(position);
        // without this, inconsistent index
        notifyItemRangeChanged(position, recordings.size());

        // no animation since all views are redrawn
        // notifyDataSetChanged();
    }

    public class RecordClickListener implements ViewHolder.ClickListener {

        @Override
        public void onClick(View v, int position, boolean isLongClick) {
            if (isLongClick) {

            }
            else {
                playRecording(v, position);
            }
        }
    }

    private void playRecording(View v, int position) {
        int audioId = recordings.get(position).getAudioId();

        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, audioId);
        mediaPlayer.start();
    }
}

