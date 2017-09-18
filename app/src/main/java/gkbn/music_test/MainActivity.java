package gkbn.music_test;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity implements MediaPlayer.OnCompletionListener,View.OnClickListener{

    public MediaPlayer mediaPlayer=null;
    public Button btnStart1;
    public Button btnStop;
    public Button btnPause;
    public Button btnRecord;
    public Button btnRecord_stop;
    public Button btnDelete;
    public File recordFile;
    public MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if(getSupportActionBar()!=null)
//        {
//            getSupportActionBar().hide();
//        }
//        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS, R.layout.progress);
//        setProgressBarIndeterminateVisibility(true);

//        requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//        getWindow().setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON,R.mipmap.ic_launcher);
        btnStart1=(Button)findViewById(R.id.btnStart1);
        btnStop=(Button)findViewById(R.id.btnStop);
        btnPause=(Button)findViewById(R.id.btnPause);
        btnRecord=(Button)findViewById(R.id.record);
        btnRecord_stop=(Button)findViewById(R.id.record_stop);
        btnDelete=(Button)findViewById(R.id.delete);
        btnStart1.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnRecord_stop.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
        Toast.makeText(this,"音乐已播放！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnStart1:
                    //mediaPlayer = MediaPlayer.create(this，R.raw.times);
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setDataSource(recordFile.getAbsolutePath());
                    //网络获取
                    //mediaPlayer.setDataSource("地址");
                    mediaPlayer.setOnCompletionListener(MainActivity.this);
//                    if (mediaPlayer!=null)
//                    {
//                        mediaPlayer.stop();
//                    }
//                    else{
//                        Toast.makeText(this,"没音频",Toast.LENGTH_SHORT).show();
//                    }
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    break;

                case R.id.btnStop:
                    if (mediaPlayer!=null)
                    {
                        mediaPlayer.stop();
                    }
                    break;

                case R.id.btnPause:
                    if(mediaPlayer !=null)
                    {
                        if("播放".equals(btnPause.getText().toString()))
                        {
                            mediaPlayer.start();
                            btnPause.setText("暂停");
                        }
                        else if("暂停".equals(btnPause.getText().toString()))
                        {
                            mediaPlayer.pause();
                            btnPause.setText("播放");
                        }
                    }
                    break;
                case R.id.record:
                    recordFile=File.createTempFile("record",".amr");
                    mediaRecorder=new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    Toast.makeText(this,"语音录制开始",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.record_stop:
                    if(mediaRecorder!=null)
                    {
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder=null;
                        Toast.makeText(this,"语音录制停止",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.delete:
                    recordFile.delete();
                    Toast.makeText(this,"音频删除",Toast.LENGTH_SHORT).show();
                    break;

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
