package com.food1.whateat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.food1.whateat.presentation.CalendarActivity;
import com.food1.whateat.presentation.FoodListActivity;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class MainActivity extends AppCompatActivity{


    private static final int GPS_ENABLE_REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_CODE =100 ;
    String KF[];
    String CF[];
    String JF[];
    String WF[];
    String FF[];
    String AF[]; //위 6개는 각 해당하는 나라의 음식 배열임.
    String ADDF[];
    String AllMenuList[][];//위 6배열을 합칠 2차원 배열

    TextView Result;//결정된 음식명
    CheckBox ko;
    CheckBox ch;
    CheckBox ja;
    CheckBox we;
    CheckBox fe;
    CheckBox as;
    private GpsTracker gpsTracker;
    //따로 추가
    String send_data;
    Dialog dilaog01;
    Dialog  question_to_goto_food_dilago;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KF=getResources().getStringArray(R.array.koreaF);
        CF=getResources().getStringArray(R.array.chinaF);
        JF=getResources().getStringArray(R.array.japanF);
        WF=getResources().getStringArray(R.array.westF);
        FF=getResources().getStringArray(R.array.fastF);
        AF=getResources().getStringArray(R.array.asiaF);
        ADDF=new String[100];

        ko=findViewById(R.id.koreaFBT);
        ch=findViewById(R.id.chinaFBT);
        ja=findViewById(R.id.japanFBT);
        we=findViewById(R.id.westFBT);
        fe=findViewById(R.id.fastFBT);
        as=findViewById(R.id.asiaFBT);

        ko.setOnClickListener(CBCL);
        ch.setOnClickListener(CBCL);
        ja.setOnClickListener(CBCL);
        we.setOnClickListener(CBCL);
        fe.setOnClickListener(CBCL);
        as.setOnClickListener(CBCL);
        Result=(TextView)findViewById(R.id.result);


        dilaog01 = new Dialog(MainActivity.this);       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.dialog01);

        question_to_goto_food_dilago = new Dialog(MainActivity.this);       // Dialog 초기화
        question_to_goto_food_dilago.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        question_to_goto_food_dilago.setContentView(R.layout.dialog03);





        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.add_btn); // ic_menu 는 메뉴 아이콘 리소스입니다.


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );




        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // 네비게이션 메뉴 아이템 클릭 이벤트 처리
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_item1:


                       drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                                startActivity(intent);
                            }
                        }, 250); //버벅여서 지연시간 추가
                        break;
                    case R.id.nav_item2:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent2 = new Intent(getApplicationContext(), FoodListActivity.class);
                                startActivity(intent2);
                            }
                        }, 250); //버벅여서 지연시간 추가
                        break;
                    case R.id.nav_item3:
                        Toast.makeText(MainActivity.this, "3누름", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_item4:
                        Toast.makeText(MainActivity.this, "4누름", Toast.LENGTH_LONG).show();
                        break;
                    // 더 많은 아이템에 대한 처리 추가
                }
                //drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



     //  toolbar.setNavigationOnClickListener(v -> {
        //    if (drawerLayout.isDrawerOpen(findViewById(R.id.navigationView))) {
         //       drawerLayout.closeDrawer(findViewById(R.id.navigationView));
      //      } else {
          //      drawerLayout.openDrawer(findViewById(R.id.navigationView));
       //     }
    //    });












    }

    public void on_Click(View v) //해당 하는 이미지를 클릭하면 작동되는 함수. 해당 하는 음식의 num을 받은 후 리퀘스트 코드에 num을 더해  ChoiceMenuMain 로전달
    {
        int id = v.getId();
        ImageView images = findViewById(id);
        String tag = (String)images.getTag();
        int MenuNum;


        Intent intent = new Intent(this, ChoiceMenuMain.class);

        switch (tag)
        {
            case "koreaF":MenuNum=1;
                intent.putExtra("arr",KF);
                  break;

            case "chinaF":MenuNum=2;
                intent.putExtra("arr",CF);
                break;

            case "japanF":MenuNum=3;
                intent.putExtra("arr",JF);
                break;

            case "westF":MenuNum=4;
                intent.putExtra("arr",WF);
                break;

            case "fastF":MenuNum=5;
                intent.putExtra("arr",FF);
                break;

            case "asiaF":MenuNum=6;
                intent.putExtra("arr",AF);
                break;


            default: MenuNum = 0;
                break;

        }



        intent.putExtra("it_tag",tag);

        startActivityForResult(intent,REQUEST_CODE+MenuNum);

    }

    //다른 엑티비티에서 메인으로 돌아올 떄
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkPermission();
                        return;
                    }
                }

                break;
        }//볼 필요 없음



      //밑은  ChoiceMenuMain 로 부터 받은 리퀘스트 코드임.


        if(requestCode == 101){
            int nonNullCount = 0;
            if(data != null){



                KF = data.getStringArrayExtra("CheckBox");//한국음식 배열을  ChoiceMenuMain 으로 부터 받은 배열로 초기화
                for (String item : KF) {
                    if (item != null) {
                        nonNullCount++;
                    }
                }

                if(KF != null){
                    showToast(this, "한식 결정!");



                }
            }


            if(nonNullCount<1)
                ko.setChecked(false);
            else
                ko.setChecked(true);




        }//101은 한국음식

        else if(requestCode == 102){


            int nonNullCount = 0;
            if(data != null){


                CF = data.getStringArrayExtra("CheckBox");

                for (String item : CF) {
                    if (item != null) {
                        nonNullCount++;
                    }
                }

                if(CF != null){
                    showToast(this, "중식 결정!");




                }
            }

            if(nonNullCount<1)
                ch.setChecked(false);
            else
                ch.setChecked(true);



        }


        else if(requestCode == 103){
            int nonNullCount = 0;
            if(data != null){


                JF = data.getStringArrayExtra("CheckBox");

                for (String item : JF) {
                    if (item != null) {
                        nonNullCount++;
                    }
                }


                if(JF != null) {
                    showToast(this, "일식 결정!");

                }




            }

            if(nonNullCount<1)
                ja.setChecked(false);
            else
                ja.setChecked(true);


        }


        else if(requestCode == 104){
            int nonNullCount = 0;
            if(data != null){


                WF = data.getStringArrayExtra("CheckBox");

                for (String item : WF) {
                    if (item != null) {
                        nonNullCount++;
                    }
                }

                if(WF != null){
                    showToast(this, "양식 결정!");
                }





            }

            if(nonNullCount<1)
                we.setChecked(false);
            else
                we.setChecked(true);


        }

        else if(requestCode == 105){
            int nonNullCount = 0;
            if(data != null){


                FF = data.getStringArrayExtra("CheckBox");



                for (String item : FF) {
                    if (item != null) {
                        nonNullCount++;
                    }
                }

                if(FF != null){
                    showToast(this, "패스트푸드 결정!");
                }
            }


            if(nonNullCount<1)
                fe.setChecked(false);
            else
                fe.setChecked(true);

        }


        else if(requestCode == 106){
            int nonNullCount = 0;
            if(data != null){


                AF = data.getStringArrayExtra("CheckBox");

                for (String item : AF) {
                    if (item != null) {
                        nonNullCount++;
                    }
                }

                if(AF != null){
                    showToast(this, "아시안 결정!");
                }

            }



            if(nonNullCount<1)
                as.setChecked(false);
            else
                as.setChecked(true);


        }

        //개별적으로 추가한 음식
        else if(requestCode == 107){
            if(data != null)
                ADDF = data.getStringArrayExtra("CheckBox");
        }





        //선택목록에서
        else if(requestCode == 108){
            {
                int nonNullCount = 0;
                if(data != null)
                {
                    KF = data.getStringArrayExtra("K");//한국음식 배열을  ChoiceMenuMain 으로 부터 받은 배열로 초기화
                    for (String item : KF)
                        if (item != null)
                            nonNullCount++;
                }
                if(nonNullCount<1)
                    ko.setChecked(false);
                else
                    ko.setChecked(true);
            }


            {
                int nonNullCount = 0;
                if(data != null)
                {
                    CF = data.getStringArrayExtra("C");//한국음식 배열을  ChoiceMenuMain 으로 부터 받은 배열로 초기화
                    for (String item : CF)
                        if (item != null)
                            nonNullCount++;
                }
                if(nonNullCount<1)
                    ch.setChecked(false);
                else
                    ch.setChecked(true);
            }


            {
                int nonNullCount = 0;
                if(data != null)
                {
                    JF = data.getStringArrayExtra("J");//한국음식 배열을  ChoiceMenuMain 으로 부터 받은 배열로 초기화
                    for (String item : JF)
                        if (item != null)
                            nonNullCount++;
                }
                if(nonNullCount<1)
                    ja.setChecked(false);
                else
                    ja.setChecked(true);
            }


            {
                int nonNullCount = 0;
                if(data != null)
                {
                    WF = data.getStringArrayExtra("W");//한국음식 배열을  ChoiceMenuMain 으로 부터 받은 배열로 초기화
                    for (String item : WF)
                        if (item != null)
                            nonNullCount++;
                }
                if(nonNullCount<1)
                    we.setChecked(false);
                else
                    we.setChecked(true);
            }


            {
                int nonNullCount = 0;
                if(data != null)
                {
                    FF = data.getStringArrayExtra("F");//한국음식 배열을  ChoiceMenuMain 으로 부터 받은 배열로 초기화
                    for (String item : FF)
                        if (item != null)
                            nonNullCount++;
                }
                if(nonNullCount<1)
                    fe.setChecked(false);
                else
                    fe.setChecked(true);
            }



            {
                int nonNullCount = 0;
                if(data != null)
                {
                    AF = data.getStringArrayExtra("A");//한국음식 배열을  ChoiceMenuMain 으로 부터 받은 배열로 초기화
                    for (String item : AF)
                        if (item != null)
                            nonNullCount++;
                }
                if(nonNullCount<1)
                    as.setChecked(false);
                else
                    as.setChecked(true);
            }


            ADDF = data.getStringArrayExtra("AD");

















        }


        //룰렛에서 돌아올때 실행
        else if(requestCode==10000)
        {

            if(!(("미정".equals(data.getStringExtra("Finish"))))) {

                Result.setText(data.getStringExtra("Finish") + "(으)로 결정!");
                send_data = data.getStringExtra("Finish");


                Log.i("What", send_data);


                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog03, null);

                TextView goto_map_to_choice = dialogView.findViewById(R.id.goto_map_to_choice);
                if (goto_map_to_choice != null) {
                    goto_map_to_choice.setText("주변에 있는 '" + send_data + "' 가게를\n 확인하실래요?");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(dialogView);
                builder.setCancelable(true);

                final AlertDialog question_to_goto_food_dilago = builder.create();

                Button yesBtn = dialogView.findViewById(R.id.yesBtn);
                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        question_to_goto_food_dilago.dismiss();

                        goto_map();
                    }
                });

                Button noBtn = dialogView.findViewById(R.id.noBtn);
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        question_to_goto_food_dilago.dismiss();
                    }
                });

                question_to_goto_food_dilago.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                question_to_goto_food_dilago.show();


            }

           // if(send_data==null)
           // Toast.makeText(MainActivity.this, data.getStringExtra("Finish"), Toast.LENGTH_LONG).show();

        }



    }




    //체크 온 오프 할때 실행되는 곳
    View.OnClickListener CBCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean checked=((CheckBox)v).isChecked();

            switch (v.getId())
            {
                case R.id.koreaFBT:

                    if(checked)//한국 음식의 체크박스가 체크되어있다면 한국음식 배열을 기존에 있는 모든 한국음식 리스트로 채움
                    {
                        KF=getResources().getStringArray(R.array.koreaF);

                    }
                    else  //아니라면 전부 널로 채움
                    {
                        Arrays.fill(KF, null);


                    }
                    break;

                case R.id.chinaFBT:

                    if(checked)
                    {

                        CF=getResources().getStringArray(R.array.chinaF);

                    }
                    else
                    {
                        Arrays.fill(CF, null);

                    }
                    break;

                case R.id.japanFBT:

                    if(checked)
                    {

                        JF=getResources().getStringArray(R.array.japanF);

                    }
                    else
                    {
                        Arrays.fill(JF, null);

                    }
                    break;

                case R.id.westFBT:

                    if(checked)
                    {

                        WF=getResources().getStringArray(R.array.westF);

                    }
                    else
                    {
                        Arrays.fill(WF, null);

                    }
                    break;

                case R.id.fastFBT:

                    if(checked)
                    {

                        FF=getResources().getStringArray(R.array.fastF);

                    }
                    else
                    {
                        Arrays.fill(FF, null);

                    }
                    break;

                case R.id.asiaFBT:

                    if(checked)
                    {

                        AF=getResources().getStringArray(R.array.asiaF);
                    }
                    else
                    {
                        Arrays.fill(AF, null);

                    }
                    break;





            }

        }
    };




    //안봐도됨
    @Override
    public void onRequestPermissionsResult(int PermRequestCode, @NonNull String[] permission, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(PermRequestCode, permission, grandResults);

        if (PermRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드 PERMISSIONS_REQUEST_CODE 권한이 정상적으로 부여되었는지 확인
            boolean check_result = true;

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 115~131 권한 설정(check_result의 값에 따라 선택)
            if (check_result) {
                //거부가 아니라면 위치를 설정할 수 있음
            } else {
                // 만얀 권한이 거부가 된다면 2가지 경우로 설명해줌.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(MainActivity.this, "권한이 없습니다. 앱을 다시 실행하여 위치 접근 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "권한이 없습니다. 설정(앱 정보)에서 위치 접근 권한을 확인해주세요. ", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
    
    
    void checkPermission(){

        //권한 확인 단계
        // FINE_LOCATION, COARSE_LOCATION이 Manifest에 들어가 있는지 확인
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        // 권한이 만약 있다면 위치값을 가져올 수 있음.
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

        } else {  // 권한 요청을 거부하였다면 다시 권한 요구

            // 사용자가 위치 접근 권한 거부를 했다면
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 다시한번 권한을 요청함.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 거부한적이 없다면 권한을 다시 바로 요청함.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    /*
    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;
        // 만약 정상적으로 작동한다면
        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //정상적으로 작동안한다면
            return "지오코더 서비스 오류";
            //GPS가 문제라면
        } catch (IllegalArgumentException illegalArgumentException) {
            return "잘못된 GPS 좌표";

        }


        // 주소가 정상적으로 검색되지 않는다면
        if (addresses == null || addresses.size() == 0) {
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        if (address.getSubLocality() == null) {
            return address.getAdminArea().toString()+" "+ // 도
                    address.getLocality()+" "+ // 시
                    address.getThoroughfare()+" "; //동
        }
        else {
        return address.getAdminArea().toString()+" "+ // 도
                address.getLocality()+" "+ // 시
                address.getSubLocality()+" "+ // 구(구가 안잡히는 곳도 있음)
                address.getThoroughfare()+" "; //동
        }
    }

    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }*/








    //룰렛이었던 페이지로 가는 함수
    public void goto_Roulette(View v){
        Intent rouletteIntent = new Intent(this, rouletteMain.class);
        ArrayList<String> allMenu1= new ArrayList<String>();
        AllMenuList=new String[][]{KF,CF,JF,WF,FF,AF,ADDF};

       for (int x=0;x<7;x++)//위 7카테고리의 음식종류 만큼 반복    모든 음식배열들을 하나로 모으는 과정
       try {

              for (int y=0;y<30;y++)//일단은 30개로 고정 AllMenuList[x].length; 로 수정 가능
              {

                  if (AllMenuList[x][y] != null)
                      allMenu1.add(AllMenuList[x][y]); //해당 음식을 allMenu1 배열에 추가

              }

           }
        //예외처리
       catch(Exception e) {

           e.printStackTrace();

           continue;
       }
        //혹시 모르는 allMenu1의 null제거
       while (allMenu1.remove(null));

       //만약 음식의 갯수가 2개미만이라면 룰렛을 돌릴 이유가 없음.
        if(allMenu1.size()<2)
            Toast.makeText(this, "음식을 두개이상 고르셔야 룰렛이 작동합니다. (현재 갯수) "+allMenu1.size()+"개", Toast.LENGTH_LONG).show();

        //아니라면 allMenu1배열을 룰렛으로 보냄
        else{
        rouletteIntent.putStringArrayListExtra("AllMenuList", allMenu1);
        startActivityForResult(rouletteIntent,10000);}
    }



    //음식 추가페이지로 이동하는 함수. 전에 추가한 음식이 있는 ADDF배열을 같이 보냄.(빼고 싶을수도 있으니깐)
    public void goto_AddFood(View v)
    {
        Intent addfood=new Intent(this,AddFoodMain.class);

            addfood.putExtra("AddedMenu", ADDF);
            setResult(RESULT_OK, addfood);
        startActivityForResult(addfood,107);

    }



    //전체 선택한 메뉴를 확인하러 가는 메서드
    public void goto_ALLSelectMenu(View v)
    {
        Intent ASM=new Intent(this,ALLSelectMenu.class);
        ArrayList<String> allMenu1= new ArrayList<String>();


            ASM.putExtra("KF",KF);
            ASM.putExtra("CF",CF);
            ASM.putExtra("JF",JF);
            ASM.putExtra("WF",WF);
            ASM.putExtra("FF",FF);
            ASM.putExtra("AF",AF);
            ASM.putExtra("ADF",ADDF);

            setResult(RESULT_OK, ASM);
            startActivityForResult(ASM,108);

    }




        //안봐도됨
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    //음식을 고르면 그 음식명을 들고 이동

    public void goto_map() {
        goto_map(null);
    }


    public void goto_map(View v)
    {

        //데이터 없으면 못들어가게 막음
        if(!(isConnected(this)))
        {
            showToast(this, "네트워크를 연결 해주세요");

        }
        //데이터는 되지만 음식을 고르지 못했다면 못가게 막음 -->그냥 지도로 이동하게
        //여기 수정 필요. 체크박스 켜져 있는 타입의 가게만 나오게끔.
        else if(send_data==null || "굶기".equals(send_data)||"".equals(send_data))
        {
            Intent intent = new Intent(this, show_restaurant_kakaoMap.class);
            startActivity(intent);
            //showToast(this, "음식을 제대로 고른후 눌러주세요");


        }

        else {
            Intent goMap = new Intent(this, show_restaurant_kakaoMap.class);
            goMap.putExtra("selected",true);
            goMap.putExtra("Finish", send_data);
            startActivity(goMap);
        }
    }


    //종료할거냐고 묻기
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // 네비게이션 드로어가 열려 있으면 드로어를 닫음.
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
        showDialog01();

    }


        //네트워크 체크하는 메서드
        public boolean isConnected(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }



        //안봐도됨 토스트 실행중엔 다른 토스트 안나오게
    private static Toast sToast;
    public static void showToast(Context context, String message) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(message);
        }
        sToast.show();
    }




    //안봐도 됨
    public void showDialog01(){

        dilaog01.show(); // 다이얼로그 띄우기
        dilaog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button noBtn = dilaog01.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog01.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        dilaog01.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                finish();           // 앱 종료
            }
        });
    }



}










