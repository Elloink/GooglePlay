package com.qq.googleplay;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.qq.googleplay.android.log.Log;
import com.qq.googleplay.ui.activity.MainActivity;
import com.qq.googleplay.ui.widget.ProgressButton;

public class ProgressButtonActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ProgressButton mAnimDownloadProgressButton;
    Button         mReset;
    TextView       mDescription;
    SeekBar        mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnimDownloadProgressButton.setCurrentText("安装");
        mAnimDownloadProgressButton.setTextSize(60f);
        mAnimDownloadProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTheButton();
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimDownloadProgressButton.setState(ProgressButton.NORMAL);
                mAnimDownloadProgressButton.setCurrentText("安装");
                mAnimDownloadProgressButton.setProgress(0);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAnimDownloadProgressButton.setButtonRadius((progress / 100.0f) * mAnimDownloadProgressButton.getHeight() / 2);
                mAnimDownloadProgressButton.postInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mDescription.setText(" This is a DownloadProgressButton library with Animation," +
                "you can change radius,textColor,coveredTextColor,BackgroudColor,etc in" +
                " your code or just in xml.\n\n" +
                "The library is open source in github https://github.com/cctanfujun/ProgressRoundButton .\n" +
                "Hope you like it ");
    }

    private void showTheButton() {
        mAnimDownloadProgressButton.setState(ProgressButton.DOWNLOADING);
        mAnimDownloadProgressButton.setProgressText("下载中", mAnimDownloadProgressButton.getProgress() + 8);
        
        Log.d(TAG, "showTheButton: " + mAnimDownloadProgressButton.getProgress());
        
        
        if (mAnimDownloadProgressButton.getProgress() + 10 > 100) {
            mAnimDownloadProgressButton.setState(ProgressButton.INSTALLING);
            mAnimDownloadProgressButton.setCurrentText("安装中");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mAnimDownloadProgressButton.setState(ProgressButton.NORMAL);
                    mAnimDownloadProgressButton.setCurrentText("打开");
                }
            }, 2000);   //2秒
        }
    }


}