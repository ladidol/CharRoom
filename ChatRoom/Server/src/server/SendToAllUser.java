package server;

import servercommon.Code;
import servercommon.Message;
import utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 20:25
 * @Version 1.0
 */
public class SendToAllUser implements Runnable{

    @Override
    public void run() {
        //用while(true)实现多次推送
        while(true){
            System.out.println("这边是服务器端,有主动广播可以在这里发出(输入exit退出广播线程)");
            String str = Utility.readString(100);
            if ("exit".equals(str)){
                break;
            }
            //构建一个消息 , 群发消息
            Message message = new Message();
            message.setSender("服务器");
            message.setMessageType(Code.TO_ALL_MES);
            message.setContent(str);
            message.setSendTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));
            message.setGetter("全部在线用户");

            /*
            *
            *
            *
            *
            *  这里可以将message存入数据库中*/
            /*
            *
            *
            *
            *
            *
            * 未完待续这里需要遍历在线客户线程池
            *
            *
            *
            * */
            HashMap<String, ClientThread> hm = ThreadContainer.getHm();

            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineUserId = iterator.next().toString();
                try {
                    ObjectOutputStream oos =
                            new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
