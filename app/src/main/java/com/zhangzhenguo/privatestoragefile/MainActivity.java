package com.zhangzhenguo.privatestoragefile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private Button btCreateFiles;
    private Button btCreateFile;
    private Button btCreateFileD;
    private TextView tvShowPath;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCreateFiles=findViewById(R.id.btCreateFiles);
        btCreateFile=findViewById(R.id.btCreateFile);
        btCreateFileD=findViewById(R.id.btCreateFileD);
        tvShowPath=findViewById(R.id.tvShowPath);
        openPermissions();
        context=MainActivity.this;
    }

    private void muCLick(){
        btCreateFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File files=new File(Environment.getExternalStorageDirectory().getPath()+"/test/Upload");
                if (!files.exists()){
                    files.mkdir();
                    Toast.makeText(context, "创建文件夹成功"+files.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "已有此文件夹"+files.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }
                File file=new File(Environment.getExternalStorageDirectory().getPath()+"/test/Upload/111.zip");
                if (!file.exists()){
                    try {
                        file.createNewFile();
                        Toast.makeText(context, "创建文件成功"+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(context, "已有此文件或创建失败"+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btCreateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path=getSDPath(context);
                Log.d("执行",path);
                File files=new File(path+"/test/Upload/");
                if (!files.exists()){
                    files.mkdirs();
                    Toast.makeText(context, "创建文件夹成功"+files.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }
                File file=new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath()+"/test/Upload/111.zip");
                if (!file.exists()){
                    try {
                        file.createNewFile();
                        Toast.makeText(context, "创建文件成功"+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        file.createNewFile();
                        Toast.makeText(context, "创建文件成功"+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btCreateFileD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path=getSDPath(context);
                Log.d("执行",path);
                File files=new File(path+"/test/");
                if (!files.exists()){
                    files.mkdir();
                    File files1=new File(path+"/test/Upload/");
                    if (!files1.exists()){
                        files1.mkdir();
                        Log.d("执行","创建文件夹成功"+files1.getAbsolutePath());
                    }
                }
                File file=new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath()+"/test/Upload/111.zip");
                if (!file.exists()){
                    try {
                        file.createNewFile();
                        Toast.makeText(context, "创建文件成功"+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        file.createNewFile();
                        Toast.makeText(context, "创建文件成功"+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public static String getSDPath(Context context) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            if (Build.VERSION.SDK_INT>=29){
                //Android10之后
                sdDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            }else {
                sdDir = Environment.getExternalStorageDirectory();// 获取SD卡根目录
            }
        } else {
            sdDir = Environment.getRootDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }
    /**
     * 打开权限
     */
    private void openPermissions(){
        final RxPermissions rxPermissions = new RxPermissions(MainActivity.this); // where this is an Activity or Fragment instance
        rxPermissions.requestEachCombined(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入
                Manifest.permission.READ_EXTERNAL_STORAGE,//读取
                Manifest.permission.READ_PHONE_STATE//获取设备信息
//                Manifest.permission.REQUEST_INSTALL_PACKAGES//允许未知来源安装


//                Manifest.permission.READ_LOGS,//输出日志
//                Manifest.permission.ACCESS_WIFI_STATE,//获取当前WiFi接入的状态以及WLAN热点的信息
//                Manifest.permission.ACCESS_FINE_LOCATION,//
//                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,//
//                Manifest.permission.USE_FINGERPRINT,//
//                Manifest.permission.CHANGE_NETWORK_STATE,//
//                Manifest.permission.SYSTEM_ALERT_WINDOW,//
//                Manifest.permission.CHANGE_WIFI_STATE,//
//                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS//
        ).subscribe(new Consumer<Permission>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted){
                        Log.d("执行","通过了所有权限");
                        muCLick();
                }else if (permission.shouldShowRequestPermissionRationale){
                    Log.d("执行","至少有一个权限被拒绝了");
                    openPermissions();
                }else {
                    Log.d("执行","转到设置");
                }
            }
        });
    }
}
