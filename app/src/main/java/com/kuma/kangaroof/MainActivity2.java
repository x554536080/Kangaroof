package com.kuma.kangaroof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import com.kuma.kumamedia.player.media.IjkPlayerView;

public class MainActivity2 extends AppCompatActivity {

    private IjkPlayerView mPlayerView;
    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/nNTov5571/SD/nNTov5571-mobile.mp4";
    private static final String VIDEO_HD_URL = "http://flv2.bn.netease.com/videolib3/1611/28/nNTov5571/SD/nNTov5571-mobile.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mPlayerView = findViewById(R.id.player_view);
        mPlayerView.init()
                .setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
                .setSkipTip(1000 * 60 * 1)
                .enableDanmaku()
                .setVideoSource(null, VIDEO_URL, VIDEO_HD_URL, null, null)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPlayerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

}