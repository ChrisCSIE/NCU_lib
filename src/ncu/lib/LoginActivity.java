package ncu.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class LoginActivity extends Activity {
	Button login, clear;
	EditText account, pwd;
	ImageView welcome;
//	LinearLayout accountLinearLayout, pwdLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().hide();
        
        account = (EditText)findViewById(R.id.account);
        pwd = (EditText)findViewById(R.id.pwd);
        login = (Button)findViewById(R.id.login);
        clear = (Button)findViewById(R.id.clear);
        welcome = (ImageView)findViewById(R.id.welcome);
//        accountLinearLayout = (LinearLayout)findViewById(R.id.accountLinearLayout);
//        pwdLinearLayout = (LinearLayout)findViewById(R.id.pwdLinearLayout);
        
//        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
        
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MenuActivity.class);
				startActivity(intent); 
//				LoginActivity.this.finish();
			}
		});
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
