package com.puyue.www.qiaoge.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.exoplayer2.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class Utils {

    public static void setClear(Context mContext) {
        SharedPreferencesUtil.saveString(mContext,"companyName","");
        SharedPreferencesUtil.saveString(mContext,"companyAddress","");
        SharedPreferencesUtil.saveString(mContext,"licenseNo","");
        SharedPreferencesUtil.saveInt(mContext,"companyType",-1);
        SharedPreferencesUtil.saveInt(mContext,"licenseLongTerm",-1);
        SharedPreferencesUtil.saveString(mContext,"licenseValidityStart","");
        SharedPreferencesUtil.saveString(mContext,"licenseValidityEnd","");
        SharedPreferencesUtil.saveString(mContext,"validity","");
        SharedPreferencesUtil.saveString(mContext,"businessUrl","");
        SharedPreferencesUtil.saveString(mContext,"licenseUrl","");
    }

    //营业执照
    public static void getLicense(Context mContext,String companyName,String companyAddress,String registerNum,int companyType, int licenseLongTerm,
                                  String licenseValidityStart,String licenseValidityEnd,String licensePath) {
        SharedPreferencesUtil.saveString(mContext,"companyName",companyName);
        SharedPreferencesUtil.saveString(mContext,"companyAddress",companyAddress);
        SharedPreferencesUtil.saveString(mContext,"licenseNo",registerNum);
        SharedPreferencesUtil.saveInt(mContext,"companyType",companyType);
        SharedPreferencesUtil.saveInt(mContext,"licenseLongTerm",licenseLongTerm);
        SharedPreferencesUtil.saveString(mContext,"licenseValidityStart",licenseValidityStart);
        SharedPreferencesUtil.saveString(mContext,"licenseValidityEnd",licenseValidityEnd);
        SharedPreferencesUtil.saveString(mContext,"licenseUrl",licensePath);
    }

    //许可证
    public static void getAllow(Context mContext,String validity,String businessUrl) {
        SharedPreferencesUtil.saveString(mContext,"validity",validity);
        SharedPreferencesUtil.saveString(mContext,"businessUrl",businessUrl);
    }



    public static Bitmap createTextImage(int width,int height,int txtSize,String innerTxt) {
        //若使背景为透明，必须设置为Bitmap.Config.ARGB_4444
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bm);

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(txtSize);

        //计算得出文字的绘制起始x、y坐标
        int posX = width/2 - txtSize*innerTxt.length()/2;
        int posY = height/2 - txtSize/2;

        canvas.drawText(innerTxt, posX, posY, paint);

        return bm;
    }

    public static float getPureDouble(String str) {
        if (str == null || str.length() == 0) return 0;
        float result = 0;
        try {
            Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");//如何提取带负数d ???
            Matcher matcher = compile.matcher(str);
            matcher.find();
            String string = matcher.group();//提取匹配到的结果
            result = Float.parseFloat(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] bmpToByteArray (Bitmap bitmap) {

        // 要返回的字符串
        byte[] reslut = null;

        ByteArrayOutputStream baos = null;

        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                /**
                 * 压缩只对保存有效果bitmap还是原来的大小
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();
                // 转换为字节数组
                reslut= baos.toByteArray();

            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return reslut;

    }

    public static void main(String[] args){

        test();
    }
    public static void test(){
        System.out.println(getPureDouble("12"));
        System.out.println(getPureDouble("wew3423.36"));
        System.out.println(getPureDouble("wewsf"));
        System.out.println(getPureDouble("000"));
        System.out.println(getPureDouble(null));
    }

    public static Integer toInteger(Object value) {
        try {
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof Number) {
                return ((Number) value).intValue();
            } else if (value instanceof CharSequence) {
                return Integer.valueOf(value.toString());
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

//    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
//        return bitmap == null ? null : new BitmapDrawable(anet.channel.util.Utils.getAppContext().getResources(), bitmap);
//    }



    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static void hiddenKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * long转date
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long currentTime, String formatType) throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * date转String
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * String 转date
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * long转string
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型

        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }




    public static boolean getTime(String date1, String date2) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");

        Date start = sdf.parse(date1);

        Date end = sdf.parse(date2);

        long cha = end.getTime() - start.getTime();

        if(cha<0){

            return false;

        }

        double result = cha * 1.0 / (1000 * 60 * 60);

        if(result<=24){

            return true;

        }else{

            return false;

        }

    }

}
