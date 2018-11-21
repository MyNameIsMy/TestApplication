package projects.suchushin.org.testapplication.data;

import io.realm.RealmObject;
import projects.suchushin.org.testapplication.Permission;

public class DataPermission extends RealmObject implements Permission{
    private boolean allow;

    public DataPermission(){

    }

    public DataPermission(boolean allow) {
        this.allow = allow;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }
}
