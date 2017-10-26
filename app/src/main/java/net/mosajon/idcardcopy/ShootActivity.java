package net.mosajon.idcardcopy;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class ShootActivity extends AppCompatActivity implements View.OnClickListener, ClipCamera.IAutoFocus {

    private ClipCamera camera;
    private Button btn_shoot;
    private Button btn_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoot);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        camera = (ClipCamera) findViewById(R.id.surface_view);
        btn_shoot = (Button) findViewById(R.id.btn_shoot);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_shoot.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (MainActivity.preJpeg.equals("tempa.jpg")) {
                btn_shoot.setBackground(getDrawable(R.drawable.sure1));
            }
            if (MainActivity.preJpeg.equals("tempb.jpg")) {
                btn_shoot.setBackground(getDrawable(R.drawable.sure2));
            }
            btn_cancle.setBackground(getDrawable(R.drawable.cancle));
        }

        camera.setIAutoFocus(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shoot:
                takePhoto();
                break;
            case R.id.btn_cancle:
                finish();
                break;
        }
    }

    public void takePhoto() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/IDCardCopy/" + MainActivity.preJpeg;
        camera.takePicture(path);
    }

    @Override
    public void autoFocus() {
        camera.setAutoFocus();
    }
}