package com.mhacks.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Alex on 9/22/13.
 */
public class RecipeAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] values;
    public RecipeAdapter(Context context, String[] values) {
        super(context, R.layout.list_row, values);

        this.context = context;
        this.values = values;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public String getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);

        TextView instruction = (TextView) rowView.findViewById(R.id.instruction);
        TextView timer = (TextView) rowView.findViewById(R.id.timer);
        TextView step = (TextView) rowView.findViewById(R.id.step);
        instruction.setText(values[position]);
        // Change the icon for Windows and iPhone
        String s = values[position];

        instruction.setText(s);
        /*
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }
        */

        return rowView;
    }
}
