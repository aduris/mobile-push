package mobile.push.sender.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * MyBatis Connection Factory
 * @since	2015-07-03
 * @author 	최일규
 */
public class MyBatisConnectionFactory {

	private static SqlSessionFactory mSqlSessionFactory;

	static {
		try {
			String resource = "mybatis-config.xml";
			Reader reader = Resources.getResourceAsReader(resource);

			if (mSqlSessionFactory == null) {
				mSqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		}
		catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		}
		catch (IOException iOException) {
			iOException.printStackTrace();
		}
	}
	public static SqlSessionFactory getSqlSessionFactory() {
		return mSqlSessionFactory;
	}
}