package ncu.lib.activity;

import ncu.lib.BookListActivity;
import ncu.lib.R;
import ncu.lib.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends Activity {
	EditText keyword;
	Button searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		keyword = (EditText)findViewById(R.id.keyword);
		searchButton = (Button)findViewById(R.id.search_button);
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(SearchActivity.this, BookListActivity.class);
//				intent.putExtra("KEYWORD", keyword.getText().toString());
//                startActivity(intent);
			}
		});
		
		
	}
}
