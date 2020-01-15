package com.github.dreamroute.excel.helper.util;

import java.text.DecimalFormat;

/**
 * 处理数字的工具
 * @author guoshihua
 * @description
 * @date 2020/1/15 10:14
 */
public class NumericUtil {

    /**
     * 格式化double<br>
     * 对 {@link DecimalFormat} 做封装<br>
     *
     * @param pattern 格式 格式中主要以 # 和 0 两种占位符号来指定数字长度。0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置。<br>
     *            <ul>
     *            <li>0 =》 取一位整数</li>
     *            <li>0.00 =》 取一位整数和两位小数</li>
     *            <li>00.000 =》 取两位整数和三位小数</li>
     *            <li># =》 取所有整数部分</li>
     *            <li>#.##% =》 以百分比方式计数，并取两位小数</li>
     *            <li>#.#####E0 =》 显示为科学计数法，并取五位小数</li>
     *            <li>,### =》 每三位以逗号进行分隔，例如：299,792,458</li>
     *            <li>光速大小为每秒,###米 =》 将格式嵌入文本</li>
     *            </ul>
     * @param value 值
     * @return 格式化后的值
     */
    public static String decimalFormat(String pattern, double value) {
        return new DecimalFormat(pattern).format(value);
    }
}
