package mobile.push.sender.main;

import org.apache.log4j.Logger;

/**
 * BizProcessThread Push sender
 * @since 	2015-07-01
 * @author 	최일규
 */
public class BizProcessThread extends Thread {

	protected final Logger mLogger = Logger.getLogger(getClass().getName());
	public BizProcessThread() {
		mLogger.info("Mobile Push Application Start...");
	}

	@Override
	public void run() {
		BizProcess sender = new BizProcess();
		while(true) {
			sender.send();
		}
	}
}