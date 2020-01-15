package com.github.dreamroute.excel.helper.util;

import org.apache.commons.lang3.time.DatePrinter;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 用于检验excel是否为日期类型的工具类
 * @author guoshihua
 * @description
 * @date 2020/1/15 9:59
 */
public class DateUtil {

    private static final Pattern date_ptrn1 = Pattern.compile("^\\[\\$\\-.*?\\]");

    private static final Pattern date_ptrn2 = Pattern.compile("^\\[[a-zA-Z]+\\]");

    private static final Pattern date_ptrn3 = Pattern.compile("^[\\[\\]yYmMdDhHsS\\-/,. :\"\\\\]+0*[ampAMP/]*$");

    private static final Pattern date_ptrn4 = Pattern.compile("^\\[([hH]+|[mM]+|[sS]+)\\]");

    /**
     * Check if a cell contains a date Since dates are stored internally in Excel as double values we infer it is a date
     * if it is formatted as such.
     *
     * @see #isADateFormat(int, String)
     */
    public static boolean isCellDateFormatted(Cell cell) {
        if (cell == null)
            return false;
        boolean bDate = false;

        double d = cell.getNumericCellValue();
        if (org.apache.poi.ss.usermodel.DateUtil.isValidExcelDate(d)) {
            CellStyle style = cell.getCellStyle();
            if (style == null)
                return false;
            int i = style.getDataFormat();
            String f = style.getDataFormatString();
            bDate = isADateFormat(i, f);
        }
        return bDate;
    }

    /**
     * Given a format ID and its format String, will check to see if the format represents a date format or not.
     * Firstly, it will check to see if the format ID corresponds to an internal excel date format (eg most US date
     * formats) If not, it will check to see if the format string only contains date formatting characters (ymd-/),
     * which covers most non US date formats.
     *
     * @param formatIndex
     *            The index of the format, eg from ExtendedFormatRecord.getFormatIndex
     * @param formatString
     *            The format string, eg from FormatRecord.getFormatString
     */
    public static boolean isADateFormat(int formatIndex, String formatString) {
        // First up, is this an internal date format?
        if (org.apache.poi.ss.usermodel.DateUtil.isInternalDateFormat(formatIndex)) {
            return true;
        }

        // If we didn't get a real string, it can't be
        if (formatString == null || formatString.length() == 0) {
            return false;
        }

        String fs = formatString;
        StringBuilder sb = new StringBuilder(fs.length());
        for (int i = 0; i < fs.length(); i++) {
            char c = fs.charAt(i);
            if (i < fs.length() - 1) {
                char nc = fs.charAt(i + 1);
                if (c == '\\') {
                    switch (nc) {
                        case '-':
                        case ',':
                        case '.':
                        case ' ':
                        case '\\':
                            // skip current '\' and continue to the next char
                            continue;
                    }
                } else if (c == ';' && nc == '@') {
                    i++;
                    // skip ";@" duplets
                    continue;
                }
            }
            sb.append(c);
        }
        fs = sb.toString();
        fs = fs.replaceAll("[\"|']", "").replaceAll("[年|月|日|时|分|秒|毫秒|微秒]", "");

        // short-circuit if it indicates elapsed time: [h], [m] or [s]
        if (date_ptrn4.matcher(fs).matches()) {
            return true;
        }

        // If it starts with [$-...], then could be a date, but
        // who knows what that starting bit is all about
        fs = date_ptrn1.matcher(fs).replaceAll("");
        // If it starts with something like [Black] or [Yellow],
        // then it could be a date
        fs = date_ptrn2.matcher(fs).replaceAll("");
        // You're allowed something like dd/mm/yy;[red]dd/mm/yy
        // which would place dates before 1900/1904 in red
        // For now, only consider the first one
        if (fs.indexOf(';') > 0 && fs.indexOf(';') < fs.length() - 1) {
            fs = fs.substring(0, fs.indexOf(';'));
        }

        // Otherwise, check it's only made up, in any case, of:
        // y m d h s - \ / , . :
        // optionally followed by AM/PM
        return date_ptrn3.matcher(fs).matches();
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date 被格式化的日期
     * @param format {@link DatePrinter} 或 {@link FastDateFormat}
     * @return 格式化后的字符串
     */
    public static String format(Date date, DatePrinter format) {
        if (null == format || null == date) {
            return null;
        }
        return format.format(date);
    }
}
