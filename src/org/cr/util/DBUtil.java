package org.cr.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBUtil {

	static String resource = "org/cr/resource/mybatis-config.xml";
	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getSessionFactory() {
		if (sqlSessionFactory == null) {
			try {
				InputStream inputStream = Resources.getResourceAsStream(resource);
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
				return sqlSessionFactory;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			return sqlSessionFactory;
		}
	}

}
