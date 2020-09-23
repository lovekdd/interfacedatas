package lengj.interfacedata.demo.util;

import java.net.URLDecoder;

/**
 * 字符串函数库，主要校验相关字符串是否合法
 */
public class StrFunc {

    public static final String UTF8 = "UTF-8";

    public static final String GB2312 = "GB2312";

    public static final String GBK = "GBK";

    public static final String ISO8859_1 = "ISO-8859-1";

    private final static byte[] val = {
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F };

    private static final String escapeStr = "+-@*/._";

    /**
     * 对前台传输的数据进行解码
     * @param str
     * @return
     * @throws Exception
     */
    public static final String unescapeURIComponent(final String str) throws Exception {
        return isNull(str) ? null : unescape(URLDecoder.decode(str, "UTF-8"));
    }
    /**判断字符串是否为 null 或  ""
     * @param str 需要判断的字符串
     * @return 为空返回true，反之false
     */
    public static final boolean isNull(final String str) {
        return (str == null || str.length() <= 0);//不要trim str，效率不好，也和函数的名称不匹配
    }
    /**对字符串进行解码
     * @param s 需要解密的字符串
     * @return 解码后的结果
     */
    public static String unescape(final String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        StringBuffer sbuf = new StringBuffer(s.length());
        int i = 0;
        int len = s.length();
        while (i < len) {
            char ch = s.charAt(i);
            if (needEscape(ch))
                sbuf.append((char) ch);
            else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i + 1)) { // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[s.charAt(i + 1)];
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    i += 2;
                }
                else { // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    cint = (cint << 4) | val[s.charAt(i + 3)];
                    cint = (cint << 4) | val[s.charAt(i + 4)];
                    cint = (cint << 4) | val[s.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            }
            else
                sbuf.append((char) ch);
            i++;
        }
        return sbuf.toString();
    }
    /**判断是否需要进行Escape
     * 不会对 ASCII 字母和数字进行编码，也不会对下面这些 ASCII 标点符号进行编码： * @ - _ + . /
     * 其他所有的字符都会被转义序列替换。
     * @param c 要判断的字符
     * @return 如果需要Escape，返回False，反之True
     */
    public static final boolean needEscape(final char c) {
        return c<0x7F && c>0x20 && ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')|| (c >= '0' && c <= '9') || (escapeStr.indexOf(c) >= 0));
    }

    /**
     * 如果str为空时，就用def的值进行替代
     * @param str 要转换的字符串
     * @param def 代替空串的字符串
     * @return str为空时，就用def的值进行替代，反之返回原串
     */
    public static final String null2default(final String str, final String def) {
        return (str == null || str.length() <= 0) ? def : str;
    }

    /**
     * hejzh：将字符串解析为boolean值。如果给定的字符串不是true/false、t/f、0/1、是/否其中之一，
     * 或为null，则返回给定的默认值
     *
     * @param str 待解析的字符串
     * @param def 给定默认值
     * @return 解析结果
     */
    public static final boolean parseBoolean(final String str, final boolean def){
        if (str == null || str.length() == 0) {
            return def;
        }
        if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("t")
                //|| str.equalsIgnoreCase("1")||str.equals("是")) {
                || str.equalsIgnoreCase("1")||str.equals( "是")) {

            return true;
        }
        else if (str.equalsIgnoreCase("false") || str.equalsIgnoreCase("f")
                //|| str.equalsIgnoreCase("0")||str.equals("否")) {
                || str.equalsIgnoreCase("0")||str.equals("否")) {
            return false;
        }
        else {
            return def;
        }
    }
    /**  //TODO 和compareText重复，可以删之一,
     * 建议删掉compareStrIgnoreBlankAndCase方法，把compareText方法改名为compareStrIgnoreBlankAndCase
     * 比较字符串是否相等
     * 比较时忽略""和null的区别，并且忽略字母大小写
     * @param str1 比较的字符串
     * @param str2 比较的字符串
     * @return 字符串相等返回true，反之false
     */
    public static final boolean compareStrIgnoreBlankAndCase(final String str1, final String str2) {
        return ((str1 == null || str1.length()==0) && (str2 == null || str2.length()==0))
                || ((str1 != null) && (str2 != null) && (str1.equalsIgnoreCase(str2)));
    }

}
