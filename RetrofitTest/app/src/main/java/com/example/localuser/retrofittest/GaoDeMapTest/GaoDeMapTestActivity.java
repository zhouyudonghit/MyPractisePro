package com.example.localuser.retrofittest.GaoDeMapTest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

import java.util.List;

import static com.amap.api.fence.GeoFenceClient.GEOFENCE_IN;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_OUT;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_STAYED;

public class GaoDeMapTestActivity extends AppCompatActivity implements AMap.OnMapLoadedListener{
    public static final String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";
    private String TAG = LogConfigs.TAG_PREFIX_GAODE + getClass().getSimpleName();
    private MapView mMapView;
    private AMap aMap;

    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                //解析广播内容
                Bundle bundle = intent.getExtras();
                //获取围栏行为：
                int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                //获取自定义的围栏标识：
                String customId = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                //获取围栏ID:
                String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
                //获取当前有触发的围栏对象：
                GeoFence fence = bundle.getParcelable(GeoFence.BUNDLE_KEY_FENCE);
                Log.d(TAG,"status = "+status+",customId = "+customId+",fenceId = "+fenceId);
                Log.d(TAG,"fence = "+fence);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gaode_test_activity_main);
        mMapView =  findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        initMapView();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(GEOFENCE_BROADCAST_ACTION);
        registerReceiver(mGeoFenceReceiver,filter);
        initGeoFence();
    }

    private void initMapView()
    {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setOnMapLoadedListener(this);
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setLogoBottomMargin(40);

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        aMap.setLocationSource(this);
    }

    private void initGeoFence()
    {
        //实例化地理围栏客户端
        GeoFenceClient mGeoFenceClient = new GeoFenceClient(getApplicationContext());

        //设置希望侦测的围栏触发行为，默认只侦测用户进入围栏的行为
        //public static final int GEOFENCE_IN 进入地理围栏
        //public static final int GEOFENCE_OUT 退出地理围栏
        //public static final int GEOFENCE_STAYED 停留在地理围栏内10分钟

//        mGeoFenceClient.setActivateAction(GEOFENCE_IN|GEOFENCE_OUT|GEOFENCE_STAYED);
        mGeoFenceClient.setActivateAction(GEOFENCE_OUT);
//        mGeoFenceClient.addGeoFence("苏宁易购广场","写字楼" , "北京",1,"000FATE23（考勤打卡）");
        GeoFenceListener fenceListenter = new GeoFenceListener() {
            @Override
            public void onGeoFenceCreateFinished(List<GeoFence> geoFenceList,
                                                 int errorCode,String s) {
                if(errorCode == GeoFence.ADDGEOFENCE_SUCCESS){//判断围栏是否创建成功
                    Log.d(TAG,"添加围栏成功!!");
                    Toast.makeText(GaoDeMapTestActivity.this,"添加围栏成功!!",Toast.LENGTH_LONG).show();
                    //geoFenceList是已经添加的围栏列表，可据此查看创建的围栏
                } else {
                    Toast.makeText(GaoDeMapTestActivity.this,"添加围栏失败!!",Toast.LENGTH_LONG).show();
                }
            }
        };
        mGeoFenceClient.setGeoFenceListener(fenceListenter);//设置回调监听
        mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
        mGeoFenceClient.addGeoFence("朝阳区","00FDTW103（在北京海淀的化妆品促销活动）");
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        unregisterReceiver(mGeoFenceReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
