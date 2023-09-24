package com.food1.whateat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
//선택지에 없는 음식을 추가하는 페이지
public class AddFoodMain extends AppCompatActivity {
    private int SIZE = 99; //<-- 음식 추가 가능 최대 갯수(100개) 조정 가능
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;
    ArrayList<String>listItem2;
    String[] listItem3;
    EditText et;
    ImageButton bt;
    ImageButton r_btn;
    ImageButton save_btn;
    String[] items = new String[SIZE];
    String[] save;
    private final String MN1 = "mn1";
    private final String MN2 = "mn2";
    private final String MN3 = "mn3";
    private final String MN4 = "mn4";
    private final String MN5 = "mn5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_main);
        et = findViewById(R.id.food_text);
        lv = findViewById(R.id.add_list);
        bt = findViewById(R.id.fAdd);
        r_btn = findViewById(R.id.reset_btn);
        save_btn = findViewById(R.id.save_btn);
        listItem = new ArrayList<String>();
        listItem2 = new ArrayList<String>();
        listItem3=new String[SIZE];
        TextView tv = findViewById(R.id.tv1);


        save=new String[SIZE];


        Intent it = getIntent();
        listItem3=it.getStringArrayExtra("AddedMenu");


        for (int a=0;a<listItem3.length;a++)
        {

            if(listItem3[a]!=null)
                listItem.add(listItem3[a]);

        }


        save_btn.setOnClickListener(new View.OnClickListener() {// 저장 담당 버튼 누를시 룰렛에 추가
            @Override
            public void onClick(View v) {
                if (listItem.size() > 0) {
                    for (int i = 0; i < listItem.size(); i++) {
                        items[i] = listItem.get(i);//<-- Items 문자열배열 안에 리스트목록에 있는 음식들을 추가
                    }
                    showToast(getApplicationContext(), "룰렛에 추가 되었습니다.");
                } else {
                    showToast(getApplicationContext(), "최소 1개의 음식이 추가되어야 합니다.");
                }


                Intent intent = new Intent(AddFoodMain.this, MainActivity.class);
                String[] myMenu = items;
                intent.putExtra("CheckBox", myMenu);
                setResult(RESULT_OK, intent);
                finish();



            }
        });


        r_btn.setOnClickListener(new View.OnClickListener() {// 초기화 담당 버튼
            @Override
            public void onClick(View v) {
                listItem.clear();
                adapter.notifyDataSetChanged();
                tv.setVisibility(View.VISIBLE);
                if (listItem.size() > 0) {
                    for (int i = 0; i < listItem.size(); i++) {
                        items[i] = null;
                    }
                }
                showToast(getApplicationContext(), "초기화 되었습니다.");
            }
        });




        bt.setOnClickListener(new View.OnClickListener() {// 입력받은것을 추가 시키는 추가 버튼
            @Override
            public void onClick(View v) {
                if (et.getText().toString().replace(" ", "").equals("")) {
                    showToast(getApplicationContext(), "추가하실 음식을 입력해주세요.");
                } else if (listItem.size() > SIZE) {
                    showToast(getApplicationContext(), "최대 " + (SIZE + 1) + "개 추가 가능합니다.");
                } else {
                    if (listItem.contains(et.getText().toString())) {
                        showToast(getApplicationContext(), "중복 등록은 안됩니다.");
                    } else {
                        tv.setVisibility(View.GONE);
                        listItem.add(et.getText().toString());
                        adapter.notifyDataSetChanged();
                        et.setText("");

                    }
                }

            }
        });





        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.memolist_type, listItem); //R.layout 수정 (준형)
        lv = findViewById(R.id.add_list);
        lv.setAdapter(adapter);









    }


    private static Toast sToast;
    public static void showToast(Context context, String message) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(message);
        }
        sToast.show();
    }





}