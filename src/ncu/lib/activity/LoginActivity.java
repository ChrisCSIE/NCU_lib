package ncu.lib.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import ncu.lib.library.VolleyProvider;

import org.json.JSONException;
import org.json.JSONObject;

import ncu.lib.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	Button login, clean;
	EditText account, pwd;
	ImageView welcome;
	CheckBox autoLogin;
	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	// LinearLayout accountLinearLayout, pwdLinearLayout;
	private SharedPreferences mSettings;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().hide();

		welcome = (ImageView) findViewById(R.id.welcome);
		account = (EditText) findViewById(R.id.account);
		pwd = (EditText) findViewById(R.id.pwd);
		login = (Button) findViewById(R.id.login);
		clean = (Button) findViewById(R.id.clean);
		autoLogin = (CheckBox) findViewById(R.id.autoLogin);

		mSettings = getSharedPreferences("Preferences", 0);
		// accountLinearLayout =
		// (LinearLayout)findViewById(R.id.accountLinearLayout);
		// pwdLinearLayout = (LinearLayout)findViewById(R.id.pwdLinearLayout);

		// getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
		if (isAutoLogin()) {
			account.setText(mSettings.getString("user", null));
			pwd.setText(mSettings.getString("passwd", null));
			autoLogin.setChecked(true);
		}
		
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pd = ProgressDialog.show(LoginActivity.this, "登入中", "請耐心等候");

				new Thread() {
					public void run() {
						try {
							sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							pd.dismiss();
						}
					}
				}.start();

				Log.d("LOGIN", "Login Button");
				RequestQueue queue = VolleyProvider
						.getQueue(getApplicationContext());
				SharedPreferences.Editor editor = mSettings.edit();

				String user = account.getText().toString();
				String passwd = pwd.getText().toString();

				if (autoLogin.isChecked()) {
					editor.remove("user");
					editor.remove("passwd");
					editor.commit();
					editor.putString("user", user);
					editor.putString("passwd", passwd);
					/* save sharedPreference of autoLoginCheckbox */
					editor.putBoolean("auto_login", true);
					editor.commit();
				} else {
					editor.remove("user");
					editor.remove("passwd");
					editor.remove("auto_login");
					editor.commit();
				}

				// final NcuLibraryApplication global =
				// (NcuLibraryApplication) getApplicationContext();
				// final NcuLibraryApplication global = new
				// NcuLibraryApplication();

				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
						Request.Method.GET, GlobalStaticVariable.BASEURL
								+ "login/?user=" + user + "&passwd=" + passwd,
						null, new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject jsonObject) {
								try {
									if (jsonObject.getBoolean("success") == true) {
										GlobalStaticVariable.global
												.setToken(jsonObject
														.getString("token"));
										GlobalStaticVariable.global
												.setLogin(true);

										// mDialog.dismiss();
										Log.d("test", "dismiss");
										if (GlobalStaticVariable.global
												.isLogin()) {
											// start MenuActivity, finish
											// LoginActivity
											Intent intent = new Intent();
											intent.setClass(LoginActivity.this,
													MenuActivity.class);
											startActivity(intent);
											LoginActivity.this.finish();
										}
									} else {
										String message = jsonObject
												.getString("message");
										Toast.makeText(getApplicationContext(),
												message, Toast.LENGTH_SHORT)
												.show();
										Log.d("test", "should not dismiss");
									}
								} catch (JSONException e) {
									e.printStackTrace();
									Log.d("JSON", "JSON ERROR");
								}

							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError volleyError) {
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(
												R.string.network_issue),
										Toast.LENGTH_SHORT).show();
								// Toast.makeText(
								// getApplicationContext(),
								// volleyError.getMessage(),
								// Toast.LENGTH_SHORT).show();
							}
						});

				queue.add(jsonObjectRequest);

				// if (GlobalStaticVariable.global.isLogin()) {
				// // start MenuActivity, finish LoginActivity
				// Intent intent = new Intent();
				// intent.setClass(LoginActivity.this, MenuActivity.class);
				// startActivity(intent);
				// LoginActivity.this.finish();
				// }
			}
		});

		clean.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("CLEAN", "Clean Button");
				account.setText("");
				pwd.setText("");
			}
		});
	}

	private boolean isAutoLogin() {
		return mSettings.getBoolean("auto_login", false);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.login, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	/* 按兩次退出才結束APP */
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
				Toast.makeText(this, "再按一次Back退出APP", Toast.LENGTH_SHORT)
						.show();

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
