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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;
import android.widget.Toast;


import com.food1.whateat.data.food.FoodDAO;
import com.food1.whateat.data.food.FoodVO;
import com.food1.whateat.db.FoodDatabase;
import com.food1.whateat.presentation.calendar.CalendarActivity;
import com.food1.whateat.presentation.add_food.AddFoodListActivity;
import com.food1.whateat.presentation.choice.ChoiceFoodActivity;
import com.food1.whateat.presentation.question.FoodQuestionActivity;
import com.food1.whateat.presentation.roulette.RouletteActivity;
import com.food1.whateat.presentation.selected_list.SelectedListActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.widget.Toolbar;
public class MainActivity extends AppCompatActivity{


    private static final int GPS_ENABLE_REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_CODE =100 ;

    TextView Result;//결정된 음식명
    CheckBox cbKo, cbCh, chJa, chWe, chFe, chAs;
    private GpsTracker gpsTracker;
    //따로 추가
    String send_data;
    Dialog dilaog01;
    Dialog question_to_goto_food_dilago;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    boolean check_result = true;
    private FoodDatabase foodDatabase;
    ArrayList<String> check_food = new ArrayList<>();

    String KF[];
    String CF[];
    String JF[];
    String WF[];
    String FF[];
    String AF[]; //위 6개는 각 해당하는 나라의 음식 배열임.

    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        cbKo=findViewById(R.id.koreaFBT);
        cbCh=findViewById(R.id.chinaFBT);
        chJa=findViewById(R.id.japanFBT);
        chWe=findViewById(R.id.westFBT);
        chFe=findViewById(R.id.fastFBT);
        chAs=findViewById(R.id.asiaFBT);

        cbKo.setOnClickListener(CBCL);
        cbCh.setOnClickListener(CBCL);
        chJa.setOnClickListener(CBCL);
        chWe.setOnClickListener(CBCL);
        chFe.setOnClickListener(CBCL);
        chAs.setOnClickListener(CBCL);
        Result = findViewById(R.id.result);

        foodDatabase = FoodDatabase.getInstance(this);


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
                                Intent intent = new Intent(MainActivity.this, FoodQuestionActivity.class);
                                startActivity(intent);
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

    public void on_Click(View v)
    {

        int MenuNum;

        Intent intent = new Intent(this, ChoiceFoodActivity.class);
        String tag = (String)v.getTag();
        switch (tag)
        {
            case "koreaF":MenuNum=1;
                intent.putExtra("category", "한식");
                break;
            case "chinaF":MenuNum=2;
                intent.putExtra("category", "중식");
                break;
            case "japanF":MenuNum=3;
                intent.putExtra("category", "일식");
                break;
            case "westF":MenuNum=4;
                intent.putExtra("category", "양식");
                break;
            case "fastF":MenuNum=5;
                intent.putExtra("category", "패스트푸드");
                break;
            case "asiaF":MenuNum=6;
                intent.putExtra("category", "아시안");
                break;
            default: MenuNum = 0;
                break;
        }
        startActivityForResult(intent,REQUEST_CODE + MenuNum);
    }

