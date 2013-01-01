package org.cr.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @description 检验评论好坏 
 * @author caorong
 * @date 2013-1-1
 * @see  完全可以用String.contains 完成的事情却用正则表达式，是不是太2b?
 */
public class WordCheckUtil {

	// 好评词语
	private static List<String> goodLists;
	// 坏评词语
	private static List<String> badLists;

	static {
		XMLUtil xmlUtil = new XMLUtil();
		// 初始化goodComm
		goodLists = xmlUtil.getCommList("src/org/cr/resource/goodComm.xml");
		// 初始化badComm
		badLists = xmlUtil.getCommList("src/org/cr/resource/badComm.xml");
	}

	/**
	 * 检验评论
	 * 
	 * @param str comm
	 * @return 0中性 1 好评 2坏评
	 * */
	public static int wordCheck(String checkComm) {
		// 评论为空
		if (checkComm.equals("")) {
			return 0;
		}
//		Pattern[] patterns = getPatternsFromKeyword(checkComm);
		// 检验good
		for (String s : goodLists) {
			Pattern[] patterns = getPatternsFromKeyword(s);
			if (IsPatternMatch(checkComm, patterns)) {
				return 1;
			}
		}
		// 检验bad
		for (String s : badLists) {
			Pattern[] patterns = getPatternsFromKeyword(s);
			if (IsPatternMatch(checkComm, patterns)) {
				return 2;
			}
		}
		// 都没有
		return 0;
	}

	// 正则检验是否匹配
	private static boolean IsPatternMatch(String sample, Pattern[] patterns) {
		for (int i = 0; i < patterns.length; i++) {
			if (patterns[i].matcher(sample).matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据文件名关键字生成搜索文件要用到的所有的正则表达式
	 * 
	 * @param keyword
	 *            文件名关键字
	 * @return 搜索要用到的所有正则表达式
	 */
	private static Pattern[] getPatternsFromKeyword(String keyword) {
		// 用于生成所有的正则表达式
		StringBuilder regexBuilder = new StringBuilder(keyword);
		// 所有的正则表达式，次序由短到长
		Pattern[] patterns = new Pattern[keyword.length() + 1];
		// 为所有正则表达式赋值
		for (int i = 0; i < patterns.length - 1; i++) {
			regexBuilder.insert(keyword.length() - i, "\\E.*");
			regexBuilder.insert(keyword.length() - i - 1, "\\Q");
			patterns[i] = Pattern.compile(regexBuilder.toString());
		}
		regexBuilder.insert(0, ".*");
		//CASE_INSENSITIVE
		patterns[patterns.length - 1] = Pattern.compile(
				regexBuilder.toString(), Pattern.CASE_INSENSITIVE);
		return patterns;
	}
}
