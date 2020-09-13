package com.example.calcmads.calc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.calcmads.R;
import com.example.calcmads.data.model.HistoryModel;
import com.example.calcmads.utils.ItemAnimation;
import com.example.calcmads.utils.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.calcmads.MyApplication.historyModelArrayList;
import static com.example.calcmads.MyApplication.sharedPreferences;
import static com.example.calcmads.calc.CalcActivity.displaytext;
import static com.example.calcmads.calc.CalcActivity.inputtext;

public class HistoryActivity extends AppCompatActivity {
    private HistoryAdapter historyAdapter;
    private RecyclerView historylist;
    private DatabaseReference mDatabase;
    private DatabaseReference historylistEndPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initToolbar();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        historylistEndPoint = mDatabase.child("Historylist");

        historylist = (RecyclerView) findViewById(R.id.history_list);
        historylist.setLayoutManager(new LinearLayoutManager(this));
        historylist.setHasFixedSize(true);

        setAdapter();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter() {
        //set data and list adapter
        try {
            historyAdapter = new HistoryAdapter(getApplicationContext(), historyModelArrayList, ItemAnimation.LEFT_RIGHT);
            if (sharedPreferences.getBoolean("islogin", false)) {
                final List<HistoryModel> firebasehistoryModelList = new ArrayList<>();
                historylistEndPoint.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                            HistoryModel historyModel = noteSnapshot.getValue(HistoryModel.class);
                            firebasehistoryModelList.add(historyModel);
                        }
                        historyAdapter = new HistoryAdapter(getApplicationContext(), firebasehistoryModelList, ItemAnimation.LEFT_RIGHT);
                        historylist.setAdapter(historyAdapter);
                        historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, HistoryModel obj, int position) {
                                finish();
                                inputtext.setText(obj.getUser_input());
                                displaytext.setText(String.valueOf(obj.getResult()));
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                historylist.setAdapter(historyAdapter);
                historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, HistoryModel obj, int position) {
                        finish();
                        inputtext.setText(obj.getUser_input());
                        displaytext.setText(String.valueOf(obj.getResult()));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // on item list clicked

    }
}