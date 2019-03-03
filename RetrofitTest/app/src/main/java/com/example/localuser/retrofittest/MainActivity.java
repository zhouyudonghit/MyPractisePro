package com.example.localuser.retrofittest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.localuser.retrofittest.AnimatorTest.AnimatorTestActivity;
import com.example.localuser.retrofittest.AsyncTaskTest.AsyncTaskTestActivity;
import com.example.localuser.retrofittest.Canvas.CanvasActivity;
import com.example.localuser.retrofittest.DialogView.DialogActivity;
import com.example.localuser.retrofittest.DragSortListView.DslvTestActivity;
import com.example.localuser.retrofittest.DrawLayout.DrawLayoutActivity;
import com.example.localuser.retrofittest.Drawable.DrawableActivity;
import com.example.localuser.retrofittest.Glide.GlideTestActivity;
import com.example.localuser.retrofittest.HandlerTest.HandlerTestActivity;
import com.example.localuser.retrofittest.JobSchedulerTest.JobSchedulerTestActivity;
import com.example.localuser.retrofittest.JsonTest.JsonTestActivity;
import com.example.localuser.retrofittest.MemoryLeak.MemoryLeakActivity;
import com.example.localuser.retrofittest.MergeTest.MergeTestActivity;
import com.example.localuser.retrofittest.MotionEventTest.MotionEventTestActivity;
import com.example.localuser.retrofittest.NoneMainThreadOpUI.NoneMainThreadOpUIActivity;
import com.example.localuser.retrofittest.NotificationTest.NotificationTestActivity;
import com.example.localuser.retrofittest.OKhttpTest.OKHttpTestActivity;
import com.example.localuser.retrofittest.PullRefreshListView.PullRefreshActivity;
import com.example.localuser.retrofittest.PullRefreshRecyclerView.PullRefreshRecyclerViewActivity;
import com.example.localuser.retrofittest.ShareAnimator.ShareAnimatorActivity;
import com.example.localuser.retrofittest.SmallMethodTest.SmallMethodTestActivity;
import com.example.localuser.retrofittest.SocketTest.SocketTestActivity;
import com.example.localuser.retrofittest.TimerTest.TimerTestActivity;
import com.example.localuser.retrofittest.Toolbar.ToolbarActivity;
import com.example.localuser.retrofittest.View.MyViewActivity;
import com.example.localuser.retrofittest.service.ServiceTestActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18,tv19,tv20,tv21,tv22,tv23;
    private TextView tv24,tv25,tv26,tv27,tv28;
    public static String TAG = "retrofit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("activityA","onCreate");
        //test3();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activityA","onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("activityA","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activityA","onResume");
        getPackageManager();
        tv1 = (TextView) findViewById(R.id.toolbar_activity);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
            }
        });

        tv2 = (TextView) findViewById(R.id.drawlayout_activity);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DrawLayoutActivity.class));
            }
        });

        tv3 = (TextView) findViewById(R.id.canvas_activity);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CanvasActivity.class));
            }
        });

        tv4 = (TextView) findViewById(R.id.myview_activity);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyViewActivity.class));
            }
        });

        tv5 = (TextView) findViewById(R.id.animatortest_activity);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimatorTestActivity.class));
            }
        });

        tv6 = (TextView) findViewById(R.id.pullrefersh_activity);
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PullRefreshActivity.class));
            }
        });

        tv7 = (TextView) findViewById(R.id.motioneventtest_activity);
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MotionEventTestActivity.class));
            }
        });

        tv8 = (TextView) findViewById(R.id.dialog_activity);
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
            }
        });

        tv9 = (TextView) findViewById(R.id.pullrefresh_recyclerview_activity);
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PullRefreshRecyclerViewActivity.class));
            }
        });

        tv10 = (TextView) findViewById(R.id.drawable_activity);
        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DrawableActivity.class));
            }
        });

        tv11 = (TextView) findViewById(R.id.bluetooth_activity);
        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv12 = (TextView) findViewById(R.id.share_animator_activity);
        tv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShareAnimatorActivity.class));
            }
        });

        tv13 = (TextView) findViewById(R.id.merge_test_activity);
        tv13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MergeTestActivity.class));
            }
        });

        tv14 = (TextView) findViewById(R.id.service_test_activity);
        tv14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ServiceTestActivity.class));
            }
        });

        tv15 = (TextView) findViewById(R.id.jobscheduler_test_activity);
        tv15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JobSchedulerTestActivity.class));
            }
        });

        tv16 = (TextView) findViewById(R.id.nonemainthread_test_activity);
        tv16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NoneMainThreadOpUIActivity.class));
            }
        });

        tv17 = (TextView) findViewById(R.id.dslv_test_activity);
        tv17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DslvTestActivity.class));
            }
        });

        tv18 = (TextView) findViewById(R.id.smallmethod_test_activity);
        tv18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SmallMethodTestActivity.class));
            }
        });

        tv19 = findViewById(R.id.glide_test_activity);
        tv19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GlideTestActivity.class));
            }
        });

        tv20 = findViewById(R.id.json_test_activity);
        tv20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JsonTestActivity.class));
            }
        });

        tv22 = findViewById(R.id.handler_test_activity);
        tv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HandlerTestActivity.class));
            }
        });

        tv23 = findViewById(R.id.timer_test_activity);
        tv23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TimerTestActivity.class));
            }
        });

        tv24 = findViewById(R.id.notifiation_test_activity);
        tv24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotificationTestActivity.class));
            }
        });

        tv25 = findViewById(R.id.asynctask_test_activity);
        tv25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AsyncTaskTestActivity.class));
            }
        });

        tv26 = findViewById(R.id.memoryleak_test_activity);
        tv26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MemoryLeakActivity.class));
            }
        });

        tv27 = findViewById(R.id.socket_test_activity);
        tv27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SocketTestActivity.class));
            }
        });
        tv28 = findViewById(R.id.okhttp_test_activity);
        tv28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OKHttpTestActivity.class));
            }
        });
