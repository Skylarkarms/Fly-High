package com.example.flyhigh.fireBase_user_package;

public class UserNotif {

    private boolean _helpRequested;
    private boolean _clientAttended;

    public UserNotif() {
    }

    public UserNotif(boolean helpRequested, boolean clientAttended) {
        this._helpRequested = helpRequested;
        this._clientAttended = clientAttended;
    }

    public boolean isHelpRequested() {
        return _helpRequested;
    }

    public boolean isClientAttended() {
        return _clientAttended;
    }

    public void setHelpRequested(boolean helpRequested) {
        this._helpRequested = helpRequested;
    }

    public void setClientAttended(boolean clientAttended) {
        this._clientAttended = clientAttended;
    }
}
