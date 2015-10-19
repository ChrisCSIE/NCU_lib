package ncu.lib;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class LoginActivity extends Activity {
	Button login, clear;
	EditText account, pwd;
	ImageView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        account = (EditText)findViewById(R.id.account);
        pwd = (EditText)findViewById(R.id.pwd);
        login = (Button)findViewById(R.id.login);
        clear = (Button)findViewById(R.id.clear);
        welcome = (ImageView)findViewById(R.id.welcome);
        
//        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
