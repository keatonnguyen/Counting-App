package keaton.counterapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<String> {
    public HistoryAdapter(@NonNull Context ctx, @NonNull List<String> data) {
        super(ctx, android.R.layout.simple_list_item_1, data);
    }

    @NonNull @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        TextView tv = v.findViewById(android.R.id.text1);
        tv.setSingleLine(false);
        return v;
    }
}
