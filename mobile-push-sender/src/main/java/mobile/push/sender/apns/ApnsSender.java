package mobile.push.sender.apns;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;
import javapns.notification.ResponsePacket;
import mobile.push.libs.apns.JavaApns;
import mobile.push.sender.common.MyBatisConnectionFactory;
import mobile.push.sender.common.PushPayload;
import mobile.push.sender.dao.ErrorDAO;
import mobile.push.sender.vo.ErrorVO;
import mobile.push.sender.vo.PersonVO;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

/**
 * APNS Thread Pooling 발송
 * @since	2015-07-10
 * @author 	최일규
 */
public class ApnsSender implements PushPayload {

	protected final Logger mLogger = Logger.getLogger(getClass().getName());

	private String 	mApnsKeystore = "";
	private String 	mApnsPassword = "";
	private Boolean mIsProduction = true;
	private int	mApnsThreadCount  = 0;

	public ApnsSender(String apnsKeystore, String apnsPassword, Boolean isProduction, int apnsThreadCount) {
		mApnsKeystore 		= apnsKeystore;
		mApnsPassword 		= apnsPassword;
		mIsProduction 		= isProduction;
		mApnsThreadCount 	= apnsThreadCount;
	}

	@Override
	public void send(List<PersonVO> persons) throws Exception {

		if(persons != null) {

			ExecutorService executor = Executors.newFixedThreadPool(mApnsThreadCount);
			List<Future<PushedNotifications>> list = new ArrayList<Future<PushedNotifications>>();
			JavaApns apns = new JavaApns(mApnsKeystore, mApnsPassword);
			PersonVO person = null;

			for(int i = 0; i < persons.size(); i++){
				person = persons.get(i);
				Callable<PushedNotifications> callable = new ApnsWorkerThread(apns, mIsProduction, person);
				Future<PushedNotifications> future = executor.submit(callable);
				list.add(future);
			}
			for(Future<PushedNotifications> future : list){
				try {
					PushedNotifications notifications = future.get();
					if(notifications != null) {
						for(PushedNotification notification : notifications) {

							if (notification.isSuccessful()) {
								mLogger.info("APNS Push notification sent successfully to: " + notification.getDevice().getToken());
							} else {
								String invalidToken = notification.getDevice().getToken();

								//Exception theProblem = notification.getException();
								//theProblem.printStackTrace();

								ResponsePacket errorResponse = notification.getResponse();
								if (errorResponse != null) {

									ErrorVO vo = new ErrorVO();
									vo.setDeviceToken(invalidToken);
									vo.setErrorMessage(errorResponse.getMessage());

									SqlSessionFactory sqlSession = MyBatisConnectionFactory.getSqlSessionFactory();
									ErrorDAO dao = new ErrorDAO(sqlSession);
									dao.insert(vo);

									mLogger.warn("#status : " + errorResponse.getStatus() + "--- #errorMessage : " + errorResponse.getMessage() + "--- #deviceToken : " + invalidToken);
								}
							}
						}
					}

				} catch (InterruptedException | ExecutionException e) {
					mLogger.error("Apns Error", e);
				}
			}
			executor.shutdown();

		}
	}
}
