package com.msh.flow;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author : 马世豪
 * time : 2021/9/24 14
 * email : ma_shihao@yeah.net
 * des :
 */
public class Test {


    @org.junit.Test
    public void test() {

        String json =

                "{\"poNo\": \"U120000005802\", \"orderQyt\": 0.50, \"unit\": \"桶\", \"productName\": \"[正大] 醇香猪油 铁桶 (15kg)\", \"productCode\": \"406863463\", \"productId\": null, \"overpickflag\": false, \"overpickRatio\": null, \"ykQty\": 34.00, \"ykUnit\": \"桶\", \"storeCnt\": 1}";


        Gson gson = new Gson();

        PdaProductYkInfo pdaProductYkInfo = gson.fromJson(json, PdaProductYkInfo.class);

        System.out.println(pdaProductYkInfo.toString());

    }

    public static boolean isNumber(String str) {
        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
        boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
        return isInt || isDouble;
    }

    public interface IHello {
        String sayHello();
    }

    public static class HelloInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            //根据不同的Method Name以及@return 单独处理调用okhttp call获取到相应的@return
            if (method.getName().equals("sayHello")) {
                return "sayHello";
            }
            return "null数据";
        }
    }

    @org.junit.Test
    public void main() {

        IHello o = (IHello) Proxy.newProxyInstance(
                IHello.class.getClassLoader(),
                new Class<?>[]{IHello.class},
                new HelloInvocationHandler());
        System.out.println(o.sayHello());
    }


    public class PdaProductYkInfo implements Serializable {
        // 入库单号
        private String poNo;

        // 订单的商品数量
        private double orderQyt;
        // 订单的商品单位信息
        private String unit;

        private String productName;
        private String productCode;
        //库存类型
        private String stockPlaceCode;
        // 该商品可越库的数量
        private double ykQty;
        // 该商品越库单位信息
        private String ykUnit;

        // 门店总数
        private Integer storeCnt;

        public String getPoNo() {
            return poNo;
        }

        public void setPoNo(String poNo) {
            this.poNo = poNo;
        }

        public double getOrderQyt() {
            return orderQyt;
        }

        public void setOrderQyt(double orderQyt) {
            this.orderQyt = orderQyt;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getStockPlaceCode() {
            return stockPlaceCode;
        }

        public void setStockPlaceCode(String stockPlaceCode) {
            this.stockPlaceCode = stockPlaceCode;
        }

        public double getYkQty() {
            return ykQty;
        }

        public void setYkQty(double ykQty) {
            this.ykQty = ykQty;
        }

        public String getYkUnit() {
            return ykUnit;
        }

        public void setYkUnit(String ykUnit) {
            this.ykUnit = ykUnit;
        }

        public Integer getStoreCnt() {
            return storeCnt;
        }

        public void setStoreCnt(Integer storeCnt) {
            this.storeCnt = storeCnt;
        }

        @Override
        public String toString() {
            return "PdaProductYkInfo{" +
                    "poNo='" + poNo + '\'' +
                    ", orderQyt=" + orderQyt +
                    ", unit='" + unit + '\'' +
                    ", productName='" + productName + '\'' +
                    ", productCode='" + productCode + '\'' +
                    ", stockPlaceCode='" + stockPlaceCode + '\'' +
                    ", ykQty=" + ykQty +
                    ", ykUnit='" + ykUnit + '\'' +
                    ", storeCnt=" + storeCnt +
                    '}';
        }
    }
}
