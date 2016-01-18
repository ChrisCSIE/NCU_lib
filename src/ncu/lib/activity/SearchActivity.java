package ncu.lib.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import ncu.lib.R;
import ncu.lib.library.VolleyProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class SearchActivity extends Activity {
	private EditText keyword;
	private Button searchButton;
	private ArrayAdapter<String> mListAdapter;
    private ListView mListView;
    private ArrayList<String> mBookNameList;
    private ArrayList<String> mBookIDList;
    private String mQueryString;
    private RelativeLayout loadingPanel, searchLayout;

    RequestQueue mQueue;

    private String mNext;
    private String mPrev;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		mListView = (ListView) findViewById(R.id.bookListView);
		keyword = (EditText)findViewById(R.id.keyword);
		searchButton = (Button)findViewById(R.id.search_button);
		searchButton.setOnClickListener(searchEvent);
		

        mBookIDList = new ArrayList<String>();
        mBookNameList = new ArrayList<String>();
        mListAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, mBookNameList);

        mQueue = VolleyProvider.getQueue(SearchActivity.this);


        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(mBookClickListener);

        Button nextBtn = (Button) findViewById(R.id.next);
        Button prevBtn = (Button) findViewById(R.id.prev);

        nextBtn.setOnClickListener(mButtonOnClick);
        prevBtn.setOnClickListener(mButtonOnClick);

        nextBtn.setClickable(false);
        prevBtn.setClickable(false);
        
        loadingPanel = (RelativeLayout)findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.GONE);
        
        searchLayout = (RelativeLayout)findViewById(R.id.searchLayout);
        searchLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(keyword.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(searchButton.getWindowToken(), 0);
				return false;
			}
		});
	}
	
	private OnClickListener searchEvent = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(searchButton.getWindowToken(), 0);

            mQueryString = keyword.getText().toString();
			if(mQueryString.equals("")) {
				Toast.makeText(SearchActivity.this, "請輸入關鍵字", Toast.LENGTH_SHORT).show();
	        }
			else {
		        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
		                Request.Method.GET, GlobalStaticVariable.BASEURL + "search/?query=" + mQueryString,
		                null, mResponseListener, mErrorListener);
	
		        mQueue.add(jsonObjectRequest);
		        loadingPanel.setVisibility(View.VISIBLE);
			}
		}
	};
	
	View.OnClickListener mButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.next:
                    if(!mNext.isEmpty()) {
                    	loadingPanel.setVisibility(View.VISIBLE);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.GET, GlobalStaticVariable.BASEURL + "search/?query=" + mQueryString + "&url=" + mNext, null, mResponseListener, mErrorListener);
                        mQueue.add(jsonObjectRequest);
                        mListView.setSelection(0);
                    } else {
                        Toast.makeText(SearchActivity.this, "No more!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.prev:
                    if(!mPrev.isEmpty()) {
                    	loadingPanel.setVisibility(View.VISIBLE);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.GET, GlobalStaticVariable.BASEURL + "search/?query=" + mQueryString + "&url=" + mPrev, null, mResponseListener, mErrorListener);
                        mQueue.add(jsonObjectRequest);
                        mListView.setSelection(0);
                    } else {
                        Toast.makeText(SearchActivity.this, "No more!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

	AdapterView.OnItemClickListener mBookClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//			Toast.makeText(SearchActivity.this, mBookNameList.get(i).toString(), Toast.LENGTH_LONG).show();
			Intent intent = new Intent();

			intent.putExtra("bookID", mBookIDList.get(i));
			intent.putExtra("bookName", mBookNameList.get(i));
			intent.setClass(SearchActivity.this, BookDetailActivity.class);
//			intent.setClassName("tw.edu.ncu.nculibrary", "tw.edu.ncu.nculibrary.BookDetailListActivity");
			startActivityForResult(intent, 1);
//			startActivity(intent);
        }
    };

    Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(SearchActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }
    };

    Response.Listener mResponseListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                mBookIDList.clear();
                mBookNameList.clear();

                mNext = jsonObject.getString("next");
                mPrev = jsonObject.getString("prev");

                JSONArray bookJSONArray = jsonObject.getJSONArray("books");
                for (int i = 0; i < bookJSONArray.length(); ++i) {
                    JSONObject temp = bookJSONArray.getJSONObject(i);
                    mBookNameList.add(i, temp.getString("booktitle"));
                    mBookIDList.add(i, temp.getString("url"));

                    mListAdapter.notifyDataSetChanged();

                    loadingPanel.setVisibility(View.GONE);
                }
                if(mListAdapter != null)
                    mListAdapter.notifyDataSetChanged();
                else
                	

                loadingPanel.setVisibility(View.GONE);
                mListView.setSelection(0);

                Button prevBtn = (Button) findViewById(R.id.prev);
                Button nextBtn = (Button) findViewById(R.id.next);

                prevBtn.setClickable(true);
                nextBtn.setClickable(true);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(mQueue != null) {
            mQueue.cancelAll(this);
        }
    }
    
    /* 旋轉螢幕不刷新
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 什麼都不用寫
        	if(mListAdapter != null)
                mListAdapter.notifyDataSetChanged();
        }
        else {
            // 什麼都不用寫
        	
        }
    } */
}
