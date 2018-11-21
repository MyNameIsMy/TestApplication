package projects.suchushin.org.testapplication.tasks;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.Random;

import projects.suchushin.org.testapplication.Game;
import projects.suchushin.org.testapplication.activities.GameActivity;

public class BotTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<GameActivity> contextHolder;
    private Random random;
    private Game game;

    public BotTask(WeakReference<GameActivity> contextHolder, Random random, Game game) {
        this.contextHolder = contextHolder;
        this.random = random;
        this.game = game;
    }

    protected Void doInBackground(Void... voids) {
        try {
            long time = getBangTimeForBotLevel();
            Thread.sleep(time);
            game.rightCowboyBangTime = game.timeToBang + time;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void aVoid) {
        contextHolder.get().bang();
    }

    private long getBangTimeForBotLevel(){
        switch (game.botLevel) {
            case "EASY" :
                return 250 + random.nextInt(100);
            case "MIDDLE" :
                return 150 + random.nextInt(75);
            case "HARD" :
                return 100 + random.nextInt(50);
            case "IMPOSSIBLE" :
                return 50 + random.nextInt(50);
        }
        return 200;
    }
}
