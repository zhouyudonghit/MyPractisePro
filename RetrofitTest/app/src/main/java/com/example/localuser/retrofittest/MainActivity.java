package com.example.localuser.retrofittest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localuser.retrofittest.AnimatorTest.AnimatorTestActivity;
import com.example.localuser.retrofittest.Canvas.CanvasActivity;
import com.example.localuser.retrofittest.DrawLayout.DrawLayoutActivity;
import com.example.localuser.retrofittest.MotionEventTest.MotionEventTestActivity;
import com.example.localuser.retrofittest.PullRefreshListView.PullRefreshActivity;
import com.example.localuser.retrofittest.Toolbar.ToolbarActivity;
import com.example.localuser.retrofittest.View.MyViewActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    public static String TAG = "retrofit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test3();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv1 = (TextView) findViewById(R.id.toolbar_activity);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
            }
        });

        tv2 = (TextView) findViewById(R.id.drawlayout_activity);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DrawLayoutActivity.class));
            }
        });

        tv3 = (TextView) findViewById(R.id.canvas_activity);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CanvasActivity.class));
            }
        });

        tv4 = (TextView) findViewById(R.id.myview_activity);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyViewActivity.class));
            }
        });

        tv5 = (TextView) findViewById(R.id.animatortest_activity);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimatorTestActivity.class));
            }
        });

        tv6 = (TextView) findViewById(R.id.pullrefersh_activity);
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PullRefreshActivity.class));
            }
        });

        tv7 = (TextView) findViewById(R.id.motioneventtest_activity);
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MotionEventTestActivity.class));
            }
        });
    }

    public void test()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://baidu.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<Translation> call = request.getCall();
        call.enqueue(new Callback<Translation>(){

            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                 response.body();
                Translation tr = new Translation();
                Translation.Content content = tr.new Content();
                content.test2();
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });
    }

    public void test2()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://fanyi.youdao.com/").addConverterFactory(GsonConverterFactory.create()).build();
        GetRequest_Interface request_interface = retrofit.create(GetRequest_Interface.class);
        Call<Translation1> call = request_interface.getCall("I love you");
        call.enqueue(new Callback<Translation1>() {
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                response.body();
                Toast.makeText(MainActivity.this,"UI Thread",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Translation1> call, Throwable t) {

            }
        });
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "test");
        Log.d("","");
    }

    public void test3()
    {
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        GetRequestRxjava_Interface request_interface = retrofit.create(GetRequestRxjava_Interface.class);
        request_interface.getTopMovie(0,10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Movie movie) {
                        Log.d(TAG, "onNext: " + movie.getTitle());
                        List<Subjects> list = movie.getSubjects();
                        for (Subjects sub : list) {
                            Log.d(TAG, "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                        }
                    }
                });
    }
}
