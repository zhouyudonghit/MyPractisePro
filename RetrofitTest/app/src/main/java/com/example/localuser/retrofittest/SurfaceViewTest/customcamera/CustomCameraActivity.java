package com.example.localuser.retrofittest.SurfaceViewTest.customcamera;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.DateUtil;
import com.example.localuser.retrofittest.Utils.imageprocessor.CompressionPredicate;
import com.example.localuser.retrofittest.Utils.imageprocessor.ImageProcessor;
import com.example.localuser.retrofittest.Utils.imageprocessor.OnCompressListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class CustomCameraActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = getClass().getSimpleName();
    private Camera mCamera;
    private boolean isAblum;
    private long startTime=0L;
    private long endTime=0L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_camera_activity_main);
        initView();
    }

    //获取照片中的接口回调
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //这里就是拍照成功后返回照片的地方，注意，返回的是原图，可能几兆十几兆大，需要压缩处理
            FileOutputStream fos = null;
            String path = Environment.getExternalStorageDirectory().getPath();
            String mFilePath = path + File.separator + "tt001.png";
            //文件
            Long currentTimeMillis = new Date().getTime();

            try {
                File jpgFile = new File(mFilePath);
                FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
                outputStream.write(data); // 写入sd卡中
                outputStream.close(); // 关闭输出流

//                fos.write(data);
                long l1 = DateUtil.getCurrentTimeMillis() - currentTimeMillis;
                Long d = DateUtil.getCurrentTimeMillis();
                Log.e("","图片存储时间--"+ l1);
                processImageAndfreshAdapter(mFilePath);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //实现连续拍多张的效果
//                mCamera.startPreview();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    protected void initView() {
        mCamera = Camera.open();    //初始化 Camera对象
        CameraPreview mPreview = new CameraPreview(this, mCamera);
        LinearLayout camera_preview = findViewById(R.id.camera_preview);
        camera_preview.addView(mPreview);

        //得到照相机的参数
        Camera.Parameters parameters = mCamera.getParameters();
        //图片的格式
        parameters.setPictureFormat(ImageFormat.JPEG);
//        //预览的大小是多少
//        parameters.setPreviewSize(camera_preview.getMeasuredWidth(), camera_preview.getMeasuredHeight());
        //设置对焦模式，自动对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(parameters);

        findViewById(R.id.tv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取照片
                mCamera.takePicture(null, null, mPictureCallback);
//                //对焦成功后，自动拍照
//                mCamera.autoFocus(new Camera.AutoFocusCallback() {
//                    @Override
//                    public void onAutoFocus(boolean success, Camera camera) {
//                        if (success) {
//
//                        }
//                    }
//                });

            }
        });
    }

//    private void jumpToMatisse(BaseActivity activity, final int count, int resultCode) {
//        SelectionCreator selectionCreator = Matisse.from(activity).choose(MimeType.ofAll());
//        selectionCreator.capture(false).countable(true)
//                .maxSelectable(count)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.px300))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .captureStrategy(
//                        new CaptureStrategy(true, activity.getApplicationContext().getPackageName() + ".fileprovider"))
//                .thumbnailScale(0.85f)
//                .theme(R.style.Matisse_Zhihu)
//                //                .imageEngine(new GlideEngine())
//                .imageEngine(new Glide4Engine())
//                .setOnSelectedListener(new OnSelectedListener() {
//                    @Override
//                    public void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
//                        Log.e("onSelected", "onSelected: pathList=" + pathList);
//                    }
//                })
//                .originalEnable(true)
//                .maxOriginalSize(10)
//                .forResult(resultCode);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PicMgr.REQUEST_CODE_FINSH && resultCode == RESULT_OK) {
//            setResult(Activity.RESULT_OK,data);
//            finish();
//        }
    }

    @Override
    public void onClick(View v) {

    }

    private void processImageAndfreshAdapter(final String path)
    {
        Log.d(TAG,"processImageAndfreshAdapter,path = "+path);
        ImageProcessor.with(CustomCameraActivity.this)
                .load(path)
                .ignoreBy(100)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用
                        startTime= System.currentTimeMillis();
                    }

                    @Override
                    public void onSuccess(final File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        endTime= System.currentTimeMillis();
                        Log.d(TAG, "file size="+file.getAbsolutePath()+"----"+(file.length()/1000f)+"k"+"   spend time: "+(endTime-startTime)+"ms");
                        //更新选图的路径为压缩后的路径
                        String imagePath=file.getAbsolutePath();
                        //大于300k（后台限定300k）, 继续压缩
                        float size = (new File(imagePath).length() / 1024f);
                        if (size > 300f) {
                            processImageAndfreshAdapter(imagePath);
                            return;
                        }
                        final ImageView myPic = findViewById(R.id.my_pic);
                        myPic.post(new Runnable() {
                            @Override
                            public void run() {
                                myPic.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            }
                        });
                        //上传图片
                    }
                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        Log.d(TAG, e.getLocalizedMessage());
                    }
                }).launch();
    }
}
