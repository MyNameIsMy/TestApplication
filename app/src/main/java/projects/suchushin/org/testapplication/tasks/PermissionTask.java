package projects.suchushin.org.testapplication.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.lang.ref.WeakReference;

import projects.suchushin.org.testapplication.Permission;
import projects.suchushin.org.testapplication.activities.StartActivity;
import projects.suchushin.org.testapplication.web.ServiceHolder;

public class PermissionTask extends AsyncTask<Void, Void, Void>{
    private WeakReference<StartActivity> contextHolder;
    private Permission permission;

    public PermissionTask(WeakReference<StartActivity> contextHolder) {
        this.contextHolder = contextHolder;
    }

    protected void onPreExecute() {
        permission = contextHolder.get().getDataProvider().getPermission();
    }

    protected Void doInBackground(Void... voids) {
        if (permission == null){
            try {
                permission = ServiceHolder.getService().permit().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    protected void onPostExecute(Void v) {
        if (permission == null){
            new PermissionTask(contextHolder).execute();
        } else {
            contextHolder.get().onPermissionExistOrCreaded(permission.isAllow());
        }
    }
}
