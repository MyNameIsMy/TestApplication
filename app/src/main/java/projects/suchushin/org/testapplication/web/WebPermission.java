package projects.suchushin.org.testapplication.web;

import io.realm.RealmObject;
import projects.suchushin.org.testapplication.Permission;

public class WebPermission implements Permission{
    private boolean allow;

    public WebPermission(){

    }

    public WebPermission(boolean allow) {
        this.allow = allow;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }
}
