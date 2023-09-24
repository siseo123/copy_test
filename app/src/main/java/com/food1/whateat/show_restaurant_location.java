package com.food1.whateat;

/*
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.util.FusedLocationSource;

public class show_restaurant_location extends AppCompatActivity implements OnMapReadyCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    TextView x_pos;
    TextView y_pos;
    LocationListener locationListener;
    @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_show_restaurant_location);
            FragmentManager fm = getSupportFragmentManager();
            MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
            x_pos = findViewById(R.id.X_pos);
            y_pos = findViewById(R.id.Y_pos);
            if (mapFragment == null ){
                mapFragment = MapFragment.newInstance();
                fm.beginTransaction().add(R.id.map,mapFragment).commit();
            }
            mapFragment.getMapAsync(this);
            locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);


    }
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        locationListener = new LocationListenerCompat() {
            @Override
            public void onLocationChanged(Location location) {
                updateMap(location);
            }

        };
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager
                .PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},100
            );
            return;
        }

        String locationProvider;
        locationProvider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider,1,1,locationListener);

        locationProvider = LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider,1,1,locationListener);

        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        uiSettings.setZoomControlEnabled(false);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //Toast.makeText(show_restaurant_location.this, locationSource.getLastLocation(), Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("SetTextI18n")
    public void updateMap(Location location){
        double my_pos = location.getLatitude();
        double mx_pos = location.getLongitude();
        x_pos.setText(""+my_pos);
        y_pos.setText(""+mx_pos);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }


}*/