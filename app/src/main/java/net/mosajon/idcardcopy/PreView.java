package net.mosajon.idcardcopy;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class PreView extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_view);

        String path = Environment.getExternalStorageDirectory().getPath() + "/IDCardCopy/" + MainActivity.preJpeg;
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}
