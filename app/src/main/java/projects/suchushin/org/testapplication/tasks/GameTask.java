package projects.suchushin.org.testapplication.tasks;

import android.os.AsyncTask;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Random;

import projects.suchushin.org.testapplication.R;
import projects.suchushin.org.testapplication.activities.GameActivity;

public class GameTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<GameActivity> contextHolder;
    private GameTask nextGameTask;
    private Random random;
    private int counter;

    public GameTask(WeakReference<GameActivity> contextHolder, Random random) {
        this.contextHolder = contextHolder;
        this.random = random;
    }

    private GameTask(WeakReference<GameActivity> contextHolder, Random random, int counter) {
        this.contextHolder = contextHolder;
        this.random = random;
        this.counter = counter;
    }

    protected Void doInBackground(Void... voids) {
        try {
            switch (counter){
                case 1:
                    Thread.sleep(2000);
                    break;
                case 2:
                    Thread.sleep(3000 + random.nextInt(3000));
                    break;
                case 3:
                    Thread.sleep(250);
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stopGame(){
        if (nextGameTask != null)
            nextGameTask.stopGame();
        if (!isCancelled())
            cancel(true);
    }

    protected void onPostExecute(Void aVoid) {
        if (counter == 0){
            nextGameTask = (GameTask) new GameTask(contextHolder, random, ++counter).execute();
            contextHolder.get().showDuelState(contextHolder.get().getResources().getString(R.string.ready));
            contextHolder.get().getLeftCowboyArea().bringToFront();
            contextHolder.get().getRightCowboyArea().bringToFront();
            contextHolder.get().getGameLayout().requestLayout();
            contextHolder.get().getGameLayout().invalidate();
        } else if (counter == 1){
            nextGameTask = (GameTask) new GameTask(contextHolder, random, ++counter).execute();
            contextHolder.get().showDuelState(contextHolder.get().getResources().getString(R.string.steady));
        } else if (counter == 2) {
            nextGameTask = (GameTask) new GameTask(contextHolder, random, ++counter).execute();
            contextHolder.get().getBangTextView().setVisibility(View.VISIBLE);
            contextHolder.get().timeToBang();
        } else {
            contextHolder.get().getBangTextView().setVisibility(View.INVISIBLE);
        }
    }
}
