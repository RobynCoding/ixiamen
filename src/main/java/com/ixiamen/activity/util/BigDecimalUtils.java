package com.ixiamen.activity.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * @author luoyongbin
 * @since on 2018/5/8.
 */
public class BigDecimalUtils {

    /**
     * 保留N位小数,N位之后的全部去掉<br>
     *
     * @param current  : 当前需要改变的
     * @param position : 位数
     */
    public static BigDecimal setScaleDown (BigDecimal current, int position ) {
        return current.setScale( position, BigDecimal.ROUND_DOWN );
    }

    /**
     * 判断是否是一个正数
     * <p>正数定义：比0大的实数叫正数[,正数前面常有一个符号"+",通常可以省略不写.</p>
     *
     */
    public static boolean isPositiveNumber ( BigDecimal bigDecimal ) {
        return bigDecimal.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断是否不是一个正数
     *
     */
    public static boolean isNotPositiveNumber ( BigDecimal bigDecimal ) {
        return ! isPositiveNumber( bigDecimal );
    }

    /**
     * 判断两个参数是否相等
     *
     */
    public static boolean isEqual ( BigDecimal before, BigDecimal after ) {
        return before.compareTo( after ) == 0;
    }

    /**
     * 判断两个参数是否相等
     */
    public static boolean isEqual ( BigDecimal before, String after ) {
        return isEqual( before, NumberUtils.createBigDecimal( after ) );
    }


    public static boolean isNotEqual ( BigDecimal before, String after ) {
        return isNotEqual( before, NumberUtils.createBigDecimal( after ) );
    }


    /**
     * 判断两个参数是否不相等
     */
    public static boolean isNotEqual ( BigDecimal before, BigDecimal after ) {
        return ! isEqual( before, after );
    }

    /**
     * 乘法计算
     */
    public static BigDecimal multiply ( BigDecimal before, int after ) {
        return before.multiply( NumberUtils.createBigDecimal( String.valueOf( after ) ) );
    }

    /**
     * 减法
     */
    public static BigDecimal subtract ( BigDecimal before, String after ) {
        return before.subtract( NumberUtils.createBigDecimal( after ) );
    }

    public static BigDecimal subtract ( BigDecimal before, BigDecimal after ) {
        return before.subtract( after );
    }

    /**
     * 加法
     */
    public static BigDecimal add ( BigDecimal before, String after ) {
        return before.add( NumberUtils.createBigDecimal( after ) );
    }

    /**
     * 加法
     */
    public static BigDecimal add ( BigDecimal before, BigDecimal after ) {
        return before.add( after );
    }

}
