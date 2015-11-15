package sg.edu.nus.ustudy;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity implements Fragment1.OnFragmentInteractionListener,
        Fragment2.OnFragmentInteractionListener{

    MyBroadcastReceiver myReceiver;
    IntentFilter myIntentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myReceiver=new MyBroadcastReceiver();
        myIntentFilter=new IntentFilter("Cat_BroadCast");
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, myIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    public void Func1(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, Func1.class);
        startActivity(intent);
    }

    public void Func2(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, Func2.class);
        startActivity(intent);
    }

    public void Func3(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, Func3.class);
        startActivity(intent);
    }

    public void Func4(View view) {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, Func4.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
