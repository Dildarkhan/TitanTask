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
    private int dataSize,inputSize;
    private boolean flagColor = false;
    public Context mContext;
    public Handler singleHandler;
    public boolean isHandlerRunning;
    int row,col;

    // data is passed into the constructor
    public ChessItemAdapter(Context context, List<ChessItem> chessItems,int num) {
        this.mInflater = LayoutInflater.from(context);
        this.chessItems = chessItems;
        this.mContext=context;
        this.dataSize=chessItems.size();
        this.inputSize=num;
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
        holder.setIsRecyclable(false);
        if(dataSize%2==0){
            if (position % 2 == 0) {
                /*try {
                    if (inputSize % position == 0) {
                        flagColor = false;
                    } else {
                        flagColor = true;
                    }
                }catch (Exception e){}

               if (flagColor) {
                    holder.lytChessItem.setBackgroundColor(R.color.bl);
                } else {
                    holder.lytChessItem.setBackgroundColor(R.color.wt);
                }*/
            }
        }else{
            if (position % 2 == 0) {
                holder.lytChessItem.setBackgroundColor(R.color.bl);
               /* if (flagColor) {
                    holder.lytChessItem.setBackgroundColor(R.color.bl);
                    flagColor = false;
                } else {
                    holder.lytChessItem.setBackgroundColor(R.color.wt);
                    flagColor = true;
                }*/
            }
        }

        holder.myTextView.setText(chessItems.get(position).getVal()+"");
        holder.myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessItemClicked(v, position);
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


    public void executeRepeated(){
        Log.d("KHAN ","executing repeated");
        for(int i=0;i<chessItems.size();i++){
            if(chessItems.get(i).flagRunning){
                int valTemp = chessItems.get(i).getVal() + 1;
                chessItems.set(i,new ChessItem(valTemp,true));
                notifyItemChanged(i,chessItems);
            }
        }
    }

   public void startHandler(){
        Log.d("KHAN ","Handler started");
        isHandlerRunning=true;
       singleHandler.postDelayed(new Runnable() {
           @Override
           public void run() {
               executeRepeated();
               singleHandler.postDelayed(this, 1000);
           }
       }, 1000);
   }

    public void chessItemClicked(View v,int pos) {
        int valTemp = Integer.parseInt(((TextView) v.findViewById(R.id.tv_chessItem)).getText().toString()) ;
        if(chessItems.get(pos).flagRunning){
            chessItems.set(pos,new ChessItem(valTemp,false));
        }else{
            chessItems.set(pos,new ChessItem(valTemp,true));
        }
        notifyItemChanged(pos,chessItems);


        //check weather handler is already running or not
        if(isHandlerRunning){
            //handler already running
        }else{
            //initiate handler and call startHandler method to start repeated hanlder
            singleHandler=new Handler();
            startHandler();
        }
    }

}