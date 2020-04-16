package com.zhangzhenguo.privatestoragefile;

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

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btCreateFiles;
    private Button btCreateFile;
    private TextView tvShowPath;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        btCreateFiles=findViewById(R.id.btCreateFiles);
        btCreateFile=findViewById(R.id.btCreateFile);
        tvShowPath=findViewById(R.id.tvShowPath);
        btCreateFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File files=new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath()+"/hgmm/Upload/");
                if (files.exists()){
                    files.mkdir();
                    Toast.makeText(context, "创建文件夹成功"+files.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btCreateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath()+"/hgmm/Upload/111.zip");
                if (!file.exists()){
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

    /**
     * 打开权限
     */
    private void openPermissions(){
        final RxPermissions rxPermissions = new RxPermissions(LoadingActivity.this); // where this is an Activity or Fragment instance
        rxPermissions.requestEachCombined(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入
                Manifest.permission.READ_EXTERNAL_STORAGE,//读取
                Manifest.permission.CAMERA,//相机
                Manifest.permission.ACCESS_COARSE_LOCATION,//获取当前系统位置信息
                Manifest.permission.RECORD_AUDIO,//录音权限
                Manifest.permission.READ_PHONE_STATE,//获取设备信息
                Manifest.permission.SEND_SMS//收发短信权限
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
                    //        android8.0未知来源安装适配
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                        if(!haveInstallPermission){
                            Log.d("执行","转到设置");
                            //权限没有打开则提示用户去手动打开
                            Uri packageURI = Uri.parse("package:"+LoadingActivity.this.getPackageName());
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
                            startActivityForResult(intent, INSTALL_PERMISS_CODE);
                        }else {
                            Log.d("执行","通过了所有权限");
                        }
                    }else {
                        Log.d("执行","通过了所有权限");
                    }
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
