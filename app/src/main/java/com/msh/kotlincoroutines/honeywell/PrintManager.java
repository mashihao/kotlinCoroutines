package com.msh.kotlincoroutines.honeywell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.msh.kotlincoroutines.R;
import com.msh.kotlincoroutines.databinding.LayoutPrintBinding;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
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


    public static PrintManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static PrintManager instance = new PrintManager();
    }

    private PrintManager() {
    }

    public void init(Context mContext, Handler myHandler) {
        this.myHandler = myHandler;
        this.mContext = mContext;
        //线连接再 打印机
        connect(myHandler, false, true);
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
            print(mContext, labelDetail, myHandler);
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
        disConnect();
    }


    private Socket socket;
    private OutputStream outputStream;

    //是否连接成功
    private boolean isConnected = false;


    private void connect(Handler myHandler, boolean isSendEmptyID, boolean isShowSuccess) {
        isConnected = false;
        disConnect();
        new Thread(() -> {
            int connect = connect();
            LogUtils.eTag(PrinterService.Companion.getTAG(), "连接打印机" + connect);
            if (connect == 0 && isShowSuccess) {//打印机连接成功
                isConnected = true;
                //TODO 替换为广播
                myHandler.removeMessages(ParamUtils.LINK_SUCCESS);
                myHandler.sendEmptyMessage(ParamUtils.LINK_SUCCESS);
                LogUtils.eTag(PrinterService.Companion.getTAG(), "打印机连接成功");
            } else if (connect == -2 && isSendEmptyID) {//没有打印机ip
                //TODO 替换为广播
                myHandler.removeMessages(ParamUtils.PRINT_ID_EMPTY);
                myHandler.sendEmptyMessage(ParamUtils.PRINT_ID_EMPTY);
                LogUtils.eTag(PrinterService.Companion.getTAG(), "没有打印ip");
                return;
            } else if (connect == -1 && isShowSuccess) {
                //TODO 替换为广播
                myHandler.removeMessages(ParamUtils.LINK_FAILURE);
                myHandler.sendEmptyMessage(ParamUtils.LINK_FAILURE);
                LogUtils.eTag(PrinterService.Companion.getTAG(), "连接失败");
            }
        }).start();
    }

    //连接打印机
    private int connect() {
        if (TextUtils.isEmpty(AppSetting.getPrint_ip())) {
            return -2;
        }
        try {
            LogUtils.eTag(PrinterService.Companion.getTAG(), "" + AppSetting.getPrint_ip());
            LogUtils.eTag(PrinterService.Companion.getTAG(), "" + AppSetting.getPrint_port());
            //打印机IP和端口号 进行连接
            socket = new Socket();
            socket.connect(new InetSocketAddress(AppSetting.getPrint_ip(), AppSetting.getPrint_port()), 10000);
            socket.sendUrgentData(0xFF); // 发送心跳包
            outputStream = socket.getOutputStream();
            return 0;
        } catch (IOException e) {
            try {
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
        return -1;
    }


    public void print(Context mContext, LabelDetail labelDetail, Handler myHandler) {

        //如果是未连接 先连接
        if (!isConnected) {
            connect(myHandler, false, true);
            ToastUtils.showShort("打印机未连接,正在重新连接");
            return;
        }

        int sp = DisplayUtil.sp2px(1.0f, 2.0f);//以2.0为模板适配
        int px = DisplayUtil.dp2px(1.0f, 2.0f);//以2.0为模板适配
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_print, null);
        LayoutPrintBinding binding = LayoutPrintBinding.bind(view);


        binding.lllayout.setPadding(40 * px, 20 * px, 0, 0);
        binding.date.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.guige.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.product.setText(labelDetail.getProduct_name());
        binding.product.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.storeName.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.widget.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);

        binding.tip0.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.tip1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.tip2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.tip3.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.tip4.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.tip5.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);

        Bitmap bitmap = ViewUtils.viewToImage(binding.getRoot(), 600, 400);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String str = ViewUtils.getZplCode(bitmap, true);
                    outputStream.write(str.getBytes());
                    myHandler.sendEmptyMessage(ParamUtils.PRINT_SUCCESS);
                } catch (Exception e) {
                    myHandler.removeMessages(ParamUtils.PRINT_FAILURE);
                    myHandler.sendEmptyMessage(ParamUtils.PRINT_FAILURE);
                }
            }
        }.start();
    }
    //}

    private void disConnect() {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = null;
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket = null;
        }
    }

    //    public static void printText(LabelDetail l, Handler myHandler) {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//
//                try {
//                    int subWeight = 0;
//                    if (l.getType() == 0) {
//                        subWeight = 20;
//                    }
//                    StringBuffer sb = new StringBuffer();
//                    sb.append("NASC \"utf-8\"" + "\n")
//                            .append("FONT \"MHeiGB18030C-Medium\",10" + "\n")
//                            .append("PP140").append("," + (258 + subWeight) + ":AN7" + "\n\n")
//                            .append("PT ").append("\"" + "           商品编码: " + l.getProduct_code() + "\"" + "\n");
//                    if (l.getType() == 0) {
//                        sb.append("PP140").append("," + (220 + subWeight) + "" + "\n")
//                                .append("PT ").append("\"" + "           门店名称: " + l.getStore_name() + "\"" + "\n")
//                                .append("PP140").append("," + (182 + subWeight) + "" + "\n")
//                                .append("PT ").append("\"" + "           商品: " + l.getProduct_name() + "\"" + "\n")
//                                .append("PP140").append("," + (144 + subWeight) + "" + "\n")
//                                .append("PT ").append("\"" + "           规格: " + l.getFormat() + "\"" + "\n")
//                                .append("PP140").append("," + (106 + subWeight) + "" + "\n")
//                                .append("PT ").append("\"" + "           重量: " + l.getQty() + "\"" + "\n")
//                                .append("PP140").append("," + (68 + subWeight) + "" + "\n")
//                                .append("PT ").append("\"" + "           日期: " + DateUtils.getDateHm() + "\"" + "\n");
//                    } else {
//                        sb.append("PP140").append("," + (220 + subWeight) + "" + "\n")
//                                .append("PT ").append("\"" + "           商品: " + l.getProduct_name() + "\"" + "\n")
//                                .append("PP140").append("," + (182 + subWeight) + "" + "\n")
//                                .append("PT ").append("\"" + "           规格: " + l.getFormat() + "\"" + "\n")
//                                .append("PP140").append("," + (144 + subWeight) + "" + "\n")
//                                .append("PT ").append("\"" + "           日期: " + DateUtils.getDateHm() + "\"" + "\n");
//                    }
//                    sb.append("PF");
//                    String s = sb.toString();
//                    Log.e("bytes123", s);
////            if (outputStream == null||socket==null) {
//                    // connect();
////           }
//                    LogUtils.i("====1234===" + Arrays.toString(s.getBytes()));
//                    myHandler.sendEmptyMessage(12);
//                    outputStream.write(s.getBytes());
//                } catch (Exception e) {
//                    myHandler.removeMessages(ParamUtils.PRINT_FAILURE);
//                    myHandler.sendEmptyMessage(ParamUtils.PRINT_FAILURE);
//                }
//            }
//        }.start();
//    }
}
