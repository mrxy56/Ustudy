package sg.edu.nus.ustudy;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Func4 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func4);
    }

    public void Battery(View view){
        IntentFilter ifilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus=this.registerReceiver(null,ifilter);

        int level=batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale=batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        float batteryPct=level/(float)scale;
        Toast.makeText(this, "Battery: " + batteryPct * 100 + "%", Toast.LENGTH_LONG).show();
    }

    public void Exit(View view){
        Intent i=new Intent("Cat_BroadCast");
        i.putExtra("num","1");
        sendBroadcast(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_func4, menu);
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
}
