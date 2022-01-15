package client;

import servercommon.Code;
import servercommon.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 19:37
 * @Version 1.0
 */
public class MessageManager {
    public void sendMessageToAll(String content,String senderId){
        //构建一个message,因为是群发,所以,只需要明确一下发送者是谁就行;
        Message message = new Message();
        message.setMessageType(Code.TO_ALL_MES);//群发
        message.setContent(content);
        message.setSender(senderId);
        message.setGetter("全部在线人员");
        message.setSendTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));//发送时间设置到message对象
        System.out.println("这里是从sendMessageToAll方法中发出来的");
        //发送给服务器
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ThreadContainer.getThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
            //存消息进入数据库的操作留在server端来做;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessageToOne(String content,String senderId,String getterId){
        Message message = new Message();
        message.setMessageType(Code.TO_ONE_MES);//私发
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));//发送时间设置到message对象
        System.out.println("这里是从sendMessageToOne方法中发出来的");
        //发送给服务器
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ThreadContainer.getThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
            //存消息进入数据库的操作留在server端来做;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
