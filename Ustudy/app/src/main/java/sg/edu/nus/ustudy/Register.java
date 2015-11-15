package sg.edu.nus.ustudy;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener{
    Button register_button;
    EditText username_edit,password_edit,confirm_password_edit;
    TextView confirm_password_error;
    static String responseMsg="";
    static int num=0;
    private static final int REQUEST_TIMEOUT = 100 * 1000;
    private static final int SO_TIMEOUT = 100 * 1000;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        InitView();
    }
    private void InitView() {
        register_button = (Button) findViewById(R.id.register_button);
        username_edit = (EditText) findViewById(R.id.user_name_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        confirm_password_edit = (EditText) findViewById(R.id.confirm_password_edit);
        confirm_password_error = (TextView) findViewById(R.id.confirm_password_error);
        register_button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                this.RegisterUser();
                break;
            default:
                break;
        }
    }

    public void RegisterUser() {

        if (!confirm_password_edit.getText().toString().trim().equals(password_edit.getText().toString().trim())){
            confirm_password_error.setVisibility(View.VISIBLE);
        } else {
            String newusername = username_edit.getText().toString();
            String newpassword = Encrypt.md5(password_edit.getText().toString());
            String confirmpwd =  Encrypt.md5(confirm_password_edit.getText().toString());

            Thread loginThread = new Thread(new RegisterThread());
            loginThread.start();
        }

    }

    // 初始化HttpClient，并设置超时
    public HttpClient getHttpClient() {
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpParams);
        return client;
    }

    private boolean registerServer(String username, String password) {
        boolean loginValidate = false;

        String urlStr = "http://mrxy562.sinaapp.com";
        HttpPost request = new HttpPost(urlStr);

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        try {

            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpClient client = getHttpClient();

            HttpResponse response = client.execute(request);


            if ((num=response.getStatusLine().getStatusCode()) == 200) {
                loginValidate = true;

                responseMsg = EntityUtils.toString(response.getEntity(),"utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginValidate;
    }

    // Handler
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username_edit.getText().toString());
                    bundle.putString("password", password_edit.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtras(bundle);

                    setResult(RESULT_OK, intent);
                    Register.this.finish();
                    Toast.makeText(Register.this, "succeed", 1).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "fail",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "fail to connect！",
                            Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };

    class RegisterThread implements Runnable {

        @Override
        public void run() {
            String username = username_edit.getText().toString();
            String password = Encrypt.md5(password_edit.getText().toString());
            // URL合法，但是这一步并不验证密码是否正确
            boolean registerValidate = registerServer(username, password);

            Message msg = handler.obtainMessage();
            if (registerValidate) {
                if (responseMsg.equals("success")) {
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else {
                    msg.what = 1;
                    handler.sendMessage(msg);
                }

            } else {
                msg.what = 2;
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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



