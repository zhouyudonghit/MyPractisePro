package com.example.localuser.retrofittest.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Environment;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 性能优化——Android图片压缩与优化的几种方式
 * https://blog.csdn.net/u013928412/article/details/80358597
 * 5、JNI调用JPEG库
 *  Android的图片引擎使用的是阉割版的skia引擎，去掉了图片压缩中的哈夫曼算法——一种耗CPU但是能在保持图片清晰度的情况下很大程度降低图片的磁盘空间大小的算法，这就是为什么ios拍出的1M的照片比Android5M的还要清晰的原因。
 * 6、还有一些技巧，比如在缩放压缩的时候，
 *  Bitmap.createBitmap(bitmap.getWidth() / radio, bitmap.getHeight() / radio, Bitmap.Config.ARGB_8888)，如果不需要图片的透明度，可以将ARGB_8888改成RGB_565，这样之前每个像素占用4个字节，现在只需要2个字节，节省了一半的大小。
 *
 *关于 Android 中 Bitmap 的 ARGB_8888、ALPHA_8、ARGB_4444、RGB_565 的理解
 *https://blog.csdn.net/tianjf0514/article/details/71698926
 *Bitmap.Config 有四种枚举类型
 * ARGB_8888：ARGB 四个通道的值都是 8 位，加起来 32 位，也就是 4 个字节。每个像素点占用 4 个字节的大小。
 * ARGB_4444：ARGB 四个通道的值都是 4 位，加起来 16 位，也就是 2 个字节。每个像素点占用 2 个字节的大小。
 * RGB_565：RGB 三个通道分别是 5 位、6 位、5 位，没有 A 通道，加起来 16 位，也就是 2 个字节。每个像素点占用 2 个字节的大小。
 * ALPHA_8：只有 A 通道，占 8 位，1 个字节。每个像素点占用 1 个字节的大小。
 *那到底用哪个呢？
 * 很明显 ARGB_8888 的图片是质量最高的，但也是占用内存最大的。
 *
 * 如果想要节省内存，可以使用 ARGB_4444 或者 RGB_565。它们相比 ARGB_8888 内存占用都小了一半。
 * ARGB_4444 图片的失真是比较严重的。
 * RGB_565 图片的失真虽然很小，但是没有透明度。
 *
 * ALPHA_8 只有透明度，没有颜色值。对于在图片上设置遮盖的效果的是有很有用。
 *
 * 总结一下！
 * ARGB_4444 图片失真严重，基本不用！
 * ALPHA_8 使用场景比较特殊！
 * 如果不设置透明度，RGB_565是个不错选择！
 * 既要设置透明度，对图片质量要求又高的化，那就用 ARGB_8888 !
 */
public class PictureUtils {
    /**
     * 质量压缩
     *质量压缩并不会改变图片在内存中的大小，仅仅会减小图片所占用的磁盘空间的大小，因为质量压缩不会改变图片的分辨率，而图片在内存中的大小是根据
     *  width*height*一个像素的所占用的字节数
     *  计算的，宽高没变，在内存中占用的大小自然不会变，质量压缩的原理是通过改变图片的位深和透明度来减小图片占用的磁盘空间大小，所以不适合作为缩略图，可以用于想保持图片质量的同时减小图片所占用的磁盘空间大小。另外，
     *  由于png是无损压缩，所以设置quality无效
     * @param format  图片格式 jpeg,png,webp
     * @param quality 图片的质量,0-100,数值越小质量越差
     */
    public static void compressByQuality(Bitmap.CompressFormat format, int quality) {
        File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(sdFile, "originImg.jpg");
        Bitmap originBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        originBitmap.compress(format, quality, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(sdFile, "resultImg.jpg"));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 采样率压缩
     *采样率压缩是通过设置BitmapFactory.Options.inSampleSize，来减小图片的分辨率，进而减小图片所占用的磁盘空间和内存大小。
     *
     *  设置的inSampleSize会导致压缩的图片的宽高都为1/inSampleSize，整体大小变为原始图片的inSampleSize平方分之一，当然，这些有些注意点：
     *
     *  1、inSampleSize小于等于1会按照1处理
     *
     *  2、inSampleSize只能设置为2的平方，不是2的平方则最终会减小到最近的2的平方数，如设置7会按4进行压缩，设置15会按8进行压缩。
     * @param inSampleSize  可以根据需求计算出合理的inSampleSize
     */
    public static void compressByInSampleSize(int inSampleSize) {
        File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(sdFile, "originImg.jpg");
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置此参数是仅仅读取图片的宽高到options中，不会将整张图片读到内存中，防止oom
        options.inJustDecodeBounds = true;
        Bitmap emptyBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap resultBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(sdFile, "resultImg.jpg"));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compress(View v) {
        File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(sdFile, "originImg.jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath());
        //设置缩放比
        int radio = 8;
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / radio, bitmap.getHeight() / radio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        RectF rectF = new RectF(0, 0, bitmap.getWidth() / radio, bitmap.getHeight() / radio);
        //将原图画在缩放之后的矩形上
        canvas.drawBitmap(bitmap, null, rectF, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(sdFile, "sizeCompress.jpg"));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
