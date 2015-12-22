package ncu.lib.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import ncu.lib.R;
import ncu.lib.activity.GlobalStaticVariable;
import ncu.lib.library.JsonObjectRequestWithPostParams;
import ncu.lib.library.VolleyProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chenli-han on 2014/8/14.
 */
public class RequestBookAdapter extends ArrayAdapter<Item> {
    //private Context context;
    private ArrayList<Item> items;
    private LayoutInflater inflater;

    public RequestBookAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        //this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int fontSize = 20;

        final Item item = items.get(position);
        if (item != null) {
            if(item.isSection()) {
                SectionItem sectionItem = (SectionItem) item;
                view = inflater.inflate(R.layout.item_section_header, null);

                view.setOnClickListener(null);

                final TextView sectionView = (TextView) view.findViewById(R.id.item_section_header);
                sectionView.setTextSize(fontSize);
                sectionView.setText(sectionItem.getTitle());
            } else {
                EntryItem entryItem = (EntryItem) item;

                view = inflater.inflate(R.layout.button_item_entry, null);

                final Button button = (Button) view.findViewById(R.id.request_book_button);

                if(button != null) {
                    button.setText(entryItem.getTitle());
                }

                if(entryItem instanceof Requestable) {
                    final String itemNumber = ((Requestable) entryItem).getItemNumber();
                    final String url = ((Requestable) entryItem).getUrl();
                    final String request = ((Requestable) entryItem).getRequest();

                    if(!((Requestable) entryItem).isRequestable()) {
                        button.setTextColor(Color.GRAY);
                        button.setClickable(false);
                        button.setBackgroundColor(R.drawable.not_requestable);
                    } else {
                        Drawable icon = getContext().getResources().getDrawable(R.drawable.ic_action_forward);

                        icon.setBounds(0, 0, 70, 70);
                        button.setCompoundDrawables(null, null, icon, null);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                Intent intent = new Intent();
//                                intent.putExtra("item_no", itemNumber);
//                                intent.putExtra("url", url);
//                                intent.putExtra("request", request);
//                                intent.setClassName("tw.edu.ncu.nculibrary", "tw.edu.ncu.nculibrary.RequestResultActivity");
//                                getContext().startActivity(intent);
                            	
                            	HashMap<String, String> params = new HashMap<String, String>();
                                params.put("item_no", itemNumber);
                                params.put("url", url);
                                String mToken = GlobalStaticVariable.global.getToken();
                            	RequestQueue mQueue = VolleyProvider.getQueue(getContext());
                            	JsonObjectRequestWithPostParams jsonObjectRequest = new JsonObjectRequestWithPostParams(Request.Method.POST,
                                        GlobalStaticVariable.BASEURL + "request/?request=" + request + "&token=" + mToken,
                                        params,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject jsonObject) {
                                                try {
                                                    String message = jsonObject.getString("message");
                                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                                                    button.setVisibility(View.GONE);
//                                                    TextView textView = (TextView) findViewById(R.id.request_result);
//                                                    textView.setText(message);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                Log.d("Url", url);
                                                Log.d("Request", request);
                                                Log.d("item_no", itemNumber);
                                            }
                                        }
                                );

                                mQueue.add(jsonObjectRequest);
                            }
                        });
                    }
                }
            }
        }

        return view;
    }
}

