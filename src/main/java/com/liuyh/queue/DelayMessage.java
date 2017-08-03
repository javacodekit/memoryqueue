package com.liuyh.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时消息实体
 * 
 * @author slimina
 * 
 */
public abstract class DelayMessage implements Delayed {

	private int id;
	private String body; // 消息内容
	private long excuteTime;// 执行时间
	private long delayTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getExcuteTime() {
		return excuteTime;
	}

	public void setExcuteTime(long excuteTime) {
		this.excuteTime = excuteTime;
	}

	public long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

	public DelayMessage(int id, String body, long delayTime) {
		this.id = id;
		this.body = body;
		this.delayTime = delayTime;
		this.excuteTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
	}

	public DelayMessage(long delayTime) {
		this.delayTime = delayTime;
		this.excuteTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
	}

	public DelayMessage() {
	}

	@Override
	public int compareTo(Delayed delayed) {
		DelayMessage msg = (DelayMessage) delayed;
		// return Integer.valueOf(this.id) > Integer.valueOf(msg.id) ? 1 : (Integer.valueOf(this.id) < Integer.valueOf(msg.id) ? -1 : 0);
		return this.excuteTime > msg.excuteTime ? 1 : (this.excuteTime < msg.excuteTime ? -1 : 0);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.excuteTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	public abstract void execute();
}