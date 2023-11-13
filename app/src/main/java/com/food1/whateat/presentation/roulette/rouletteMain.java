package com.food1.whateat.presentation.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.WheelItem;
import com.food1.whateat.MainActivity;
import com.food1.whateat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class rouletteMain extends AppCompatActivity {
    private SensorManager sm;
    //private Sensor mAccelerometer;
    TextView randomTextView;
    SoundPool soundPool;
    int soundPlay;
    List<WheelItem> wheelItems;//룰렛에 들어갈 음식칸
    //String point;
    //String LastPoint=new String();
    String money;
    ArrayList <String> allMenu2=new ArrayList<>();//모든 음식 배열
    private boolean isStopped = true;
    Dialog dilaog0;
    ImageButton start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette_main);
        soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        soundPlay=soundPool.load(this,R.raw.wheel_wheel,0);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        start = findViewById(R.id.spin_btn);
        ImageButton Finish = findViewById(R.id.Ifinish);
        randomTextView = findViewById(R.id.select_food_text);


       // mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        dilaog0 = new Dialog(rouletteMain.this);       // Dialog 초기화
        dilaog0.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog0.setContentView(R.layout.dialog02);


        Intent it = getIntent();
        allMenu2=it.getStringArrayListExtra("AllMenuList");//최종 음식들의 배열을 allMenu2에 저장






        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!isStopped) {
                    int randomIndex = new Random().nextInt(allMenu2.size());
                    String randomMenu = allMenu2.get(randomIndex);
                    start.setBackgroundResource(R.drawable.action_btn);
                    // UI 갱신은 메인 스레드에서 수행되어야 함
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            randomTextView.setText(randomMenu);
                        }
                    });
                }
                handler.postDelayed(this, 10); // 0.1초마다 업데이트
            }
        };
        handler.post(runnable);



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isStopped) {
                    // 스톱 버튼을 누를 때 선택한 값을 확인하고 제거할지 확인
                    final String currentItem = randomTextView.getText().toString();
                    isStopped = true;

                    // 사용자에게 확인 또는 취소를 선택하도록 다이얼로그를 표시


                    dilaog0.show(); // 다이얼로그 띄우기
                    dilaog0.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView showView=dilaog0.findViewById(R.id.will_you_choice);
                    Button noBtn = dilaog0.findViewById(R.id.noBtn);

                    showView.setText(currentItem+"(으)로 결정하시겠습니까?");

                    //아니오 버튼을 눌렀을때
                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(allMenu2.size()==1)
                                showToast(rouletteMain.this,"더이상 지울수 없습니다.");

                            else if (allMenu2.contains(currentItem)) //아이템이 존재한다면
                            {
                                //showToast(rouletteMain.this,"음식이 있습니다."+allMenu2.size());
                                allMenu2.remove(currentItem);
                            }
                            isStopped = false;                   //룰렛을 정지
                            dilaog0.dismiss();                   // 다이얼로그 닫기
                        }
                     });


                    // 네 버튼을 눌렀을때
                    dilaog0.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 원하는 기능 구현

                            isStopped = true;                   //계속 돌림
                            dilaog0.dismiss();                  // 다이얼로그 닫기
                            money=currentItem;

                            on_Click_sub(view); //결정되면 바로 이동하게 임시코드.

                        }
                    });


                }


                else {
                    isStopped = false;
                }
            }
        });



    }







    public void on_Click_sub(View v){
        if(money==null)
        {

            showToast(rouletteMain.this,"메뉴를 결정하세요");
        }


        else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Finish", money);
            setResult(RESULT_OK, intent);
            finish();
        }
    }//최종 결정된 LastPoint를 전달






    @Override
    public void onBackPressed(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Finish", "미정");
        setResult(RESULT_OK, intent);
        finish();
    }//백스페이스로 돌아가면 intent에 아무값도 없어 오류가 나 공백을 넣음


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






