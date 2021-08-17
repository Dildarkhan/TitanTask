package com.titan.titantask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.progressindicator.DeterminateDrawable;
import com.google.android.material.textfield.TextInputLayout;
import com.titan.titantask.util.ChessItem;
import com.titan.titantask.util.ChessItemAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements ChessItemAdapter.ItemClickListener{
    public static final String TAG = "MainActivity";
    private EditText inputNumber;
    private TextInputLayout inputLayoutNumber;
    Button btnProceed;
    RecyclerView rv_chess;
    ChessItemAdapter chessItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputLayoutNumber = (TextInputLayout) findViewById(R.id.input_layout_number);
        inputNumber=(EditText)findViewById(R.id.input_number);
        btnProceed=(Button) findViewById(R.id.btProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int num = Integer.parseInt(inputNumber.getText().toString());
                    drawView(num);
                }catch (Exception e){
                    Log.d(TAG,"Exception");
                    e.printStackTrace();
                }
            }
        });
    }


    private void drawView(int num){
        Log.d(TAG,"Given number: "+num);
        int square=num*num;
        List<ChessItem> chessItemList=new ArrayList<>();
        //int[] data = new int[square];
        for (int i=0;i<square;i++){
            chessItemList.add(i,new ChessItem(1,false));
        }
        rv_chess=(RecyclerView)findViewById(R.id.rv_chessList);
        rv_chess.setHasFixedSize(false);
        rv_chess.setLayoutManager(new GridLayoutManager(this, num));
        rv_chess.setItemAnimator(new DefaultItemAnimator());
        chessItemAdapter=new ChessItemAdapter(MainActivity.this,chessItemList,num);
        rv_chess.setAdapter(chessItemAdapter);
        chessItemAdapter.setClickListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {

    }

}