package ec.edu.uce.appnotifybackground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class NotificationActivity extends AppCompatActivity {
    private RadioGroup mOptionsGroup;
    //String id = "channelNot";
    //int importance = NotificationManager.IMPORTANCE_LOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mOptionsGroup = (RadioGroup) findViewById(R.id.options_group);
    }

    public void onPostClick(View v) {
        final int noteId = mOptionsGroup.getCheckedRadioButtonId();
        final Notification note;
        switch (noteId) {
            case R.id.option_basic:
            case R.id.option_bigtext:
                note = buildStyleNotification(noteId);
                break;

            default:
                throw new IllegalArgumentException("Tipo desconocido");
        }
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      //  NotificationChannel channel = new NotificationChannel(id,"canal",importance);
        manager.notify(noteId,note);

    }

    private Notification buildStyleNotification(int type){
        Intent launchIntent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, launchIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this);
        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("Algo sucedió")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle("FINALIZADO!")
                .setContentText("Click here!")
                .setContentIntent(contentIntent);

        switch (type){
            case R.id.option_basic:
                return builder.build();

            case R.id.option_bigtext:
                builder.addAction(android.R.drawable.ic_menu_call,"Llamada", contentIntent);
                builder.addAction(android.R.drawable.ic_menu_recent_history, "History",contentIntent);

                NotificationCompat.BigTextStyle textStyle = new NotificationCompat.BigTextStyle(builder);
                textStyle.bigText("Texto adicional a mostrar cuando se muestra la notificación en modo expandible, se puede visualizar mucha más información de esta manera!");
                return textStyle.build();




            default:
                throw new IllegalArgumentException("Tipo Desconocido");

        }

    }

}
