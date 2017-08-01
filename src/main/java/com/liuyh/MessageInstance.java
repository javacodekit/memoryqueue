package com.liuyh;

import java.util.HashMap;
import java.util.Map;

/**
 * 短消息队列Map实例
 * 
 * @author Tony.Tang
 */
public class MessageInstance {

	/**
	 * 短消息队列Map
	 */
	public Map queueMap = new HashMap();

	public Map queueTypeMap = new HashMap();
	/**
	 * 消息队列实例
	 */
	private static MessageInstance instance = new MessageInstance();

	public static MessageInstance getInstance() {
		// if (instance == null) {
		// instance = new MessageInstance();
		// }
		return instance;
	}

	public Map getQueueMap() {
		return queueMap;
	}

	public void setQueueMap(Map queueMap) {
		this.queueMap = queueMap;
	}

	public Map getQueueTypeMap() {
		return queueTypeMap;
	}

	public void setQueueTypeMap(Map queueTypeMap) {
		this.queueTypeMap = queueTypeMap;
	}

}
