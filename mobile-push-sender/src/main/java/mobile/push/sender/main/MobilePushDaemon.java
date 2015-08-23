package mobile.push.sender.main;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.log4j.Logger;

/**
 * Linux Daemonize service
 * @since 	2015-06-29
 * @author 	최일규
 */
public class MobilePushDaemon implements Daemon {

	protected final Logger mLogger = Logger.getLogger(getClass().getName());
	Thread t = null;

	@Override
	public void init(DaemonContext context) throws DaemonInitException,
	Exception {

		mLogger.info("Mobile push daemon init");
		t = new BizProcessThread();
	}

	@Override
	public void start() throws Exception {
		mLogger.info("Mobile push daemon start");
		t.start();
	}

	@Override
	public void stop() throws Exception {
		mLogger.info("Mobile push daemon stop");
		t.interrupt();
	}

	@Override
	public void destroy() {
		mLogger.info("Mobile push daemon destroy");
	}
}