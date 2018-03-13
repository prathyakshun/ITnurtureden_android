package com.itnurtureden.learning;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    //private TextView Info;
    private Button Login;
    private CheckBox showPassword;
    private static Animation shakeAnimation;
    private static LinearLayout loginLayout;
    private int counter = 5;
    String JSON_get_nonce_URL = "http://itnurtureden.com/courses/api/get_nonce/?controller=auth&method=generate_auth_cookie";
    private String tag = "debug";
    String userDataPreUrl = "http://itnurtureden.com/courses/api/user/generate_auth_cookie/?username=", userDataUrl;
    String nonce;
    String email, username;
    String statusLogin;
    String userid;
    String userPassword;
    String cookieName;
    String cookieValue;
    String userpic;
    ProgressBar p;
    TextView tvIncorrect;


    public void saveInfo() {
        SharedPreferences pref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);        // Only this application share this file
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("loginStatus_val", statusLogin);
        editor.putString("email_val", email);
        editor.putString("username_val", username);
        editor.putString("userid_val", userid);
        editor.putString("userpassword_val", userPassword);
        editor.putString("cookiename_val", cookieName);
        editor.putString("cookievalue_val", cookieValue);
        editor.putString("userpic_val", userpic);
        editor.apply();
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences pref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);        // Only this application share this file
        if (pref.getString("username_val","").equals("")){
            Log.e("not there","not there");
        }
        else{
            Intent intent = new Intent(LoginActivity.this, NavigationBar.class);
            startActivity(intent);
        }
        Log.e("test","test");
        Name = (EditText) findViewById(R.id.login_username);
        Password = (EditText) findViewById(R.id.login_password);
        Login = (Button) findViewById(R.id.loginBtn);
        //Info = (TextView) findViewById(R.id.tvAttempts);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);

        showPassword = (CheckBox) findViewById(R.id.show_hide_password);
        tvIncorrect = (TextView) findViewById(R.id.tvIncorrect);

        //Info.setText("Number of attempts remaining: "+String.valueOf(counter));
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Enter", "Enter");
                userPassword = Password.getText().toString();
                p = (ProgressBar)findViewById(R.id.progressBar1);
                if(p.getVisibility() != View.VISIBLE){ // check if it is visible
                    p.setVisibility(View.VISIBLE); // if not set it to visible
                //    arg0.setVisibility(1 or 2); // use 1 or 2 as parameters.. arg0 is the view(your button) from the onclick listener
                }
                Validate(Name.getText().toString(), Password.getText().toString());
                //        p.setVisibility(View.GONE); // Invisible
            }
        });
        // ANimation initialization
        initViews();


        showPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton checkBox, boolean isChecked) {
                if (checkBox.isChecked()) {
                    Password.setTransformationMethod(null);
                } else {
                    Password.setTransformationMethod(new PasswordTransformationMethod());
                }
                Password.setSelection(Password.length());
            }
        });
    }

    private void Validate(String userName, String userPassword) {
        SharedPreferences pref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);        // Only this application share this file
        Log.e("username", userName);
        Log.e("password", userPassword);
        // Check for both field is empty or not
        if (userName.equals("") || userName.length() == 0 || userPassword.equals("") || userPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            //new CustomToast().Show_Toast(getActivity(), view, "Enter both credentials.");
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JSON_get_nonce_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nonce = response.getString("nonce");
                            Log.d("check", nonce);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                });
        requestQueue.add(obreq);

        userDataUrl = userDataPreUrl + userName + "&password=" + userPassword + "&insecure=cool";
        Log.d("userurl", userDataUrl);
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest obreq2 = new JsonObjectRequest(Request.Method.GET, userDataUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            statusLogin = (String) response.getString("status");
                            Log.d("status", statusLogin);
                            Log.e("sofar", "good");
                            cookieName = response.getString("cookie_name");
                            cookieValue = response.getString("cookie");
                            JSONObject obj = response.getJSONObject("user");
                            email = obj.getString("email");
                            username = obj.getString("username");
                            userid = String.valueOf(obj.getInt("id"));
                            userpic = String.valueOf(obj.getString("avatar"));
                            Log.d("email", email);
                            saveInfo();
                            if (statusLogin.equals("ok")) {
                                moveToNavActivity();
                            }
                            //for (int i=0;i<obj.length();i++)
                        } catch (JSONException e) {
                            loginLayout.startAnimation(shakeAnimation);
                            tvIncorrect.setText("INCORRECT CREDENTIALS !");
                            p.setVisibility(View.INVISIBLE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        p.setVisibility(View.INVISIBLE);
                        Log.e("Volley", "Error");
                    }
                });
        requestQueue.add(obreq2);
        tvIncorrect.setTextColor(Color.RED);
        }

    public void moveToNavActivity() {
        tvIncorrect.setText("");
        SharedPreferences pref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);        // Only this application share this file
        String em = pref.getString("loginStatus_val", "");
        Log.e("statusLogin", em);
        if (em.equals("ok")) {
            Log.e("okdone", "okdone");
            p.setVisibility(View.INVISIBLE);
            // Move from one activity to another
            Intent intent = new Intent(LoginActivity.this, NavigationBar.class);
            startActivity(intent);
        }

    }
    // Initiate Views
    private void initViews() {
        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
    }
}
