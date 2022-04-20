package com.msh.kotlincoroutines.honeywell;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.msh.kotlincoroutines.R;
import com.msh.kotlincoroutines.databinding.LayoutPrintBinding;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * @createDate: 2021/10/29
 * @author: mayz
 * @version: 1.0
 */
public class PrintService {

    private static Socket socket;
    private static OutputStream outputStream;

    public static void connect(Handler myHandler, boolean isSendEmptyID, boolean isShowSuccess) {
        disConnect();
        new Thread(() -> {
            int connect = connect();
            if (connect == 0 && isShowSuccess) {//打印机连接成功
                myHandler.removeMessages(ParamUtils.LINK_SUCCESS);
                myHandler.sendEmptyMessage(ParamUtils.LINK_SUCCESS);
            } else if (connect == -2 && isSendEmptyID) {//没有打印机ip
                myHandler.removeMessages(ParamUtils.PRINT_ID_EMPTY);
                myHandler.sendEmptyMessage(ParamUtils.PRINT_ID_EMPTY);
                return;
            } else if (connect == -1 && isShowSuccess) {
                myHandler.removeMessages(ParamUtils.LINK_FAILURE);
                myHandler.sendEmptyMessage(ParamUtils.LINK_FAILURE);
            }
        }).start();
    }

    //连接打印机
    private static int connect() {
        if (TextUtils.isEmpty(AppSetting.getPrint_ip())) {
            return -2;
        }
        try {

            LogUtils.eTag("MSH", "" + AppSetting.getPrint_ip());
            LogUtils.eTag("MSH", "" + AppSetting.getPrint_port());
            //打印机IP和端口号 进行连接
            socket = new Socket(AppSetting.getPrint_ip(), AppSetting.getPrint_port());
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


    public static Bitmap getBitmap(Context mContext, LabelDetail labelDetail, Handler myHandler) {
        int sp = DisplayUtil.sp2px(1.0f, 2.0f);//以2.0为模板适配
        int px = DisplayUtil.dp2px(1.0f, 2.0f);//以2.0为模板适配
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_print, null);
        LayoutPrintBinding binding = LayoutPrintBinding.bind(view);

//        binding.tvProductStore.setText(String.format(mContext.getString(R.string.product_store), labelDetail.getStore_name()));
//        binding.tvProductName.setText(String.format(mContext.getString(R.string.product_name), labelDetail.getProduct_name()));
//        binding.tvProductFormat.setText(String.format(mContext.getString(R.string.product_format), labelDetail.getFormat()));
//        binding.tvProductWeight.setText(String.format(mContext.getString(R.string.product_weight), labelDetail.getQty()));
//        binding.tvProductDate.setText(String.format(mContext.getString(R.string.product_date), TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm:ss")));
//        if (labelDetail.getType() == 1) {
//            binding.tvProductStore.setVisibility(View.GONE);
//            binding.tvProductWeight.setVisibility(View.GONE);
//        }

        binding.lllayout.setPadding(30 * px, 20 * px, 0, 0);
        binding.date.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.guige.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.product.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.storeName.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        binding.widget.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14 * sp);
        //float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 57, mContext.getResources().getDisplayMetrics());
        // float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 50, mContext.getResources().getDisplayMetrics());
        Bitmap bitmap = ViewUtils.viewToImage(binding.getRoot(), 600, 400);

        return bitmap;
    }

    public static void print(Context mContext, LabelDetail labelDetail, Handler myHandler) {
        int sp = DisplayUtil.sp2px(1.0f, 2.0f);//以2.0为模板适配
        int px = DisplayUtil.dp2px(1.0f, 2.0f);//以2.0为模板适配
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_print, null);
        LayoutPrintBinding binding = LayoutPrintBinding.bind(view);

//        binding.tvProductStore.setText(String.format(mContext.getString(R.string.product_store), labelDetail.getStore_name()));
//        binding.tvProductName.setText(String.format(mContext.getString(R.string.product_name), labelDetail.getProduct_name()));
//        binding.tvProductFormat.setText(String.format(mContext.getString(R.string.product_format), labelDetail.getFormat()));
//        binding.tvProductWeight.setText(String.format(mContext.getString(R.string.product_weight), labelDetail.getQty()));
//        binding.tvProductDate.setText(String.format(mContext.getString(R.string.product_date), TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm:ss")));
//        if (labelDetail.getType() == 1) {
//            binding.tvProductStore.setVisibility(View.GONE);
//            binding.tvProductWeight.setVisibility(View.GONE);
//        }

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

        //float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 57, mContext.getResources().getDisplayMetrics());
        // float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 50, mContext.getResources().getDisplayMetrics());
        Bitmap bitmap = ViewUtils.viewToImage(binding.getRoot(), 600, 400);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String str = PrintUtil.getZplCode(bitmap, true);
                    outputStream.write(str.getBytes());
                    myHandler.sendEmptyMessage(ParamUtils.PRINT_SUCCESS);
                } catch (Exception e) {
                    myHandler.removeMessages(ParamUtils.PRINT_FAILURE);
                    myHandler.sendEmptyMessage(ParamUtils.PRINT_FAILURE);
                }
                disConnect();
            }
        }.start();
    }
    //}

    public static void disConnect() {
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

}
