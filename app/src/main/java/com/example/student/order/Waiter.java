package com.example.student.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.Color.parseColor;

public class Waiter extends AppCompatActivity {
    private TextView txtNumber, txtName, txtDate;
    private TabHost tabHost_table;
    private TabHost.TabSpec spec;
    private LinearLayout tab_eatIn,tab_takeAway;
    private tableFragment tableNo1,tableNo2,tableNo3,tableNo4,tableNo5,tableNo6,tableNo7,tableNo8,tableNo9;
    DatabaseReference root;
    EditText customerName,customerTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);
        customerName=(EditText)findViewById(R.id.customerName);
        customerTel=(EditText)findViewById(R.id.customerTel);

        findView();
        readSharePreferences();
        tabSettings("eat in",R.id.tab_eatIn,"內用");
        tabSettings("take away",R.id.tab_takeAway,"外帶");
        changeTabBackgroundOnChanged();



    }

    @Override
    protected void onStart() {
        super.onStart();
        setFragmentTableNumber();
    }

    //初始化
    public void findView(){
        txtDate = (TextView) findViewById(R.id.txtDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String strDate = formatter.format(curDate);
        txtDate.setText(strDate);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtName = (TextView) findViewById(R.id.txtName);
        tabHost_table = (TabHost)findViewById(R.id.tabHost_table);
        tabHost_table.setup();
        tab_eatIn=(LinearLayout)findViewById(R.id.tab_eatIn);
        tab_takeAway=(LinearLayout)findViewById(R.id.tab_takeAway);
        tableNo1=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table1);
        tableNo2=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table2);
        tableNo3=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table3);
        tableNo4=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table4);
        tableNo5=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table5);
        tableNo6=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table6);
        tableNo7=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table7);
        tableNo8=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table8);
        tableNo9=(tableFragment)getSupportFragmentManager().findFragmentById(R.id.table9);

    }

    public void readSharePreferences(){
        SharedPreferences sp = getSharedPreferences("log",MODE_PRIVATE);
        txtNumber.setText(sp.getString("id","0000").toString());
        txtName.setText(sp.getString("name", "Default").toString());
    }

    //設定tab
    public void tabSettings(String tabTagName,  int id,String name){
        spec=tabHost_table.newTabSpec(tabTagName);
        spec.setContent(id);
        spec.setIndicator(name);
        tabHost_table.addTab(spec);
        setTabBackground();
    }

    //設定tab背景色和高度
    public void setTabBackground(){
        //初次設定
        for(int i=0; i<tabHost_table.getTabWidget().getTabCount();i++){
            TextView tv=tabHost_table.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(parseColor("#ffffff"));
            tv.setTextSize(25);
            tabHost_table.getTabWidget().getChildAt(tabHost_table.getCurrentTab()).setBackgroundColor(parseColor("#99fdad0c"));
            tabHost_table.getTabWidget().getChildAt(i).getLayoutParams().height = 140;
            if(i==0){
                tabHost_table.getTabWidget().getChildAt(i).setBackgroundColor(parseColor("#99ff504d"));
            }else if(i==1){
                tabHost_table.getTabWidget().getChildAt(i).setBackgroundColor(parseColor("#99019e94"));
            }
        }
        TextView tv = (TextView) tabHost_table.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(parseColor("#000000"));
        tabHost_table.getTabWidget().getChildAt(tabHost_table.getCurrentTab()).setBackgroundColor(parseColor("#99fdad0c"));
    }

    //切換tab時設定background顏色
    public void changeTabBackgroundOnChanged(){
        tabHost_table.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                setTabBackground();

            }
        });
    }

    public void takeAwayOk(View view) {
        if(!customerName.getText().toString().equals("") && !customerTel.getText().toString().equals(""))
        {Intent it =new Intent();
        it.setClass(Waiter.this,OrderActivity.class);
        it.putExtra("clientname",customerName.getText().toString());
        it.putExtra("clienttel",customerTel.getText().toString());
        startActivity(it);}
        else{
            Toast.makeText(Waiter.this,"請輸入完整資料",Toast.LENGTH_LONG).show();
        }
    }
    //設定桌號
    public void setFragmentTableNumber(){
        tableNo1.setTableNumber("A");
        tableNo2.setTableNumber("B");
        tableNo3.setTableNumber("C");
        tableNo4.setTableNumber("D");
        tableNo5.setTableNumber("E");
        tableNo6.setTableNumber("F");
        tableNo7.setTableNumber("G");
        tableNo8.setTableNumber("H");
        tableNo9.setTableNumber("I");
    }

}
