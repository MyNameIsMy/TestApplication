package projects.suchushin.org.testapplication.data;

import android.content.Context;
import android.provider.ContactsContract;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DataProvider {
    private Realm realm;

    public void open(Context context){
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void close(){
        realm.close();
    }

    public void savePermission(boolean allow){
        realm.beginTransaction();
        realm.insert(new DataPermission(allow));
        realm.commitTransaction();
    }

    public void deletePermission() {
        realm.beginTransaction();
        realm.delete(DataPermission.class);
        realm.commitTransaction();
    }

    public DataPermission getPermission() {
        DataPermission permission;

        realm.beginTransaction();
        permission = realm.where(DataPermission.class).findFirst();
        realm.commitTransaction();

        return permission;
    }
}
