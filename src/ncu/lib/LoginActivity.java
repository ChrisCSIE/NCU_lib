package ncu.lib;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class LoginActivity extends Activity {
	Button login, clear;
	EditText account, pwd;
	ImageView welcome;
	private static Boolean isExit = false;
	private static Boolean hasTask = false;
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
				LoginActivity.this.finish();
			}
		});
        
        clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				account.setText("");
				pwd.setText("");
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
    Timer timerExit = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 判斷是否按下Back
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 是否要退出
			if (isExit == false) {
				isExit = true; // 記錄下一次要退出
				Toast.makeText(this, "再按一次Back退出APP", Toast.LENGTH_SHORT).show();

				// 如果超過兩秒則恢復預設值
				if (!hasTask) {
					timerExit.schedule(task, 2000);
				}
			} else {
				finish(); // 離開程式
				System.exit(0);
			}
		}
		return false;
	}
}
