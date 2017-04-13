package com.androidlab.rxpack.RxBindingTest;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlab.rxpack.R;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerViewAdapter;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RxBindingRegister extends RxAppCompatActivity {

    @BindView(R.id.text_rx_register)
    TextView mTextRxRegister;
    @BindView(R.id.btn_rx_register)
    Button mBtnRxRegister;
    @BindView(R.id.rxBinding_recycler)
    RecyclerView mRxBindingRecycler;
    private Observable mObservable;

    private MyAdapter mAdapter;

    private List<String> mList = new ArrayList<>();

    public static String index = "debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding_register);
        ButterKnife.bind(this);
        mList.add("hello");
        mAdapter = new MyAdapter(mList, this);
        initData();
        initView();
    }

    private void initData() {
        RxRecyclerViewAdapter.dataChanges(mAdapter)
                .subscribe(new Action1<MyAdapter>() {
                    @Override
                    public void call(MyAdapter myAdapter) {
                        Log.e("TAG", "DATA CHANGED");
                    }
                });
    }

    private void initView() {
        mRxBindingRecycler.setAdapter(mAdapter);
        mRxBindingRecycler.setLayoutManager(new LinearLayoutManager(this));

        RxRecyclerView.scrollStateChanges(mRxBindingRecycler)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("TAG", "change");
                    }
                });
        Action1 action = new Action1() {
            @Override
            public void call(Object o) {
                mList.add("hello");
                mAdapter.notifyDataSetChanged();

            }
        };

        RxView.clicks(mBtnRxRegister)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(action);


        RxBus.getInstance()
                .toObserverable(PersonBean.class)
                .subscribe(new Action1<PersonBean>() {
                    @Override
                    public void call(PersonBean personBean) {
                        Toast.makeText(RxBindingRegister.this, "help", Toast.LENGTH_SHORT).show();
                        Log.i("personBean", personBean.getName().toString() + personBean.getAge() + "");
                    }
                });
        Action1 action1 = new Action1<UserBean>() {
            @Override
            public void call(UserBean userBean) {
                Log.e("UserBean", userBean.getId().toString() + userBean.getName().toString());
            }
        };
        RxBus.getInstance()
                .toObserverable(UserBean.class)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(action1);


    }
}
