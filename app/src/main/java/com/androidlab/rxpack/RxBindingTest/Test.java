package com.androidlab.rxpack.RxBindingTest;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Haodong on 2017/3/17.
 */

public class Test {


    public static void main(String[] args) {

      List<Group>groupList=new ArrayList<>();
        
        
        Observable<Group>groupObservable= Observable.from(groupList);
        Observable<Group>groupObservable2=Observable.from(groupList);
        Observable<Group>group=Observable.merge(groupObservable,groupObservable2);
                


    }
}