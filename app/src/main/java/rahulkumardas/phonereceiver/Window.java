package rahulkumardas.phonereceiver;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Rahul Kumar Das on 03-05-2017.
 */

public class Window extends Service {

    private WindowManager mWindowManager;
    private View rootView, button;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        rootView = LayoutInflater.from(this).inflate(R.layout.window_view, null);
        button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Window.this, "I was pressed", Toast.LENGTH_SHORT).show();
            }
        });

        //define layout parameters for the rootView
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //define wondow manager and add the rootView
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(rootView, params);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (CallReceiver.state==1){
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }
        }).start();
    }
}
