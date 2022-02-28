package com.bigfun.sdk;

import android.os.Handler;
import android.os.Looper;

public class ExceptionHandler {
    //你自己定义的处理异常类
    private static CustomExceptionHandler mCustomExceptionHandler;
    //程序默认的异常处理类，在这里用于处理子线程所出现的异常
    private static Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    //标记是否设置过异常处理
    private static boolean mIsInstall = false;

    private ExceptionHandler() {

    }

    /**
     * 安装自己的全局异常处理
     * @param customExceptionHandler 自定义的出现异常的处理类
     */
    public static synchronized void install(CustomExceptionHandler customExceptionHandler) {
        //如果已经安装过，就不再执行，避免重复执行
        if (mIsInstall) {
            return;
        }
        mIsInstall = true;
        //你自己定义的异常处理类，可以自己决定异常如何处理
        mCustomExceptionHandler = customExceptionHandler;
        /*
            通过Handler向主线程的queue中添加一个Runnabel(此处new Handler()里面传
         参数Looer.getMainLooper()就是为了是向主线程的queue中添加，如果不传这个
         参数就是默认的了)当主线程执行到我们发送的这个Runnable的时候就会进入我们
         的while死循环，如果while内部是空的话就会造成代码卡死在这里导致ANR，但是我们
         在while中调用了Looper.loop(),这就使得我们的主线程又开始工作了，不断的从
        queue中获取Message执行(其实Android的机制就是这样的不断的从主线程的queue中
         获取message并且执行)。这样就可以保证以后主线程的所有异常都会从我们手动调用
        的Looper.loop()处抛出了。
        下面分析一下为什么这样就可以保证主线程的所有异常都会从我们手动调用的Looper.loop()
        处抛出。
        ![ActivityThread_main()]()
        首先看主线程的源码，我的主线程都是在main()函数里面开始执行的，就像Java一样，这里
        是入口。我们可以看到在main函数中调用了Looper.loop()。
        ![Looper.loop()]()
        Looper.loop()方法的源码，在这里就很明显了，我们可以看到在这个方法中有一个
        for(;;)死循环,不断的从queue中获取message执行。所以我们的方法就变成了
        for(;;){
            Message msg = queue.next();
            //如果msg是我们post过来的Runnable就会执行下面的代码了
            while(true){
                try{
                    Looper.loop();(的本质就是 for(;;){.....})
                }catch(....){
                    ........
                }
            }
        }
        所以当出现异常的时候就会抛出了。所以这样做的话就可以保证所有主线程中的异常可以被捕获了，单纯这样做
        的话，子线程中如果出现问题的话还是会crash的，所以就要加上Thread.setDefaultUncaughtExceptionHandler()
        了。
         */
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable throwable) {
                        //这个判断主要是用于uninstall方法，如果抛出的异常使
                        //QuitExceptionHandler的话就跳出这个方法。
                        if (throwable instanceof QuitExceptionHandler) {
                            return;
                        }
                        if (mCustomExceptionHandler != null) {
                            mCustomExceptionHandler.handlerException(Looper.getMainLooper().getThread(), throwable);
                        }
                    }
                }
            }
        });
        //将原先默认的处理方式保存下来，以便后期如果有需要的话恢复原状态。
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (mCustomExceptionHandler != null) {
                    mCustomExceptionHandler.handlerException(t, e);
                }
            }
        });

    }

    /**
     * 当不需要这个全局的异常处理的时候可以调用此方法
     * 用来卸载这个处理类
     */
    public static synchronized void uninstall(){
        if (!mIsInstall){
            return;
        }
        mIsInstall = false;
        mCustomExceptionHandler = null;
        //恢复之前的异常处理状态
        Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);
        //向主线程post这个runnable(),主动抛出一个QuitExceptionHandler异常
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new QuitExceptionHandler("Quit ExceptionHandler....");
            }
        });

    }

    /**
     * 自定义的处理异常
     */
    public interface CustomExceptionHandler {

        void handlerException(Thread thread, Throwable throwable);
    }

}
class QuitExceptionHandler extends RuntimeException {
    public QuitExceptionHandler(String message) {
        super(message);
    }

}
