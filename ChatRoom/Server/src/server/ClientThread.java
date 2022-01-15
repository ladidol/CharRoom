package server;

import servercommon.Code;
import servercommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 21:01
 * @Version 1.0
 */
public class ClientThread extends Thread{
    private Socket socket;
    private String userId;

    public ClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while(true){
            System.out.println("服务端和客户端" + userId + " 保持通信，读取数据...");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //根据发来的message类型,做相应的业务;
                if (message.getMessageType().equals(Code.GET_ONLINE_FRIEND)){
                    System.out.println(message.getSender() + "请求全部在线用户列表");
                    String onlineUser = ThreadContainer.getOnlineUser();
                    //返回message
                    //构建一个message对象返回个客户端,
                    Message message1 = new Message();
                    message1.setMessageType(Code.RET_ONLINE_FRIEND);
                    message1.setContent(onlineUser);
                    //将这个信息原路返回;
                    message1.setGetter(message.getSender());

                    //正常送出包;
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message1);

                }else if (message.getMessageType().equals(Code.TO_ONE_MES)){
                    //私聊,因为要发给专门的用户,所以,要通过getter,找到它的线程,在找到它的socket
                    ObjectOutputStream oos =
                            new ObjectOutputStream(ThreadContainer.getClientThread(message.getGetter()).getSocket().getOutputStream());
                    //发给你想要发给的那位ta;
                    oos.writeObject(message);

                }else if (message.getMessageType().equals(Code.TO_ALL_MES)){
                    //用一个新的hashmap来装在线人员;
                    HashMap<String, ClientThread> hm =ThreadContainer.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();



                    /*
                    * 有待商榷,的地方;*/

                    while (iterator.hasNext()) {

                        //取出在线用户id
                        String onLineUserId = iterator.next().toString();

                        if (!onLineUserId.equals(message.getSender())) {//排除群发消息的这个用户

                            //进行转发message
                            ObjectOutputStream oos =
                                    new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }

                    }
                }else{
                    System.out.println("其他类型的message,暂时不处理");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
