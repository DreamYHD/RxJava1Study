package com.androidlab.rxpack.RxBindingTest;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import com.androidlab.rxpack.R;
import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.IOException;
import java.security.Permissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Haodong on 2017/3/15.
 */

public class RxPermissionTest extends RxAppCompatActivity {


    private static final String TAG = "RxPermissionsSample";
    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;
    @BindView(R.id.btn_permission)
    Button mBtnPermission;

    private Camera camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);

        setContentView(R.layout.permiss_main);
        ButterKnife.bind(this);
        rxPermissions.requestEach(Manifest.permission.CAMERA,Manifest.permission.VIBRATE)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted){
                            Log.i("RxPermissions:",permission.name+"this permission get success");
                        }else{
                            Log.i("RxPermissions:",permission.name+"this permission get failed");
                        }


                    }
                });
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {

                        if (aBoolean) {
                            Log.i("RxPermissions:","all permissions get success");
                        }else {
                            Log.i("RxPermissions:","must has one ore more permissions get failed");
                        }
                    }
                });
        RxView.clicks(mBtnPermission)
                .compose(rxPermissions.ensure(Manifest.permission.CAMERA,Manifest.permission.VIBRATE))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {

                    }
                });
        RxView.clicks(mBtnPermission)
                .compose(rxPermissions.ensureEach(Manifest.permission.CAMERA,Manifest.permission.VIBRATE))
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {

                    }
                });



    }

}