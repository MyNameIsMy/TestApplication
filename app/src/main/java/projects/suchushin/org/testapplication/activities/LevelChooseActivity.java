package projects.suchushin.org.testapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import projects.suchushin.org.testapplication.R;

public class LevelChooseActivity extends Activity{
    @BindViews({R.id.easy_level_button, R.id.middle_level_button, R.id.hard_level_button, R.id.impossible_level_button})
    List<Button> levelButtons;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_choose_activity);
        ButterKnife.bind(this);

        for(Button levelButton : levelButtons){
            levelButton.setOnClickListener((v) -> startNextActivity(v.getId()));
        }
    }

    public void startNextActivity(int id){
        Intent intent = null;
        switch (id) {
            case R.id.easy_level_button :
                intent = new Intent(this, GameActivity.class).putExtra("LEVEL", "EASY");
                break;
            case R.id.middle_level_button :
                intent = new Intent(this, GameActivity.class).putExtra("LEVEL", "MIDDLE");
                break;
            case R.id.hard_level_button :
                intent = new Intent(this, GameActivity.class).putExtra("LEVEL", "HARD");
                break;
            case R.id.impossible_level_button :
                intent = new Intent(this, GameActivity.class).putExtra("LEVEL", "IMPOSSIBLE");
                break;
        }
        if (intent != null){
            intent.putExtra("SINGLE_PLAYER", true);
            startActivity(intent);
            finish();
        }
    }
}
