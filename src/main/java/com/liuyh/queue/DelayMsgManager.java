package com.liuyh.queue;

import java.util.concurrent.DelayQueue;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 消息队列读取类
 * 
 * @author Tony.Tang
 */
public class DelayMsgManager extends Thread {

	boolean order;
	DelayQueue<DelayMessage> queue;
	int permitsPerSecond;
	static int defaltPerSecond = 5;

	public void setQueue(DelayQueue<DelayMessage> queue) {
		this.queue = queue;
	}

	public int getPermitsPerSecond() {
		return permitsPerSecond;
	}

	public void setPermitsPerSecond(int permitsPerSecond) {
		this.permitsPerSecond = permitsPerSecond;
	}

	public void run() {
		RateLimiter limiter = RateLimiter.create(permitsPerSecond == 0 ? defaltPerSecond : permitsPerSecond); // 每秒不超过4个任务被提交
		while (true) {
			try {
				// limiter.acquire();
				if (permitsPerSecond != 0) {
					// 开启流控
					if (!limiter.tryAcquire()) {
						continue;
					}
				}
				DelayMessage msgObject = (DelayMessage) queue.take();
				if (msgObject != null) {
					// 启动消息处理
					if (order) {
						msgObject.execute();
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

	public void setOrder(boolean order) {
		this.order = order;
	}

}
