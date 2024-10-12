package cn.sdqingyun.smartpark.framework.common.util.pdf;

public class MoneyConverter {
    private static final char[] CN_NUMBERS = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
    private static final char[] CN_UNITS = {'分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟'};
    private static final char CN_TEN_MIN = '分';
    private static final char CN_HUNDRED_MILLION = '亿';
    private static final char CN_TEN_THOUSAND = '万';

    public static String convert(double money) {
        StringBuilder result = new StringBuilder();
        int yuan = (int) Math.floor(money);
        int jiao = (int) Math.floor(money * 10 % 10); // 角
        int fen = (int) Math.floor(money * 100 % 10); // 分

        if (yuan > 0) {
            String yuanStr = String.valueOf(yuan);
            for (int i = 0; i < yuanStr.length(); i++) {
                result.append(CN_NUMBERS[yuanStr.charAt(i) - '0']);
                result.append(CN_UNITS[yuanStr.length() - 1 - i + 2]);
            }
        } else {
            result.append(CN_NUMBERS[0]);
        }

        if (jiao > 0) {
            result.append(CN_NUMBERS[jiao]);
            result.append(CN_UNITS[3]);
        } else if (jiao == 0 && result.charAt(result.length() - 1) == '零') {
            result.append(CN_NUMBERS[0]);
            result.append(CN_UNITS[3]);
        }

        if (fen > 0) {
            result.append(CN_NUMBERS[fen]);
            result.append(CN_UNITS[0]);
        } else if (fen == 0 && result.charAt(result.length() - 1) == '零') {
            result.append(CN_NUMBERS[0]);
            result.append(CN_UNITS[0]);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(convert(123456789.12)); // 输出大写金额字符串
    }
}