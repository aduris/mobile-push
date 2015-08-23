package mobile.push.libs.apns;

import javapns.Push;
import javapns.notification.PushedNotifications;

/**
 * Javapns Wrapper
 * @since	2015-07-01
 * @author 	최일규
 */
public class JavaApns {

	private String mApnsKeystore = "";
	private String mApnsPassword = "";

	//Constructor
	public JavaApns(String keystore, String password) {
		mApnsKeystore = keystore;
		mApnsPassword = password;
	}

	/**
	 * Javapns Wrapper
	 * @param token
	 * @param alert
	 * @param badge
	 * @param link
	 * @param alramId
	 * @param isProduction
	 */
	public PushedNotifications sendApns(String token
			, String alert
			, int badge
			, NameValuePair link
			, NameValuePair alramId
			, boolean isProduction) throws Exception {

		/* Build a blank payload to customize */
		CustomPushNotificationPayload payload = CustomPushNotificationPayload.complex();

		/* Customize the payload */
		payload.addAlert(alert);
		payload.addBadge(badge);
		payload.addSound("default");
		payload.addCustomAps(link.getName()		, link.getValue());
		payload.addCustomAps(alramId.getName()	, alramId.getValue());

		/* Push your custom payload */
		PushedNotifications notifications = Push.payload(payload
				, mApnsKeystore
				, mApnsPassword
				, isProduction
				, token);
		return notifications;
	}
}