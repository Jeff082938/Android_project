package com.example.firstproject.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mLatitude ;  // 緯度
    private final MutableLiveData<String> mLongitude; // 經度

    public HomeViewModel() {
        mLatitude = new MutableLiveData<>();
        mLongitude = new MutableLiveData<>();
        mLatitude.setValue("未取得緯度");
        mLongitude.setValue("未取得經度");
    }


    public LiveData<String> getLatitude() {
        return mLatitude;
    }

    public LiveData<String> getLongitude() {
        return mLongitude;
    }

    public void setLatitude(String latitude) {
        mLatitude.setValue(latitude);
    }

    public void setLongitude(String longitude) {
        mLongitude.setValue(longitude);
    }
}
