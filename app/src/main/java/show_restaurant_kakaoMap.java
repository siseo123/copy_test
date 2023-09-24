import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.naver.maps.map.MapView;
//import com.naver.maps.map.renderer.MapRendererScheduler;


import com.food1.whateat.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class show_restaurant_kakaoMap extends AppCompatActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener{
    //MapView mapView = new MapView(this);
    Task task;
    int pages = 1;
    int id = 0;
    MapView mapView;
    Activity kakaoMap;
    LocationManager locationManager;
    URL url;
    TextView p_name;
    TextView p_address;
    TextView p_phone;
    double my_pos;//위도
    double mx_pos;//경도
    final String key = "93475733697d72692048d958ab1e28cc";
    TextView selected_food;
    String keyword;
    MapPoint mapPoint;

    String is_end;

    MapPoint pMarkerPoint;
    MapPoint pMapPoint;
    String encodeUrl ="";
    String api;
    int cnt = 0;
    MapPOIItem pMarker;
    int final_cnt;

    final String PACKAGE_NAME = "net.daum.android.map";

    double lat;
    double lng;

    String getUrl;

    //파싱 정보 저장 클래스
    public static class xpp_list {
        final int list_cnt = 9999;
        public String[] getp_url = new String[list_cnt];
        public String[] get_Name = new String[list_cnt];
        public String[] get_addr = new String[list_cnt];
        public String[] get_phone = new String[list_cnt];
        public String[] get_x = new String[list_cnt];
        public String[] get_y = new String[list_cnt];
    }

    xpp_list xppList = new xpp_list();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        kakaoMap = show_restaurant_kakaoMap.this;
        task = new Task();
        setContentView(R.layout.activity_show_restaurant_kakao_map);

        Intent get_data = getIntent();
        keyword = get_data.getStringExtra("selected_food");


        selected_food.setText(keyword);
        mapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        locationManager = null;


        getHashKey();
        startLocationService();


        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);
        mapView.setPOIItemEventListener(this);

        mapView.setMapCenterPoint(mapPoint,true);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        //Log.i("마커", String.valueOf(mapView.isShowingCurrentLocationMarker()));
        try {
            encodeUrl = URLEncoder.encode(keyword,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        api = "https://dapi.kakao.com/v2/local/search/keyword.xml?page="+pages+"&size=15&sort=accuracy&category_group_code=FD6&y="+my_pos+"&x="+mx_pos+"&radius=1000&query=" + encodeUrl;
        //api = "https://dapi.kakao.com/v2/local/search/keyword.xml?page="+pages+"&size=15&sort=accuracy&category_group_code=FD6&query=" + encodeUrl;


        //파싱 기본구성
        task.execute(api);
    }


    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


    public void startLocationService() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 100
            );
            return;
        }
        //gps, network 위치정보

        Location location;

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }else{
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        mx_pos = location.getLongitude();
        my_pos = location.getLatitude();
        mapPoint = MapPoint.mapPointWithGeoCoord(my_pos,mx_pos);

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        id = mapPOIItem.getTag();
        lat = mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude;
        lng = mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude;
        pMapPoint = MapPoint.mapPointWithGeoCoord(lat,lng);
        mapView.setMapCenterPoint(pMapPoint,true);
        p_name.setText(xppList.get_Name[id]);
        p_address.setText(xppList.get_addr[id]);
        p_phone.setText(xppList.get_phone[id]);
        if(xppList.get_phone[id]==null){
            p_phone.setText("번호가 없는 가게에요.. ㅠㅠ");
        }
        Log.i("좌표들",lat+" "+lng);
        getUrl = xppList.getp_url[id];
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }


    //카카오맵 생성시
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setCurrentLocationRadius(1000);
        mapView.setCurrentLocationRadiusStrokeColor(Color.argb(100,0,0,0));
        mapView.setZoomLevel(4,true);
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    //파싱 부분
    public class Task extends AsyncTask<String, Void, String> {

        String string_data;
        String str;

        @Override
        protected String doInBackground(String... urls) {
            try{
                String txt = downloadUrl(urls[0]);
                Log.i("txt: ",txt);
                return txt;

            } catch (IOException e){
                return "다운로드 실패";
            }
        }

        @Override
        protected void onCancelled() {
            Log.i("완료", String.valueOf(task.getStatus()));
        }


        @Override
        protected void onPostExecute(String result){
            try{

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                XmlPullParser xpp2 = factory.newPullParser();
                xpp.setInput(new StringReader(result));
                xpp2.setInput(new StringReader(result));
                int eventType = xpp.getEventType();
                int eventType2 = xpp.getEventType();



                String phone_num;
                String address_name;
                String x;
                String y;
                String name;
                String place_name;
                String place_url = null;
                while(eventType!=XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_TAG){

                        name = xpp.getName();
                        Log.i("myname:",name);

                        switch (name) {
                            case "documents":
                                break;
                            case "address_name":
                                xpp.next();
                                address_name = xpp.getText();
                                xppList.get_addr[cnt] = address_name;
                                break;
                            case "phone":
                                xpp.next();
                                phone_num = xpp.getText();
                                xppList.get_phone[cnt] = phone_num;
                                break;
                            case "place_name":
                                xpp.next();
                                place_name = xpp.getText();
                                xppList.get_Name[cnt] = place_name;
                                break;
                            case "place_url":
                                xpp.next();
                                place_url = xpp.getText();
                                xppList.getp_url[cnt] = place_url;
                                Log.i("인터넷",place_url);
                                break;

                            case "x":
                                xpp.next();
                                x = xpp.getText();
                                xppList.get_x[cnt] = x;
                                break;
                            case "y":
                                xpp.next();
                                y = xpp.getText();
                                xppList.get_y[cnt++] = y;
                                break;

                        }

                    }
                    eventType = xpp.next();
                }
                while(eventType2!=XmlPullParser.END_DOCUMENT) {
                    if (eventType2 == XmlPullParser.START_TAG) {

                        name = xpp2.getName();

                        if (name.equals("documents")){

                        }else if(name.equals("is_end")){
                            xpp2.next();
                            is_end = xpp2.getText();
                            //text.append("가게이름: "+place_name+"\n\n");

                        }
                    }
                    eventType2 = xpp2.next();
                }
                Log.i("페페이즈",is_end);
            }catch (Exception e){
                Log.i("Error",e.getMessage());
            }

            //boolean is_end = Boolean.parseBoolean(xppList.get_end);
            Boolean convert_isEnd = Boolean.parseBoolean(is_end);
            //파싱이 완료 되었을때
            //xppList 클래스에 저장된 파싱 정보를 카카오맵 마커로 제작

            /*if(!is_end){
                pages += 1;
                task = new Task();
                api = "https://dapi.kakao.com/v2/local/search/keyword.xml?page="+pages+"&size=15&sort=accuracy&category_group_code=FD6&query=" + encodeUrl;
                task.execute(api);
                Log.i("페이지", String.valueOf(pages));
                Log.i("에이피",api);
            }else{
                for(int i = 0;i<cnt;i++) {
                    MapPOIItem pMarker = new MapPOIItem();
                    double x = Double.parseDouble(xppList.get_x[i]);
                    double y = Double.parseDouble(xppList.get_y[i]);
                    pMarkerPoint = MapPoint.mapPointWithGeoCoord(y, x);
                    pMarker.setItemName(xppList.get_Name[i]);
                    pMarker.setTag(i);
                    pMarker.setMapPoint(pMarkerPoint);
                    pMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    mapView.addPOIItem(pMarker);
                }
            }
            if(cnt == 0){
                Toast.makeText(getApplicationContext(),"선택된 음식의 가게가 없어요.. ㅠㅠ\n처음 화면으로 돌아갈게요...",Toast.LENGTH_LONG).show();
                kakaoMap.finish();
            }*/
            if(!convert_isEnd){
                pages += 1;
                task = new Task();
                api = "https://dapi.kakao.com/v2/local/search/keyword.xml?page="+pages+"&size=15&sort=accuracy&category_group_code=FD6&y="+my_pos+"&x="+mx_pos+"&radius=1000&query=" + encodeUrl;
                //api = "https://dapi.kakao.com/v2/local/search/keyword.xml?page="+pages+"&size=15&sort=accuracy&category_group_code=FD6&query=" + encodeUrl;
                task.execute(api);

                //로그 작성 부분 - 정상 작동
                Log.i("페이지", String.valueOf(pages));//pages수 검출
                Log.i("에이피",api);//api pages수 정상 상승 주소 검출

            }else if(convert_isEnd){
                for(int i = 0;i<cnt;i++) {
                    pMarker = new MapPOIItem();
                    double x = Double.parseDouble(xppList.get_x[i]);
                    double y = Double.parseDouble(xppList.get_y[i]);
                    pMarkerPoint = MapPoint.mapPointWithGeoCoord(y, x);
                    pMarker.setItemName(xppList.get_Name[i]);
                    pMarker.setTag(i);
                    pMarker.setMapPoint(pMarkerPoint);
                    pMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    mapView.addPOIItem(pMarker);
                    final_cnt = i;
                }

                Log.i("몇개", String.valueOf(cnt));
                //파싱이 끝났을 때 마지막 마커로 이동 및 정보 출력
                if(cnt > 0){
                    mapView.selectPOIItem(pMarker,true);
                    p_name.setText(xppList.get_Name[final_cnt]);
                    p_address.setText(xppList.get_addr[final_cnt]);
                    p_phone.setText(xppList.get_phone[final_cnt]);
                    lat = Double.parseDouble(xppList.get_y[final_cnt]);
                    lng = Double.parseDouble(xppList.get_x[final_cnt]);
                    if(xppList.get_phone[final_cnt]==null){
                        p_phone.setText("번호가 없는 가게에요.. ㅠㅠ");
                    }
                    getUrl = xppList.getp_url[final_cnt];
                }else{
                    Toast.makeText(getApplicationContext(),"선택된 음식의 가게가 주변에\n없어 처음 화면으로 돌아갈게요",Toast.LENGTH_LONG).show();
                    kakaoMap.finish();
                }
            }

        }

        private String downloadUrl(String api) throws IOException{


            try {
                url = new URL(api);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                conn.setRequestProperty("Authorization", "KakaoAK " + key);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                    //InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(buf, StandardCharsets.UTF_8));
                    StringBuilder buffer = new StringBuilder();

                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    string_data = buffer.toString();
                    Log.i("receiveMsg : ", string_data);

                    reader.close();
                    conn.disconnect();
                } else {
                    Log.i("결과", conn.getResponseCode() + "Error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return string_data;
        }

    }

    public void installedKakaoMap(View v)
    {
        Intent intent = getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        String my_pos = String.valueOf(this.my_pos);
        String mx_pos = String.valueOf(this.mx_pos);
        Uri uri = Uri.parse("kakaomap://route?sp="+my_pos+","+mx_pos+"&ep="+lat+","+lng+"&by=FOOT");
        Uri uri2 = Uri.parse("market://launch?id=net.daum.android.map");
        if(intent == null){
            Toast.makeText(getApplicationContext(),"카카오 맵을 설치해주세요.",Toast.LENGTH_LONG).show();
            Intent it = new Intent(Intent.ACTION_VIEW,uri2);
            startActivity(it);
        }
        else
        {
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);
        }
    }
    public void place_info(View v){
        Intent it = new Intent (Intent.ACTION_VIEW,Uri.parse(getUrl));
        startActivity(it);
    }

}
