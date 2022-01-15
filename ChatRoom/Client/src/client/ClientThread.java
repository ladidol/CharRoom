package client;

import servercommon.Code;
import servercommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 18:20
 * @Version 1.0
 */
public class ClientThread extends Thread{
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //为了一直保持通信,这里使用了while(true)循环

        while(true){
            System.out.println("客户端线程已经打开,等待你的选择或者服务器的通知");
            //这里全是展示在客户端的窗口
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果未收到消息message,该线程就阻塞,但是不影响main线程的前进;
                //新建一个message来接收消息
                Message message = (Message) ois.readObject();
                //因为服务器只会发送message对象回来,所以这个message对象可能有不同的类型;
                //需要判断一下执行相应的操作
                if (message.getMessageType().equals(Code.TO_ALL_MES)){
                    //表示普通的群聊对象
                    System.out.println("你已经向大家发送了一条群聊");
                }else if (message.getMessageType().equals(Code.TO_ONE_MES)){
                    System.out.println("你已经向" + message.getGetter() + "悄悄说了" + message.getContent());
                }else if (message.getMessageType().equals(Code.RET_ONLINE_FRIEND)){

                    //建立一个String数组来接收被分割的在线列表信息;
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n======当前在线用户列表======");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户 : " + onlineUsers[i]);
                    }



                }else{
                    System.out.println("message类型无匹配,暂不处理...");
                }




            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
