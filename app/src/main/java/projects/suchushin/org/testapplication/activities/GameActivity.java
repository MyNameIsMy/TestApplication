package projects.suchushin.org.testapplication.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.suchushin.org.testapplication.Game;
import projects.suchushin.org.testapplication.R;
import projects.suchushin.org.testapplication.tasks.BotTask;
import projects.suchushin.org.testapplication.tasks.GameTask;

public class GameActivity extends Activity {
    Game game = new Game();
    Random random = new Random();
    GameTask gameTask;
    @BindView(R.id.right_cowboy_area)
    RelativeLayout rightCowboyArea;
    @BindView(R.id.left_cowboy_area)
    RelativeLayout leftCowboyArea;
    @BindView(R.id.right_cowboy_ready_button)
    Button rightCowboyReadyButton;
    @BindView(R.id.left_cowboy_ready_button)
    Button leftCowboyReadyButton;
    @BindView(R.id.back_button)
    Button backButton;
    @BindView(R.id.repeat_button)
    Button repeatButton;
    @BindView(R.id.right_cowboy)
    ImageView rightCowboy;
    @BindView(R.id.left_cowboy)
    ImageView leftCowboy;
    @BindView(R.id.bang_text_view)
    TextView bangTextView;
    @BindView(R.id.game_layout)
    RelativeLayout gameLayout;
    @BindView(R.id.game_end_block)
    LinearLayout gameEndBlock;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        ButterKnife.bind(this);

        game.isSinglePlayer = getIntent().getExtras().getBoolean("SINGLE_PLAYER");

        backButton.setOnClickListener((v) -> {
            finish();
        });
        repeatButton.setOnClickListener((v) -> {
            startActivity(getIntent());
            finish();
        });

        leftCowboyReadyButton.setOnClickListener((v) -> {
            game.leftCowboyReady = true;
            leftCowboyReadyButton.setVisibility(View.INVISIBLE);
            startGame();
        });

        if (game.isSinglePlayer){
            game.rightCowboyReady = true;
            game.botLevel = getIntent().getExtras().getString("LEVEL");
            rightCowboyReadyButton.setVisibility(View.INVISIBLE);
            startGame();
        } else {
            rightCowboyReadyButton.setOnClickListener((v) -> {
                game.rightCowboyReady = true;
                rightCowboyReadyButton.setVisibility(View.INVISIBLE);
                startGame();
            });
        }
    }

    private void startGame(){
        if (game.leftCowboyReady && game.rightCowboyReady){
            gameTask = new GameTask(new WeakReference<>(this), random);
            gameTask.execute();
            leftCowboyArea.setOnClickListener((v) -> {
                if (game.leftCowboyBangTime == 0) {
                    game.leftCowboyBangTime = System.currentTimeMillis();
                    bang();
                }
            });
            if (!game.isSinglePlayer)
                rightCowboyArea.setOnClickListener((v) -> {
                    if (game.rightCowboyBangTime == 0) {
                        game.rightCowboyBangTime = System.currentTimeMillis();
                        bang();
                    }
                });
        }
    }

    public void showDuelState(String message){
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setBackgroundColor(getResources().getColor(R.color.transparent));

        toast.setView(textView);
        toast.show();
    }

    public void timeToBang(){
        game.bangPossibility = true;
        game.timeToBang = System.currentTimeMillis();

        if (game.isSinglePlayer){
            new BotTask(new WeakReference<>(this), random, game).execute();
        }
    }

    public void bang(){
        if (!game.bangPossibility) {
            if(gameTask != null)
                gameTask.stopGame();
            if (game.leftCowboyBangTime > game.rightCowboyBangTime)
                rightCowboyWin();
            else if (game.leftCowboyBangTime < game.rightCowboyBangTime)
                leftCowboyWin();
        } else if (game.leftCowboyBangTime == 0 || game.rightCowboyBangTime == 0){
            if (game.leftCowboyBangTime > game.rightCowboyBangTime)
                leftCowboyWin();
            else if (game.leftCowboyBangTime < game.rightCowboyBangTime)
                rightCowboyWin();
        }
        bangTextView.setVisibility(View.VISIBLE);
        gameEndBlock.setVisibility(View.VISIBLE);
        gameEndBlock.bringToFront();
        gameLayout.requestLayout();
        gameLayout.invalidate();
    }

    private void leftCowboyWin(){
        leftCowboy.setBackground(getDrawable(R.drawable.cowboy_bang_left));
        rightCowboy.setBackground(getDrawable(R.drawable.cowboy_dead_right));
        bangTextView.setText(R.string.left_cowboy_win);
    }

    private void rightCowboyWin(){
        leftCowboy.setBackground(getDrawable(R.drawable.cowboy_dead_left));
        rightCowboy.setBackground(getDrawable(R.drawable.cowboy_bang_right));
        bangTextView.setText(R.string.right_cowboy_win);
    }

    public RelativeLayout getGameLayout() {
        return gameLayout;
    }

    public TextView getBangTextView() {
        return bangTextView;
    }

    public RelativeLayout getRightCowboyArea() {
        return rightCowboyArea;
    }

    public RelativeLayout getLeftCowboyArea() {
        return leftCowboyArea;
    }
}
