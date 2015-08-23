package mobile.push.sender.gcm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mobile.push.sender.common.MyBatisConnectionFactory;
import mobile.push.sender.common.PushPayload;
import mobile.push.sender.dao.ErrorDAO;
import mobile.push.sender.vo.ErrorVO;
import mobile.push.sender.vo.PersonVO;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * GCM Thread Pooling 발송
 * @since	2015-07-02
 * @author 	최일규
 */
public class GcmSender implements PushPayload {

	protected final Logger mLogger 	= Logger.getLogger(getClass().getName());
	private String mGcmApiKey 		= "";
	private int	mGcmThreadCount 	= 0;

	public GcmSender(String gcmApiKey, int gcmThreadCount) {
		mGcmApiKey 		= gcmApiKey;
		mGcmThreadCount	= gcmThreadCount;
	}

	@Override
	public void send(List<PersonVO> persons) throws Exception {

		if(persons != null) {

			ExecutorService executor = Executors.newFixedThreadPool(mGcmThreadCount);
			List<Future<Result>> list = new ArrayList<Future<Result>>();
			Sender sender = new Sender(mGcmApiKey);
			PersonVO person = null;

			for(int i = 0; i < persons.size(); i++){
				person = persons.get(i);
				Callable<Result> callable = new GcmWorkerThread(sender, person);
				Future<Result> future = executor.submit(callable);
				list.add(future);
			}
			for(Future<Result> future : list){
				try {
					Result result = future.get();
					if(result != null) {
						if(result.getErrorCodeName() == null) {
							mLogger.info("GCM Push notification sent successfully to --- #messageId : " + result.getMessageId() + "--- #deviceToken : " + result.getDeviceToken());
						} else {

							ErrorVO vo = new ErrorVO();
							vo.setDeviceToken(result.getDeviceToken());
							vo.setErrorMessage(result.getErrorCodeName());

							SqlSessionFactory sqlSession = MyBatisConnectionFactory.getSqlSessionFactory();
							ErrorDAO dao = new ErrorDAO(sqlSession);
							dao.insert(vo);

							mLogger.warn("#errorCodeName : "+result.getErrorCodeName() + "--- #messageId : " + result.getMessageId() + "--- #deviceToken : " + result.getDeviceToken());
						}
					}
				} catch (InterruptedException | ExecutionException e) {
					mLogger.error("Gcm Error", e);
				}
			}
			executor.shutdown();

		}
	}
}
