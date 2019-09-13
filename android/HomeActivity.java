package jk.cordova.plugin.kiosk;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.*;
import android.widget.*;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.this.startKioskActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeActivity.this.startKioskActivity();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //startKioskActivity();
        return true; // prevent event from being propagated
    }

    // http://www.andreas-schrade.de/2015/02/16/android-tutorial-how-to-create-a-kiosk-mode-in-android/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(false && !hasFocus) {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);

            ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            am.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);

            // sametime required to close opened notification area
            Timer timer = new Timer();
            timer.schedule(new TimerTask(){
                public void run() {
                    Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                    sendBroadcast(closeDialog);
                }
            }, 500); // 0.5 second
        }
    }

    private void startKioskActivity() {
        Intent serviceIntent = new Intent(this, KioskActivity.class);

        serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(serviceIntent);
    }
}
