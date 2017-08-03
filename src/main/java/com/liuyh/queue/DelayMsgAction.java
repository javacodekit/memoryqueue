package com.liuyh.queue;

/**
 * 消息体处理类
 * 
 * @author Tony.Tang
 */
public class DelayMsgAction extends Thread {
	/**
	 * 消息对象
	 */
	private DelayMessage msgObject;

	/**
	 * 设置消息对象
	 * 
	 * @param msg
	 */
	public void setMsgObject(DelayMessage msg) {
		this.msgObject = msg;
	}

	public void run() {
		try {
			msgObject.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
