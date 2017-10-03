package com.example.asanaapp;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Usser on 15.11.2016.
 */
public class PracticeActivity extends AppCompatActivity implements MediaController.MediaPlayerControl{
    Timer timer;
    int page = 0;
    int m = 0;
    int interval;
    ArrayList<PoseObject> poses;
    ProgressBar pbar;
    TextView mus;
    private MediaPlayer mp;
    private MediaController mc;
    private Handler handler = new Handler();
    Music[] music;
    Random rand;
    String musname =  "http://youryoga.org/music/g_m/shalom_asalaam.mp3";
    boolean stopped = false;
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static int num_pages;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        poses = bundle.getParcelableArrayList("obj");
        num_pages = poses.size();
        rand = new Random();
        interval = 10;
        Backendless.Persistence.of( Music.class).find(new AsyncCallback<BackendlessCollection<Music>>(){
            @Override
            public void handleResponse( BackendlessCollection<Music> foundContacts )
            {

                music = new Music[foundContacts.getTotalObjects()];
                Iterator<Music> iterator = foundContacts.getCurrentPage().iterator();
                int i = 0;

                while (iterator.hasNext()) {
                    Music muse  = iterator.next();
                    music[i] = muse;
                    i++;
                }
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });


        mPager = (ViewPager) findViewById(R.id.pager);
        pbar = (ProgressBar)findViewById(R.id.progressBar);
        mus = (TextView)findViewById(R.id.practiceTV);
        rand = new Random();
        if(LoggedInActivity.musicSwitch) {
            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setLooping(true);
            mc = new MediaController(this);
            mc.setAnchorView(mus);
            mc.setMediaPlayer(PracticeActivity.this);
            try {
                mus.setText("Now Playing: " + "Shalom");
                mp.setDataSource(String.valueOf(musname));
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {
                    handler.post(new Runnable() {
                        public void run() {
                            mc.show();
                            mp.start();
                        }
                    });
                }
            });


            mc.setPrevNextListeners(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //next button is clicked
                    mp.reset();
                    int x = rand.nextInt(((3-1) +1) + 0);
                    String musfile = music[x].getMusicFile();
                    mus.setText("Now Playing: " + music[x].getName());
                    System.out.println(x  + " " + musfile);
                    try {
                        mp.setDataSource(String.valueOf(musfile));
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //previous button clicked
                    mp.reset();
                    int x = rand.nextInt(((3-1) +1) + 0);
                    String musfile = music[x].getMusicFile();
                    mus.setText("Now Playing: " + music[x].getName());
                    System.out.println(x  + " " + musfile);
                    try {
                        mp.setDataSource(String.valueOf(musfile));
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });} else {
                mus.setText("Music is off");
        }
            pbar.setMax(interval*num_pages);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            pageSwitcher(interval);
    }
    public void pageSwitcher(int seconds) {
        m++;

        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay

    }

    @Override
    public void start() {
        mp.start();
    }

    @Override
    public void pause() {
        if ( mp.isPlaying() )
            mp.pause();
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mp.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        int percentage = (mp.getCurrentPosition() * 100) / mp.getDuration();
        return percentage;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(LoggedInActivity.musicSwitch)
            mc.show();

        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(LoggedInActivity.musicSwitch) {
            mp.stop();
            mp.release();
            stopped = true;
        }

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > num_pages-1) { // In my case the number of pages are 5
                        timer.cancel();
                        // Showing a toast for just testing purpose

                        Toast.makeText(getApplicationContext(), "Congratulations! You have completed training",
                                Toast.LENGTH_LONG).show();
                        if(LoggedInActivity.musicSwitch && !stopped) {
                            mp.pause();
                            mp.seekTo(0);
                        }
                    } else {
                        mPager.setCurrentItem(page++);
                    }
                    pbar.setProgress(page*interval);
                }
            });

        }
    }
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.newInstance(position, poses);
        }

        @Override
        public int getCount() {
            return num_pages;
        }
    }
}
