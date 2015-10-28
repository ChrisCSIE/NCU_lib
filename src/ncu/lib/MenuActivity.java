package ncu.lib;

import android.app.Activity;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_menu);
		Drawable title = getResources().getDrawable(R.drawable.title);
		getActionBar().setBackgroundDrawable(title);
		getActionBar().setTitle("��ߤ����j�ǹϮ��]");
//		getActionBar().hide();
		setContentView(new MainMenuView(MenuActivity.this));
	}
}
