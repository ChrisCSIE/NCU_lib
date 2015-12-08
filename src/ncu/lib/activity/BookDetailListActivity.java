package ncu.lib.activity;

import ncu.lib.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BookDetailListActivity extends Activity {
//	TextView testText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail_list);
		
//		testText = (TextView) findViewById(R.id.testtext);
	}
}
