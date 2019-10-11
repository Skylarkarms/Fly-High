package com.example.flyhigh.fireBase_user_package;

public class UserFactory {

    private UserFactory() {
    }

    public static UserFactory getInstance() {
        return new UserFactory();
    }

    public UserNotif getNewUserNotif(boolean helpRequested, boolean clientAttended) {
        UserNotif userNotif;
        return userNotif = new UserNotif(helpRequested, clientAttended);
    }

    public FlyHighUser getNewFlyHighUser(String _uId, String _username) {
        return new FlyHighUser(_uId, _username);
    }

    public User getNewUser(String _email, String _name, int _phone) {
        User user;
        return user = new User(_email,_name,_phone);
    }

}