//        BootCompletedReceiver receiver = new BootCompletedReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
//        registerReceiver(receiver,intentFilter);
        Exception e = new Exception("test test");
        e.printStackTrace();
    }

    public void test()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://baidu.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<Translation> call = request.getCall();
        call.enqueue(new Callback<Translation>(){

            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                 response.body();
                Translation tr = new Translation();
                Translation.Content content = tr.new Content();
                content.test2();
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });
    }

    public void test2()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://fanyi.youdao.com/").addConverterFactory(GsonConverterFactory.create()).build();
        GetRequest_Interface request_interface = retrofit.create(GetRequest_Interface.class);
        Call<Translation1> call = request_interface.getCall("I love you");
        call.enqueue(new Callback<Translation1>() {
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                response.body();
                Toast.makeText(MainActivity.this,"UI Thread",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Translation1> call, Throwable t) {

            }
        });
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "test");
        Log.d("","");
    }

    public void test3()
    {
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        GetRequestRxjava_Interface request_interface = retrofit.create(GetRequestRxjava_Interface.class);
//        request_interface.getTopMovie(0,10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Movie>() {
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Movie movie) {
//                        Log.d(TAG, "onNext: " + movie.getTitle());
//                        List<Subjects> list = movie.getSubjects();
//                        for (Subjects sub : list) {
//                            Log.d(TAG, "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
//                        }
//                    }
//                });
    }

