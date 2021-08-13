package com.titan.titantask.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.service.chooser.ChooserTarget;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.titan.titantask.MainActivity;
import com.titan.titantask.R;

import java.util.List;

public class ChessItemAdapter extends RecyclerView.Adapter<ChessItemAdapter.ViewHolder> {

    private List<ChessItem> chessItems;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int dataSize;
    private boolean flagColor = false;
    public Context mContext;

    // data is passed into the constructor
    public ChessItemAdapter(Context context, List<ChessItem> chessItems) {
        this.mInflater = LayoutInflater.from(context);
        this.chessItems = chessItems;
        this.mContext=context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_chess_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position % 2 == 0) {
            if (flagColor) {
                holder.lytChessItem.setBackgroundColor(R.color.bl);
                flagColor = false;
            } else {
                holder.lytChessItem.setBackgroundColor(R.color.wt);
                flagColor = true;
            }
        }

        holder.myTextView.setText(chessItems.get(position).getVal()+"");

        holder.myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessItemClicked(v, position,chessItems);
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return chessItems.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView;
        RelativeLayout lytChessItem;

        ViewHolder(View itemView) {
            super(itemView);
            lytChessItem = itemView.findViewById(R.id.lytChessItem);
            myTextView = itemView.findViewById(R.id.tv_chessItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /*// convenience method for getting data at click position
    int getItem(int id) {
        return chessItems[id];
    }*/

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    public void chessItemClicked(View v,int pos, List<ChessItem> chessItemList) {
        Handler[] handlers=new Handler[chessItemList.size()];
        for(int i=0;i<chessItemList.size();i++){
            handlers[i]=new Handler();
        }

       /* Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int valTemp = Integer.parseInt(((TextView) v.findViewById(R.id.tv_chessItem)).getText().toString()) + 1;
                chessItemList.set(pos,new ChessItem(valTemp,true));
                notifyItemChanged(pos,chessItemList);
                if(chessItemList.get(pos).flagRunning) {
                    handlers[pos].postDelayed(this, 1000);
                }
            }
        };*/

        if(chessItemList.get(pos).flagRunning){
            handlers[pos].removeCallbacks(null);
            chessItemList.get(pos).setFlagRunning(false);
            notifyDataSetChanged();
            Log.d("KHAN "," not RUnning");
        }else{
            handlers[pos].postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("KHAN ","run");
                    int valTemp = Integer.parseInt(((TextView) v.findViewById(R.id.tv_chessItem)).getText().toString()) + 1;
                    chessItemList.set(pos,new ChessItem(valTemp,true));
                    notifyItemChanged(pos,chessItemList);
                    if(chessItemList.get(pos).flagRunning) {
                        Log.d("KHAN ","RUnning");
                        handlers[pos].postDelayed(this, 1000);
                    }
                }
            }, 1000);
        }
    }


}