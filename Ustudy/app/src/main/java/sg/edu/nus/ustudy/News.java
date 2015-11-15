package sg.edu.nus.ustudy;

import android.database.Cursor;
import android.os.Message;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class News extends ActionBarActivity implements View.OnClickListener{
    ListView content;
    static String text="";
    contactDB db;
    ArrayList<HashMap<String,String>> mylist;
    SimpleAdapter mSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mylist = new ArrayList<HashMap<String,String>>();
        db=new contactDB(this);


        content=(ListView)findViewById(R.id.content);

        ArrayList<String[]> contacts=GetContacts();


        for(int i=0;i<contacts.size();i++){
            String[] contact=contacts.get(i);
            insertRow(Integer.parseInt(contact[0]), contact[1], contact[2]);
        }
        Button update_button=(Button)findViewById(R.id.update_button);
        update_button.setOnClickListener(this);

        mSchedule = new SimpleAdapter(this,
                mylist,
                R.layout.my_listitem,
                new String[]{"name","number"},
                new int[]{R.id.ItemTitle,R.id.ItemText});
        content.setAdapter(mSchedule);
    }

    public void insertRow(long rowId,String name,String number){
        HashMap<String,String> map = new HashMap<>();
        map.put("name", name);
        map.put("number", number);
        mylist.add(map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_button:
                Thread downloadThread = new Thread(new DownloadThread());
                downloadThread.start();
                break;
            default:
                break;
        }
    }

    private Handler handler = new Handler(new Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    content.removeAllViewsInLayout();
                    Toast.makeText(getApplicationContext(),"update successful",Toast.LENGTH_LONG).show();
                    content.setAdapter(mSchedule);
                    break;
                default:
                    break;

            }
            return false;
        }
    });

    class DownloadThread implements Runnable {

        @Override
        public void run() {
            Message message=Message.obtain();
            text=catchPage("http://nusdaily.sinaapp.com");
            deleteContact();
            String[] tokens = text.split("#");
            for(int i=0;i<tokens.length;i++){
                if (i == 11) break;
                addContact(tokens[i+12], tokens[i]);
            }
            message.what=1;
            handler.sendMessage(message);
        }
    }


    private String catchPage(String targetUrl){

        URL url;
        URLConnection con;
        StringBuffer sb=null;
        try {
            url = new URL(targetUrl);
            con=url.openConnection();
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            sb=new StringBuffer();
            String temp;
            while((temp=reader.readLine())!=null){
                sb.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void addContact(String name, String number){
        db.open();
        long id=db.insertContact(name, number);
        db.close();
    }


    public ArrayList<String []> GetContacts(){
        db.open();
        Cursor c=db.getAllContacts();
        ArrayList<String[]> contacts=new ArrayList<String[]>();
        if(c.moveToFirst()){
            do{
                String[]contact = {c.getString(0),c.getString(1),c.getString(2)};
                contacts.add(contact);
            }while(c.moveToNext());
        }
        db.close();
        return contacts;
    }

    public void deleteContact(){
        db.open();
        db.deleteAll();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
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
