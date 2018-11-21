package projects.suchushin.org.testapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.suchushin.org.testapplication.R;

public class GameMenuActivity extends Activity{
    @BindView(R.id.one_player_button)
    Button onePlayerButton;
    @BindView(R.id.two_players_button)
    Button twoPlayersButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu_layout);
        ButterKnife.bind(this);

        onePlayerButton.setOnClickListener((v) -> startNextActivity(true));
        twoPlayersButton.setOnClickListener((v) -> startNextActivity(false));
    }

    public void startNextActivity(boolean isSinglePlayer){
        Intent intent = isSinglePlayer ? new Intent(this, LevelChooseActivity.class) : new Intent(this, GameActivity.class).putExtra("SINGLE_PLAYER", isSinglePlayer);
        startActivity(intent);
    }
}
