package com.liuyh;

public enum QueueType {
	DELAY(1, "延迟消息"), COMMON(0, "正常消息");

	private Integer code;

	private String msg = "";

	QueueType(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

	public static String getMsg(Integer code) {
		for (QueueType re : QueueType.values()) {
			if (re.code.intValue() == code.intValue()) {
				return re.msg;
			}
		}
		return "";
	}

	public static QueueType getRc(Integer code) {
		for (QueueType re : QueueType.values()) {
			if (re.code.intValue() == code.intValue()) {
				return re;
			}
		}
		return null;
	}

}