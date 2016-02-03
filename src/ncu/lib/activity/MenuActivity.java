package ncu.lib.activity;

import java.util.Timer;
import java.util.TimerTask;

import ncu.lib.R;
import ncu.lib.R.drawable;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class MenuActivity extends Activity {
	private static Boolean isExit = false;
	private static Boolean hasTask = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_menu);
//		Drawable title = getResources().getDrawable(R.drawable.title);
//		getActionBar().setBackgroundDrawable(title);
//		getActionBar().setTitle("��ߤ����j�ǹϮ��]");
		getActionBar().hide();
		setContentView(new MainMenuView(MenuActivity.this));
	}
	
	
	/* ���⦸�h�X�~����APP */
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
		// �P�_�O�_���UBack
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// �O�_�n�h�X
			if (isExit == false) {
				isExit = true; // �O���U�@���n�h�X
				Toast.makeText(this, getResources().getText(R.string.press_back_again), Toast.LENGTH_SHORT).show();

				// �p�G�W�L���h��_�w�]��
				if (!hasTask) {
					timerExit.schedule(task, 2000);
				}
			} else {
				finish(); // ���}�{��
				System.exit(0);
			}
		}
		return false;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
