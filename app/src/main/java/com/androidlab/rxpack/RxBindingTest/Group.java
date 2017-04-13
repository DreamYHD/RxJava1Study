package com.androidlab.rxpack.RxBindingTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haodong on 2017/3/19.
 */

public class Group {

    private  String name;
    private List<Member>mSmallGroups=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return mSmallGroups;
    }

    public void setMembers(List<Member> smallGroups) {
        mSmallGroups = smallGroups;
    }
}
