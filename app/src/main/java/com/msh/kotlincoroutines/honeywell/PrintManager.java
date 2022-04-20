package com.msh.kotlincoroutines.honeywell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @createDate: 2021/12/8
 * @author: mayz
 * @version: 1.0
 */
public class PrintManager {

    private Handler myHandler;
    private Context mContext;

    public PrintManager(Context mContext, Handler myHandler) {
        this.myHandler = myHandler;
        this.mContext = mContext;
        //线连接再 打印机
//        PrintService.connect(myHandler, false,true);
    }

    public void connect() {
        PrintService.connect(myHandler, false, true);
    }

    private static final Queue<LabelDetail> sWriteQueue =
            new ConcurrentLinkedQueue<>();
    private boolean sIsWriting = false;

    public synchronized void addQueue(LabelDetail labelDetail) {
        //当doWrite()正在进行过程中时，会把sIsWriting置为true
        //当要调用nextWrite()之前把sIsWriting置为false.
        if (sWriteQueue.isEmpty() && !sIsWriting) {
            show(labelDetail);
        } else {
            sWriteQueue.add(labelDetail);
        }
    }

    private synchronized void nextShow() {
        if (!sWriteQueue.isEmpty() && !sIsWriting) {
            show(sWriteQueue.poll());
        }
    }

    private void show(LabelDetail labelDetail) {
        sIsWriting = true;
        try {
//                    PrintService.printText(labelDetail,myHandler);
            PrintService.print(mContext, labelDetail, myHandler);
            Thread.sleep(1000);
            handler.sendEmptyMessage(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                sIsWriting = false;
                nextShow();
            }
        }
    };

    public void close() {
        PrintService.disConnect();
    }
}
