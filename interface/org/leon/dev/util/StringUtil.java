package org.leon.dev.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ��˵�����ַ�����
 * @author LeonWong
 */
public class StringUtil {
	/**
	 * 
	 */
	private static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	/**
	 * 
	 */
	private static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	/**
	 * 
	 */
	private static int[] ai = new int[18];

	/**
	 * �ж��ַ��Ƿ�Ϊ��
	 * 
	 * @param string �����ַ�
	 * @return boolean �����Ƿ�Ϊ��
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	/**
	 * �ж������ַ��Ƿ�ֵ���
	 * 
	 * @param a ���õ�һ���ַ�
	 * @param b ���õڶ����ַ�
	 * @return boolean ���رȽϵĽ��
	 */
	public static boolean compare(String a, String b) {
		if (isEmpty(a) && isEmpty(b))
			return true;
		if (!isEmpty(a) && !isEmpty(b))
			return a.equals(b);
		else
			return false;
	}
	/**
	 *  验证邮箱格式
	 * @param value:传入的邮箱值
	 * @return boolean
	 * 
	 */
	public static boolean checkEmail(String value){
		String reg="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)？\\.)+[a-zA-Z]{2,}$";
		String regex="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		if(value.matches(regex)||value.matches(reg)){
			return true;
		}
		return false;
	}
	/**
	 * 验证是否过期
	 * @param src:有效期
	 * @param dst:当前时间
	 */
	public static boolean checkTimeIfOver(String src,String dst){
		if(src==null){
			return false;
		}
		String reg="-";
		String s[]=src.split(reg);
		String d[]=dst.split(reg);
		src=s[0]+s[1]+s[2];
		dst=d[0]+d[1]+d[2];
		if(Long.parseLong(src)<= Long.parseLong(dst)){
			return true;
		}
		return false;
	}
	/**
	 * �ж������ַ��Ƿ�ֵ��ȣ����Դ�Сд
	 * 
	 * @param a ���õ�һ���ַ�
	 * @param b ���õڶ����ַ�
	 * @return boolean ���رȽϵĽ��
	 */
	public static boolean compareIgnoreCase(String a, String b) {
		if (isEmpty(a) && isEmpty(b))
			return true;
		if (!isEmpty(a) && !isEmpty(b))
			return a.equalsIgnoreCase(b);
		else
			return false;
	}

	/**
	 * �����ַ��дӿ�ʼ��ָ����λ��
	 * 
	 * @param src �����ַ�
	 * @param len ָ�����Ƶ�ĳ��λ��
	 * @return String ���ؽ��
	 */
	public static String copy(String src, int len) {
		if (src == null)
			return null;
		if (src.length() > len)
			return len <= 0 ? null : src.substring(0, len);
		else
			return src;
	}

	/**
	 * ɾ���ַ���ָ����һ���ַ�����
	 * 
	 * @param src ����ԭ�ַ�
	 * @param delStr ������Ҫɾ����ַ�
	 * @return String ���ؽ��
	 */
	public static String delete(String src, String delStr) {
		if (isEmpty(src) || isEmpty(delStr))
			return src;
		StringBuffer buffer = new StringBuffer(src);
		for (int index = src.length(); (index = src.lastIndexOf(delStr, index)) >= 0; index -= delStr.length())
			buffer.delete(index, index + delStr.length());

		return buffer.toString();
	}

	/**
	 * ��ָ�����ַ��λ�ò��뵽ԭ�ַ���
	 * 
	 * @param src ����ԭ�ַ�
	 * @param anotherStr ������Ҫ������ַ�
	 * @param offset λ��
	 * @return String ���ؽ��
	 */
	public static String insert(String src, String anotherStr, int offset) {
		if (isEmpty(src) || isEmpty(anotherStr))
			return src;
		StringBuffer buffer = new StringBuffer(src);
		if (offset >= 0 && offset <= src.length())
			buffer.insert(offset, anotherStr);
		return buffer.toString();
	}

	/**
	 * ׷��ָ�����ַ�ԭ�ַ���
	 * 
	 * @param src ����ԭ�ַ�
	 * @param appendStr ������Ҫ׷�ӵ��ַ�
	 * @return String ���ؽ��
	 */
	public static String append(String src, String appendStr) {
		if (isEmpty(src) || isEmpty(appendStr)) {
			return src;
		} else {
			StringBuffer buffer = new StringBuffer(src);
			buffer.append(appendStr);
			return buffer.toString();
		}
	}

	/**
	 * ��ݲ����滻�ַ����ݹ���
	 * 
	 * @param src ����ԭ�ַ�
	 * @param oldStr ָ���滻�ַ�
	 * @param newStr ��Ҫ�滻������
	 * @param isCaseSensitive �Ƿ���ִ�Сд
	 * @return String ���ؽ��
	 */
	public static String replace(String src, String oldStr, String newStr, boolean isCaseSensitive) {
		if (isEmpty(src) || isEmpty(oldStr) || newStr == null)
			return src;
		String s = isCaseSensitive ? src : src.toLowerCase();
		String o = isCaseSensitive ? oldStr : oldStr.toLowerCase();
		StringBuffer buffer = new StringBuffer(src);
		for (int index = s.length(); (index = s.lastIndexOf(o, index)) >= 0; index -= o.length())
			buffer.replace(index, index + o.length(), newStr);

		return buffer.toString();
	}

	/**
	 * ��ݲ����滻�ַ����ݹ��ܣ���ָ��λ��
	 * 
	 * @param src ����ԭ�ַ�
	 * @param oldStr ָ���滻�ַ�
	 * @param newStr ��Ҫ�滻������
	 * @param isCaseSensitive �Ƿ���ִ�Сд
	 * @param index ָ��λ��
	 * @return String ���ؽ��
	 */
	public static String replace(String src, String oldStr, String newStr, boolean isCaseSensitive, int index) {
		if (src == null || src.length() == 0 || oldStr == null || oldStr.length() == 0 || index <= 0)
			return src;
		if (newStr == null)
			newStr = "";
		String s = isCaseSensitive ? src : src.toLowerCase();
		String old = isCaseSensitive ? oldStr : oldStr.toLowerCase();
		StringBuffer buffer = new StringBuffer(src);
		int length = old.length();
		int pos;
		for (pos = s.indexOf(old, 0); pos >= 0; pos = s.indexOf(old, pos + length))
			if (--index == 0)
				break;
		if (pos >= 0 && index == 0)
			buffer.replace(pos, pos + length, newStr);
		return buffer.toString();
	}

	/**
	 * ������ַ�ǰ���˫���
	 * 
	 * @param str ����ԭ�ַ�
	 * @return String ���ؽ��
	 */
	public static String quote(String str) {
		if (isEmpty(str))
			return "\"\"";
		StringBuffer buffer = new StringBuffer(str);
		if (!str.startsWith("\""))
			buffer.insert(0, "\"");
		if (!str.endsWith("\""))
			buffer.append("\"");
		return buffer.toString();
	}

	/**
	 * ȥ���ַ��е�˫���
	 * 
	 * @param str ����ԭ�ַ�
	 * @return String ���ؽ��
	 */
	public static String extractQuotedStr(String str) {
		if (isEmpty(str))
			return str;
		StringBuffer buffer = new StringBuffer(str);
		int index = str.length();
		while (buffer.charAt(buffer.length() - 1) == '"') {
			buffer.deleteCharAt(buffer.length() - 1);
			index = buffer.length();
			if (index <= 0)
				break;
		}
		if (buffer.length() == 0)
			return "";
		while (buffer.charAt(0) == '"') {
			buffer.deleteCharAt(0);
			index = buffer.length();
			if (index <= 0)
				break;
		}
		return buffer.toString();
	}

	/**
	 * ��ȡ�ַ��е�ָ�����ַ�����ݣ�����߿�ʼ
	 * 
	 * @param str ����ԭ�ַ�
	 * @param c ����ָ���ַ�
	 * @return String ���ؽ��
	 */
	public static String strChar(String str, char c) {
		if (str == null || str.length() == 0)
			return null;
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == c)
				return str.substring(i);

		return null;
	}

	/**
	 * ��ȡ�ַ��е�ָ�����ַ�����ݣ����ұ߿�ʼ
	 * 
	 * @param str ����ԭ�ַ�
	 * @param c   ����ָ���ַ�
	 * @return String ���ؽ��
	 */
	public static String strRChar(String str, char c) {
		if (str == null || str.length() == 0)
			return null;
		for (int i = str.length() - 1; i >= 0; i--)
			if (str.charAt(i) == c)
				return str.substring(i);

		return null;
	}

	/**
	 * ��Object��������ת���ַ�����
	 * 
	 * @param array  ����Object��������
	 * @return String[] ���ؽ��
	 */
	public static String[] toArray(Object array[]) {
		if (array == null || array.length == 0)
			return null;
		String result[] = new String[array.length];
		for (int i = 0; i < array.length; i++)
			if (array[i] != null)
				result[i] = array[i].toString();

		return result;
	}

	/**
	 * ���ַ����鸴�Ƶ�LIST��
	 * 
	 * @param array �����ַ�����
	 * @param list ����LIST���϶���
	 * @param index ���ø��Ƶ�LISTλ��
	 * @return List ���ؽ��
	 */
	public static List copyToList(String array[], List list, int index) {
		if (array == null || array.length == 0)
			return list;
		if (list == null || index < 0)
			return list;
		for (int i = 0; i < array.length; i++)
			if (list.size() <= i + index)
				list.add(index + i, array[i]);
			else
				list.set(index + i, array[i]);

		return list;
	}

	/**
	 * ��֤�Ƿ�Ϊ�����ʼ���ʽ
	 * 
	 * @param theEmail ���õ����ʼ���ַ�ַ�
	 * @return boolean �����Ƿ�Ϸ�
	 */
	public static boolean isValidEmail(String theEmail) {
		boolean isEmail = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(theEmail);
			boolean isMatched = matcher.matches();
			if (isMatched) {
				isEmail = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return isEmail;
		}
		return isEmail;
	}

	/**
	 * ȥ���ַ���߿ո�
	 * 
	 * @param str ����ԭ�ַ�
	 * @return String ���ؽ��
	 */
	public static String trimLeft(String str) {
		if (str == null)
			return null;
		int length = str.length();
		if (length == 0)
			return "";
		StringBuffer buffer = new StringBuffer(str);
		int index;
		for (index = 0; index < length && buffer.charAt(index) == ' '; index++)
			;
		if (index == length)
			return "";
		else
			return buffer.substring(index);
	}

	/**
	 * ȥ���ַ��ұ߿ո�
	 * 
	 * @param str ����ԭ�ַ�
	 * @return String ���ؽ��
	 */
	public static String trimRight(String str) {
		if (str == null)
			return null;
		int length = str.length();
		if (length == 0)
			return "";
		StringBuffer buffer = new StringBuffer(str);
		int index;
		for (index = length - 1; index >= 0 && buffer.charAt(index) == ' '; index--)
			;
		if (index < 0 && buffer.charAt(0) == ' ')
			return "";
		else
			return buffer.substring(0, index + 1);
	}

	/**
	 * ��֤���֤�ĺϷ���
	 * 
	 * @param idcard �������֤�ַ�
	 * @return boolean ���ؽ��
	 */
	public static boolean idCardVerify(String idcard) {
		if (idcard.length() == 15) {
			idcard = idCardUptoeighteen(idcard);
		}
		if (idcard.length() != 18) {
			return false;
		}
		String verify = idcard.substring(17, 18);
		if (verify.equals(getIdCardVerify(idcard))) {
			return true;
		}
		return false;
	}

	/**
	 * ������֤�ĺϷ���
	 * 
	 * @param eightcardid �������֤�ַ�
	 * @return String ���ؽ��
	 */
	public static String getIdCardVerify(String eightcardid) {
		int remaining = 0;
		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}
		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
			}
			for (int i = 0; i < 17; i++) {
				sum = sum + wi[i] * ai[i];
			}
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	/**
	 * ������֤15ת18λ
	 * 
	 * @param fifteencardid �������֤�ַ�
	 * @return String ���ؽ��
	 */
	public static String idCardUptoeighteen(String fifteencardid) {
		if (fifteencardid.length() != 15)
			return null;
		String eightcardid = fifteencardid.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteencardid.substring(6, 15);
		eightcardid = eightcardid + getIdCardVerify(eightcardid);
		return eightcardid;
	}

	/**
	 * ��֤�绰����Ϸ���ʽ����ʽΪ02584555112
	 * 
	 * @param phoneCode ���õ绰�����ַ�
	 * @return boolean ���ؽ��
	 */
	public static boolean isPhoneNum(String phoneCode) {
		Pattern p = Pattern.compile("[0][1-9]{2,3}[1-9]{6,8}");
		Matcher m = p.matcher(phoneCode);
		boolean b = m.matches();
		return b;
	}

	/**
	 * �ַ�����ת��Ϊ�ַ�,�ö��Ÿ���
	 * @param str �ַ�����
	 * @return String
	 */
	public static String arrayToString(String[] str) {
		if (str == null)
			return "";
		StringBuffer rStr = new StringBuffer("");
		for (int i = 0; i < str.length; i++) {
			rStr.append(str[i]);
			rStr.append(",");
		}
		// ��ȡ����
		if (rStr.toString().length() > 0) {
			rStr.setLength(rStr.length() - 1);
		}
		return rStr.toString();
	}

	/**
	 * ��Stringת����BigDecimal
	 * @param str String
	 * @return BigDecimal
	 */
	public static BigDecimal asBigDecimal(String str) {
		return asBigDecimal(str, new BigDecimal(BigInteger.ZERO));
	}

	/**
	 * ��Stringת����BigDecimal
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return String
	 */
	public static BigDecimal asBigDecimal(String str, BigDecimal defaultValue) {
		try {
			return new BigDecimal(str.trim());
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		} catch (RuntimeException runtimeexception) {
			return defaultValue;
		}
	}

	/**
	 * ��Stringת����BigInteger
	 * @param str String
	 * @return BigInteger
	 */
	public static BigInteger asBigInteger(String str) {
		return asBigInteger(str, BigInteger.ZERO);
	}

	/**
	 * ��Stringת����BigInteger
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return BigInteger
	 */
	public static BigInteger asBigInteger(String str, BigInteger defaultValue) {
		try {
			return new BigInteger(str.trim());
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ boolean
	 * @param str String
	 * @return boolean
	 */
	public static boolean asBoolean(String str) {
		return asBoolean(str, false);
	}

	/**
	 *  �� String ����Ϊ Boolean
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Boolean
	 */
	public static Boolean asBoolean(String str, Boolean defaultValue) {
		try {
			str = str.trim();
			return Integer.decode(str).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
		}
		if (str.equals(""))
			return defaultValue;
		for (int i = 0; i < FALSE_STRINGS.length; i++)
			if (str.equalsIgnoreCase(FALSE_STRINGS[i]))
				return Boolean.FALSE;

		return Boolean.TRUE;
	}

	/**
	 *  �� String ����Ϊ boolean
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return boolean
	 */
	public static boolean asBoolean(String str, boolean defaultValue) {
		try {
			str = str.trim();
			return Integer.decode(str).intValue() != 0;
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
		}
		if (str.equals(""))
			return defaultValue;
		for (int i = 0; i < FALSE_STRINGS.length; i++)
			if (str.equalsIgnoreCase(FALSE_STRINGS[i]))
				return false;

		return true;
	}

	/**
	 * �� String ����Ϊ byte
	 * @param str String
	 * @return byte
	 */
	public static byte asByte(String str) {
		return asByte(str, (byte) 0);
	}

	/**
	 * �� String ����Ϊ Byte
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Byte
	 */
	public static Byte asByte(String str, Byte defaultValue) {
		try {
			return Byte.decode(str.trim());
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ byte
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return byte
	 */
	public static byte asByte(String str, byte defaultValue) {
		try {
			return Byte.decode(str.trim()).byteValue();
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ char
	 * @param str String
	 * @return char
	 */
	public static char asCharacter(String str) {
		return asCharacter(str, '\0');
	}

	/**
	 * �� String ����Ϊ Character
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Character
	 */
	public static Character asCharacter(String str, Character defaultValue) {
		try {
			return new Character(str.trim().charAt(0));
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (IndexOutOfBoundsException indexoutofboundsexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ char
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return char
	 */
	public static char asCharacter(String str, char defaultValue) {
		try {
			return str.trim().charAt(0);
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (IndexOutOfBoundsException indexoutofboundsexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ double
	 * @param str String
	 * @return double
	 */ 
	public static double asDouble(String str) {
		return asDouble(str, 0.0D);
	}

	/**
	 * �� String ����Ϊ Double
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Double
	 */
	public static Double asDouble(String str, Double defaultValue) {
		try {
			return new Double(str.trim());
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ double
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return double
	 */
	public static double asDouble(String str, double defaultValue) {
		try {
			return (new Double(str.trim())).doubleValue();
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ float
	 * @param str String
	 * @return float
	 */
	public static float asFloat(String str) {
		return asFloat(str, 0.0F);
	}

	/**
	 * �� String ����Ϊ Float
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Float
	 */
	public static Float asFloat(String str, Float defaultValue) {
		try {
			return new Float(str.trim());
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ float
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return float
	 */
	public static float asFloat(String str, float defaultValue) {
		try {
			return (new Float(str.trim())).floatValue();
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ int
	 * @param str String
	 * @return int
	 */
	public static int asInteger(String str) {
		return asInteger(str, 0);
	}

	/**
	 * �� String ����Ϊ Integer
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Integer
	 */
	public static Integer asInteger(String str, Integer defaultValue) {
		try {
			return Integer.decode(str.trim());
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 *  �� String ����Ϊ int
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return int
	 */
	public static int asInteger(String str, int defaultValue) {
		try {
			return Integer.decode(str.trim()).intValue();
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ long
	 * @param str String
	 * @return long
	 */
	public static long asLong(String str) {
		return asLong(str, 0L);
	}

	/**
	 * �� String ����Ϊ Long
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Long
	 */
	public static Long asLong(String str, Long defaultValue) {
		try {
			return Long.decode(str.trim());
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ long
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return long
	 */
	public static long asLong(String str, long defaultValue) {
		try {
			return Long.decode(str.trim()).longValue();
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ short
	 * @param str String
	 * @return short
	 */
	public static short asShort(String str) {
		return asShort(str, (short) 0);
	}

	/**
	 * �� String ����Ϊ Short
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Short
	 */
	public static Short asShort(String str, Short defaultValue) {
		try {
			return Short.decode(str.trim());
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ short
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return short
	 */
	public static short asShort(String str, short defaultValue) {
		try {
			return Short.decode(str.trim()).shortValue();
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		} catch (NumberFormatException numberformatexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ String
	 * @param str String
	 * @return String
	 */
	public static String asString(String str) {
		return asString(str, "", "");
	}

	/**
	 * �� String ����Ϊ String
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return String
	 */
	public static String asString(String str, String defaultValue) {
		return asString(str, defaultValue, defaultValue);
	}

	/**
	 * �� String ����Ϊ String
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @param emptyStringValue ""��Ӧ��ֵ
	 * @return String
	 */
	public static String asString(String str, String defaultValue, String emptyStringValue) {
		try {
			return str.equals("") ? emptyStringValue : str;
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		}
	}

	/**
	 * �� String ����Ϊ Date
	 * @param str String
	 * @return Date
	 */
	public static Date asDate(String str) {
		return asDate(str, new Date(), null);
	}

	/**
	 * �� String ����Ϊ Date
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @return Date
	 */
	public static Date asDate(String str, Date defaultValue) {
		return asDate(str, defaultValue, null);
	}

	/**
	 * �� String ����Ϊ Date
	 * @param str String
	 * @param defaultValue Ĭ��ֵ
	 * @param pattern String
	 * @return Date
	 */
	public static Date asDate(String str, Date defaultValue, String pattern) {
		DateFormat formatter = new SimpleDateFormat(pattern != null ? pattern : DEFAULT_DATE_PATTTERN);
		try {
			return formatter.parse(str);
		} catch (ParseException parseexception) {
			return defaultValue;
		} catch (NullPointerException nullpointerexception) {
			return defaultValue;
		}
	}

	/**
	 * ��String��ָ����type���н���
	 * @param type ָ������ �������Ͷ����Date��
	 * @param str String
	 * @return Object
	 */
	public static Object asType(Class type, String str) {
		if (type.isAssignableFrom(java.lang.String.class))
			return asString(str, "", "");
		if (type.isAssignableFrom(java.lang.Integer.class) || type.equals(Integer.TYPE))
			return asInteger(str, new Integer(0));
		if (type.isAssignableFrom(java.lang.Double.class) || type.equals(Double.TYPE))
			return asDouble(str, new Double(0.0D));
		if (type.isAssignableFrom(java.lang.Boolean.class) || type.equals(Boolean.TYPE))
			return asBoolean(str, Boolean.FALSE);
		if (type.isAssignableFrom(java.lang.Float.class) || type.equals(Float.TYPE))
			return asFloat(str, new Float(0.0F));
		if (type.isAssignableFrom(java.lang.Long.class) || type.equals(Long.TYPE))
			return asLong(str, new Long(0L));
		if (type.isAssignableFrom(java.lang.Short.class) || type.equals(Short.TYPE))
			return asShort(str, new Short((short) 0));
		if (type.isAssignableFrom(java.lang.Byte.class) || type.equals(Byte.TYPE))
			return asByte(str, new Byte((byte) 0));
		if (type.isAssignableFrom(java.lang.Character.class) || type.equals(Character.TYPE))
			return asCharacter(str, new Character('\0'));
		if (type.isAssignableFrom(java.math.BigDecimal.class))
			return asBigDecimal(str, new BigDecimal(BigInteger.ZERO));
		if (type.isAssignableFrom(java.math.BigInteger.class))
			return asBigInteger(str, BigInteger.ZERO);
		if (type.isAssignableFrom(java.util.Date.class))
			return asDate(str, new Date(), null);
		else
			return null;
	}

	/**
	 * ��String��ָ����type���н���
	 * @param type ָ������ �������Ͷ����Date��
	 * @param str String
	 * @param defaultValue Ĭ��ֵ ��strΪnull��""ʱ��ֵ��
	 * @return Object
	 */
	public static Object asType(Class type, String str, Object defaultValue) {
		if (type.isAssignableFrom(java.lang.String.class))
			return asString(str, (String) defaultValue);
		if (type.isAssignableFrom(java.lang.Integer.class) || type.equals(Integer.TYPE))
			return asInteger(str, (Integer) defaultValue);
		if (type.isAssignableFrom(java.lang.Double.class) || type.equals(Double.TYPE))
			return asDouble(str, (Double) defaultValue);
		if (type.isAssignableFrom(java.lang.Boolean.class) || type.equals(Boolean.TYPE))
			return asBoolean(str, (Boolean) defaultValue);
		if (type.isAssignableFrom(java.lang.Float.class) || type.equals(Float.TYPE))
			return asFloat(str, (Float) defaultValue);
		if (type.isAssignableFrom(java.lang.Long.class) || type.equals(Long.TYPE))
			return asLong(str, (Long) defaultValue);
		if (type.isAssignableFrom(java.lang.Short.class) || type.equals(Short.TYPE))
			return asShort(str, (Short) defaultValue);
		if (type.isAssignableFrom(java.lang.Byte.class) || type.equals(Byte.TYPE))
			return asByte(str, (Byte) defaultValue);
		if (type.isAssignableFrom(java.lang.Character.class) || type.equals(Character.TYPE))
			return asCharacter(str, (Character) defaultValue);
		if (type.isAssignableFrom(java.math.BigDecimal.class))
			return asBigDecimal(str, (BigDecimal) defaultValue);
		if (type.isAssignableFrom(java.math.BigInteger.class))
			return asBigInteger(str, (BigInteger) defaultValue);
		if (type.isAssignableFrom(java.util.Date.class))
			return asDate(str, (Date) defaultValue);
		else
			return null;
	}

	/**
	 * ��Object��ָ����type���н���
	 * @param type ָ������
	 * @param obj Object
	 * @return Object
	 */
	public static Object asType(Class type, Object obj) {
		if (!type.equals(java.lang.String.class) && type.isInstance(obj))
			return obj;
		if (obj == null || (obj instanceof String))
			return asType(type, (String) obj);
		if ((obj instanceof Date) && (java.lang.String.class).isAssignableFrom(type))
			return (new SimpleDateFormat(DEFAULT_DATE_PATTTERN)).format((Date) obj);
		if ((obj instanceof Number) && (java.lang.Number.class).isAssignableFrom(type)) {
			Number num = (Number) obj;
			if (type.isAssignableFrom(java.lang.Number.class))
				return num;
			if (type.isAssignableFrom(java.lang.Integer.class))
				return new Integer(num.intValue());
			if (type.isAssignableFrom(java.lang.Double.class))
				return new Double(num.doubleValue());
			if (type.isAssignableFrom(java.lang.Float.class))
				return new Float(num.floatValue());
			if (type.isAssignableFrom(java.lang.Long.class))
				return new Long(num.longValue());
			if (type.isAssignableFrom(java.lang.Short.class))
				return new Short(num.shortValue());
			if (type.isAssignableFrom(java.lang.Byte.class))
				return new Byte(num.byteValue());
			if (type.isAssignableFrom(java.math.BigInteger.class))
				return (new BigDecimal(num.toString())).toBigInteger();
			if (type.isAssignableFrom(java.math.BigDecimal.class))
				return new BigDecimal(num.toString());
		}
		return asType(type, obj.toString());
	}

	/**
	 * ��Object��ָ����type���н���
	 * @param type ָ������
	 * @param obj Object
	 * @param defaultValue Ĭ��ֵ
	 * @return Object
	 */
	public static Object asType(Class type, Object obj, Object defaultValue) {
		if (!type.equals(java.lang.String.class) && type.isInstance(obj))
			return obj;
		if (obj == null || (obj instanceof String))
			return asType(type, (String) obj, defaultValue);
		if ((obj instanceof Date) && (java.lang.String.class).isAssignableFrom(type))
			return (new SimpleDateFormat(DEFAULT_DATE_PATTTERN)).format((Date) obj);
		if ((obj instanceof Number) && (java.lang.Number.class).isAssignableFrom(type)) {
			Number num = (Number) obj;
			if (type.isAssignableFrom(java.lang.Number.class))
				return num;
			if (type.isAssignableFrom(java.lang.Integer.class))
				return new Integer(num.intValue());
			if (type.isAssignableFrom(java.lang.Double.class))
				return new Double(num.doubleValue());
			if (type.isAssignableFrom(java.lang.Float.class))
				return new Float(num.floatValue());
			if (type.isAssignableFrom(java.lang.Long.class))
				return new Long(num.longValue());
			if (type.isAssignableFrom(java.lang.Short.class))
				return new Short(num.shortValue());
			if (type.isAssignableFrom(java.lang.Byte.class))
				return new Byte(num.byteValue());
			if (type.isAssignableFrom(java.math.BigInteger.class))
				return (new BigDecimal(num.toString())).toBigInteger();
			if (type.isAssignableFrom(java.math.BigDecimal.class))
				return new BigDecimal(num.toString());
		}
		return asType(type, obj.toString(), defaultValue);
	}

	/**
	 * ��ȡclassName
	 * @param cls Class
	 * @return String
	 */
	public static String getClassName(Class cls) {
		return getClassName(cls.getName());
	}

	/**
	 * ��ݸ��Ӧ��ȫ���ȡ����
	 * @param fullName Ӧ��ȫ��
	 * @return String
	 */
	public static String getClassName(String fullName) {
		if (fullName == null) {
			return null;
		} else {
			fullName = fullName.trim();
			String className = fullName.substring(fullName.lastIndexOf('.') + 1).replace('$', '.').trim();
			return className.equals("") ? null : className;
		}
	}

	/**
	 * ָ������Ϊfalse���ַ�
	 */
	private static String FALSE_STRINGS[] = { "false", "null", "nul", "off", "no", "n" };
	/**
	 * Ĭ������ģʽ
	 */
	private static String DEFAULT_DATE_PATTTERN = "yyyy-M-d";

	/**
	 * ת��UTF8���ַ�ΪUnicode
	 * @param instr String
	 * @return String
	 * @throws IOException IOException
	 */
	public static String convertUTF8String2Unicode(String instr) throws IOException {
		// byte[] strbytes = instr.getBytes();
		int charindex = instr.length();
		int actualValue;
		int inputValue;
		StringBuffer sbtemp = new StringBuffer();

		for (int i = 0; i < charindex;) {

			actualValue = -1;
			inputValue = instr.charAt(i++);

			inputValue &= 0xff;

			if ((inputValue & 0x80) == 0) {
				actualValue = inputValue;
			} else if ((inputValue & 0xF8) == 0xF0) {
				actualValue = (inputValue & 0x1f) << 18;

				int nextByte = instr.charAt(i++) & 0xff;
				if ((nextByte & 0xC0) != 0x80)
					throw new IOException("Invalid UTF-8 format");
				actualValue += (nextByte & 0x3F) << 12;

				nextByte = instr.charAt(i++) & 0xff;
				if ((nextByte & 0xC0) != 0x80)
					throw new IOException("Invalid UTF-8 format");
				actualValue += (nextByte & 0x3F) << 6;

				nextByte = instr.charAt(i++) & 0xff;
				if ((nextByte & 0xC0) != 0x80)
					throw new IOException("Invalid UTF-8 format");
				actualValue += (nextByte & 0x3F);
			} else if ((inputValue & 0xF0) == 0xE0) {
				actualValue = (inputValue & 0x1f) << 12;

				int nextByte = instr.charAt(i++) & 0xff;
				if ((nextByte & 0xC0) != 0x80)
					throw new IOException("Invalid UTF-8 format");
				actualValue += (nextByte & 0x3F) << 6;

				nextByte = instr.charAt(i++) & 0xff;
				if ((nextByte & 0xC0) != 0x80)
					throw new IOException("Invalid UTF-8 format");
				actualValue += (nextByte & 0x3F);
			} else if ((inputValue & 0xE0) == 0xC0) {
				actualValue = (inputValue & 0x1f) << 6;

				int nextByte = instr.charAt(i++) & 0xff;
				if ((nextByte & 0xC0) != 0x80)
					throw new IOException("Invalid UTF-8 format");
				actualValue += (nextByte & 0x3F);
			}
			sbtemp.append((char) actualValue);
		}

		return sbtemp.toString();
	}

	/**
	 * ��Unicode�ַ�ת��ΪUTF8���ֽ�����
	 * @param instr String
	 * @return byte[]
	 */
	public static byte[] convertUnicode2UTF8Byte(String instr) {
		int len = instr.length();
		byte[] abyte = new byte[len << 2];
		int j = 0;
		for (int i = 0; i < len; i++) {
			char c = instr.charAt(i);

			if (c < 0x80) {
				abyte[j++] = (byte) c;
			} else if (c < 0x0800) {
				abyte[j++] = (byte) (((c >> 6) & 0x1F) | 0xC0);
				abyte[j++] = (byte) ((c & 0x3F) | 0x80);
			} else if (c < 0x010000) {
				abyte[j++] = (byte) (((c >> 12) & 0x0F) | 0xE0);
				abyte[j++] = (byte) (((c >> 6) & 0x3F) | 0x80);
				abyte[j++] = (byte) ((c & 0x3F) | 0x80);
			} else if (c < 0x200000) {
				abyte[j++] = (byte) (((c >> 18) & 0x07) | 0xF8);
				abyte[j++] = (byte) (((c >> 12) & 0x3F) | 0x80);
				abyte[j++] = (byte) (((c >> 6) & 0x3F) | 0x80);
				abyte[j++] = (byte) ((c & 0x3F) | 0x80);
			}
		}

		byte[] retbyte = new byte[j];
		for (int i = 0; i < j; i++) {
			retbyte[i] = abyte[i];
		}
		return retbyte;
	}

	/**
	 * ��ISO10646ת��ΪUnicode
	 * @param myByte byte[]
	 * @return String
	 */
	public static String ISO106462Unicode(byte[] myByte) {
		String result = new String("");

		StringBuffer sb = new StringBuffer("");
		try {
			/* ���ַ�ת����byte���� */
			// byte[] myByte= str.getBytes("ISO10646");
			int len = myByte.length;

			for (int i = 0; i < len; i = i + 2) {
				byte hiByte = myByte[i];
				byte loByte = myByte[i + 1];

				int ch = hiByte << 8;
				ch = ch & 0xff00;
				ch += loByte & 0xff;

				sb.append((char) ch);
			}

			result = new String(sb.toString());

		} catch (Exception e) {
			System.out.println("Encoding Error");
		}
		return result;
	}

	/**
	 * ��Unicode�ַ�ת��Ϊ�ֽ�
	 * @param s String
	 * @return byte[]
	 */
	public static byte[] Unicode2Byte(String s) {
		int len = s.length();
		byte abyte[] = new byte[len << 1];
		int j = 0;
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			abyte[j++] = (byte) (c & 0xff);
			abyte[j++] = (byte) (c >> 8);
		}

		return abyte;
	}

	/**
	 * ���ַ�ת��������
	 * @param str String
	 * @return int
	 */
	public static int ConvertToInt(String str){
		int num = 0;
		try{
			num = Integer.parseInt(str);
		}catch(Exception e){
			num = 0;
		}
		return num;
	}
	/**
	 * ���ַ�ת�����������
	 * @param str String
	 * @return Integer
	 */
	public static Integer ConvertToInteger(String str){
		Integer num = 0;
		try{
			num = Integer.parseInt(str);
		}catch(Exception e){
			num = 0;
		}
		return num;
	}

	/**
	 * ���ַ�ת���ɳ�����
	 * @param str String
	 * @return long
	 */
	public static long ConvertToLong(String str){
		long num = 0L;
		try{
			num = Long.parseLong(str);
		}catch(Exception e){
			num = 0L;
		}
		return num;
	}


	/**
	 * ���ַ�ת���ɳ��������
	 * @param str String
	 * @return Long
	 */
	public static Long ConvertToLonger(String str){
		Long num = 0L;
		try{
			num = Long.parseLong(str);
		}catch(Exception e){
			num = 0L;
		}
		return num;
	}
	/**
	 *
	 */
	private static final String[] LOWERCASES = { "O", "һ", "��", "��", "��", "��",
		"��", "��", "��", "��", "ʮ" };
	/**
	 *
	 */
	private static final String[] UPPERCASES = { "��", "Ҽ", "��", "��", "��", "��",
		"½", "��", "��", "��", "ʰ" };

	/**
	 * ���ַ��е�����ת���ɼ����д���ĵĸ�ʽ
	 *
	 * @param transStr String
	 * @return ���ؽ��
	 */
	public static String lowerCaseTrans(String transStr) {
		if (null == transStr) {
			return null;
		}
		StringBuffer sbTmp = new StringBuffer();
		for (int i = 0; i < transStr.length(); i++) {
			String stmp = String.valueOf(transStr.charAt(i));
			if ("0123456789".indexOf(stmp) >= 0) {
				int irec = Integer.parseInt(stmp);
				sbTmp.append(LOWERCASES[irec]);
			} else {
				sbTmp.append(stmp);
			}
		}
		return sbTmp.toString();
	}
	/**
	 * ���ַ��е�����ת���ɷ����д���ĵĸ�ʽ
	 *
	 * @param transStr String
	 * @return ���ؽ��
	 */
	public static String upperCaseTrans(String transStr) {
		if (null == transStr) {
			return null;
		}
		StringBuffer sbTmp = new StringBuffer();
		for (int i = 0; i < transStr.length(); i++) {
			String stmp = String.valueOf(transStr.charAt(i));
			if ("0123456789".indexOf(stmp) >= 0) {
				int irec = Integer.parseInt(stmp);
				sbTmp.append(UPPERCASES[irec]);
			} else {
				sbTmp.append(stmp);
			}
		}
		return sbTmp.toString();
	}
	/**
	 * ���ַ��е�����ת���ɼ����д���ĵĸ�ʽ(��:1977��11��08��->һ��������ʮһ�°���)
	 *
	 * @param transStr String
	 * @return ���ؽ��
	 */
	public static String lowerDateTrans(String transStr) {
		if (null == transStr) {
			return null;
		}
		String sbTmp = "";
		try{
			String syear = transStr.substring(0, 4);
			int imon = Integer.parseInt(transStr.substring(5, 7));
			int iday = Integer.parseInt(transStr.substring(8, transStr.length() - 1));
			sbTmp += lowerCaseTrans(syear) + "��";
			if(imon>9 && imon<19) {
				String mtmp = String.valueOf(imon).substring(1, 2);
				if("0".equals(mtmp)){
					sbTmp += "ʮ";
				} else {
					sbTmp += "ʮ" + lowerCaseTrans(mtmp);
				}
			} else if(imon > 19) {
				String mtmp = lowerCaseTrans(String.valueOf(imon));
				sbTmp += mtmp.substring(0, 1) + "ʮ" + mtmp.substring(1, 2);
			} else {
				sbTmp += lowerCaseTrans(String.valueOf(imon));
			}
			sbTmp += "��";
			if(iday>9 && iday<19) {
				String dtmp = String.valueOf(iday).substring(1, 2);
				if("0".equals(dtmp)){
					sbTmp += "ʮ";
				} else {
					sbTmp += "ʮ" + lowerCaseTrans(dtmp);
				}
			} else if(iday > 19) {
				String dtmp = lowerCaseTrans(String.valueOf(iday));
				sbTmp += dtmp.substring(0, 1) + "ʮ" + dtmp.substring(1, 2);
			} else {
				sbTmp += lowerCaseTrans(String.valueOf(iday));
			}
			sbTmp += "��";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		return sbTmp;
	}
	/**
	 * ���ַ��е�����ת���ɷ����д���ĵĸ�ʽ(��:1977��11��08��->Ҽ��������ʰҼ�°���)
	 *
	 * @param transStr String
	 * @return ���ؽ��
	 */
	public static String upperDateTrans(String transStr) {
		if (null == transStr) {
			return null;
		}
		String sbTmp = "";
		try{
			String syear = transStr.substring(0, 4);
			int imon = Integer.parseInt(transStr.substring(5, 7));
			int iday = Integer.parseInt(transStr.substring(8, transStr.length() - 1));
			sbTmp += upperCaseTrans(syear) + "��";
			if(imon>9 && imon<19) {
				String mtmp = String.valueOf(imon).substring(1, 2);
				if("0".equals(mtmp)){
					sbTmp += "ʰ";
				} else {
					sbTmp += "ʰ" + upperCaseTrans(mtmp);
				}
			} else if(imon > 19) {
				String mtmp = upperCaseTrans(String.valueOf(imon));
				sbTmp += mtmp.substring(0, 1) + "ʰ" + mtmp.substring(1, 2);
			} else {
				sbTmp += upperCaseTrans(String.valueOf(imon));
			}
			sbTmp += "��";
			if(iday>9 && iday<19) {
				String dtmp = String.valueOf(iday).substring(1, 2);
				if("0".equals(dtmp)){
					sbTmp += "ʰ";
				} else {
					sbTmp += "ʰ" + upperCaseTrans(dtmp);
				}
			} else if(iday > 19) {
				String dtmp = upperCaseTrans(String.valueOf(iday));
				sbTmp += dtmp.substring(0, 1) + "ʰ" + dtmp.substring(1, 2);
			} else {
				sbTmp += upperCaseTrans(String.valueOf(iday));
			}
			sbTmp += "��";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		return sbTmp;
	}
	/**
	 * �е��ַ��д��ұ߿�ʼ��ͬ���ַ�
	 *
	 * @param str ��Ҫ������ַ�
	 * @param chr ��Ҫ�е����ַ�
	 * @return String �е�����ַ�
	 */
	public static String removeRigthChar(String str, char chr) {
		if (str == null || str.trim().length() < 1) {
			return null;
		}

		char[] chrArray = str.toCharArray();
		int iCount = 0;
		for (int i = chrArray.length - 1; i >= 0; i--) {
			if (chrArray[i] != chr) {
				break;
			}
			++iCount;
		}

		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < chrArray.length - iCount; j++) {
			buf.append(chrArray[j]);
		}
		return buf.toString();
	}
	/**
	 * ���ַ��м��ϻس����з������ı�������
	 *
	 * @param str ����ԭ�ַ�
	 * @param len ÿ�е�����
	 * @return String �����ַ�
	 */
	public static String warp(String str, int len) {
		StringBuffer buffer;
		if (StringUtil.isEmpty(str))
			return str;
		if (len <= 0 || len >= str.length())
			return str;
		buffer = new StringBuffer();
		StringReader reader = new StringReader(str);
		BufferedReader r = new BufferedReader(reader);

		// str = str.replaceAll("\n\r", ""); //�滻���ַ������еĻس����з�
		String line;
		try {
			while ((line = r.readLine()) != null) {
				for (int i = 0; i < line.length(); i += len) {
					if (i + len < line.length())
						buffer.append(line.substring(i, i + len));
					else
						buffer.append(line.substring(i, line.length()));
					buffer.append("\n");
				}

			}
		} catch (IOException ioexception) {
		} finally {
			try {
				r.close();
			} catch (IOException ioexception1) {
			}
		}
		return buffer.toString();
	}
	/**
	 * ת����д��ͷ�ַ�
	 *
	 * @param str ����ԭ�ַ�
	 * @return String �����ַ�
	 */
	public static String capitalize(String str) {
		if (StringUtil.isEmpty(str)) {
			return str;
		} else {
			StringBuffer buffer = new StringBuffer(str);
			buffer.setCharAt(0, str.substring(0, 1).toUpperCase().charAt(0));
			return buffer.toString();
		}
	}
	/**
	 * ת��Сд��ͷ�ַ�
	 *
	 * @param str ����ԭ�ַ�
	 * @return String �����ַ�
	 */
	public static String deCapitalize(String str) {
		if (StringUtil.isEmpty(str)) {
			return str;
		} else {
			StringBuffer buffer = new StringBuffer(str);
			buffer.setCharAt(0, str.substring(0, 1).toLowerCase().charAt(0));
			return buffer.toString();
		}
	}
	/**
	 * ��ѯ�빦�ܣ�������ĺ��֣�����ת��ΪӢ�ļ�д���ṩ��ѯʹ��
	 *
	 * @param character �����ַ�
	 * @return String ���ؽ��
	 */
	public static String getQueryCode(String character) {
		StringBuffer result=new StringBuffer("");
		  int  j=0,k=0,l=0,q=0;
		  char zm1[]={'A','B','C','D','E','F','G','H','J','K','L','M','N','O','P','Q','R','S','T','W','X','Y','Z'};
		  long n,m,p;
		  char firstzm;

		 String Strhz[]={" � �� �� �� �� � �� �� �� �� �� �� �� �� �� �� � �� �� "+
              " �� �� �� � � �� �� �� �� �� �� �� �� �� �� ",""+
              " �� �� � � �� �� �� �� �� �� �� �� �� � � �� �� � �� "+
              " �� �� �� � �� �� �� �� �� �� �� �� �� �� � �� �� �� �� �� �� "+
              " �� �� �� � �� � ذ �� �� �� �� ݩ �� ޵ �� �� �� �� � �� � "+
              " �� �� � �� � �� � � �� �� �� �� �� �� �� �� �� �� �� �� �� "+
              " �� �� �� � �� � �� � �� �� �� �� � �� � � � �� �� � �� "+
              " �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� "+
              " � �� �� �� � �� �� � �� �� �� �� ߲ �� � �� �� �� �� ",""+
              " �� �� �� �� � �� �� �� �� � �� �� � � ɲ �� �� � �� �� � � � "+
              " � �� �� �� �� ٭ �� �� � �� �� �� �� �� �� � �� �� � �� �� "+
              " �� �� � �� �� �� �� �� �� �� �� � �� � �� �� � �� �� �� �� "+
              " �� �� �� �� � �� �� �� �� �� �� � � � �� �� �� �� ة �� �� "+
              " �� �� �� � �� �� �� �� �� �� �� ܯ �� ߳ �� � �� �� �� � �� "+
              " � � �� � � �� �� �� �� �� �� �� � � �� �� ٱ �� � � �� "+
              " �� ء ۻ �� �� � �� �� �� �� �� �� �� �� �� �� � �� � �� �� "+
              " � �� �� �� �� � � �� �� �� �� �� � � �� �� �� � �� �� �� "+
              " �� �� �� ߥ �� �� �� �� � � �� � �� �� �� �� �� �� � �� �� �� �� �� ",""+
              " �� �� � �� � � �� �� �� �� �� ܤ ߰ ߾ � �� �� � �� �� "+
              " �� �� �� � � �� �� �� �� �� �� �� �� �� �� � � �� ߶ �� �� "+
              " � �� �� � �� �� � �� �� �� ص �� ڮ �� ۡ �� ݶ �� � � �� "+
              " � �� �� �� �� �� �� �� �� �� �� �� �� �� � � �� �� �� � �� "+
              " �� �� ܦ �� � � � �� �� �� �� �� � �� �� �� � �� �� �� �� "+
              " �� �� �� � � � �� �� �� � �� � � �� ܶ � �� �� � � �� "+
              " �� �� � �� �� � � �� �� �� � �� �� �� �� �� �� �� �� � �� �� �� �� ",""+
              " ج �� �� �� ݭ �� �� � �� � �� �� � �� �� � �� �� �� �� ٦ �� �� �� � �� ",""+
              " �� �� ެ �� � �� �� � �� �� �� �� �� �� �� �� �� �� � �� �� � � �� "+
              " � �� �� �� �� �� �� �� �� �� � �� �� �� �� ٺ ۺ �� �� �� � � "+
              " �� �� ۮ ܽ �� �� �� ݳ �� �� ߻ � �� �� �� �� �� � � �� �� "+
              " �� �� �� �� � �� � � �� �� �� �� �� �� �� �� ",""+
              " �� �� �� � �� ؤ �� �� � �� �� �� �� ߦ �� �� �� � �� � � �� �� "+
              " � � �� غ ھ ۬ ޻ �� �� �� � � �� �� ت �� �� �� ܪ �� �� "+
              " � �� �� �� �� � � �� �� ب ݢ �� �� �� �� � �� �� �� � �� "+
              " �� ڸ � �� �� �� �� �� � �� �� �� �� ڬ �� �� �� �� �� �� �� "+
              " �� �� � � � �� �� � �� �� �� �� �� �� �� �� ڴ �� �� �� � "+
              " �� �� ݸ �� �� �� �� �� �� �� �� �� �� �� Ȳ �� �� �� � � �� "+
              " �� �� � �� �� �� �� � �� �� �� �� �� �� �� �� �� � � � �� �� �� ",""+
              " �� Ϻ �� �� �� �� �� �� �� �� �� �� � �� �� �� � �� �� �� � �� �� �� "+
              " �� �� ޶ �� �� � � � � � � ڭ �� �� �� �� �� �� �� � �� �� "+
              " ޿ �� �� �� �� ڧ ݦ ޮ ް �� �� ܩ �� �� �� �� �� �� �� �� �� �� "+
              " � � �� � � �� �� �� �� � �� �� �� �� �� � �� �� �� �� �� �� "+
              " �� �� � �� �� ۨ ۼ �� ߧ �� � � � �� � �� �� �� �� �� �� �� "+
              " �� �� �� � �� � � �� �� �� �� ޥ �� �� � �� � � �� �� �� �� "+
              " �� � � � �� ڻ �� �� �� �� ޽ ߫ �� � �� �� �� �� � ",""+
              " آ ؽ �� �� ٥ �� ڵ �� ܸ �� �� �� ު �� ߴ �� �� �� � �� � �� �� �� "+
              " �� � �� � � �� �� �� � � � �� � � � �� �� �� �� �� �� �� "+
              " �� �� �� �� ٤ ۣ �� � � �� �� � �� � �� �� �� �� �� �� �� "+
              " �� �� �� �� �� �� �� �� �� �� � �� �� �� � � � �� �� � �� "+
              " �� � �� �� �� �� �� �� �� �� �� �� �� � � �� �� �� �� �� �� "+
              " �� ܴ �� �� �� � �� �� � � � �� �� �� �� �� �� ڦ ڵ �� � "+
              " � � �� �� �� �� � � �� �� �� �� �� �� ݣ �� �� �� �� � �� "+
              " � �� �� �� �� �� �� �� ݼ � � �� �� �� � �� �� �� � �� �� "+
              " �� �� �� � �� �� �� � �� �� �� �� ڪ �� �� �� �� �� �� � �� "+
              " � � � �� �� � �� � �� �� �� �� �� �� �� �� �� ۲ �� � �� "+
              " �� �� �� �� �� �� �� �� ާ � �� �� �� � �� �� �� �� �� �� �� "+
              " �� �� �� �� �� �� �� ",""+
              " �� �� �� �� �� �� �� �� �� � �� ٩ ݨ �� � �� � �� �� �� �� �� "+
              " �� �� � � � �� �� � �� �� � � �� � �� �� � � �� �� "+
              " �� � �� �� �� �� ޢ ߵ �� �� �� ܥ � � �� ٨ �� �� ۦ �� "+
              " �� � �� �� ڲ ڿ �� �� �� �� �� �� ظ �� �� �� �� �� � � "+
              " � � �� �� � �� �� �� �� � �� �� � �� �� �� �� �� ",""+
              " �� �� � �� �� �� �� � �� �� �� � � �� � �� � � � �� �� "+
              " ݹ �� � �� � �� �� �� �� �� �� �� � �� �� �� �� ߷ �� �� "+
              " �� ڳ �� �� �� �� �� �� ܨ � ت ٳ ٵ ۪ �� �� ݰ �� ޼ ߿ "+
              " � � �� � �� � �� � �� �� �� �� �� �� �� �� � � � � "+
              " �� �� �� �� � �� �� �� �� �� �� �� �� �� �� �� �� � �� � "+
              " �� � �� �� � �� �� �� ܮ ݹ � �� �� �� ޤ �� �� � � �� "+
              " �� �� �� �� �� �� � �� �� �� �� �� �� �� �� �� �� � � � "+
              " �� �� ۹ �� �� �� �� � �� �� � �� �� �� �� � �� �� � � "+
              " �� � �� �� �� �� �� �� �� �� �� � �� �� � �� �� �� �� �� "+
              " �� �� ޤ ߣ �� �� �� �� �� �� � �� � �� �� �� � � �� �� "+
              " �� �� � �� �� �� �� �� �� �� �� �� � �� � �� � �� �� �� "+
              " �� �� �� � �� �� �� � �� �� �� � �� �� ",""+
              " �� �� �� � � ۽ ݤ �� ܬ � �� �� �� � �� �� �� �� �� �� �� "+
              " �� �� � �� � �� �� � � � � � �� �� ݮ �� � � �� � "+
              " �� �� �� �� �� �� � �� �� �� ޫ �� �� �� � �� � �� �� �� "+
              " �� �� �� �� � �� � �� �� �� �� �� �� �� �� �� �� �� �� �� "+
              " �� � �� �� � �� �� ؿ �� � �� �� � �� �� �� �� �� � �� "+
              " �� ڤ �� �� �� � �� �� �� �� �� �� �� �� �� � �� �� �� ٰ "+
              " �� �� �� �� �� �� �� �� �� �� � �� ",""+
              " �� �� �� �� ؾ ܵ �� �� � �� �� � �� �� �� ߭ �� �� �� ث �� �� � "+
              " � �� �� �� ګ � ٣ �� � �� �� � �� � �� �� �� إ �� �� "+
              " � �� �� �� �� �� �� ؿ �� �� � � �� �� �� �� �� � �� �� "+
              " �� �� � ٯ �� �� �� �� �� �� � �� �� �� �� �� � ",""+
              " ک �� � �� ��",""+
              " �� �� �� �� ٽ �� �� �� �� �� �� � �� �� �� �� � �� �� �� �� �� �� "+
              " �� � �� �� �� �� ܡ �� �� � ا �� �� �� �� ۯ �� �� ܱ �� "+
              " ߨ �� �� �� �� �� � �� � � � �� � �� �� � �� �� �� �� "+
              " �� �� �� �� �� �� ݳ �� �� �� �� �� � � �� � � �� � �� "+
              " � ٷ � �� �� �� ۶ �� �� � �� �� �� � �� �� �� �� �� �� "+
              " � � � �� �� �� ",""+
              " ؽ �� ٹ �� ܻ �� �� ݽ �� �� ޭ �� � � � �� � �� � �� �� � �� "+
              " �� �� � �� � �� �� �� �� �� �� �� �� �� �� �� �� ٻ �� �� "+
              " ܷ �� �� ݡ �� � � � � � � �� �� �� �� � �� � �� �� "+
              " �� �� �� � �� � �� �� �� �� �� �� �� ڽ �� �� � � � �� "+
              " �� �� �� �� ۧ � � � �� �� �� �� �� �� � �� �� �� �� � "+
              " �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� � �� �� "+
              " �� �� ٴ �� �� �� �� �� � �� � �� �� �� �� �� �� ڰ ۾ �� "+
              " ޡ ޾ � �� �� � �� � �� �� �� � � �� � �� �� �� ڹ �� "+
              " � � �� � �� �� �� �� �� �� � �� ",""+
              " �� �� �� �� � �� � �� �� �� �� � �� �� �� �� �� �� �� �� �� �� ޸ "+
              " �� � �� � �� � �� � �� �� ި �� � � ټ ",""+
              " ئ �� �� �� � �� �� � �� �� � ܣ �� �� �� �� �� �� � �� �� �� � "+
              " � �� �� �� �� �� ڨ ۷ �� �� �� � �� �� �� �� �� �� � �� "+
              " �� �� �� � �� �� �� ۿ �� �� � �� �� �� �� �� �� ߡ � �� "+
              " � � �� ڷ �� ݷ �� �� �� � �� �� �� �� �� �� �� �� �� �� "+
              " �� �� �� �� �� ݪ �� ߱ �� �� �� �� � �� �� �� �� �� �� �� "+
              " � �� �� ٿ �� �� �� �� �� � � � �� � �� � � �� �� �� "+
              " �� �� �� �� �� �� �� �� �� �� ٹ �� �� �� �� �� �� � �� �� "+
              " �� �� � �� �� �� �� �� �� ڡ ݿ �� �� �� � �� � ޴ � �� "+
              " �� �� � � �� �� �� �� �� � � � �� �� �� �� � �� �� �� "+
              " �� �� �� �� ݴ � �� �� �� � ݥ �� � � �� �� �� � �� �� �� �� ",""+
              " ̡ ̢ ̣ �� � �� �� �� �� � � �� �� �� ̦ ̪ ̭ ۢ ޷ ߾ �� �� �� "+
              " �� �� �� ̴ ̵ ̶ ̷ ̺ ̻ ̾ ۰ � � �� �� �� �� �� �� �� "+
              " � �� � �� �� � � �� �� ػ �� � � �� �� ߯ �� � � �� "+
              " �� �� �� �� �� �� �� �� �� �� �� � �� � � �� �� �� �� �� "+
              " �� �� �� �� �� � ٬ �� �� � �� �� �� �� �� �� �� �� �� ͡ "+
              " ͤ ͧ �� �� �� �� � �� �� �� �� �� ͩ ͪ ͫ ͮ ͱ ١ �� �� "+
              " �� �� � �� �� �� �� �� ܢ ݱ �� �� �� �� �� � �� ߯ �� � "+
              " �� �� ر ٢ �� �� �� �� �� �� �� �� �� �� �� ��  ",""+
              " �� � �� �� �� �� �� �� �� �� ܹ ݸ �� �� � �� �� � �� �� �� � �� "+
              " �� �� �� �� �� �� ޱ �� � �� �� � � �� �� � � �� � �� "+
              " � � � �� �� �� �� �� �� �� �� � �� �� ޳ �� �� ݫ � � "+
              " �� � �� �� أ �� �� �� �� �� �� �� �� �� � � �� �� �� �� "+
              " � �� �� �� �� �� �� �� �� ",""+
              " �� �� �� ۭ �� ݾ �� �� �� �� �� � �� � �� �� �� �� �� �� � � �� "+
              " �� �� � � �� �� � � �� �� �� �� �� �� �� �� �� �� �� � "+
              " �� �� �� �� �� �� �� ݲ ޺ � �� �� � � �� �� �� �� � �� "+
              " �� �� �� �� �� ܼ �� �� �� �� � � �� �� �� �� �� �� �� � "+
              " �� �� �� �� �� �� �� �� �� �� ޯ ߢ � �� �� � �� � �� � "+
              " �� � �� �� �� ض ܰ � � �� �� �� �� �� ߩ � � �� ܺ �� "+
              " � �� �� �� � �� �� ڼ �� �� ޣ � � �� �� �� �� � �� �� "+
              " �� �� �� �� �� �� �� �� �� � � �� �� �� �� �� �� �� Ѧ �� "+
              " �� � �� �� �� �� ۨ �� �� ަ � �� � � �� � �� �� ",""+
              " �� �� � �� � �� �� � � �� �� �� �� �� ٲ �� �� �� �� ۱ ۳ ܾ �� "+
              " �� �� �� �� �� �� �� �� �� �� �� �� �� �� � �� �� �� �� �� "+
              " �� � �� � �� Ҩ Կ ز س ߺ �� �� �� �� �� �� �� �� � �� "+
              " � �� �� ҭ Ү Ҵ ҷ Ҹ ק �� �� �� �� �� �� �� ҿ �� �� �� "+
              " �� �� �� ٫ ڱ �� �� ܲ �� �� ޲ �� �� �� ߮ ߽ �� �� �� �� "+
              " � �� � �� �� �� � �� �� �� � � �� �� �� �� �� �� �� � "+
              " � �� �� �� �� �� �� ط ۴ �� �� ܧ �� �� � �� � � �� � "+
              " �� � � � �� �� �� ۫ �� �� ݺ �� �� �� �� �� �� �� � �� "+
              " � � �� �� � � � ӷ Ӹ Ӻ ӻ ӽ Ӿ �� ٸ �� ܭ � � �� "+
              " �� � �� �� �� �� �� ٧ ݬ ݯ ݵ �� �� �� � �� �� � �� �� "+
              " �� �� �� �� �� �� �� �� خ ع �� ٶ �� �� �� �� �� �� �� �� "+
              " �� �� �� �� �� �� �� �� � �� � �� �� �� �� � � �� �� �� "+
              " � �� �� �� �� �� � �� �� �� �� �� �� �� �� �� ܫ ܾ �� �� "+
              " �� �� � �� � �� � � �� �� �� � �� �� � �� �� �� �� �� "+
              " �� �� ۩ ܿ �� � � � � �� �� � �� ",""+
              " �� �� �� � �� �� �� �� �� �� �� � �� �� �� �� �� �� � �� �� �� �� "+
              " �� � �� � �� ߸ �� � � �� �� �� �� �� � �� �� � �� �� "+
              " ۵ � �� � �� � � �� گ �� �� �� �� �� �� �� ߡ �� �� �� "+
              " �� �� �� �� �� �� �� �� �� �� �� �� �� � �� �� �� � � �� "+
              " �� �� �� �� � � � �� ֡ ں � �� �� ش �� ۤ �� �� �� �� "+
              " �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� � �� "+
              " �� �� �� �� �� �� �� �� ڣ �� �� � �� �� ݧ �� � �� � �� "+
	           " �� �� �� �� �� ٪ ۥ �� � � �� �� �� �� �� �� �� �� �� �� "+
              " �� �� �� �� �� �� ҷ ק ׭ �� �� �� � �� �� �� �� پ �� ߪ "+
              " � �� � �� �� �� � �� �� �� �� � �� � �� � �� � �� �� "+
              " �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� �� ۸ �� �� �� "+
              " �� �� �� ߬ �� �� ީ ߤ �� �� �� �� �� �� �� �� �� �� "};

		 int len=character.getBytes().length;
		 int i=0;
		  k=-1;
		  while (i<len)
		  {
		   j=(int)(character.getBytes()[i]&0xFF);
		   if (j<128)
		   {
			   k++;
			   result.append((char)character.getBytes()[i]);
			   i++;
		   }
		   else
		   {
			  k++;
		      n=(int)(character.getBytes()[i]&0xFF);
		      n=n*256;
		      m=(int)(character.getBytes()[i+1]&0xFF);
		      p=n+m;
		      //System.out.println(p);
		      if (p==41891) {firstzm='#';}
		      else if (p==41892) {firstzm='$';}
		      else if (p==41901) {firstzm='-';}
		      else if (p==41458) {firstzm='@';}
		      else if (p==41407) {firstzm=']';}
		      else if (p==41406) {firstzm='[';}
		      else if (p==41399) {firstzm='>';}
		      else if (p==41398) {firstzm='<';}
		      else if (p==41279) {firstzm='.';}

		      else if (p==41896) {firstzm='(';}
		      else if (p==41897) {firstzm=')';}
		      else if (p==41387) {firstzm='~';}
		      else if (p==41893) {firstzm='%';}
		      else if (p==41465) {firstzm='&';}
		      else if (p==41915) {firstzm=';';}
		      else if (p==41900) {firstzm=',';}
		      else if (p==41889) {firstzm='?';}
		      else if (p==41919) {firstzm='!';}

		      else if (p==41904) {firstzm='0';}
		      else if (p==41905) {firstzm='1';}
		      else if (p==41906) {firstzm='2';}
		      else if (p==41907) {firstzm='3';}
		      else if (p==41908) {firstzm='4';}
		      else if (p==41909) {firstzm='5';}
		      else if (p==41910) {firstzm='6';}
		      else if (p==41911) {firstzm='7';}
		      else if (p==41912) {firstzm='8';}
		      else if (p==41913) {firstzm='9';}


		      else if (p==41921) {firstzm='A';}
		      else if (p==41922) {firstzm='B';}
		      else if (p==41923) {firstzm='C';}
		      else if (p==41924) {firstzm='D';}
		      else if (p==41925) {firstzm='E';}
		      else if (p==41926) {firstzm='F';}
		      else if (p==41927) {firstzm='G';}
		      else if (p==41928) {firstzm='H';}
		      else if (p==41929) {firstzm='I';}
		      else if (p==41930) {firstzm='G';}
		      else if (p==41931) {firstzm='K';}
		      else if (p==41932) {firstzm='L';}
		      else if (p==41933) {firstzm='M';}
		      else if (p==41934) {firstzm='N';}
		      else if (p==41935) {firstzm='O';}
		      else if (p==41936) {firstzm='P';}
		      else if (p==41937) {firstzm='Q';}
		      else if (p==41938) {firstzm='R';}
		      else if (p==41939) {firstzm='S';}
		      else if (p==41940) {firstzm='T';}
		      else if (p==41941) {firstzm='U';}
		      else if (p==41942) {firstzm='V';}
		      else if (p==41943) {firstzm='W';}
		      else if (p==41944) {firstzm='X';}
		      else if (p==41945) {firstzm='Y';}
		      else if (p==41946) {firstzm='Z';}


		      else if (p>=45217&&p<=45252){firstzm='A';}
		      else if (p>=45253&&p<=45760){firstzm='B';}
		      else if (p>=45761&&p<=46317){firstzm='C';}
		      else if (p>=46318&&p<=46825){firstzm='D';}
		      else if (p>=46826&&p<=47009){firstzm='E';}
		      else if (p>=47010&&p<=47296){firstzm='F';}
		      else if (p>=47297&&p<=47613){firstzm='G';}
		      else if (p>=47614&&p<=48118){firstzm='H';}
		      else if (p>=48119&&p<=49061){firstzm='J';}
		      else if (p>=49062&&p<=49323){firstzm='K';}
		      else if (p>=49324&&p<=49895){firstzm='L';}
		      else if (p>=49896&&p<=50370){firstzm='M';}
		      else if (p>=50371&&p<=50613){firstzm='N';}
		      else if (p>=50614&&p<=50621){firstzm='O';}
		      else if (p>=50622&&p<=50905){firstzm='P';}
		      else if (p>=50906&&p<=51386){firstzm='Q';}
		      else if (p>=51387&&p<=51445){firstzm='R';}
		      else if (p>=51446&&p<=52217){firstzm='S';}
		      else if (p>=52218&&p<=52697){firstzm='T';}
		      else if (p>=52698&&p<=52979){firstzm='W';}
		      else if (p>=52980&&p<=53688){firstzm='X';}
		      else if (p>=53689&&p<=54480){firstzm='Y';}
		      else if (p>=54481&&p<=55289){firstzm='Z';}
		      else {firstzm='*';}

		      if (firstzm=='*')
				     {
					 byte[] tmp=new byte[2];
					 tmp[0]=character.getBytes()[i];
					 tmp[1]=character.getBytes()[i+1];
					 String s=new String(tmp);
					 for (l=0;l<23;l++)
					 {
						 if(Strhz[l].indexOf(s)>=0)
						 {
							 result.append(zm1[l]);
							 break;
						 }
					 }
					 if (l==23) result.append('*');
		       }else{
		    	   result.append(firstzm);
		       }
		      i++;
		      i++;
		    }

		   }
		  return result.toString();
	}

	/**
	 * �ж�һ���ַ��Ƿ���ֵ���ո�Ҳ������ֵ
	 * @param str String
	 * @return boolean
	 */
	public static boolean availableStr(String str){
		return (str!=null && !"".equals(str));
	}
	/**
	 *
	 * ���ַ���html���ȥ�����������ڷ�ֹhtml����ע��
	 *
	 * @param inputString ��html���ַ�
	 * @return ȥ��html��ǵ��ַ�
	 */
	 public static String html2Text(String inputString) {
		String htmlStr = inputString; // ��html��ǩ���ַ�
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // ����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // ����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // ����script��ǩ

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // ����style��ǩ

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // ����html��ǩ

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// �����ı��ַ�
	}

  	/**
	 * ��sql����Ҫת����ַ����ת�塣����SQL
	 * @param str ��Ҫת����ַ�������ֵ
	 * @param escapeCh �Զ���ת���ַ�һ��Ϊ'\'
	 * @return ���ַ�ǰ��'%;���%';�����������ַ�ת�壻�磺a%c���أ�"'%a\%c%'";
	 */
	public static String transferSql(String str, char escapeCh) {
		if (str != null && !"".equals(str)) {
			StringBuffer sbf = new StringBuffer(str.length());
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				if (ch == '%' || ch == '_' || ch == escapeCh) {
					sbf.append(escapeCh).append(ch);
				} else
					sbf.append(ch);
			}
			return sbf.toString();
		} else
			throw new java.lang.IllegalArgumentException("������ַ������ֵ��");
	}	 
	
	/**
	 * ��ȡ���ַ����˿ո�
	 * @param str String
	 * @return String
	 */
	public static String trim(String str) {
		if (str == null)
			return null;
		str = str.trim();
		if (str.length() == 0)
			return null;
		else
			return str;
	}	
	
  	/**
	 * �������ַ�У��
	 * 
	 * <ul>
	 * <li>@param value �ַ�
	 * <li>@return boolean
	 * </ul>
	 */
	public static boolean isHasCn(String value) {
		if (value == null)
			return false;
		if (value.equals(""))
			return false;
		char[] cs = value.toCharArray();

		for (int i = 0; i < cs.length; i++) {
			if (Character.getType(cs[i]) != 5)
				return false;
		}
		return true;
	}	
	
	/**
	 * �ж��ַ��Ƿ�������
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (str == null)
			return true;
		return str.matches("^[-\\+]?\\d+$");
	}

	/**
	 * �ж��ַ��Ƿ��Ǹ�����
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		if (str == null)
			return true;
		return str
				.matches("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$")
				|| isInteger(str);
	}
	
	/**
	 * �ж��ַ��еĴ����š������š�С�����Ƿ�ƥ��
	 * ���õ����ӣ�StringUtil.analyiz("fewe{f(sdd(f)a[j]sdk)j}f",0);
	 * @param text
	 * @param ip
	 * @return
	 * @author gaoLi
	 */
	private static LinkedList<Character> stack = new LinkedList<Character>();
	public static boolean analyiz(String text,int ip) {
		char temp = text.charAt(ip);
		char a;
		if (temp == '(' || temp == '[' || temp == '{') {
			stack.add(temp);
		} else if (temp == ')') {
			a = (Character) stack.getLast();
			if (a == '(') {
				stack.removeLast();
			}
		} else if (temp == ']') {
			a = (Character) stack.getLast();
			if (a == '[') {
				stack.removeLast();
			}
		} else if (temp == '}') {
			a = (Character) stack.getLast();
			if (a == '{') {
				stack.removeLast();
			}
		}
		if (stack.size() == 0 && ip == text.length() - 1) {
			//System.out.println("ƥ��");
			return true;
		} else if (stack.size() != 0 && ip == text.length() - 1) {
			//System.out.println("��ƥ��");
			return false;
		} else {
			return analyiz(text,ip + 1);
		}
	}	
	
	
	/**
	 * ����������
	 *
	 * @param args String[]
	 */
	public static void main(String[] args) {
//		String str = StringUtil.transferSql("abc%_ab", '\\');
//		System.out.println("str=" + str);
		StringUtil.analyiz("fewe{f(sdd(f)a[j]sdk)j}f",0);
	}
}
