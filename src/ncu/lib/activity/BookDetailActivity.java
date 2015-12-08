package ncu.lib.activity;

import java.util.ArrayList;

import ncu.lib.R;
import ncu.lib.util.BookDetailAdapter;
import ncu.lib.library.VolleyProvider;
import ncu.lib.util.EntryItem;
import ncu.lib.util.Item;
import ncu.lib.util.SectionItem;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class BookDetailActivity extends Activity {
	RequestQueue mQueue;

    private String mBookname;
    private String mISBN;
    private String mImprint;
    private String mVerison;
    private String mRequest;
    private String mLink;

    private ListView mDetailListView;
    private ArrayList<Item> mDetailList;
    private BookDetailAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);
		
		String bookID = getIntent().getStringExtra("bookID");
        String bookName = getIntent().getStringExtra("bookName");

        mDetailListView = (ListView) findViewById(R.id.book_detail_listview);
        mDetailList = new ArrayList<Item>();
        mAdapter = new BookDetailAdapter(BookDetailActivity.this, mDetailList);
        mDetailListView.setAdapter(mAdapter);

        mQueue = VolleyProvider.getQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
            GlobalStaticVariable.BASEURL + "getbook/?url=" + bookID, null,
           new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                mDetailList.clear();

                try {
                    mBookname = jsonObject.getString("title");
                    mRequest = jsonObject.optString("request");
                    mISBN = jsonObject.optString("isbn");
                    mImprint = jsonObject.optString("imprint");
                    mVerison = jsonObject.optString("version");
                    mLink = jsonObject.optString("links");

//                    Button button = (Button) findViewById(R.id.request_button);


                    if(mBookname != "") {
                        mDetailList.add(new SectionItem(getString(R.string.bookname)));
                        mDetailList.add(new EntryItem(mBookname));
                    }

                    if(mISBN != "") {
                        mDetailList.add(new SectionItem(getString(R.string.isbn)));
                        mDetailList.add(new EntryItem(mISBN));
                    }

                    if(mImprint != "") {
                        mDetailList.add(new SectionItem(getString(R.string.imprint)));
                        mDetailList.add(new EntryItem(mImprint));
                    }

                    if(mVerison != "") {
                        Log.d("test", mVerison);
                        mDetailList.add(new SectionItem(getString(R.string.version)));
                        mDetailList.add(new EntryItem(mVerison));
                    }

//                    if(mLink != "") {
//                        button.setText(getString(R.string.link));
//                        button.setVisibility(View.VISIBLE);
//                        button.setCompoundDrawablesWithIntrinsicBounds(null, null,
//                                getResources().getDrawable(R.drawable.ic_action_forward), null);
//                        button.setClickable(true);
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mLink));
//
//                                startActivity(intent);
//                            }
//                        });
//
//                    }

//                    if(mRequest != "") {
//                        button.setText(getString(R.string.request_button));
//                        button.setClickable(true);
//                        button.setVisibility(View.VISIBLE);
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent = new Intent();
//
//                                intent.setClassName(GlobalStaticVariable.PKGNAME,
//                                        GlobalStaticVariable.PKGNAME + ".RequestBookActivity");
//                                intent.putExtra("request", mRequest);
//                                startActivity(intent);
//                            }
//                        });
//
//                    }

                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("test", "onErrorResponse");
            }
        });

        mQueue.add(jsonObjectRequest);

//        Button button = (Button) findViewById(R.id.request_button);
//        button.setClickable(false);
	}
}
