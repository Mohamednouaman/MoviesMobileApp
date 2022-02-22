package aya.net.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dao.DataBaseManager;
import models.MovieItem;

public class LoginActivity extends AppCompatActivity {


    private Button btnLogin;
    private Button btnSignup;
    private EditText emailEditText;
    private EditText passwordEditText;
    private List<MovieItem>   data;
    private DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        btnLogin=findViewById(R.id.btnLoginLogin);
        btnSignup=findViewById(R.id.btnSignupLogin);
        emailEditText=findViewById(R.id.emailLogin);
        passwordEditText=findViewById(R.id.passwordLogin);
        init();
        btnLogin.setOnClickListener(view -> {
            String email =emailEditText.getText().toString();

            String password=passwordEditText.getText().toString();

            if(email.length()==0 || password.length()==0){
                Toast.makeText(getApplicationContext(),"Email | Password are required ",Toast.LENGTH_LONG).show();
            }else{
               String username=dataBaseManager.authenticateUser(email,password);

                if(username.length()==0){
                    Toast.makeText(getApplicationContext(),"Incorrect identifier !",Toast.LENGTH_LONG).show();
                }else{
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    String url="https://api.themoviedb.org/3/movie/top_rated?api_key=03b6f795108f543052602896781f76fd";
                    StringRequest  stringRequest= new StringRequest(Request.Method.GET,url, response->{

                        Log.println(Log.VERBOSE,"successTag",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray dataList=jsonObject.getJSONArray("results");
                            for (int i = 0; i < dataList.length(); i++) {
                                JSONObject        dataListInfo=dataList.getJSONObject(i);
                                String pathImage=dataListInfo.getString("backdrop_path");
                                String date =dataListInfo.getString("release_date");
                                String title=dataListInfo.getString("title");

                                MovieItem  movieItem=new MovieItem();
                                movieItem.setDate(date);
                                movieItem.setPathImage(pathImage);
                                movieItem.setTitle(title);

                                data.add(movieItem);
                            }
                            Intent intent = new Intent(this, DashboardActivity.class);
                            intent.putExtra("data", (Serializable) data);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }catch (JSONException e){
                            Log.i("ExceptionTag",e.getMessage());
                        }


                    }, error -> {

                        Log.println(Log.VERBOSE,"ErrorTag",error.toString());
                    });
                    requestQueue.add(stringRequest);
                    requestQueue.start();

                }
            }

        });


        btnSignup.setOnClickListener(view -> {
            Intent intent = new Intent(this, InscriptionActivity.class);

            startActivity(intent);
        });

    }
    public void init(){
            data=new ArrayList<>();
            dataBaseManager=new DataBaseManager(getApplicationContext());
    }

}