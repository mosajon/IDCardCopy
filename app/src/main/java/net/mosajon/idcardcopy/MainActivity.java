package net.mosajon.idcardcopy;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, MarkFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener {

    final FragmentManager fm = getFragmentManager();
    private HomeFragment homeFragment;
    private MarkFragment markFragment;
    private AboutFragment aboutFragment;
    public static String preJpeg, strThing, startDate, endDate;
    public static float fpostScale;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction transaction1 = fm.beginTransaction();
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                    }
                    transaction1.replace(R.id.content, homeFragment);
                    transaction1.commit();
                    return true;
                case R.id.navigation_dashboard:
                    FragmentTransaction transaction2 = fm.beginTransaction();
                    if (markFragment == null) {
                        markFragment = new MarkFragment();
                    }
                    transaction2.replace(R.id.content, markFragment);
                    transaction2.commit();
                    return true;
                case R.id.navigation_notifications:
                    FragmentTransaction transaction3 = fm.beginTransaction();
                    if (aboutFragment == null) {
                        aboutFragment = new AboutFragment();
                    }
                    transaction3.replace(R.id.content, aboutFragment);
                    transaction3.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // 设置默认的Fragment
        setDefaultFragment();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23);
        }
    }

    private void setDefaultFragment()
    {
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.content, homeFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri ) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 23) {
            for (int i = 0; i < permissions.length; i++) {
                String s = permissions[i];
                if (s.equals(Manifest.permission.CAMERA) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //setContentView(R.layout.activity_main);
                }
            }
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                  setContentView(R.layout.activity_main);
//                    initView();
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}