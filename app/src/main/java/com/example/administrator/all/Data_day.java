package com.example.administrator.all;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class Data_day {
    String tag;//一天中有几行
    Double jq;//排列文字

    public Data_day(String tag, Double jq) {
        this.tag = tag;
        this.jq = jq;

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Double getJq() {
        return jq;
    }

    public void setJq(Double jq) {
        this.jq = jq;
    }

}
