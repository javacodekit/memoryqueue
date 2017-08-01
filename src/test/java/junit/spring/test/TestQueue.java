package junit.spring.test;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.liuyh.DateUtils;
import com.liuyh.MessageFactory;

/**
 * 队列特性： 1.支持延迟队列和普通队列 2.支持顺序消费和并发消费 3.支持流控
 * 
 * @author liuyonghong
 *
 */
public class TestQueue {
	protected static Logger logger = LoggerFactory.getLogger(TestQueue.class);

	@BeforeTest
	public void beforeTest() {
	}

	@BeforeSuite
	public void beforeSuite() {
	}

	@Test(invocationCount = 1, threadPoolSize = 50)
	public void testDelayMessage() {
		// 初始化队列
		MessageFactory mf = new MessageFactory();
		mf.createDelayMessageQueue("log", true, 0);
		// 打印当前时间
		String dd = DateUtils.getTimeStr(new Date());
		logger.info("当前时间:" + dd);
		// mf.setObjectToQueue(new DemoMessage(3, "aaa", 8000), "log");
		// mf.setObjectToQueue(new DemoMessage(2, "bbb", 6000), "log");
		// mf.setObjectToQueue(new DemoMessage(1, "ccc", 90000), "log");
		// 测试数据
		for (int i = 1000; i > 0; i--) {
			Random random = new Random();
			int retry = 1000 * random.nextInt(10) + 1000;
			mf.setObjectToQueue(new DemoMessage(i, "aaa" + i, retry), "log");
		}
	}

	@Test(invocationCount = 1, threadPoolSize = 50)
	public void testCommonMessage() {
		// 初始化队列
		MessageFactory mf = new MessageFactory();
		mf.createCommonMessageQueue("log2", true, 10000, 100);
		// 打印当前时间
		String dd = DateUtils.getTimeStr(new Date());
		System.out.println("当前时间:" + dd);
		// mf.setObjectToQueue(new DemoMessage(3, "aaa", 8000), "log");
		// mf.setObjectToQueue(new DemoMessage(2, "bbb", 6000), "log");
		// mf.setObjectToQueue(new DemoMessage(1, "ccc", 90000), "log");
		// 测试数据
		for (int i = 10000; i > 0; i--) {
			mf.setObjectToQueue(new DemoMessage(i, "aaa" + i), "log2");
		}

		while (true) {
			;
		}
	}
}
