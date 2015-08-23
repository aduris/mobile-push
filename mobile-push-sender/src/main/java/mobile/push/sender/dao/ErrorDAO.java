package mobile.push.sender.dao;

import java.util.HashMap;
import java.util.Map;

import mobile.push.sender.vo.ErrorVO;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 에러 DAO(Data Access Objects)
 * @since	2015-07-08
 * @author 	최일규
 *
 */
public class ErrorDAO extends BaseDAO {

	private SqlSessionFactory sqlSessionFactory = null;

	public ErrorDAO(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * Insert an instance of Error into the database.
	 * @param person the instance to be persisted.
	 */
	public int insert(ErrorVO vo){
		int id = -1;
		SqlSession session = sqlSessionFactory.openSession();

		Map<String, Object> paramMap 	= new HashMap<String, Object>();
		paramMap.put("device_token"		, vo.getDeviceToken());
		paramMap.put("error_msg"		, vo.getErrorMessage());

		try {
			id = session.insert("Error.insert", paramMap);
		} catch (Exception e) {
			mLogger.error("Person DAO Error", e);
		} finally {
			session.commit();
			session.close();
		}
		return id;
	}


}