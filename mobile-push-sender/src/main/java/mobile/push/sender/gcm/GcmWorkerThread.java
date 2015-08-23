package mobile.push.sender.gcm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import mobile.push.sender.vo.PersonVO;

import org.apache.log4j.Logger;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * GCM WorkerThread
 * @since	2015-07-06
 * @author 	최일규
 */
public class GcmWorkerThread implements Callable<Result> {

	protected final Logger mLogger 	= Logger.getLogger(getClass().getName());
	private Sender mGcmSender 		= null;
	private PersonVO mPerson 		= null;

	public GcmWorkerThread(Sender sender, PersonVO person) {
		mGcmSender		= sender;
		mPerson 		= person;
	}

	@Override
	public Result call() {

		Result result 	= null;
		String alarmId 	= mPerson.getAlarmId();
		String token 	= mPerson.getDeviceToken();
		String message 	= mPerson.getMessage();
		String link 	= mPerson.getLink();

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);

		try {

			//발송성공률을 높이기 위해.. 옵션조절.. collapse_key 기본값으로.. TTS 1800초, DelayWhileIdle false 설정, retry 1회
			Message gcmMsg = new Message.Builder()
			.collapseKey("collapseKey" + System.currentTimeMillis())     	//메시지의 고유ID
			.timeToLive(1800)                        						//기기가 비활성일때, 보관되는 시간 (30분 유지)
			.delayWhileIdle(false)                   						//기기가 활성화 상태일 때 보여줄 것인지. 난 비활성일때도 수신되게 할거니까.. (false)
			.addData("Alarm_Id"     , alarmId)
			.addData("Title"        , "")
			.addData("Cont"         , message)
			.addData("ticker"       , "")
			.addData("Send_Date"    , strDate)
			.addData("Link"         , link)
			.addData("badge"        , "1")
			.build();
			result = mGcmSender.send(gcmMsg, token, 0);

		} catch (Exception e) {
			mLogger.error("Gcm Error", e);
		}
		return result;
	}

}
