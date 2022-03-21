package com.ushaqi.zhuishushenqi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.util.DensityUtil;

/**
 * @author chengwencan
 * @date 2021/7/12
 * Describe：
 */
public class AppGuideDialog extends Dialog implements View.OnClickListener{

    private ImageView mIvGuideStep1, mLLSkipGuide;
    private Button mBtnGuideNext;
    private RelativeLayout mRlGuideBackground;
    private VideoView mVvGuide;
    private RelativeLayout mLayoutVideo, mLayoutVideoMute;
    private ImageView mImageViewVideoVoice;

    private MediaPlayer mMediaPlayer;

    private boolean mVideoIsAudio = true;

    public AppGuideDialog(Context context ) {
        super(context,R.style.Theme_Triver_Activity_FullScreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        }
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_guide_app);
        mRlGuideBackground=findViewById(R.id.rl_guide_background);


       mIvGuideStep1= findViewById(R.id.iv_welcome_step1);
       mLLSkipGuide= findViewById(R.id.ll_skip_guide);
        mVvGuide = findViewById(R.id.iv_welcome_video);
        mBtnGuideNext = findViewById(R.id.btn_guide_next);
        mLayoutVideo = findViewById(R.id.layout_welcome_video);
        mLayoutVideoMute = findViewById(R.id.layout_guide_video_mute);
        mImageViewVideoVoice =findViewById(R.id.image_guide_video_mute);

        mBtnGuideNext.setOnClickListener(this);
       mLLSkipGuide.setOnClickListener(this);
        mLayoutVideoMute.setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_guide_next){
            mIvGuideStep1.setVisibility(View.GONE);
            mBtnGuideNext.setVisibility(View.GONE);
            mLayoutVideo.setVisibility(View.VISIBLE);
            playVideo();
        } else if (v.getId() == R.id.layout_guide_video_mute) {
            if (mMediaPlayer != null) {
                if (mVideoIsAudio) {
                    mMediaPlayer.setVolume(0f, 0f);
                    mImageViewVideoVoice.setImageResource(R.drawable.icon_guide_video_mute);
                    mVideoIsAudio = false;
                } else {
                    mMediaPlayer.setVolume(1f, 1f);
                    mImageViewVideoVoice.setImageResource(R.drawable.icon_guide_video_audio);
                    mVideoIsAudio = true;
                }
            }
        } else if (v.getId()==R.id.ll_skip_guide){
            if (isShowing()) {
                dismiss();
            }
        }
    }

    public void playVideo() {
        String url="https://statics.zhuishushenqi.com/ttyy/image/courses.mp4";
        mVvGuide.setVideoPath(url);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(285),DensityUtil.dp2px(506));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mVvGuide.setLayoutParams(layoutParams);

        mVvGuide.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer = mp;
            }
        });

        mVvGuide.requestFocus();
        mVvGuide.start();
    }
}
