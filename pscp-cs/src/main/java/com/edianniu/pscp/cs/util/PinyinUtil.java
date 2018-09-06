package com.edianniu.pscp.cs.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.apache.log4j.Logger;

/**
 * 
 * @author cyl
 *
 */
public class PinyinUtil {
	private static Logger logger = Logger.getLogger(PinyinUtil.class);
	private static final Map<Character, Map<String, Set<String>>> polyphones = new HashMap<Character, Map<String, Set<String>>>();
	private static final PinyinTree[] pTrees = new PinyinTree[26];
	private static final Set<String> singlePhones = new HashSet<String>();
	static {
		try {
			initPolyphones();
		} catch (Exception e) {
			logger.error(
					"error init poloyphones,method toPinyin() may not work correctly：{}",
					e);
		}
		try {
			initPinyins();
		} catch (Exception e) {
			logger.error(
					"error init pinyins method splitPinyin() may not work correctly：{}",
					e);
		}
		singlePhones.addAll(Arrays.asList(new String[] { "a", "e", "o" }));
	}
	/**
	 * 
	 * @param pinyinStr
	 * @param smart
	 * @return
	 */
	public static List<String> splitPinyin(String pinyinStr, boolean smart) {
		if (pinyinStr == null || pinyinStr.length() == 0) {
			return null;
		}
		pinyinStr = pinyinStr.toLowerCase().trim();
		if (pinyinStr.length() == 0) {
			return null;
		}
		List<String> tokens = new ArrayList<String>();
		int len = pinyinStr.length();
		StringBuilder pinyinBuilder = new StringBuilder();
		StringBuilder digitBuilder = new StringBuilder();
		char ch = pinyinStr.charAt(0);
		PinyinTree pTree = null;
		if (isAlpha(ch)) {
			pTree = pTrees[ch - 'a'];
			pinyinBuilder.append(ch);
		} else {
			digitBuilder.append(ch);
		}
		for (int i = 1; i < len; i++) {
			ch = pinyinStr.charAt(i);
			if (isDigit(ch)) {// process digits
				digitBuilder.append(ch);
				if (pinyinBuilder.length() > 0) {
					tokens.add(pinyinBuilder.toString());
					pinyinBuilder = new StringBuilder();
				}
			} else if (isAlpha(ch)) {// process english characters
				if (digitBuilder.length() > 0) {
					tokens.add(digitBuilder.toString());
					digitBuilder = new StringBuilder();
				}
				pinyinBuilder.append(ch);
				pTree = pTree == null ? null : pTree.nextTree(ch);
				if (pTree == null) {
					if (pinyinBuilder.length() > 1) {// a pinyin bound has found
						// we have gone too far by 1 character
						pinyinBuilder.deleteCharAt(pinyinBuilder.length() - 1);
						tokens.add(pinyinBuilder.toString());
						pinyinBuilder = new StringBuilder();
						pinyinBuilder.append(ch);
					}
					pTree = pTrees[ch - 'a'];
				}
			} else if (!smart) {
				// other character will be treated as spliter
				// if smart is used,any spliter will be ignored
				if (pinyinBuilder.length() > 0) {
					tokens.add(pinyinBuilder.toString());
					pinyinBuilder = new StringBuilder();
				}
				if (digitBuilder.length() > 0) {
					tokens.add(digitBuilder.toString());
					digitBuilder = new StringBuilder();
				}
			}
		}
		if (pinyinBuilder.length() > 0) {
			tokens.add(pinyinBuilder.toString());
		}
		if (digitBuilder.length() > 0) {
			tokens.add(digitBuilder.toString());
		}
		List<String> result = new ArrayList<String>();
		// merge single characters
		pinyinBuilder = new StringBuilder();
		for (String s : tokens) {
			if (s.length() == 1 && !singlePhones.contains(s)) {
				pinyinBuilder.append(s);
			} else {
				if (pinyinBuilder.length() > 0) {
					result.add(pinyinBuilder.toString());
					pinyinBuilder = new StringBuilder();
				}
				result.add(s);
			}
		}
		if (pinyinBuilder.length() > 0) {
			result.add(pinyinBuilder.toString());
		}
		return result;
	}

