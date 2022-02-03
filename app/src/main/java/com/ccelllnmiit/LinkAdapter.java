package com.ccelllnmiit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder>
{
    private Context mctx;
    private List<Contacts> contactList;
    private Display disp;

    public LinkAdapter(Context mctx, List<Contacts> contactList, Display disp) {
        this.mctx = mctx;
        this.contactList = contactList;
        this.disp=disp;
    }

    @NonNull
    @Override
    public LinkAdapter.LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        View view;
        switch (viewType)
        {
            case 0:inflater=LayoutInflater.from(mctx);
                    view=inflater.inflate(R.layout.seperator,parent,false);
                return new LinkAdapter.LinkViewHolder(view,viewType);
                default:inflater=LayoutInflater.from(mctx);
                            view=inflater.inflate(R.layout.link_layout,parent,false);
                    return new LinkAdapter.LinkViewHolder(view,viewType);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final LinkAdapter.LinkViewHolder holder, int position) {
        final Contacts contact=contactList.get(position);

        if(position==contactList.size()-4)
        {
            holder.sep.setText(contact.getName());
        }
        else
        {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            disp.getMetrics(displayMetrics);
            holder.site.getLayoutParams().width=(int)(displayMetrics.widthPixels-20)/2;

            holder.site.setText(contact.getName());
            holder.web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contact.getNo()));
                    mctx.startActivity(browserIntent);
                }
            });

            holder.copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) mctx.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(contact.getName(),contact.getNo());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mctx,"Link has been copied",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType=1;
        if(position==contactList.size()-4)
        {
            viewType=0;
        }
        return viewType;
    }

    class LinkViewHolder extends RecyclerView.ViewHolder
    {

        TextView site,sep;
        ImageView web,copy;

        public LinkViewHolder(View itemView,int viewType) {
            super(itemView);

            if(viewType==0)
            {
                sep=itemView.findViewById(R.id.sep);
            }
            else
            {
                site=itemView.findViewById(R.id.site);
                web=itemView.findViewById(R.id.web);
                copy=itemView.findViewById(R.id.copy);
            }
        }
    }
}