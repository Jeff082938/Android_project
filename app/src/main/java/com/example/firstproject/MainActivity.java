package com.example.firstproject;

import androidx.fragment.app.Fragment;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import com.example.firstproject.ui.home.HomeViewModel;
import  java.util.List;
import java.util.Locale;
import  android.location.Geocoder;
import com.example.firstproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public TextView tvResult;
    public TextView AddressTextView;
    public String city = "";
    private ActivityMainBinding binding;
    private HomeViewModel homeViewModel;
    public static final String TAG = MainActivity.class.getSimpleName()+"My";
    private String lastLocationText = "";  // 儲存上次的位置文本
    String localProvider = LocationManager.GPS_PROVIDER;  // 或者 LocationManager.NETWORK_PROVIDER
    Button btShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tv_result);
        btShow = findViewById(R.id.bt_show);
        AddressTextView = findViewById(R.id.addressTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

    //顯示地址
    private List<Address> getAddress(Location location, TextView textView) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE);//地址翻譯成中文
                result = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                // 將地址資訊顯示在 TextView 上
                if (result != null && !result.isEmpty()) {
                    Address address = result.get(0); // 取得第一個地址
                    String addressText = address.getAddressLine(0); // 取得完整地址
                    textView.setText(addressText); // 設定 TextView 顯示內容
                } else {
                    textView.setText("無法取得地址資訊");
                }

                Log.v("TAG", "取得地址資訊：" + result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText("查詢地址時出現錯誤");
        }
        return result;
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lastLocationText = tvResult.getText().toString();
        // 保存顯示的經緯度文字
        outState.putString("location_text", tvResult.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 恢復顯示的經緯度文字
        String locationText = savedInstanceState.getString("location_text");
        if (locationText != null) {
            tvResult.setText(locationText);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 顯示上一次已知的經緯度
        if (!lastLocationText.isEmpty()) {
            tvResult.setText(lastLocationText);
        }
        // 觀察 HomeViewModel 的緯度和經度變化
        homeViewModel.getLatitude().observe(this, latitude -> {
            // 更新 TextView
            String longitude = homeViewModel.getLongitude().getValue();
            tvResult.setText("緯度：" + latitude + " 經度：" + longitude);
        });

        homeViewModel.getLongitude().observe(this, longitude -> {
            // 更新 TextView
            String latitude = homeViewModel.getLatitude().getValue();
            tvResult.setText("緯度：" + latitude + " 經度：" + longitude);
        });
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        if (currentFragment instanceof FirstFragment) {
            getLocal(); // 只有在 FirstFragment 时更新经纬度
        }
        Log.d(TAG, "重新呼叫getlocal");
        getLocal();

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocal();
        }
    }

    public void getLocal() {
        /**沒有權限則返回*/
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // 更新提示
        tvResult.setText("正在更新位置…");

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        localProvider = LocationManager.GPS_PROVIDER;
        Location location = manager.getLastKnownLocation(localProvider);


        if (location != null) {
            Log.d(TAG, "获取到位置信息: " + location.toString());
            String latitude = String.format("%.5f", location.getLatitude());
            String longitude = String.format("%.5f", location.getLongitude());

            // 更新 HomeViewModel 中的經緯度
             homeViewModel.setLatitude(latitude);
             homeViewModel.setLongitude(longitude);

            String address = "緯度：" + latitude + " 經度：" + longitude;
            tvResult.setText(address);
            lastLocationText = address;
            showLocation(location);
        } else {
            Log.d(TAG, "getLocal: 無法取得最後位置，正在請求位置更新");
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mListener);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mListener);
        }
        getAddress(location, AddressTextView);

    }
    /**監聽位置變化*/
    LocationListener mListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }
    };

    private void showLocation(Location location){

        String address = "  緯度："+String.format("%.5f", location.getLatitude()) +"  經度："+String.format("%.5f", Math.abs(location.getLongitude()));

        tvResult.setText(address);
        lastLocationText = address; // 儲存最新位置文本
        btShow.setOnClickListener(view -> {
            String url = "https://www.google.com/maps/@"+location.getLatitude()+","+Math.abs(location.getLongitude())+",15z";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

    }

}