	/**
	 * <pre>
	 * 将汉语句子转化成拼音,非汉字字符将原样输出,拼音之间以空格隔开
	 *  eg. 轻舟已过万重山->qing zhou yi guo wan chong shan
	 * </pre>
	 * 
	 * @param s
	 *            汉语句子
	 * @return 转化后的拼音字符串
	 */
	public static String toPinyin(String s) {
		return toPinyin(s, false);
	}

	/**
	 * 将汉语句子转化成拼音,非汉字字符将原样输出,拼音之间以空格隔开
	 * 
	 * @param s
	 *            汉语句子
	 * @param appendCaptials
	 *            是否在拼音字符串的末尾添加汉字拼音的首字母
	 * @return 转化后的拼音字符串
	 */
	public static String toPinyin(String s, boolean appendCaptials) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder builder = new StringBuilder();
		StringBuilder enBuilder = new StringBuilder();
		StringBuilder numBuilder = new StringBuilder();
		StringBuilder capBuilder = null;
		if (appendCaptials) {
			capBuilder = new StringBuilder();
		}
		int len = s.length();
		int i = 0;
		char curChar = '\0';
		while (i < len) {
			// process english letter(a-zA-z)
			while (i < len && isAlpha(curChar = s.charAt(i))) {
				enBuilder.append(Character.toLowerCase(curChar));
				i++;
			}
			if (enBuilder.length() > 0) {
				builder.append(enBuilder.toString()).append(' ');
				enBuilder = new StringBuilder();
				if (appendCaptials && capBuilder.length() > 0) {
					capBuilder.append(' ');
				}
			}
			// process digit(0-9)
			while (isDigit(curChar)) {
				numBuilder.append(curChar);
				if (++i >= len) {
					break;
				}
				curChar = s.charAt(i);
			}
			if (numBuilder.length() > 0) {
				builder.append(numBuilder.toString()).append(' ');
				numBuilder = new StringBuilder();
				if (appendCaptials && capBuilder.length() > 0) {
					capBuilder.append(' ');
				}
			}
			// process chinese character
			while (isChinese(curChar)) {
				String[] pinyins = null;
				try {
					pinyins = PinyinHelper.toHanyuPinyinStringArray(curChar,
							format);
				} catch (Exception e) {
					logger.error("error：{} get pinyin of character " + curChar, e);
				}
				if (pinyins != null && pinyins.length > 0) {
					String thePinyin = pinyins[0];
					if (pinyins.length > 1) {
						Map<String, Set<String>> polyphoneMap = polyphones
								.get(curChar);
						if (polyphoneMap != null) {
							char lastChar, nextChar;
							String postFixeWord = null;
							String prefixWord = null;
							if (i > 0) {
								lastChar = s.charAt(i - 1);
								postFixeWord = new String(new char[] {
										lastChar, curChar });
							}
							if (i < len - 1) {
								nextChar = s.charAt(i + 1);
								prefixWord = new String(new char[] { curChar,
										nextChar });
							}
							for (String pinyin : pinyins) {
								Set<String> words = polyphoneMap.get(pinyin);
								if (words == null) {
									continue;
								}
								if (postFixeWord != null
										&& words.contains(postFixeWord)) {
									thePinyin = pinyin;
									break;
								} else if (prefixWord != null
										&& words.contains(prefixWord)) {
									thePinyin = pinyin;
									break;
								}
							}
						}
					}
					builder.append(thePinyin).append(' ');
					if (appendCaptials) {
						capBuilder.append(thePinyin.charAt(0));
					}
				}
				if (++i >= len) {
					break;
				}
				curChar = s.charAt(i);
			}
			// ingnore other characters
			if (!isAlpha(curChar) && !isDigit(curChar)) {
				i++;
			}
		}
		if (appendCaptials) {
			builder.append(capBuilder.toString());
		}
		return builder.toString();
	}

	private static boolean isAlpha(char ch) {
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
	}

	private static boolean isDigit(char ch) {
		return ch >= '0' && ch <= '9';
	}

	private static boolean isChinese(char ch) {
		return ch >= '\u4e00' && ch <= '\u9fa5';
	}

	private static void initPolyphones() throws Exception {
		InputStream in = Thread
				.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(
						"polyphones.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));
		String line = null;
		int lineIdx = 0;

		while ((line = reader.readLine()) != null) {
			lineIdx++;
			line = line.trim();
			if (line.length() <= 1) {
				continue;
			}
			char polyphoneChar = line.charAt(0);
			int lbrace = line.indexOf('{');
			int rbrace = line.indexOf('}');
			if (rbrace <= lbrace) {
				continue;
			}
			String wordsStr = line.substring(lbrace + 1, rbrace);
			wordsStr = wordsStr.trim();
			if (wordsStr.length() <= 1) {
				continue;
			}
			List<String> kvs = new ArrayList<String>();
			StringBuilder kvBulider = new StringBuilder();
			boolean inBrace = false;
			for (int i = 0; i < wordsStr.length(); i++) {
				char ch = wordsStr.charAt(i);
				if (ch == '[') {
					if (inBrace) {
						throw new RuntimeException("wrong fomat of string \""
								+ wordsStr + "\" at line " + lineIdx
								+ ",[ does not match.");
					}
					inBrace = true;
				} else if (ch == ']') {
					if (!inBrace) {
						throw new RuntimeException("wrong fomat of string \""
								+ wordsStr + "\" at line " + lineIdx
								+ ",] does not match.");
					}
					inBrace = false;
				} else if (ch != ',' || inBrace) {
					kvBulider.append(ch);
				} else {
					kvs.add(kvBulider.toString());
					kvBulider = new StringBuilder();
				}
			}
			if (kvBulider.length() > 0) {
				kvs.add(kvBulider.toString());
			}
			Map<String, Set<String>> phoneMap = new HashMap<String, Set<String>>();
			for (String kv : kvs) {
				String[] kvInfo = kv.split("\\s*[:：]\\s*");
				String k = kvInfo[0];
				Set<String> vSet = new HashSet<String>();
				if (kvInfo.length == 2) {
					String v = kvInfo[1];
					String[] phoneWords = v.split("\\s*,\\s*");
					vSet.addAll(Arrays.asList(phoneWords));
				}
				phoneMap.put(k, vSet);
			}
			polyphones.put(polyphoneChar, phoneMap);
		}
	}

	private static void initPinyins() throws Exception {
		InputStream is = Thread
				.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("pinyins.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		String line;
		for (int i = 0; i < pTrees.length; i++) {
			pTrees[i] = new PinyinTree((char) ('a' + i));
		}
		while ((line = reader.readLine()) != null) {
			line = line.trim().toLowerCase();
			if (line.length() > 0) {
				char ch = line.charAt(0);
				PinyinTree pTree = pTrees[ch - 'a'];
				pTree.addPinyin(line);
			}
		}
	}

	/**
	 * 26叉拼音树
	 * 
	 * @author Kyo.Ou
	 * 
	 */
	private static class PinyinTree {
		private char ch;
		private Map<Character, PinyinTree> childrenMap;

		public PinyinTree(char ch) {
			this.ch = ch;
		}

		public void addPinyin(String pinyin) {
			if (pinyin == null) {
				return;
			}
			char ch = pinyin.charAt(0);
			if (ch != this.ch) {
				return;
			}
			int len = pinyin.length();
			if (len < 2) {
				return;
			}
			PinyinTree child = this;
			for (int i = 1; i < len; i++) {
				ch = pinyin.charAt(i);
				if (child.childrenMap == null) {
					child.childrenMap = new HashMap<Character, PinyinTree>();
				}
				PinyinTree pTree = child.childrenMap.get(ch);
				if (pTree == null) {
					pTree = new PinyinTree(ch);
					child.childrenMap.put(ch, pTree);
				}
				child = pTree;
			}
		}

		public PinyinTree nextTree(char ch) {
			return childrenMap == null ? null : childrenMap.get(ch);
		}
	}
}
