package cn.sdqingyun.smartpark.framework.common.util.currency;

import java.math.BigDecimal;

public class CurrencyConverter {
    public static int convertYuanToFen(double yuan) {
        return (int) (yuan * 100);
    }


    public static int convertYuanToFen(BigDecimal yuan) {
        Double price = Double.valueOf(String.valueOf(yuan));
        return (int) (price * 100);
    }

    public static Double fenToYuan(int fen) {
        return (double) (fen / 100);
    }

    public static int convertYuanToFen(String yuan) {
        Double price = Double.valueOf(yuan);
        return (int) (price * 100);
    }

    public static void main(String[] args) {
        Integer fen = 2000;
        Double yuan = fenToYuan(fen);
        System.out.println("分: " + fen);
        System.out.println("元: " + yuan);
    }
}