package net.mosajon.idcardcopy;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageView mImageView01;
    private ImageView mImageView02;
    private Button mbutton;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        File destDir = new File(Environment.getExternalStorageDirectory().toString() + "/IDCardCopy");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mImageView01 = (ImageView) view.findViewById(R.id.mImageView01);
        mImageView02 = (ImageView) view.findViewById(R.id.mImageView02);
        mbutton = (Button) view.findViewById(R.id.button);

        mImageView01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mImageView01.setImageResource(R.drawable.surea);
                MainActivity.preJpeg = "tempa.jpg";
                Intent intent1 = new Intent(getActivity(), ShootActivity.class);
                startActivity(intent1);
            }
        });

        mImageView02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mImageView02.setImageResource(R.drawable.sureb);
                MainActivity.preJpeg = "tempb.jpg";
                Intent intent2 = new Intent(getActivity(), ShootActivity.class);
                startActivity(intent2);
            }
        });

        mbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mergeBitmap();
                    /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    //选择图片格式
                    intent.setType("image/*");
                    intent.putExtra("return-data",true);
                    startActivity(intent);*/
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        String patha = Environment.getExternalStorageDirectory().getPath() + "/IDCardCopy/tempa.jpg";
        String pathb = Environment.getExternalStorageDirectory().getPath() + "/IDCardCopy/tempb.jpg";
        File filea = new File(patha);
        File fileb = new File(pathb);

        if (filea.exists()) {
            Bitmap bma = BitmapFactory.decodeFile(patha);
            mImageView01.setImageBitmap(bma);
        }

        if (fileb.exists()) {
            Bitmap bmb = BitmapFactory.decodeFile(pathb);
            mImageView02.setImageBitmap(bmb);
        }
        super.onResume();
    }

    private void mergeBitmap() {
        String path1 = Environment.getExternalStorageDirectory().getPath() + "/IDCardCopy/tempa.jpg";
        String path2 = Environment.getExternalStorageDirectory().getPath() + "/IDCardCopy/tempb.jpg";
        File file1 = new File(path1);
        File file2 = new File(path2);
        if (file1.exists() && file2.exists()) {
            mImageView01.setDrawingCacheEnabled(true);
            mImageView02.setDrawingCacheEnabled(true);

            Bitmap rawBitmap1 = mImageView01.getDrawingCache();
            Bitmap rawBitmap2 = mImageView02.getDrawingCache();

            MainActivity.fpostScale = 720f / rawBitmap1.getHeight();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String p = decimalFormat.format(MainActivity.fpostScale);//format 返回的是字符串
            //Log.i("fpostScale", "previewSizeList size.width=" + rawBitmap1.getWidth() + "  size.height=" + rawBitmap1.getHeight()+p);

            if (Float.parseFloat(p) < 1.0f) {
                rawBitmap1 = small(rawBitmap1);
                rawBitmap2 = small(rawBitmap2);
            }

            try {
                Paint paint = new Paint();
                paint.setColor(Color.rgb(255, 255, 255));

                MainActivity.preJpeg = "IDCard_" + System.currentTimeMillis() + ".jpg";
                String path = Environment.getExternalStorageDirectory().getPath() + "/IDCardCopy/" + MainActivity.preJpeg;
                File file = new File(path);
                FileOutputStream out = new FileOutputStream(file);

                Bitmap bitmap = Bitmap.createBitmap((int) (rawBitmap1.getWidth() * 2.45), (int) (rawBitmap1.getHeight() * 5.55), rawBitmap1.getConfig());
                Canvas canvas = new Canvas(bitmap);
                canvas.drawRect(0, 0, (int) (rawBitmap1.getWidth() * 2.45), (int) (rawBitmap1.getHeight() * 5.55), paint);
                canvas.drawBitmap(rawBitmap1, ((int) (rawBitmap1.getWidth() * 2.45) - rawBitmap1.getWidth()) / 2, (int) (rawBitmap1.getHeight() * 5.55) / 2 - rawBitmap1.getHeight(), paint);
                canvas.drawBitmap(rawBitmap2, ((int) (rawBitmap1.getWidth() * 2.45) - rawBitmap1.getWidth()) / 2, (int) (rawBitmap1.getHeight() * 5.55) / 2, paint);

                if (MainActivity.strThing != null) {
                    if (!MainActivity.strThing.isEmpty()) {
                        paint.setTextSize(48 / MainActivity.fpostScale);
                        canvas.drawText(MainActivity.strThing, ((int) (rawBitmap1.getWidth() * 2.45) - rawBitmap1.getWidth()) / 2 + 20, (int) (rawBitmap1.getHeight() * 5.55) / 2 - rawBitmap1.getHeight() + 35, paint);
                    }
                }

                if (MainActivity.startDate != null && MainActivity.endDate != null) {

                    if (!MainActivity.startDate.isEmpty() && !MainActivity.endDate.isEmpty()) {
                        paint.setTextSize(36 / MainActivity.fpostScale);
                        canvas.drawText(getString(R.string.mark_checktv2) + " : " + MainActivity.startDate + " " + getString(R.string.mark_to) + " " + MainActivity.endDate, ((int) (rawBitmap1.getWidth() * 2.45) - rawBitmap1.getWidth()) / 2 + 20, (int) (rawBitmap1.getHeight() * 5.55) / 2 - 15, paint);
                    }
                }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                bitmap.recycle();
                // 通知图库更新
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

                Uri uri = Uri.fromFile(new File(path));
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                getActivity().sendBroadcast(intent);
                this.startActivity(intent.setClass(getActivity(), PreView.class));

                rawBitmap1.recycle();
                rawBitmap2.recycle();

                mImageView01.setDrawingCacheEnabled(false);
                mImageView02.setDrawingCacheEnabled(false);

                if (!TextUtils.isEmpty(path1)) {
                    Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    ContentResolver mContentResolver = getActivity().getContentResolver();
                    String where = MediaStore.Images.Media.DATA + "='" + path1 + "'";
                    //删除图片
                    mContentResolver.delete(mImageUri, where, null);

                    //发送广播
                    Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri1 = Uri.fromFile(file1);
                    intent.setData(uri);
                    getActivity().sendBroadcast(intent);
                }

                if (!TextUtils.isEmpty(path2)) {
                    Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    ContentResolver mContentResolver = getActivity().getContentResolver();
                    String where = MediaStore.Images.Media.DATA + "='" + path2 + "'";
                    //删除图片
                    mContentResolver.delete(mImageUri, where, null);

                    //发送广播
                    Intent intent2 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri2 = Uri.fromFile(file2);
                    intent.setData(uri);
                    getActivity().sendBroadcast(intent);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (file1.exists() && !file2.exists()) {
            if (!TextUtils.isEmpty(path1)) {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getActivity().getContentResolver();
                String where = MediaStore.Images.Media.DATA + "='" + path1 + "'";
                //删除图片
                mContentResolver.delete(mImageUri, where, null);

                //发送广播
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file1);
                intent.setData(uri);
                getActivity().sendBroadcast(intent);
            }
        } else if (!file1.exists() && file2.exists()) {
            if (!TextUtils.isEmpty(path2)) {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getActivity().getContentResolver();
                String where = MediaStore.Images.Media.DATA + "='" + path2 + "'";
                //删除图片
                mContentResolver.delete(mImageUri, where, null);

                //发送广播
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file2);
                intent.setData(uri);
                getActivity().sendBroadcast(intent);
            }
        }
    }

    //Bitmap放大的方法
    private static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    //Bitmap缩小的方法
    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(MainActivity.fpostScale, MainActivity.fpostScale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }
}
