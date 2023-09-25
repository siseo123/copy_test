package com.food1.whateat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ChoiceMenuMain extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private LinearLayout container;
    private static final float FONT_SIZE = 50;
    //private String cs1;
    ListView listView;

    //TextView tv;
   // String result = "";


    String[] qqqqq0;    //최종 전달될 배열
    CheckBox[] qqqqq;//메인 체크박스의 배열
    CheckBox[] qqqqq2;//밑에 체크된 음식들의 이름을 저장. 없어도 무관한데 코드 수정 귀찮아 일단 보류 삭제하면 뭔가 큰일남
    String []myLoc2;
    String[] myLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_menu_main);
        Intent it = getIntent();
        String tag = it.getStringExtra("it_tag");//해당 음식의 테그


       myLoc2=it.getStringArrayExtra("arr");//체크된 배열 전달한거(체크 된거만)


        //Toast.makeText(this, "접근 ", Toast.LENGTH_LONG).show();

        int stringId;
        stringId = getResources().getIdentifier(tag,"array",getPackageName()); //메인의 public void on_Click(View v) 에서 전달받은 코드를 바ㄷ음
        myLoc = getResources().getStringArray(stringId);//전달받은 코드에 따라 음식의 종류를 나누고 그에 해당하는 오리지널 배열 생성(다 있는거)


        qqqqq0 =new String[myLoc.length];
        qqqqq=new CheckBox[myLoc.length];
        qqqqq2=new CheckBox[myLoc.length];
        for(int a=0; a<qqqqq2.length; a++)

        {
            qqqqq2[a]= new CheckBox(this);
        }//전달할 체크박스배열 qqqqq2 생성


       //  tv=(TextView) findViewById(R.id.result); //맨밑에 전체 선택한 음식을 보여주는 텍스트상자



        for(int i=0; i<myLoc.length;i++){
            container = (LinearLayout) findViewById(R.id.layout);
            checkbox(myLoc[i],i);

        }//화면의 체크박스들 생성



        //tv.setText("선택목록 : "+result); //선택된 모든음식 선택



        for(int a=0;a<myLoc.length;a++)
        {

            qqqqq[a].setOnCheckedChangeListener(this);
        }



    }




    private void checkbox(String s,int i) {


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        qqqqq[i]= new CheckBox(this);//체크박스 생성
        qqqqq[i].setText(s);//이름을 생성
        qqqqq2[i].setText(s);//일단 넣어

        qqqqq[i].setTextSize(FONT_SIZE);
        qqqqq[i].setTextColor(ContextCompat.getColor(this, R.color.text_color)); //텍스트 컬러 수정(준형)


        qqqqq[i].setPadding(40, 20, 0, 10);

        for(int b=0; b<myLoc2.length; b++)
        try{{
            if(myLoc2[b].equals(myLoc[i]))
             {
                 qqqqq0[i]=qqqqq[i].getText().toString();//누르는시 바로 저장? 전달하는 객체
                qqqqq[i].setChecked(true);
                // result+=qqqqq[i].getText()+" ";//음식의 컨테이너가 생성시 선택목록에 그 음식의 이름을 추가
                break;
             }
        }}
        catch(Exception e) {

            e.printStackTrace();

            continue;
        }



        qqqqq[i].setId(i);
        qqqqq[i].getTag(i);
        qqqqq[i].setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FF5722")));





        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        lp.gravity = Gravity.LEFT;//왼쪽으로
        qqqqq[i].setLayoutParams(lp);



        container.addView(qqqqq[i]);

        View underline = new View(this);
        LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2  // 두께는 2 픽셀로 설정
        );
        underlineParams.setMargins(0, 10, 0, 0);  // 마진 설정
        underline.setLayoutParams(underlineParams);
        underline.setBackgroundColor(Color.GRAY);  // 밑줄의 색 설정

        // 밑줄 추가
        linearLayout.addView(underline);

        // 이제 이 linearLayout을 원하는 위치에 추가
        container.addView(linearLayout);







    }




        //체크박스 누를때 마다 실행
@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
{



        for(int a=0; a<qqqqq.length; a++)

        {

            {
                if (qqqqq[a].isChecked()) {
                    qqqqq2[a].setText(qqqqq[a].getText());
                    qqqqq0[a] = qqqqq[a].getText().toString();
                }


                else {
                    qqqqq2[a].setText(null);
                    qqqqq0[a]=null;
                }

            }





        }

    //tv.setText("선택목록 : "+result2);

    }




    public void on_Click_sub(View v){
        Intent intent = new Intent(this, MainActivity.class);
        String[] myMenu =qqqqq0;
        intent.putExtra("CheckBox", myMenu);
        setResult(RESULT_OK, intent);

        finish();
    }//인텐트 전달용



    @Override
    public void onBackPressed(){

        Intent intent = new Intent(this, MainActivity.class);
        String[] myMenu =myLoc2;
        intent.putExtra("CheckBox", myMenu);
        setResult(RESULT_OK, intent);
        finish();
    }



}


