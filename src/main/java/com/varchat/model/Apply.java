package com.varchat.model;

import java.util.Date;

/**
 * Created by hunger on 2016/12/3.
 */
public class Apply {
    private int applyId;//申请id
    private User sender;//发送者对象
    private int receiverId;//接收者id
    private Date createTime;//创建时间
    private int status;//处理状态

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "applyId=" + applyId +
                ", sender=" + sender +
                ", receiverId=" + receiverId +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
