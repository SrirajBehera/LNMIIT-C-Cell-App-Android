package com.ccelllnmiit;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
{
    private Context mctx;
    private List<Contacts> contactList;
    private String is="cell";

    public ContactAdapter(Context mctx, List<Contacts> contactList, String is) {
        this.mctx = mctx;
        this.contactList = contactList;
        this.is=is;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        View view;
        inflater=LayoutInflater.from(mctx);

                view=inflater.inflate(R.layout.contact_layout,parent,false);
                return new ContactAdapter.ContactViewHolder(view,viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        final Contacts contact=contactList.get(position);


            holder.cont_name.setText(contact.getName());
            holder.cont_no.setText(contact.getNo());
            holder.cont_mail.setText(contact.getEmail());
            if (is.contains("pos")) {
                holder.cont_pos.setVisibility(View.VISIBLE);
                holder.cont_pos.setText(contact.getPos());
                if (contact.getEmail().contains("+-*/")) {
                    holder.cont_email.setVisibility(View.GONE);
                    holder.cont_mail.setVisibility(View.GONE);
                }
            }
            holder.cont_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + contact.getNo()));
                    mctx.startActivity(intent);
                }
            });

            holder.cont_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + contact.getEmail()));
                        mctx.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mctx, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            holder.cont_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                    contactIntent
                            .putExtra(ContactsContract.Intents.Insert.NAME, contact.getName())
                            .putExtra(ContactsContract.Intents.Insert.PHONE, contact.getNo());
                    if (!contact.getEmail().contains("+-*/")) {
                        contactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, contact.getEmail());
                    }

                    mctx.startActivity(contactIntent);

                }
            });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public int getItemViewType(int position) {

            return position;

    }

    class ContactViewHolder extends RecyclerView.ViewHolder
    {

        TextView cont_name,cont_no,cont_mail,cont_pos,sep;
        ImageView cont_phone,cont_email,cont_save;

        public ContactViewHolder(View itemView,int viewType) {
            super(itemView);


                cont_name = itemView.findViewById(R.id.cont_name);
                cont_no = itemView.findViewById(R.id.cont_no);
                cont_mail = itemView.findViewById(R.id.cont_mail);
                cont_pos = itemView.findViewById(R.id.cont_pos);
                cont_phone = itemView.findViewById(R.id.cont_phone);
                cont_email = itemView.findViewById(R.id.cont_email);
                cont_save = itemView.findViewById(R.id.cont_save);

        }
    }
}
