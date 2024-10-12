package cn.sdqingyun.smartpark.module.bus.framework.utils.coverUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName CodeUtil
 * @Description 生成唯一码
 * @Author SUNk
 * @Date 2024/8/13 14:22
 * @ModifyDate 2024/8/13 14:22
 * @Version 1.0
 */
public class CodeUtil {
    public static String generateUniquecode() {
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyMMddHHmmsssss" );
        String timestr = sdf.format( new Date( timestamp ) );
        Random random = new Random();
        int randomNum = random.nextInt( 1000000 );
        String randomStr = String.format( "%06d", randomNum );
        String unigueCode = timestr + randomStr;
        if (unigueCode.length() < 10) {
            unigueCode = unigueCode + "0000000000".substring( 0, 10 - unigueCode.length() );
        }
        return unigueCode;
    }
}
