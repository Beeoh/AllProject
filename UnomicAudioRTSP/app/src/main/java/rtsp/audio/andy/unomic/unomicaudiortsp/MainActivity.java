
package rtsp.audio.andy.unomic.unomicaudiortsp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends Activity {
    private MediaPlayer mPlayer;
    private MediaPlayer mSilentPlayer;
    private VisualizerView mVisualizerView;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate");
        dbHelper = new DBHelper(getApplicationContext(), "FREQUENCY.db", null, 1);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        init();
    }

    @Override
    protected void onPause() {
        Log.d("MainActivity", "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "oNDestroy");
        super.onDestroy();
    }

    private void init() {
        Log.d("MainActivity", "init");

        final ProgressDialog progressDialog;
        //String url = "rtsp://192.168.0.119:8000/1000.m4a";
        String url = "rtsp://192.168.0.114:8000/HeyJude.m4a";
        //String url = "rtsp://192.168.0.148/axis-media/media.amp?videocodec=mpeg4";

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Android Audio");
        progressDialog.setMessage("rtsp");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer player) {
                progressDialog.dismiss();
                player.start();
            }
        });
        mPlayer.prepareAsync();

        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
        mVisualizerView.link(mPlayer);
        addLineRenderer(dbHelper);
    }

    private void addLineRenderer(DBHelper dbHelper) {
        Log.d("MainActivity", "LineRender");
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.WHITE);
        //linePaint.setColor(Color.argb(88, 0, 128, 255));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(5f);
        lineFlashPaint.setAntiAlias(true);
        lineFlashPaint.setColor(Color.YELLOW);
        //lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, dbHelper);
        mVisualizerView.addRenderer(lineRenderer);
    }

    // Actions for buttons defined in xml
    public void startPressed(View view) throws IllegalStateException, IOException {
        Log.d("MainActivity", "startPressed");
        if (mPlayer.isPlaying()) {
            return;
        }
        mPlayer.prepare();
        mPlayer.start();
    }

    public void stopPressed(View view) {
        mPlayer.stop();
    }
}

