package com.ccelllnmiit;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FAQadap extends RecyclerView.Adapter<FAQadap.LinkViewHolder>
{
    private Context mctx;
    private List<Contacts> contactList;

    public FAQadap(Context mctx, List<Contacts> contactList) {
        this.mctx = mctx;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public FAQadap.LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(mctx);
        View view=inflater.inflate(R.layout.faq_layout,parent,false);
        return new FAQadap.LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FAQadap.LinkViewHolder holder, int position) {
        final Contacts contact=contactList.get(position);


        holder.ques.setText(contact.getName());
        holder.ans.setText(contact.getNo());

        holder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug",contact.getName());
                if(holder.ans.getVisibility()==View.GONE)
                {
                    holder.but.setImageResource(R.drawable.ic_remove);
                    holder.ans.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.but.setImageResource(R.drawable.ic_add);
                    holder.ans.setVisibility(View.GONE);
                }
            }
        });

        holder.ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.ans.getVisibility()==View.GONE)
                {
                    holder.but.setImageResource(R.drawable.ic_remove);
                    holder.ans.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.but.setImageResource(R.drawable.ic_add);
                    holder.ans.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class LinkViewHolder extends RecyclerView.ViewHolder
    {

        TextView ques,ans;
        ImageView but;

        public LinkViewHolder(View itemView) {
            super(itemView);

            ques=itemView.findViewById(R.id.ques);
            ans=itemView.findViewById(R.id.ans);
            but=itemView.findViewById(R.id.but);
        }
    }
}