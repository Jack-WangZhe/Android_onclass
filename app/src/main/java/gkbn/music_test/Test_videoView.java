package gkbn.music_test;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import Defined.Utils;


public class Test_videoView extends AppCompatActivity implements View.OnClickListener{
    private VideoView videoView ;
    private Button start;
    private Button pause;
    private Button btnForward;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_video_view);
        //本地的视频  需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+"/fl1234.mp4" ;
        //网络视频
        String videoUrl2 = Utils.videoUrl ;
        Uri uri = Uri.parse( videoUrl2 );
        videoView = (VideoView)this.findViewById(R.id.videoView );
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));
        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());
        //设置视频路径
        videoView.setVideoURI(uri);
        //开始播放视频
        //videoView.start();
        start=(Button)findViewById(R.id.start);
        pause=(Button)findViewById(R.id.pause);
        btnForward=(Button)findViewById(R.id.btnForward);
        btnBack=(Button)findViewById(R.id.btnBack);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        btnForward.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start:
                videoView.start();
                break;
            case R.id.pause:
                videoView.pause();
                break;
            case R.id.btnForward:
                if(videoView.canSeekForward()){
                    int pos=videoView.getCurrentPosition();
                    videoView.seekTo(pos+10000);
                    Toast.makeText(this,"快进10秒",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnBack:
                if(videoView.canSeekBackward())
                {
                    int pos=videoView.getCurrentPosition();
                    if(pos>10000)
                    {
                        pos-=10000;
                    }
                    else{
                        pos=0;
                    }
                    videoView.seekTo(pos);
                    Toast.makeText(this,"回退10秒",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( Test_videoView.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
}
