package servercommon;

import java.io.Serializable;
import java.util.Objects;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 17:46
 * @Version 1.0
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;
    private String getter;
    private String sendTime;
    private String content;
    private String messageType;



    @Override
    public int hashCode() {
        return Objects.hash(sender, getter, sendTime, content, messageType);
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
