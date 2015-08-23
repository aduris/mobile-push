package mobile.push.sender.apns;

import java.util.concurrent.Callable;

import javapns.notification.PushedNotifications;
import mobile.push.libs.apns.JavaApns;
import mobile.push.libs.apns.NameValuePair;
import mobile.push.sender.vo.PersonVO;

import org.apache.log4j.Logger;

/**
 * APNS WorkerThread
 * @since	2015-07-06
 * @author 	최일규
 */
public class ApnsWorkerThread implements Callable<PushedNotifications> {

	protected final Logger mLogger 	= Logger.getLogger(getClass().getName());

	private PersonVO mPerson 		= null;
	private Boolean mIsProduction 	= false;
	private JavaApns mApns 			= null;

	public ApnsWorkerThread(JavaApns apns, Boolean isProduction, PersonVO person) {
		mApns 			= apns;
		mIsProduction 	= isProduction;
		mPerson 		= person;
	}

	@Override
	public PushedNotifications call() {

		PushedNotifications notifications = null;
		String alarmId 	= mPerson.getAlarmId();
		String token 	= mPerson.getDeviceToken();
		String message 	= mPerson.getMessage();
		String link 	= mPerson.getLink();

		try {
			notifications = mApns.sendApns(token
					, message
					, 1
					, new NameValuePair("link", link)
			, new NameValuePair("alarm_id", alarmId)
			, mIsProduction);

		} catch (Exception e) {
			mLogger.error("Apns Error", e);
		}
		return notifications;
	}

}
