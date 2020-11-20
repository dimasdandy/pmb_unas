package com.db.pmb_unas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.db.pmb_unas.R;

import java.util.ArrayList;
import java.util.List;


public class ProdiAdapter extends ArrayAdapter<String>{
    private List<String> rootList;
    private List<String> curList;
    private List<String> suggestions;
    private int resourceId;

    public ProdiAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
        curList = objects;
        suggestions = new ArrayList<>();
        rootList = new ArrayList<>();

        for (String item : objects){
            rootList.add(item);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tvSearch);
        textView.setText(curList.get(position));
        return super.getView(position, convertView, parent);
    }

    public Filter getFilter(){
        return searchFilter;
    }

    Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null){
                suggestions.clear();
                for (String item : rootList){
                    if (item.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        suggestions.add(item);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = suggestions;
                results.count = suggestions.size();
                return results;
            }else{
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            List<String> results = (List<String>) filterResults.values;
            clear();
            if(results != null && results.size() > 0){
                addAll(results);
            }
            notifyDataSetChanged();
        }
    };
}
