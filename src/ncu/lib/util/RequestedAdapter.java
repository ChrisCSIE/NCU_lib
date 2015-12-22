package ncu.lib.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

import ncu.lib.R;

/**
 * Created by chenli-han on 14/11/6.
 */
public class RequestedAdapter extends ArrayAdapter {
    private ArrayList<String> booknames;
    private ArrayList<String> status;
    private LayoutInflater inflater;
    private WindowManager vm;
    private Display display;
    private DisplayMetrics metrics;

    public RequestedAdapter(Context context, ArrayList<String> booknames, ArrayList<String> status) {
        super(context, 0, booknames);
        this.booknames = booknames;
        this.status = status;
        
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        vm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = vm.getDefaultDisplay();
        
        metrics = new DisplayMetrics();
        vm.getDefaultDisplay().getMetrics(metrics);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        final String bookname = booknames.get(position);
        final String book_status = status.get(position);
        
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        if(bookname != null) {
            view = inflater.inflate(R.layout.item_requested, null);
            //view.setOnClickListener(null);
            final CheckedTextView booknameView = (CheckedTextView) view.findViewById(R.id.requested_item);
            booknameView.setTextSize(width/50);
            final TextView bookStatusView = (TextView) view.findViewById(R.id.requested_item_status);
            bookStatusView.setTextSize(width/70);

            booknameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((CheckedTextView)view).toggle();
                }
            });

            bookStatusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    booknameView.toggle();
                }
            });

            booknameView.setText(bookname);
            bookStatusView.setText(book_status);
        }

        return view;
    }
}

