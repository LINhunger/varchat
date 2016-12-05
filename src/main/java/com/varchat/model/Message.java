package com.varchat.model;
import java.util.Date;
/**
 * 消息类
 *
 */
public class Message {

	private int msgId;//id
	private int senderId;// 发送者id
	private String senderName;	// 发送者名称
	private int receiverId;
	public String text;	// 发送的文本
	public Date date;	// 发送日期

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message{" +
				"msgId=" + msgId +
				", senderId=" + senderId +
				", senderName='" + senderName + '\'' +
				", receiverId=" + receiverId +
				", text='" + text + '\'' +
				", date=" + date +
				'}';
	}
}
