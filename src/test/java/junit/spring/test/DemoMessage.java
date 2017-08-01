package junit.spring.test;

import java.util.Date;

import com.liuyh.DateUtils;
import com.liuyh.DelayMessage;

/**
 * 延迟队列测试例子
 * 
 * @author liuyonghong
 *
 */
public class DemoMessage extends DelayMessage {

	public DemoMessage(int id, String body, long delayTime) {
		super(id, body, delayTime);
	}

	public DemoMessage(int id, String body) {
		super(id, body, 0);
	}

	@Override
	public void execute() {
		Date d = new Date();
		String dd = DateUtils.getTimeStr(d);
		System.out
				.println("消费消息: id:" + this.getId() + ",msg:" + this.getBody() + ",delaytime:" + this.getDelayTime() + ",当前时间:" + dd + ",time:" + d.getTime());
	}

	public static void main(String[] args) {
	}
}