    public int selectFood(Intent data) {
        int nonNullCount = 0;
        if (data == null) {
            return 0;
        }
        FoodDAO foodDAO = foodDatabase.foodDAO();
        String[] selecteds = data.getStringArrayExtra("selected");
        if (selecteds == null) {
            return 0;
        }
        for (String item : selecteds) {
            String selected = item.substring(0,1);
            String foodName = item.substring(2);
            if (selected.equals("T")) {
                FoodVO foodVO = foodDAO.findByFoodName(foodName);
                if (foodVO == null) {
                    FoodVO addFoodVO = new FoodVO(foodName);
                    addFoodVO.setSelected(true);
                    foodDAO.insert(addFoodVO);
                } else {
                    foodVO.setSelected(true);
                    foodDAO.update(foodVO);
                }
                nonNullCount++;
            } else {
                FoodVO foodVO = foodDAO.findByFoodName(foodName);
                if (foodVO != null) {
                    foodVO.setSelected(false);
                    foodDAO.update(foodVO);
                }
            }
        }
        return nonNullCount;
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



        if(requestCode == 101){
            if(selectFood(data)<1) {
                cbKo.setChecked(false);
            }
            else {
                showToast(this, "한식 결정!");
                cbKo.setChecked(true);
            }

        }
        else if(requestCode == 102){
            if(selectFood(data)<1) {
                cbCh.setChecked(false);
            }
            else {
                showToast(this, "중식 결정!");
                cbCh.setChecked(true);
            }
        }
        else if(requestCode == 103){
            if(selectFood(data)<1) {
                chJa.setChecked(false);
            }
            else {
                showToast(this, "일식 결정!");
                chJa.setChecked(true);
            }
        }
        else if(requestCode == 104){
            if(selectFood(data)<1) {
                chWe.setChecked(false);
            }
            else {
                showToast(this, "양식 결정!");
                chWe.setChecked(true);
            }
        }

        else if(requestCode == 105){
            if(selectFood(data)<1) {
                chFe.setChecked(false);
            }
            else {
                showToast(this, "패스트푸드 결정!");
                chFe.setChecked(true);
            }
        }
        else if(requestCode == 106){
            if(selectFood(data)<1) {
                chAs.setChecked(false);
            }
            else {
                showToast(this, "아시안 결정!");
                chAs.setChecked(true);
            }
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
                        check_food.add("한식");

                    }
                    else  //아니라면 전부 널로 채움
                    {
                        check_food.remove("한식");
                        Arrays.fill(KF, null);
                    }
                    break;

                case R.id.chinaFBT:

                    if(checked)
                    {

                        CF=getResources().getStringArray(R.array.chinaF);
                        check_food.add("중식");
                    }
                    else
                    {
                        check_food.remove("중식");
                        Arrays.fill(CF, null);

                    }
                    break;

                case R.id.japanFBT:

                    if(checked)
                    {
                        check_food.add("일식");
                        JF=getResources().getStringArray(R.array.japanF);

                    }
                    else
                    {
                        check_food.remove("일식");
                        Arrays.fill(JF, null);

                    }
                    break;

                case R.id.westFBT:

                    if(checked)
                    {
                        check_food.add("양식");
                        WF=getResources().getStringArray(R.array.westF);

                    }
                    else
                    {
                        check_food.remove("양식");
                        Arrays.fill(WF, null);

                    }
                    break;

                case R.id.fastFBT:

                    if(checked)
                    {
                        check_food.add("패스트푸드");
                        FF=getResources().getStringArray(R.array.fastF);

                    }
                    else
                    {
                        check_food.remove("패스트푸드");
                        Arrays.fill(FF, null);

                    }
                    break;

                case R.id.asiaFBT:

                    if(checked)
                    {
                        check_food.add("아시아음식");
                        AF=getResources().getStringArray(R.array.asiaF);
                    }
                    else
                    {
                        check_food.remove("아시아음식");
                        Arrays.fill(AF, null);

                    }
                    break;





            }

        }
    };

    public void clickAddFoodActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), AddFoodListActivity.class);
        startActivity(intent);
    }

    //안봐도됨
    @Override
    public void onRequestPermissionsResult(int PermRequestCode, @NonNull String[] permission, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(PermRequestCode, permission, grandResults);

        if (PermRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드 PERMISSIONS_REQUEST_CODE 권한이 정상적으로 부여되었는지 확인
           // boolean check_result = true;

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
                        Toast.makeText(MainActivity.this, "위치 접근 권한이 없습니다.\n설정(앱 정보)에서 확인해주세요.", Toast.LENGTH_LONG).show();
                       // finish();
                    } else {
                    Toast.makeText(MainActivity.this, "위치 접근 권한이 없습니다.\n설정(앱 정보)에서 확인해주세요.", Toast.LENGTH_LONG).show();
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
        Intent rouletteIntent = new Intent(this, RouletteActivity.class);

        FoodDAO foodDAO = foodDatabase.foodDAO();
        List<FoodVO> foodsBySelected = foodDAO.findFoodsBySelected();
        if (foodsBySelected.size() < 2)
            Toast.makeText(this, "음식을 두개이상 고르셔야 룰렛이 작동합니다. (현재 갯수) "
                    + foodsBySelected.size() + "개", Toast.LENGTH_LONG).show();
        else {
            List<String> foodList = foodsBySelected.stream()
                    .map(foodVO -> foodVO.getName())
                    .collect(Collectors.toList());
            rouletteIntent.putStringArrayListExtra("selectedFoodList", new ArrayList<>(foodList));
            startActivityForResult(rouletteIntent, 10000);
        }
    }


    //전체 선택한 메뉴를 확인하러 가는 메서드
    public void goto_ALLSelectMenu(View v)
    {
        Intent ASM=new Intent(this, SelectedListActivity.class);
        ArrayList<String> allMenu1 = new ArrayList<String>();
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


        else if (!check_result) {
            // 만얀 권한이 거부가 된다면 2가지 경우로 설명해줌.

            Toast.makeText(MainActivity.this, "위치 접근 권한이 없습니다.\n설정(앱 정보)에서 확인해주세요.", Toast.LENGTH_LONG).show();

        }

        //데이터는 되지만 음식을 고르지 못했다면 못가게 막음 -->그냥 지도로 이동하게
        //여기 수정 필요. 체크박스 켜져 있는 타입의 가게만 나오게끔.
        else if(send_data==null || "굶기".equals(send_data)||"".equals(send_data))
        {
            if(check_food.isEmpty()){
                Toast.makeText(MainActivity.this, "카테고리 1개 이상 선택해주세요.", Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(this, show_restaurant_kakaoMap.class);
                intent.putExtra("selected",false);
                intent.putExtra("check_food_list",check_food);
                startActivity(intent);
            }

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


