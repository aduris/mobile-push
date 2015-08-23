package mobile.push.sender.main;

/**
 * Main Application Start
 * @since 	2015-07-01
 * @author 	최일규
 */
public class MainApplication {

	public static void main(String[] args) {
		Thread t = new BizProcessThread();
		t.start();
	}
}