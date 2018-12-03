package com.example.dendy_s784.mvccleanapptemplate.mainactivity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.model.Note;

import java.util.ArrayList;

/**
 *
 */

public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.MyViewHolder> {

    public ArrayList<Note> usersList=new ArrayList<>();
    public ArrayList<Note> selected_usersList=new ArrayList<>();
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout listNOte;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.note_title);
            listNOte =(LinearLayout)view.findViewById(R.id.listnote);

        }
    }


    public MultiSelectAdapter(Context context, ArrayList<Note> userList, ArrayList<Note> selectedList) {
        this.mContext=context;
        this.usersList = userList;
        this.selected_usersList = selectedList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = usersList.get(position);
        holder.title.setText(note.getmTitle());

        if(selected_usersList.contains(usersList.get(position)))
            holder.listNOte.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
        else
            holder.listNOte.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_normal_state));

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}

