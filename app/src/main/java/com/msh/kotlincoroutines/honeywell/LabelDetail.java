package com.msh.kotlincoroutines.honeywell;

/**
 * @author : 马世豪
 * time : 2022/4/20 13
 * email : ma_shihao@yeah.net
 * des :
 */
public class LabelDetail {

    private int type;
    private String product_name;
    private String product_code;
    private String store_name;
    private String format;
    private double qty;

    public LabelDetail(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}
