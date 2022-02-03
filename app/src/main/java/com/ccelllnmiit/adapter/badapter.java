package com.ccelllnmiit.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccelllnmiit.Pop;
import com.ccelllnmiit.R;
import com.ccelllnmiit.User;
import com.ccelllnmiit.model.MyDataModel;

import java.util.ArrayList;
import java.util.List;

public class badapter extends RecyclerView.Adapter<badapter.Myviewholder> {
    private List<MyDataModel> modelList;
    private Context mctx;

    User us;


    public badapter(Context context, List<MyDataModel> objects) {

        modelList = objects;
        mctx = context;
        Log.d("newtag","BadAdapter se aaya hu");
        us=new User(mctx.getApplicationContext());
        Log.d("newtag","BadAdapter=>\n"+us.getDetSet().toString());
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.layout_row_view, parent, false);


        return new Myviewholder(view);


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, int position) {

        final MyDataModel item = modelList.get(position);


        holder.title.setText(item.getName());
        holder.content.setText(item.getCountry());

        if(item.isN())
        {
            holder.lbl.setVisibility(View.VISIBLE);
        }

        holder.clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                holder.clicker.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.clicker.setEnabled(true);
                    }
                }, 500);


                        Intent intent = new Intent(mctx, Pop.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("copyname",holder.title.getText());
                        intent.putExtra("copycountry",holder.content.getText());

                        mctx.startActivity(intent);

                        if(item.isN())
                        {
                            item.setN(false);
                            ArrayList<String> newl=us.getBulSet();
                            newl.set(us.getDetSet().indexOf(holder.title.getText().toString()+"Φ"+
                                    holder.content.getText().toString()),"false");
                            us.update_bul(newl);
                            holder.lbl.setVisibility(View.GONE);
                        }
                        //v.getContext().startActivity(new Intent(v.getContext(),Pop.class));


            }
        });


    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }


    class Myviewholder extends RecyclerView.ViewHolder {

        TextView title,clicker;
        TextView content;
        ImageView lbl;

        public Myviewholder(final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            clicker = itemView.findViewById(R.id.clicker);
            content = itemView.findViewById(R.id.content);
            lbl = itemView.findViewById(R.id.new_u);



        }
    }
}



