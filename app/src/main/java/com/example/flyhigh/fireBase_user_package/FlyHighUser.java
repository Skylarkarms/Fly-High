package com.example.flyhigh.fireBase_user_package;

public class FlyHighUser {


    private String _uId;
    private String _username;

    public FlyHighUser() {
    }

    public FlyHighUser(String _uId, String _username) {
        this._uId = _uId;
        this._username = _username;
    }

    public String get_uId() {
        return _uId;
    }

    public String get_username() {
        return _username;
    }

}
