package com.example.localuser.retrofittest.Glide;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.localuser.retrofittest.R;
import java.io.File;

public class GlideTestActivity extends AppCompatActivity {
    private ImageView image1,image2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        image1 = findViewById(R.id.image1);
        String url = "http://bpic.588ku.com/element_origin_min_pic/18/06/09/fb7cf5cf5f6c40116011455951665037.jpg";
        Glide.with(this).load(url).into(image1);
        //Environment.getExternalStorageDirectory();

        image2 = findViewById(R.id.image2);
        String mapsFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+"/camera/VID_19700115_073234.mp4";
        File file = new File(mapsFilePath);
        Glide.with(this).load(Uri.fromFile(file)).into(image2);
        SparseArray<String> arrays = new SparseArray<>();
        arrays.put(1,"1");
        ArrayMap map = new ArrayMap();
        map.size();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
