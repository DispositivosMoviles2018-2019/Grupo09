package ec.edu.uce.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class WebImageActivity extends AppCompatActivity {
    //private WebImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_image);
        /*
        imageView = (WebImageView) findViewById(R.id.webImage);
        imageView.setPlaceholderImage(R.mipmap.ic_launcher);
        imageView.setImageUrl("http://lorempixel.com/400/200");
        */
        Button boton = findViewById(R.id.btnCargar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(WebImageActivity.this).load("http://lorempixel.com/400/200")
                        .placeholder(R.drawable.ic_autorenew_black_24dp)
                        .error(R.drawable.ic_error_black_24dp)
                        .into(imageView);

            }
        });

    }
}
