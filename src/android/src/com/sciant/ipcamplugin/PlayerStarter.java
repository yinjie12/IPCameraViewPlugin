package com.sciant.ipcamplugin;


import android.content.Intent;
import android.util.Log;

public class PlayerStarter implements Runnable {

    private CameraPlayer cameraPlayer;
    private final String TAG = "SCNT_CAM_PLAY";

    public PlayerStarter(CameraPlayer cameraPlayer) {
        this.cameraPlayer = cameraPlayer;
    }

    @Override
    public void run() {
        Log.d(TAG, "PlayerStarter is running");
        boolean idle = true;
        while (idle) {
            if (cameraPlayer.isReady()) {
                idle = false;
                Intent intent = cameraPlayer.getIntent();
                String host = intent.getStringExtra("host");
                String portStr = intent.getStringExtra("port");
                int port = -1;
                try {
                    port = Integer.valueOf(portStr.trim());
                } catch (Exception e) {
                    Log.d(TAG, "Connection port is not an integer: " + port);
                }
                String user = intent.getStringExtra("user");
                String pass = intent.getStringExtra("pass");
                cameraPlayer.play(host, port, user, pass);
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
