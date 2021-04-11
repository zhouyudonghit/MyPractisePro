package com.example.localuser.retrofittest.DialogView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.ScreenUtils;

public class DialogActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_DIALOG + getClass().getSimpleName();
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_main);
        button = (Button) findViewById(R.id.show_dialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_view,null);
//        addContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void showDialog()
    {
//        MyDialog myDialog = new MyDialog(this);
//        myDialog.show();
        //addContentView();
        Dialog myDialog = new AlertDialog.Builder(this).setTitle("dailog").setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogActivity.this,"haha",Toast.LENGTH_LONG).show();
            }
        })
                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setMessage("fdsfadsfds").setView(R.layout.layout_dialog_view).create();
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(true);
        myDialog.show();
        WindowManager.LayoutParams lp = myDialog.getWindow().getAttributes();
        Log.d(TAG,"dialog window = "+myDialog.getWindow());
        Log.d(TAG,"dialog lp.type = "+lp.type);
        Log.d(TAG,"activity window = "+getWindow());
        Log.d(TAG,"activity lp.type = "+getWindow().getAttributes().type);

        Log.d(TAG,"dialog window,width = "+myDialog.getWindow().getAttributes().width+",height = "+myDialog.getWindow().getAttributes().height);
        Log.d(TAG,"activity window,width = "+getWindow().getAttributes().width+",height = "+getWindow().getAttributes().height);

        ScreenUtils.getWindowSizeMethod1(myDialog.getWindow());
        ScreenUtils.getWindowSizeMethod1(getWindow());
    }
}
