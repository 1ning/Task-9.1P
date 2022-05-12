package com.example.task61d;

import static android.content.Intent.createChooser;
import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyorderAdapter extends RecyclerView.Adapter<MyorderAdapter.MyViewHolder> {
    private List<item> list;
    private View inflater;
    private Context mContext;


    public MyorderAdapter(Context context, List<item> list) {
        this.list = list;
        mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(mContext).inflate(R.layout.add_contacts2, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).thing);
        holder.id=list.get(position).id;
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Button title;
        String id;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (Button) itemView.findViewById(R.id.title);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 Global.id=id;
                    Intent intent=new Intent(mContext,Orderdetail.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}

