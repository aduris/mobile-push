package mobile.push.sender.main;

import java.util.List;

import mobile.push.libs.Config;
import mobile.push.sender.apns.ApnsSender;
import mobile.push.sender.common.MyBatisConnectionFactory;
import mobile.push.sender.dao.PersonDAO;
import mobile.push.sender.gcm.GcmSender;
import mobile.push.sender.vo.PersonVO;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 모바일 푸시발송 비즈니스로직
 * @since	2015-07-02
 * @author 	최일규
 */
public class BizProcess {

	public BizProcess() {}

	public void send() {

		String apnsKeystore 	= "";
		String apnsPassword 	= "";
		String apnsProduction 	= "";
		boolean isProduction 	= false;
		String gcmApiKey 		= "";
		int gcmThreadCount		= 1;
		int apnsThreadCount		= 1;
		Config appConfig 		= null;

		appConfig 		= Config.instance("config.xml");
		apnsKeystore 	= appConfig.getString("apns.keystore");
		apnsPassword 	= appConfig.getString("apns.password");
		apnsProduction 	= appConfig.getString("apns.production");
		isProduction 	= Boolean.parseBoolean(apnsProduction);
		gcmApiKey	 	= appConfig.getString("gcm.apikey");
		gcmThreadCount	= Integer.parseInt(appConfig.getString("gcm.threadcount"));
		apnsThreadCount	= Integer.parseInt(appConfig.getString("apns.threadcount"));

		SqlSessionFactory sqlSession	= MyBatisConnectionFactory.getSqlSessionFactory();
		PersonDAO dao 					= new PersonDAO(sqlSession);
		List<PersonVO> apnsPersons 		= dao.select("001");
		List<PersonVO> gcmPersons 		= dao.select("002");

		/*
		PushSender sender = new PushSender();
		sender.send(
				new ApnsSender(apnsKeystore, apnsPassword, isProduction, apnsThreadCount)
				, apnsPersons);
		sender.send(
				new GcmSender(gcmApiKey, gcmThreadCount)
				, gcmPersons);
		 */

		PushSender apnsSender = new PushSender(
				new ApnsSender(apnsKeystore, apnsPassword, isProduction, apnsThreadCount)
				, apnsPersons);
		apnsSender.start();

		PushSender gcmSender = new PushSender(
				new GcmSender(gcmApiKey, gcmThreadCount)
				, gcmPersons);
		gcmSender.start();

	}
}
