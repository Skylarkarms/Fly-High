package com.example.flyhigh.from_firebase_to_list.mutable_snapshot_adder;

public class ValueClassRelator {

    private String value;

    private Class mClass;

    public ValueClassRelator(String value, Class aClass) {
        this.value = value;
        mClass = aClass;
    }

    public String getValue() {
        return value;
    }


    public Class getAClass() {
        return mClass;
    }
}
