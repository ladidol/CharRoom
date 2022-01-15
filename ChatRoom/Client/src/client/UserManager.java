package client;

import servercommon.Code;
import servercommon.Message;
import servercommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 17:43
 * @Version 1.0
 *
 * 这里面包含几个关于用户的的方法
 * 第一个,检测用户是否合法,details 通过向服务器端发送请求,服务器端在通过数据库判断用户是否合法,
 * 因为打判断的时候已经打通了管道,所以就趁此机会,启动一个新线程,来和服务端保持通信;
 *
 * 第二个,请求在线人员的列表;
 *
 */
public class UserManager {
    private User user = new User();
    private Socket socket;

    public boolean checkUser(String userId,String password){
        boolean fanHuiZhi = false;

        //对即将发送给服务器端的的用户对象进行初始化;

        user.setUserId(userId);
        user.setPasswd(password);
        try {
            socket = new Socket("127.0.0.1",9999);
            //用objectOutputStream来传对象;
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            //送去的是用户对象,返回的是message对象;

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message checkMessage = (Message) ois.readObject();

            //接着判断登录是否ok
            if (checkMessage.getMessageType().equals(Code.LOGIN_SUCCEED)){
                //这时候就可以创建一个线程来表示该用户;
                //可以创建一个新类,ClientThread;
                ClientThread clientThread = new ClientThread(socket);
                //并将其放进一个集合中方便管理,方便一些方法通过userid找到相应的线程,从而得到socket

                ThreadContainer.addThread(userId,clientThread);


                fanHuiZhi = true;
            }else{
                //登录失败就不能启动新线程,并且关闭socket;
                socket.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return fanHuiZhi;

    }
    public void onlineUserList(){
        //发送一个Message,类型为,请求在线列表GET_ONLINE_FRIEND
        Message message = new Message();
        message.setMessageType(Code.GET_ONLINE_FRIEND);
        message.setSender(user.getUserId());

        //发送给服务器

        try {
            //通过正常查询,得到该用户和服务器连接的线程;
            ObjectOutputStream oos =
                    new ObjectOutputStream(ThreadContainer.getThread(user.getUserId()).getSocket().getOutputStream());
            //发送message对象,请求,在线用户列表;
            oos.writeObject(message);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
