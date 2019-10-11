package com.example.flyhigh.fireBase_user_package;

public class User {

    private String _email;
    private String _name;
    private int _phone;

    public User() {
    }

    public User(String _email, String _name, int _phone) {
        this._email = _email;
        this._name = _name;
        this._phone = _phone;
    }

    public String get_email() {
        return _email;
    }

    public String get_name() {
        return _name;
    }

    public int get_phone() {
        return _phone;
    }
}
