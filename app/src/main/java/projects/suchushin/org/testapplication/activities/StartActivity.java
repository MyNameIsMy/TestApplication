package projects.suchushin.org.testapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.suchushin.org.testapplication.R;
import projects.suchushin.org.testapplication.data.DataProvider;
import projects.suchushin.org.testapplication.tasks.PermissionTask;

public class StartActivity extends Activity {
    private DataProvider provider;
    private Boolean permission;
    @BindView(R.id.progress)
    ProgressBar progressView;
    @BindView(R.id.text_status)
    TextView statusView;
    @BindView(R.id.target_app)
    TextView targetApp;
    @BindView(R.id.recreate_permission_button)
    Button recreatePermissionButton;
    @BindView(R.id.start_app_button)
    Button startAppButton;
    @BindView(R.id.app_management_block)
    LinearLayout appManagementBlock;
    @BindView(R.id.permission_loading_block)
    LinearLayout permissionLoadingBlock;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        ButterKnife.bind(this);

        getDataProvider().open(this);

        appManagementBlock.setVisibility(View.INVISIBLE);

        startAppButton.setOnClickListener((v) -> {
            if (permission != null){
                startActivity(permission ? new Intent(StartActivity.this, GameMenuActivity.class) : new Intent(StartActivity.this, WebActivity.class));
            }
        });
        recreatePermissionButton.setOnClickListener((v) -> {
            getDataProvider().deletePermission();
            new PermissionTask(new WeakReference<>(this)).execute();
            targetApp.setText("");
            appManagementBlock.setVisibility(View.INVISIBLE);
            permissionLoadingBlock.setVisibility(View.VISIBLE);
        });

        new PermissionTask(new WeakReference<>(this)).execute();
    }

    public DataProvider getDataProvider() {
        if (provider == null)
            provider = new DataProvider();
        return provider;
    }

    public void onPermissionExistOrCreaded(boolean allow){
        permission = allow;
        getDataProvider().savePermission(permission);
        appManagementBlock.setVisibility(View.VISIBLE);
        permissionLoadingBlock.setVisibility(View.INVISIBLE);
        if (permission)
            targetApp.setText(getResources().getString(R.string.target_app_game));
        else
            targetApp.setText(getResources().getString(R.string.target_app_web_app));
    }

    protected void onDestroy() {
        super.onDestroy();
        provider.close();
    }

}