//    public void execute(Runnable command) {
//        if (command == null)
//            throw new NullPointerException();
//        /*
//         * 如果少于corePoolSize个数的thread正在运行，尝试启动一个新的线程带着给定的command作为这个线程的第一个任务。
//         * 调用addWorker方法自动地检查runState和workerCount，并且预防假的当不应该增加线程的时候增加线程的警报，
//         * 通过返回false。如果一个任务能够成功地排队，我们仍需要二次检查我们是否需要增加一个新的thread
//         * （因为自从上次检查存在的线程可能死了）或者线程池自从我们进入这个方法是否关闭了。
//         * 所以我们重新检查状态并且如果必要重新排队或者启动一个新线程如果一个线程也没有的话。如果我们不能把
//         * 任务入队，我们尝试增加一个新的线程。如果失败，我们知道我们被关闭了或者饱和了并且拒绝这个任务。
//         */
//        int c = ctl.get();
//        if (workerCountOf(c) < corePoolSize) {
//            //如果工作线程数量小于核心线程数，那么调用addWorker方法，第二个参数传true
//            if (addWorker(command, true))
//                return;
//            //addWorker方法返回false，那么重新获取线程池状态
//            c = ctl.get();
//        }
//
//        //能走到这一步，说明工作线程数超过了核心线程数，或者是addworker方法返回false，那么把该任务添加到工作队列后面
//        if (isRunning(c) && workQueue.offer(command)) {
//            //如果线程池正在运行并且把任务成功添加到任务队列末尾
//            int recheck = ctl.get();
//            if (! isRunning(recheck) && remove(command))
//                reject(command);
//            else if (workerCountOf(recheck) == 0)
//                addWorker(null, false);
//        }
//        //如果任务队列满了，那么创建新的线程
//        else if (!addWorker(command, false))
//            reject(command);
//    }
//
//    private boolean addWorker(Runnable firstTask, boolean core) {
//        retry:
//        //下面的for循环用来检查是否能够创建新的工作线程
//        for (;;) {
//            int c = ctl.get();
//            int rs = runStateOf(c);
//
//            // Check if queue empty only if necessary.
//            if (rs >= SHUTDOWN &&
//                    ! (rs == SHUTDOWN &&
//                            firstTask == null &&
//                            ! workQueue.isEmpty()))
//                return false;
//
//            for (;;) {
//                int wc = workerCountOf(c);
//                if (wc >= CAPACITY ||
//                        wc >= (core ? corePoolSize : maximumPoolSize))//这里面会检查当前工作线程数量是否超过限限制
//                    return false;
//                if (compareAndIncrementWorkerCount(c))//工作线程数自增1，跳出循环
//                    break retry;
//                c = ctl.get();  // Re-read ctl
//                if (runStateOf(c) != rs)
//                    continue retry;
//                // else CAS failed due to workerCount change; retry inner loop
//            }
//        }
//
//        boolean workerStarted = false;
//        boolean workerAdded = false;
//        Worker w = null;
//        //下面是创建新的worker过程，并启动线程
//        try {
//            w = new Worker(firstTask);
//            final Thread t = w.thread;
//            if (t != null) {
//                final ReentrantLock mainLock = this.mainLock;
//                mainLock.lock();
//                try {
//                    // Recheck while holding lock.
//                    // Back out on ThreadFactory failure or if
//                    // shut down before lock acquired.
//                    int rs = runStateOf(ctl.get());
//
//                    if (rs < SHUTDOWN ||
//                            (rs == SHUTDOWN && firstTask == null)) {
//                        if (t.isAlive()) // precheck that t is startable
//                            throw new IllegalThreadStateException();
//                        //把新工作线程添加到工作线程队列
//                        workers.add(w);
//                        int s = workers.size();
//                        if (s > largestPoolSize)
//                            largestPoolSize = s;
//                        workerAdded = true;
//                    }
//                } finally {
//                    mainLock.unlock();
//                }
//                if (workerAdded) {
//                    //启动新的工作线程
//                    t.start();
//                    workerStarted = true;
//                }
//            }
//        } finally {
//            if (! workerStarted)
//                addWorkerFailed(w);
//        }
//        return workerStarted;
//    }
//
//    private final class Worker
//            extends AbstractQueuedSynchronizer
//            implements Runnable
//    {
//        public void run() {
//            //调用外部类的runWorker方法
//            runWorker(this);
//        }
//    }
//
//    final void runWorker(Worker w) {
//        Thread wt = Thread.currentThread();
//        Runnable task = w.firstTask;
//        w.firstTask = null;
//        w.unlock(); // allow interrupts
//        boolean completedAbruptly = true;
//        try {
//            while (task != null || (task = getTask()) != null) {
//                w.lock();
//                // If pool is stopping, ensure thread is interrupted;
//                // if not, ensure thread is not interrupted.  This
//                // requires a recheck in second case to deal with
//                // shutdownNow race while clearing interrupt
//                if ((runStateAtLeast(ctl.get(), STOP) ||
//                        (Thread.interrupted() &&
//                                runStateAtLeast(ctl.get(), STOP))) &&
//                        !wt.isInterrupted())
//                    wt.interrupt();
//                try {
//                    //此处定义了一个钩子方法，如果有需要的话，子类可以重写该方法实现自己的逻辑，有点像动态代理的样子
//                    beforeExecute(wt, task);
//                    Throwable thrown = null;
//                    try {
//                        //任务的执行
//                        task.run();
//                    } catch (RuntimeException x) {
//                        thrown = x; throw x;
//                    } catch (Error x) {
//                        thrown = x; throw x;
//                    } catch (Throwable x) {
//                        thrown = x; throw new Error(x);
//                    } finally {
//                        afterExecute(task, thrown);
//                    }
//                } finally {
//                    task = null;
//                    w.completedTasks++;
//                    w.unlock();
//                }
//            }
//            completedAbruptly = false;
//        } finally {
//            processWorkerExit(w, completedAbruptly);
//        }
//    }
//
//    private Runnable getTask() {
//        boolean timedOut = false; // Did the last poll() time out?
//
//        for (;;) {
//            int c = ctl.get();
//            int rs = runStateOf(c);
//
//            // Check if queue empty only if necessary.
//            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
//                decrementWorkerCount();
//                return null;
//            }
//
//            int wc = workerCountOf(c);
//
//            // Are workers subject to culling?
//            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
//
//            if ((wc > maximumPoolSize || (timed && timedOut))
//                    && (wc > 1 || workQueue.isEmpty())) {
//                if (compareAndDecrementWorkerCount(c))
//                    return null;
//                continue;
//            }
//
//            try {
//                Runnable r = timed ?
//                        workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
//                        workQueue.take();
//                if (r != null)
//                    return r;
//                timedOut = true;
//            } catch (InterruptedException retry) {
//                timedOut = false;
//            }
//        }
//    }
//
//    final void reject(Runnable command) {
//        handler.rejectedExecution(command, this);
//    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activityA","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activityA","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activityA","onDestroy");
    }
}
