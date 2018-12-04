package ec.edu.uce.vista;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class RecordActivity extends AppCompatActivity {

    private MediaRecorder recorder;
    private Button start, stop, play;
    String archivoSalida = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        play = (Button) findViewById(R.id.playButton);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RecordActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
        // start.setOnClickListener(startListener);
        // stop.setOnClickListener(stopListener);

        //  recorder = new MediaRecorder();
        //  path = new File(Environment.getExternalStorageDirectory(), "grabacion.mp3");
        //  resetRecorder();
    }

    /*
        @Override
        protected void onDestroy() {
            super.onDestroy();
            recorder.release();
        }
    */
    public void recorder(View view) {
        if (recorder == null) {
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiograbado.mp3";
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setOutputFile(archivoSalida);
            try {
                recorder.prepare();
                recorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            start.setEnabled(false);
            stop.setEnabled(true);
            play.setEnabled(false);
            Toast.makeText(this, "Grabando...", Toast.LENGTH_SHORT).show();

        }

    }

    public void detenerAudio(View view){
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            start.setEnabled(true);
            stop.setEnabled(false);
            play.setEnabled(true);
            Toast.makeText(this, "Grabacion Finalizada", Toast.LENGTH_SHORT).show();
        }


    }

    public void reproducirAudio(View view) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(archivoSalida);
            mediaPlayer.prepare();

        } catch (Exception e) {

        }
        mediaPlayer.start();
        Toast.makeText(this, "Reproduciendo Audio", Toast.LENGTH_SHORT).show();

    }
}
