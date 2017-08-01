package com.liuyh;

import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 消息队列读取类
 * 
 * @author Tony.Tang
 */
public class CommonMsgManager extends Thread {
	protected static Logger logger = LoggerFactory.getLogger(CommonMsgManager.class);

	ArrayBlockingQueue<DelayMessage> queue;
	/**
	 * 是否按顺序消费
	 */
	boolean order;
	/**
	 * 是否开启流控，为0的时候，不开启流控
	 */
	int permitsPerSecond;
	static int defaltPerSecond = 5;

	public void setQueue(ArrayBlockingQueue<DelayMessage> queue) {
		this.queue = queue;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public void setPermitsPerSecond(int permitsPerSecond) {
		this.permitsPerSecond = permitsPerSecond;
	}

	public void run() {
		RateLimiter limiter = RateLimiter.create(permitsPerSecond == 0 ? defaltPerSecond : permitsPerSecond); // 每秒不超过4个任务被提交
		while (true) {
			try {
				// System.out.println("消费消息: 1");
				if (permitsPerSecond != 0) {
					// 开启流控
					if (!limiter.tryAcquire()) {
						// System.out.println("流控限制");
						continue;
					}
				}
				// System.out.println("消费消息: 2");
				DelayMessage msgObject = (DelayMessage) queue.take();
				if (msgObject != null) {
					// 启动消息处理
					if (order) {
						// System.out.println("消费消息: 3");
						msgObject.execute();
						// System.out.println("消费消息: 4");
					} else {
						DelayMsgAction msgAction = new DelayMsgAction();
						msgAction.setMsgObject(msgObject);
						msgAction.start();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
