package com.example.localuser.retrofittest.RxJava2Test;

import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJava2Test {
    private String TAG = LogConfigs.TAG_PREFIX_RXJAVA_TEST + getClass().getSimpleName();
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item: " + s);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }

        @Override
        public void onComplete() {

        }
    };

    Subscriber<String> subsriber = new Subscriber<String>() {
        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(String s) {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    };

    Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {
            Log.d(TAG,"subscribe");
            e.onNext("Hello");
            e.onNext("Hi");
            e.onNext("Aloha");
            e.onComplete();
        }
    }).subscribeOn(AndroidSchedulers.mainThread());

    public void test()
    {
//        observable.subscribe(new Consumer() {
//            @Override
//            public void accept(Object o) throws Exception {
//                Log.d(TAG,"o = " + o);
//                observer.onNext((String)o);
//            }
//        });
        Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                Log.d(TAG,"defer call");
                return observable;
            }
        }).subscribe(observer);
//        Observable.just("just test").subscribe(observer);
//        double d1 = 0.0252;
//        double d2 = 0.019600000000000003;
//        Log.d(TAG,"d = "+Double.valueOf("0.044800000000000003"));
    }

//    protected ArrayList<Double> a(Canvas canvas, LineGraphModel lineGraphModel, double d, double d2) {
//        float f = (((float) this.b) - this.g) + ((float) this.f);
//        float f2 = ((((float) this.b) - this.g) - ((float) this.e)) - this.g;
//        ArrayList<Double> arrayList = new ArrayList();
//        long millis = lineGraphModel.e.getMillis();
//        long millis2 = lineGraphModel.f.getMillis();
//        Calendar gregorianCalendar = new GregorianCalendar(TimeZone.getDefault());
//        gregorianCalendar.setTimeInMillis(millis);
//        //2019-09-04 20:20:30 443
//        int i = gregorianCalendar.get(Calendar.MILLISECOND) + ((gregorianCalendar.get(Calendar.MINUTE) * 60) * 1000);
//        int i2 = gregorianCalendar.get(Calendar.HOUR_OF_DAY) + 1;
//        //下面的循环j 的取值就是21点和22点整点的毫秒数
//        for (long j = (DateUtils.MILLIS_PER_HOUR - ((long) ((gregorianCalendar.get(Calendar.SECOND) * 1000) + i))) + millis; j < millis2; j += DateUtils.MILLIS_PER_HOUR) {
//            int i3;
//            if (i2 > 24) {
//                i3 = 1;
//            } else {
//                i3 = i2;
//            }
//            String format = String.format("%02d", new Object[]{Integer.valueOf(i3)});
//            float f3 = ((float) this.c) + ((float) (((double) (j - millis)) * d2));
//            canvas.drawText(format, f3, f, this.i);
//            canvas.drawLine(f3, (float) this.e, f3, ((float) this.e) + f2, this.m);
//            arrayList.add(Double.valueOf((double) f3));
//            i2 = i3 + 1;
//        }
//        return arrayList;
//    }
//
//    protected ArrayList<PointF> a(Canvas canvas, LineGraphModel lineGraphModel, boolean z, double d, double d2, double d3, ArrayList<Double> arrayList) {
//        ArrayList<PointF> arrayList2 = new ArrayList();
//        int[] iArr = new int[]{Color.parseColor("#006d7a"), Color.parseColor("#00527a")};
//        float f = ((((float) this.b) - this.g) - ((float) this.e)) - this.g;
//        Shader linearGradient = new LinearGradient(0.0f, (float) this.e, 0.0f, ((float) this.e) + f, new int[]{Color.parseColor("#006d7a"), Color.parseColor("#004b73")}, null, TileMode.CLAMP);
//        Shader linearGradient2 = new LinearGradient(0.0f, (float) this.e, 0.0f, ((float) this.e) + f, iArr, null, Shader.TileMode.CLAMP);
//        Paint paint = new Paint();
//        paint.setAntiAlias(false);
//        paint.setShader(linearGradient);
//        double d4 = (double) this.c;
//        int i = (int) d4;
//        double d5 = d4;
//        int i2 = 0;
//        int i3 = 0;
//        int i4 = i;
//        double d6 = 0.0d;
//        while (i3 < lineGraphModel.g.length) {
//            double d7 = (1.0d - lineGraphModel.g[i3]) * 0.95d;
//            if (d7 < 0.001d) {
//                d7 = 0.001d;
//            }
//            if (i3 < 5 && d7 > 0.1d) {
//                d7 = ((d7 * ((double) i3)) + (0.01d * ((double) (5 - i3)))) / 5.0d;
//            }
//            double d8 = ((double) f) * d7;
//            if (z) {
//                d7 = ((double) (this.b / 200)) * (((Math.sin((0.01d * d3) * 0.001d) * 8.0d) % 5.0d) * 0.4d);
//            } else {
//                d7 = d6;
//            }
//            d8 = (((double) this.e) + d8) + d7;
//            if (i3 > 0) {
//                int i5 = (int) d5;
//                int i6 = i4;
//                while (i6 <= i5) {
//                    canvas.drawLine((float) i6, (float) d8, (float) i6, ((float) this.e) + f, paint);
//                    i6++;
//                }
//                if (i2 < arrayList.size() && d5 > ((Double) arrayList.get(i2)).doubleValue()) {
//                    i2++;
//                    paint.setShader(i2 % 2 == 0 ? linearGradient : linearGradient2);
//                }
//                arrayList2.add(new PointF((float) d5, (float) d8));
//                i = i6;
//                i4 = i2;
//            } else {
//                i = i4;
//                i4 = i2;
//            }
//            d3 += d2;
//            d5 += d;
//            i3++;
//            i2 = i4;
//            i4 = i;
//            d6 = d7;
//        }
//        return arrayList2;
//    }
//
//    protected void a(Canvas canvas, LineGraphModel lineGraphModel) {
//        if (lineGraphModel != null && lineGraphModel.g != null) {
//            float f = (float) ((this.a - this.c) - this.d);
//            float length = f / ((float) lineGraphModel.g.length);
//            double timeIntervalInMillis = (double) lineGraphModel.e.getTimeIntervalInMillis(lineGraphModel.f);
//            double d = ((double) f) / timeIntervalInMillis;
//            double max = Math.max(1.0d, timeIntervalInMillis / ((double) lineGraphModel.g.length));
//            double millis = (double) lineGraphModel.e.getMillis();
//            float f2 = ((float) this.f) + (((float) this.b) - this.g);
//            float f3 = ((((float) this.b) - this.g) - ((float) this.e)) - this.g;
//            Resources resources = MainApplication.b().getResources();
//            canvas.drawText((String) resources.getText(2131165225), 0.0f, ((float) this.e) + this.g, this.h);
//            canvas.drawText((String) resources.getText(2131165223), 0.0f, ((float) this.e) + (0.45f * f3), this.h);
//            canvas.drawText((String) resources.getText(2131165238), 0.0f, (((float) this.e) + f3) - (this.g * 2.0f), this.h);
//            canvas.drawText((String) resources.getText(2131165470), 0.0f, (((float) this.e) + f3) - this.g, this.h);
//            canvas.drawText((String) resources.getText(2131165400), 0.0f, f2, this.h);
//            ArrayList a = a(canvas, lineGraphModel, max, d);
//            ArrayList a2 = a(canvas, lineGraphModel, true, (double) length, max, millis, a);
//            Shader linearGradient = new LinearGradient(0.0f, (float) this.e, 0.0f, ((float) this.e) + f3, new int[]{Color.parseColor("#00ecff"), Color.parseColor("#008af1")}, null, TileMode.CLAMP);
//            this.j.setAntiAlias(false);
//            this.j.setShader(linearGradient);
//            PointF pointF = new PointF(0.0f, 0.0f);
//            Iterator it = a2.iterator();
//            PointF pointF2 = pointF;
//            int i = 0;
//            while (it.hasNext()) {
//                pointF = (PointF) it.next();
//                if (!(pointF2.x == 0.0f && pointF2.y == 0.0f)) {
//                    canvas.drawLine(pointF2.x, pointF2.y, pointF.x, pointF.y, this.j);
//                }
//                if (i < a.size()) {
//                    double doubleValue = ((Double) a.get(i)).doubleValue();
//                    if (((double) pointF.x) > doubleValue) {
//                        i++;
//                        float f4 = 1.0f + ((float) doubleValue);
//                        canvas.drawLine(f4, (float) this.e, f4, ((float) this.e) + f3, this.l);
//                    }
//                }
//                pointF2 = pointF;
//                i = i;
//            }
//        }
//    }
}
