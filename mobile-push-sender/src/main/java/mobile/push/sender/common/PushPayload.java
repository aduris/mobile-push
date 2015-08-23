package mobile.push.sender.common;

import java.util.List;

import mobile.push.sender.vo.PersonVO;

/**
 * 푸시발송 인터페이스
 * @author 최일규
 */
public interface PushPayload {
	public abstract void send(List<PersonVO> persons) throws Exception;
}