package aya.net.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dao.DataBaseManager;
import models.MovieItem;

public class DashboardActivity extends AppCompatActivity {
    private List<MovieItem> data;
    private ListView listView;
    private ListItemModel moviesModel;
    private TextView textViewUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dashboard_layout);
        listView=findViewById(R.id.listView);
        textViewUsername=findViewById(R.id.fullname);
        init();

        String username=  getIntent().getStringExtra("username");

        textViewUsername.setText(username);



    }

    public void init(){
        data = (ArrayList<MovieItem>)getIntent().getSerializableExtra("data");
        moviesModel=new ListItemModel(getApplicationContext(),R.layout.moviesitem_layout,data);
        listView.setAdapter(moviesModel);
        textViewUsername.setText("");
    }
}