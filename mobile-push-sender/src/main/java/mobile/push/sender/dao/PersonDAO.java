package mobile.push.sender.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile.push.sender.vo.PersonVO;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 대상자 DAO(Data Access Objects)
 * @since	2015-07-03
 * @author 	최일규
 *
 */
public class PersonDAO extends BaseDAO {

	private SqlSessionFactory sqlSessionFactory = null;

	public PersonDAO(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * 대상자 조회
	 * @param serviceId
	 * @param testYn
	 * @return
	 */
	public  List<PersonVO> select(String serviceId){
		List<PersonVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("svc_id"		, serviceId);

		try {
			list = session.selectList("Person.select", paramMap);
		} catch (Exception e) {
			mLogger.error("Person DAO Error", e);
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Insert an instance of Person into the database.
	 * @param person the instance to be persisted.
	 */
	public int insert(PersonVO person){
		int id = -1;
		SqlSession session = sqlSessionFactory.openSession();

		try {
			id = session.insert("Person.insert", person);
		} finally {
			session.commit();
			session.close();
		}
		return id;
	}
	/**
	 * Update an instance of Person into the database.
	 * @param person the instance to be persisted.
	 */
	public void update(PersonVO person){
		int id = -1;
		SqlSession session = sqlSessionFactory.openSession();

		try {
			id = session.update("Person.update", person);

		} finally {
			session.commit();
			session.close();
		}
	}

	/**
	 * Delete an instance of Person from the database.
	 * @param id value of the instance to be deleted.
	 */
	public void delete(int id){

		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.delete("Person.delete", id);
		} finally {
			session.commit();
			session.close();
		}
	}
}