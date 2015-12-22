package ncu.lib.activity;

import java.util.ArrayList;
import java.util.HashMap;

import ncu.lib.R;
import ncu.lib.library.JsonObjectRequestWithPostParams;
import ncu.lib.library.VolleyProvider;
import ncu.lib.util.RequestedAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class RequestedActivity extends Activity {
	private ArrayList<String> mBookNameList;
    private ArrayList<String> mItemsIDList;
    private ArrayList<String> mStatusList;

    private String mToken;

    private RequestedAdapter mRequestedAdapter;
    private ListView mListView;

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested);

        mItemsIDList = new ArrayList<String>();
        mBookNameList = new ArrayList<String>();
        mStatusList = new ArrayList<String>();

        mRequestedAdapter = new RequestedAdapter(this, mBookNameList, mStatusList);
        mListView = (ListView) findViewById(R.id.requested_listview);
        mListView.setAdapter(mRequestedAdapter);

        mQueue = VolleyProvider.getQueue(this);
        mToken = GlobalStaticVariable.global.getToken();
//        mToken = ((NcuLibraryApplication) getApplicationContext()).getToken();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, GlobalStaticVariable.BASEURL + "user/reserve?token=" + mToken,
                null, mResponseListener, mErrorListener);

        mQueue.add(jsonObjectRequest);

        mListView.setItemsCanFocus(false);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    Response.Listener<JSONObject> mResponseListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                TextView tv = (TextView) findViewById(R.id.no_requested);
                tv.setVisibility(View.GONE);

                parseJsonBookArray(jsonObject);

                if(mRequestedAdapter.getCount() == 0) {
                    tv.setVisibility(View.VISIBLE);
                }

                mRequestedAdapter.notifyDataSetChanged();

                if(mRequestedAdapter.getCount() != 0) {
                    final Button button = (Button) findViewById(R.id.cancel_requested_button);
                    button.setVisibility(View.VISIBLE);
                    button.setClickable(true);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HashMap<String, String> params = new HashMap<String, String>();

                            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.cancel_requested_loading);
                            progressBar.setVisibility(View.VISIBLE);

                            for (int i = 0; i < mListView.getAdapter().getCount(); i++) {
                                CheckedTextView temp = (CheckedTextView) mListView.getChildAt(i).findViewById(R.id.requested_item);

                                if (temp.isChecked()) {
                                    params.put(mItemsIDList.get(i), "on");
                                }
                            }

                            JsonObjectRequestWithPostParams jsonObjectRequest = new JsonObjectRequestWithPostParams(
                                    Request.Method.POST,
                                    GlobalStaticVariable.BASEURL + "user/cancelreserve?token=" + mToken,
                                    params,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject jsonObject) {
                                            try {
                                                parseJsonBookArray(jsonObject);

                                                mRequestedAdapter.notifyDataSetChanged();
                                                progressBar.setVisibility(View.GONE);

                                                if(mRequestedAdapter.getCount() == 0) {
                                                    button.setVisibility(View.GONE);

                                                    TextView tv = (TextView) findViewById(R.id.no_requested);
                                                    tv.setVisibility(View.VISIBLE);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                        }
                                    }
                            );

                            mQueue.add(jsonObjectRequest);

                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void parseJsonBookArray(JSONObject jsonObject) throws JSONException {
        mBookNameList.clear();
        mItemsIDList.clear();
        mStatusList.clear();

        JSONArray bookArray = jsonObject.getJSONArray("booksArray");

        for (int i = 0; i < bookArray.length(); ++i) {
            JSONObject temp = bookArray.getJSONObject(i);
            mBookNameList.add(temp.getString("bookname"));
            mStatusList.add(temp.getString("status"));
            mItemsIDList.add(temp.getString("item"));
        }
    }

    Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(RequestedActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }
    };
}
