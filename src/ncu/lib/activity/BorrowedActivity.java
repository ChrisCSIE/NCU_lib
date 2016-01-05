package ncu.lib.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import ncu.lib.library.JsonObjectRequestWithPostParams;
import ncu.lib.library.VolleyProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import ncu.lib.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ncu.lib.R;
import ncu.lib.util.RequestedAdapter;;

public class BorrowedActivity extends Activity {

	private ArrayList<String> mBookNameList;
    private ArrayList<String> mItemsIDList;
    private ArrayList<String> mStatusList;

    private String mToken;

    /* reuse the RequestedAdapter */
    private RequestedAdapter mBorrowedAdapter;
    private ListView mListView;
    private ProgressBar progressBar;

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed);

        mItemsIDList = new ArrayList<String>();
        mBookNameList = new ArrayList<String>();
        mStatusList = new ArrayList<String>();

        mBorrowedAdapter = new RequestedAdapter(this, mBookNameList, mStatusList);
        mListView = (ListView) findViewById(R.id.borrowed_listview);
        mListView.setAdapter(mBorrowedAdapter);

        mQueue = VolleyProvider.getQueue(this);
        mToken = GlobalStaticVariable.global.getToken();
//        mToken = ((NcuLibraryApplication) getApplicationContext()).getToken();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, GlobalStaticVariable.BASEURL + "user/info?token=" + mToken,
                null, mResponseListener, mErrorListener);

        mQueue.add(jsonObjectRequest);

        mListView.setItemsCanFocus(false);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        progressBar = (ProgressBar) findViewById(R.id.borrowed_loading);
        progressBar.setVisibility(View.VISIBLE);
    }
    
    Response.Listener<JSONObject> mResponseListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
        	progressBar.setVisibility(View.GONE);
            try {
                TextView tv = (TextView) findViewById(R.id.no_borrowed);
                tv.setVisibility(View.GONE);

                parseJsonBookArray(jsonObject);

                if(mBorrowedAdapter.getCount() == 0) {
                    tv.setVisibility(View.VISIBLE);
                }

                mBorrowedAdapter.notifyDataSetChanged();

                if(mBorrowedAdapter.getCount() != 0) {
                    final Button button = (Button) findViewById(R.id.extend_borrowed_button);
                    button.setVisibility(View.VISIBLE);
                    button.setClickable(true);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HashMap<String, String> params = new HashMap<String, String>();
                            
                            progressBar.setVisibility(View.VISIBLE);

                            for (int i = 0; i < mListView.getAdapter().getCount(); i++) {
                                CheckedTextView temp = (CheckedTextView) mListView.getChildAt(i).findViewById(R.id.requested_item);

                                if (temp.isChecked()) {
                                    params.put(mItemsIDList.get(i), "on");
                                }
                            }

                            JsonObjectRequestWithPostParams jsonObjectRequest = new JsonObjectRequestWithPostParams(
                                    Request.Method.POST,
                                    GlobalStaticVariable.BASEURL + "user/renew?token=" + mToken,
                                    params,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject jsonObject) {
                                            try {
                                                parseJsonBookArray(jsonObject);

                                                mBorrowedAdapter.notifyDataSetChanged();
                                                progressBar.setVisibility(View.GONE);

                                                if(mBorrowedAdapter.getCount() == 0) {
                                                    button.setVisibility(View.GONE);

                                                    TextView tv = (TextView) findViewById(R.id.no_borrowed);
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
                                            Log.d("test", "VolleyError: " + volleyError.getMessage());
                                            //volleyError.printStackTrace();
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
            Toast.makeText(BorrowedActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }
    };
}
