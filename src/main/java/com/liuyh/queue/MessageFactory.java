package com.liuyh.queue;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;

import org.apache.log4j.Logger;

/**
 * 1.支持顺序消费和并发消费
 * 
 * 2.同时支持延迟队列和普通队列
 * 
 * 3.支持限流,每秒钟消费数量
 * 
 * @author liuyonghong
 *
 */
public class MessageFactory {
	protected static Logger logger = Logger.getLogger(MessageFactory.class);

	/**
	 * 创建一个延迟消息队列
	 * 
	 * @param queueName
	 *            消息队列名称
	 * @param order
	 *            是否是顺序消费
	 */
	public synchronized void createDelayMessageQueue(String queueName, boolean order, int permitsPerSecond) {
		Map map = MessageInstance.getInstance().getQueueMap();
		Map typemap = MessageInstance.getInstance().getQueueTypeMap();
		if (map.get(queueName) != null) {
			logger.warn(queueName + " has already been register,it's not be recreate!");
		} else {
			// 创建消息队列，并将消息队列放入实例中，便于管理
			DelayQueue<DelayMessage> queue = new DelayQueue<DelayMessage>();
			map.put(queueName, queue);
			MessageInstance.getInstance().setQueueMap(map);
			typemap.put(queueName, QueueType.DELAY);
			MessageInstance.getInstance().setQueueTypeMap(typemap);
			// 启动消息队列
			DelayMsgManager msgManager = new DelayMsgManager();
			msgManager.setOrder(order);
			msgManager.setQueue(queue);
			msgManager.setPermitsPerSecond(permitsPerSecond);
			msgManager.start();
		}
	}

	/**
	 * 创建一个普通消息队列
	 * 
	 * @param queueName
	 *            队列名称
	 * @param order
	 *            是否延迟消费
	 * @param queueSize
	 *            队列大小
	 * @param permitsPerSecond
	 *            流控参数，0为关闭流控
	 */
	public synchronized void createCommonMessageQueue(String queueName, boolean order, int queueSize, int permitsPerSecond) {
		Map map = MessageInstance.getInstance().getQueueMap();
		Map typemap = MessageInstance.getInstance().getQueueTypeMap();
		if (map.get(queueName) != null) {
			logger.warn(queueName + " has already been register,it's not be recreate!");
		} else {
			// 创建消息队列，并将消息队列放入实例中，便于管理
			ArrayBlockingQueue<DelayMessage> queue = new ArrayBlockingQueue<DelayMessage>(queueSize, true);
			map.put(queueName, queue);
			typemap.put(queueName, QueueType.COMMON);
			MessageInstance.getInstance().setQueueMap(map);
			// 启动消息队列
			CommonMsgManager msgManager = new CommonMsgManager();
			msgManager.setQueue(queue);
			msgManager.setOrder(order);
			msgManager.setPermitsPerSecond(permitsPerSecond);
			msgManager.start();
		}
	}

	/**
	 * 将消息对象放入指定的消息队列中
	 * 
	 * @param object
	 *            消息对象
	 * @param queueName
	 *            消息队列名称
	 */
	public void setObjectToQueue(DelayMessage message, String queueName) {
		Map map = MessageInstance.getInstance().getQueueMap();
		Map typemap = MessageInstance.getInstance().getQueueTypeMap();
		if (map.get(queueName) == null) {
			logger.warn(queueName + " is not exist!");
		} else {
			if (typemap.containsKey(queueName)) {
				QueueType type = (QueueType) typemap.get(queueName);
				if (type == QueueType.DELAY) {
					DelayQueue<DelayMessage> queue = (DelayQueue<DelayMessage>) map.get(queueName);
					queue.add(message);
				} else if (type == QueueType.COMMON) {
					ArrayBlockingQueue<DelayMessage> queue = (ArrayBlockingQueue<DelayMessage>) map.get(queueName);
					queue.add(message);
				}
			}

		}
	}

	/**
	 * 得到消息队列大小
	 * 
	 * @param queueName
	 *            消息队列名称
	 * @return
	 */
	public int getQueueSize(String queueName) {
		int size = 0;
		try {
			Map map = MessageInstance.getInstance().getQueueMap();
			Map typemap = MessageInstance.getInstance().getQueueTypeMap();
			if (map.get(queueName) == null) {
				logger.warn(queueName + " is not exist!");
			} else {
				QueueType type = (QueueType) typemap.get(queueName);
				if (type == QueueType.DELAY) {
					DelayQueue<DelayMessage> queue = (DelayQueue<DelayMessage>) map.get(queueName);
					size = queue.size();
				} else if (type == QueueType.COMMON) {
					ArrayBlockingQueue<Object> queue = (ArrayBlockingQueue<Object>) map.get(queueName);
					size = queue.size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 得到消息隊列的所有名字 2009 Mar 27, 20097:16:08 PM Jack.Wang
	 * 
	 * @return 返回所有消息隊列的名字集合
	 */
	public Set<String> getQueueNames() {
		return MessageInstance.getInstance().getQueueMap().keySet();
	}

	/**
	 * 得到消息隊列工廠中一個初始化了幾個消息隊列 2009Mar 27, 20097:19:19 PM Jack.Wang
	 * 
	 * @return 消息工廠初始化的隊列個數
	 */
	public int getMessageFacotorySize() {
		return MessageInstance.getInstance().getQueueMap().size();
	}

	public static void main(String[] args) {
		MessageFactory mf = new MessageFactory();
		Set<String> set = mf.getQueueNames();
		for (Iterator it = set.iterator(); it.hasNext();) {
			String name = it.next().toString();
		}
	}
}
