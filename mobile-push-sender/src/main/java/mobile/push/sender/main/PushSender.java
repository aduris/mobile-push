package mobile.push.sender.main;

import java.util.List;

import mobile.push.sender.common.PushPayload;
import mobile.push.sender.vo.PersonVO;

import org.apache.log4j.Logger;

/**
 * 푸시발송
 * @since	2015-07-15
 * @author 	최일규
 *
 */
public class PushSender extends Thread {
	protected final Logger mLogger 	= Logger.getLogger(getClass().getName());

	private PushPayload mPayload 	= null;
	private List<PersonVO> mPersons = null;

	public PushSender() {}

	public PushSender(PushPayload payload, List<PersonVO> persons) {
		mPayload 	= payload;
		mPersons 	= persons;
	}

	/**
	 * 동기발송
	 * @param push
	 * @param persons
	 */
	public void send(PushPayload payload, List<PersonVO> persons) {
		try {
			payload.send(persons);
		} catch (Exception e) {
			mLogger.error("Push Error", e);
		}
	}

	/**
	 * 비동기발송
	 */
	@Override
	public void run() {
		try {
			mPayload.send(mPersons);
		} catch (Exception e) {
			mLogger.error("Push Error", e);
		}
	}
}
