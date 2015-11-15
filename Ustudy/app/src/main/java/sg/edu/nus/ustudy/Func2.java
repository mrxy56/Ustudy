package sg.edu.nus.ustudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;


public class Func2 extends ActionBarActivity implements View.OnClickListener{
    private PopupWindow popupwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func2);
    }

    public void Button1(View v) {
        if (popupwindow != null && popupwindow.isShowing()) {
            popupwindow.dismiss();
            return;
        } else {
            initmPopupWindowView();
            popupwindow.showAsDropDown(v, 0, 5);
        }
    }

    public void initmPopupWindowView() {

        View customView = getLayoutInflater().inflate(R.layout.popview_item,null,false);

        popupwindow = new PopupWindow(customView, 400, 380);

        popupwindow.setAnimationStyle(R.style.AnimationFade);

        customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    popupwindow = null;
                }

                return false;
            }
        });


        Button btton2 = (Button) customView.findViewById(R.id.button2);
        Button btton3 = (Button) customView.findViewById(R.id.button3);
        Button btton4 = (Button) customView.findViewById(R.id.button4);
        Button btton5 = (Button) customView.findViewById(R.id.button5);
        Button btton6 = (Button) customView.findViewById(R.id.button6);
        btton2.setOnClickListener(this);
        btton3.setOnClickListener(this);
        btton4.setOnClickListener(this);
        btton5.setOnClickListener(this);
        btton6.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_func2, menu);
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
    public void onClick(View v){
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.button2:
                intent.setClass(Func2.this, MapsActivity.class);
                intent.putExtra("lat",1.295036);
                intent.putExtra("lng", 103.773932);
                intent.putExtra("ID",1);
                startActivity(intent);
                break;
            case R.id.button3:
                intent.setClass(Func2.this, MapsActivity.class);
                intent.putExtra("lat", 1.306038);
                intent.putExtra("lng",103.772962);
                intent.putExtra("ID",2);
                startActivity(intent);
                break;
            case R.id.button4:
                intent.setClass(Func2.this, MapsActivity.class);
                intent.putExtra("lat",1.2967193);
                intent.putExtra("lng", 103.773203);
                intent.putExtra("ID",3);
                startActivity(intent);
                break;
            case R.id.button5:
                intent.setClass(Func2.this, MapsActivity.class);
                intent.putExtra("lat",1.292410);
                intent.putExtra("lng", 103.773997);
                intent.putExtra("ID",4);
                startActivity(intent);
                break;
            case R.id.button6:
                intent.setClass(Func2.this, MapsActivity.class);
                intent.putExtra("lat",1.297155);
                intent.putExtra("lng",103.780310);
                intent.putExtra("ID",5);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
