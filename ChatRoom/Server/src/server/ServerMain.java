package server;

import servercommon.Code;
import servercommon.Message;
import servercommon.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 20:13
 * @Version 1.0
 */
public class ServerMain {
    public static void main(String[] args) {
        new ServerMain();
        System.out.println("服务器退出......");
    }
    private ServerSocket ss = null;
    private static ConcurrentHashMap<String, User> loginnedUsers = new ConcurrentHashMap<>();
    static {
        String sql = "select * from user";
        JdbcTemplate jdbcTemplate  = new JdbcTemplate(JDBCUtils.getDataSource());

        List<User> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
        for (User u: list) {
            loginnedUsers.put(u.getUserId(),u);
        }
    }
    public ServerMain(){
        //服务器端接收界面
        System.out.println("服务器在9999端口监听....");
        //这里可以有一个推送服务器消息的线程;
        new Thread(new SendToAllUser()).start();

        try {
            ss = new ServerSocket(9999);
            //一直监听,
            while(true){
                //接收来自客户端的socket
                //首先判断该用户登录成功与否
                Socket socket = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                //读取客户端发来的用户对象,看是否合法;
                User user = (User) ois.readObject();
                Message message = new Message();
                if (checkUser(user.getUserId(),user.getPasswd())){
                    message.setMessageType(Code.LOGIN_SUCCEED);
                    oos.writeObject(message);
                    //创建一个线程,和客户端保持通信通过获取到的socket为标志;
                    ClientThread clientThread = new ClientThread(socket,user.getUserId());
                    //
                    clientThread.start();
                    //加进集合中管理
                    ThreadContainer.addClientThread(user.getUserId(),clientThread);
                }else{
                    System.out.println("用户 id=" + user.getUserId() + " pwd=" + user.getPasswd() + " 验证失败");
                    message.setMessageType(Code.LOGIN_FAIL);
                    oos.writeObject(message);
                    //关闭socket
                    socket.close();
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //验证用户是否有效的方法
    private boolean checkUser(String userId, String passwd) {

        User user = loginnedUsers.get(userId);
        //过关的验证方式
        if(user == null) {//说明userId没有存在validUsers 的key中
            System.out.println("不存在用户,请重新输入");
            return  false;
        }
        if(!user.getPasswd().equals(passwd)) {//userId正确，但是密码错误
            System.out.println("userId正确,但是密码错误");
            return false;
        }
        return  true;
    }


}
