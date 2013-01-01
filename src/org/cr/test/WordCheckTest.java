package org.cr.test;

import org.cr.util.WordCheckUtil;

public class WordCheckTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int ans = WordCheckUtil.wordCheck("你啊加油  ".trim());
		System.out.println(ans);
	}

}
