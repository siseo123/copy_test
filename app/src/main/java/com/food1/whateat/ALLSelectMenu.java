package com.food1.whateat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ALLSelectMenu extends AppCompatActivity {

    ArrayList <String> allMenu2=new ArrayList<>();
    private LinearLayout container;
    TextView[] KFT;
    TextView[] CFT;
    TextView[] JFT;
    TextView[] WFT;
    TextView[] FFT;
    TextView[] AFT;
    TextView[] ADFT;



    String KF[];
    String CF[];
    String JF[];
    String WF[];
    String FF[];
    String AF[]; //위 6개는 각 해당하는 나라의 음식 배열임.
    String ADDF[];


    String KF_ori[];
    String CF_ori[];
    String JF_ori[];
    String WF_ori[];
    String FF_ori[];
    String AF_ori[]; //위 6개는 각 해당하는 나라의 음식 배열임.
    String ADDF_ori[];





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allselect_menu);

        Intent it = getIntent();
        KF = it.getStringArrayExtra("KF");
        CF = it.getStringArrayExtra("CF");
        JF = it.getStringArrayExtra("JF");
        WF = it.getStringArrayExtra("WF");
        FF = it.getStringArrayExtra("FF");
        AF = it.getStringArrayExtra("AF");
        ADDF = it.getStringArrayExtra("ADF");


        KF_ori = it.getStringArrayExtra("KF");
        CF_ori = it.getStringArrayExtra("CF");
        JF_ori = it.getStringArrayExtra("JF");
        WF_ori = it.getStringArrayExtra("WF");
        FF_ori = it.getStringArrayExtra("FF");
        AF_ori = it.getStringArrayExtra("AF");
        ADDF_ori = it.getStringArrayExtra("ADF");





        KFT=new TextView[KF.length];
        for(int i=0; i<KF.length;i++)
        {
            if(KF[i]!=null)
            {
                container = (LinearLayout) findViewById(R.id.layout);
                MenuList(KF[i], i, KFT,KF);
            }

        }//전달받은 음식수만큼의 컨테이너 생성. 후 메뉴를 배열에 음식 삽입

        CFT=new TextView[CF.length];
        for(int i=0; i<CF.length;i++)
        {
            if(CF[i]!=null) {
                container = (LinearLayout) findViewById(R.id.layout);
                MenuList(CF[i], i, CFT,CF);
            }
        }//전달받은 음식수만큼의 컨테이너 생성. 후 메뉴를 배열에 음식 삽입

        JFT=new TextView[JF.length];
        for(int i=0; i<JF.length;i++)
        {
            if(JF[i]!=null) {
                container = (LinearLayout) findViewById(R.id.layout);
                MenuList(JF[i], i, JFT,JF);
            }

        }//전달받은 음식수만큼의 컨테이너 생성. 후 메뉴를 배열에 음식 삽입

        WFT=new TextView[WF.length];
        for(int i=0; i<WF.length;i++)
        {
            if(WF[i]!=null) {
                container = (LinearLayout) findViewById(R.id.layout);
                MenuList(WF[i], i, WFT,WF);
            }

        }//전달받은 음식수만큼의 컨테이너 생성. 후 메뉴를 배열에 음식 삽입

        FFT=new TextView[FF.length];
        for(int i=0; i<FF.length;i++)
        {
            if(FF[i]!=null) {
                container = (LinearLayout) findViewById(R.id.layout);
                MenuList(FF[i], i, FFT,FF);
            }

        }//전달받은 음식수만큼의 컨테이너 생성. 후 메뉴를 배열에 음식 삽입

        AFT=new TextView[AF.length];
        for(int i=0; i<AF.length;i++)
        {
            if(AF[i]!=null) {
                container = (LinearLayout) findViewById(R.id.layout);
                MenuList(AF[i], i, AFT,AF);
            }

        }//전달받은 음식수만큼의 컨테이너 생성. 후 메뉴를 배열에 음식 삽입

        ADFT=new TextView[ADDF.length];
        for(int i=0; i<ADDF.length;i++)
        {
            if(ADDF[i]!=null) {
                container = (LinearLayout) findViewById(R.id.layout);
                MenuList(ADDF[i], i, ADFT,ADDF);
            }

        }//전달받은 음식수만큼의 컨테이너 생성. 후 메뉴를 배열에 음식 삽입





    }

        //해당 음식의 정보를 가진 컨테이너 생성
        private void MenuList(String s, int i, TextView[] arr, String[] foodArray) {
            // 새로운 리니어 레이아웃 생성
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout.setGravity(Gravity.CENTER_VERTICAL);

            // 음식 항목을 표시할 텍스트 뷰 생성
            TextView foodTextView = new TextView(this);
            foodTextView.setText(" "+s);
            foodTextView.setTextSize(45);
            foodTextView.setTextColor(ContextCompat.getColor(this, R.color.text_color)); //텍스트 컬러 수정(준형)



            foodTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,

                    1.0f
            ));
            foodTextView.setGravity(Gravity.LEFT);

            foodTextView.setPadding(0, 20, 0, 10);



            // 삭제 버튼 생성
            Button deleteButton = new Button(this);
            deleteButton.setText("삭제");
            deleteButton.setLayoutParams(new LinearLayout.LayoutParams(

                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            deleteButton.setGravity(Gravity.CENTER);






                // 밑줄을 위한 View 생성
                    View underline = new View(this);
                    underline.setBackgroundColor(Color.BLACK);  // 밑줄의 색상 설정
                    LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1  // 밑줄의 높이 설정 (1픽셀)
                    );
            underline.setLayoutParams(lineParams);






            // 삭제 버튼 클릭 이벤트 처리
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 레이아웃을 삭제하고 배열에서도 제거
                    container.removeView(underline);
                    container.removeView(layout);
                    arr[i] = null; // TextView 배열에서 해당 항목 제거

                    // 전역 배열에서도 해당 항목 제거
                    if (i >= 0 && i < foodArray.length) {
                        foodArray[i] = null;
                    }
                }
            });

            // 리니어 레이아웃에 음식 항목과 삭제 버튼 추가
            layout.addView(foodTextView);
            layout.addView(deleteButton);

            // 리니어 레이아웃을 컨테이너에 추가
            container.addView(layout);

            // 텍스트뷰 배열에 추가
            arr[i] = foodTextView;

            // 밑줄을 컨테이너에 추가
            container.addView(underline);
        }


    public void on_Click_sub(View v){
        Intent intent = new Intent(this, MainActivity.class);
        String[] myMenuK =KF;
        intent.putExtra("K", myMenuK);
        String[] myMenuC =CF;
        intent.putExtra("C", myMenuC);
        String[] myMenuJ =JF;
        intent.putExtra("J", myMenuJ);
        String[] myMenuW =WF;
        intent.putExtra("W", myMenuW);
        String[] myMenuF =FF;
        intent.putExtra("F", myMenuF);
        String[] myMenuA =AF;
        intent.putExtra("A", myMenuA);
        String[] myMenuAD =ADDF;
        intent.putExtra("AD", myMenuAD);



        setResult(RESULT_OK, intent);

        finish();
    }//인텐트 전달용


    @Override
    public void onBackPressed(){

        Intent intent = new Intent(this, MainActivity.class);
        String[] myMenuK =KF_ori;
        intent.putExtra("K", myMenuK);
        String[] myMenuC =CF_ori;
        intent.putExtra("C", myMenuC);
        String[] myMenuJ =JF_ori;
        intent.putExtra("J", myMenuJ);
        String[] myMenuW =WF_ori;
        intent.putExtra("W", myMenuW);
        String[] myMenuF =FF_ori;
        intent.putExtra("F", myMenuF);
        String[] myMenuA =AF_ori;
        intent.putExtra("A", myMenuA);
        String[] myMenuAD =ADDF_ori;
        intent.putExtra("AD", myMenuAD);
        setResult(RESULT_OK, intent);
        finish();
    }



}