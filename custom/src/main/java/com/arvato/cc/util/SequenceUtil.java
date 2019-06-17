package com.arvato.cc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: pang008
 * Date: 01/10/12
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class SequenceUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmsss");
    private static final Object obj = new Object();

    private static enum Prefix {
        O,
        SO,
        SI,
        OR,
        RF;

        private String value;

        private void Prefix(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * generate order no.
     *
     * @return
     */
    public static String generateOrderNo() {
        synchronized (obj) {
            return Prefix.O.name().concat(sdf.format(new Date(System.nanoTime())));
        }
    }

    /**
     * generate stkout no
     *
     * @return
     */
    public static String generateStkoutNo() {
        synchronized (obj) {
            return Prefix.SO.name().concat(sdf.format(new Date(System.nanoTime())));
        }
    }

    /**
     * generate stkout no
     *
     * @return
     */
    public static String generateStkInNo() {
        synchronized (obj) {
            return Prefix.SI.name().concat(sdf.format(new Date(System.nanoTime())));
        }
    }

    /**
     * generate order return no
     *
     * @return
     */
    public static String generateOrderReturnNo() {
        synchronized (obj) {
            return Prefix.OR.name().concat(sdf.format(new Date(System.nanoTime())));
        }
    }

    /**
     * generate return refund no
     *
     * @return
     */
    public static String generateReturnRefundNo() {
        synchronized (obj) {
            return Prefix.RF.name().concat(sdf.format(new Date(System.nanoTime())));
        }
    }

    public static String generateRefundNo() {
        synchronized (obj) {
            return sdf.format(new Date(System.nanoTime()));
        }
    }

    public static void main(String[] args)
    {
        generateStkoutNo();
    }
}
