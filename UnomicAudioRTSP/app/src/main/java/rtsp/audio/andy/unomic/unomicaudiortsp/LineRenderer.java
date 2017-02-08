package rtsp.audio.andy.unomic.unomicaudiortsp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LineRenderer extends Renderer {
    private Paint mPaint;
    DBHelper dbHelper;

    public LineRenderer(Paint paint, Paint flashPaint, DBHelper dbHelper) {
        super();
        mPaint = paint;
        this.dbHelper = dbHelper;
    }

    @Override
    public void onRender(Canvas canvas, AudioData data, Rect rect) {
        //Frequency Calculate
        int numSamples = data.bytes.length;
        int numCrossing = 0;
        for (int p = 0; p < numSamples - 1; p++)
            if ((data.bytes[p] > 0 && data.bytes[p + 1] <= 0) || (data.bytes[p] < 0 && data.bytes[p + 1] >= 0))
                numCrossing++;

        float numSecondsRecorded = (float) numSamples / (float) 44100;
        float numCycles = numCrossing / 2;
        float frequency = numCycles / numSecondsRecorded;
        int freq = (int) (numCycles / numSecondsRecorded);

        //Time Calculate
        String today = null;
        Date date = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, -3);   // 3초 전
        today = sdformat.format(cal.getTime());
        //System.out.println("변환후 : " + today);

        /*SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
        long now = System.currentTimeMillis();
        Date dates = new Date(now);
        String strNow = sdfNow.format(dates);
        System.out.println("변환전 : " + strNow);*/

        if (today.substring(20, 21).equals("5") || today.substring(20, 21).equals("0")) {
            Log.d("Now : " + today.substring(20, 21), today);
            System.out.println("Frequency : " + (int) frequency);
            dbHelper.insert("", today, freq);
        }
    }

    @Override
    public void onRender(Canvas canvas, FFTData Fdata, Rect rect) {

    }
}
