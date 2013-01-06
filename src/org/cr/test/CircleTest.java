package org.cr.test;

/**
 * @Description
 * @author caorong
 * @date 2013-1-6
 * 
 */
public class CircleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// x'=xcost-ysint
		// y'=xsiny+ycost
		int x = 660;
		int y = 300;
		int R = 100;
		for (int i = 0; i < 10; i++) {
			System.out.println(i+" x'=: " + /*(int)*/(x + R * Math.cos(i *36* Math.PI / 180) ));
			System.out.println(i+" y'=: " + /*(int)*/(y + R * Math.sin(i *36* Math.PI / 180) ));
		}

	}
}
