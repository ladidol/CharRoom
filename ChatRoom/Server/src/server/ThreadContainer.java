package server;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 21:18
 * @Version 1.0
 */
public class ThreadContainer {
    private static HashMap<String, ClientThread> hm = new HashMap<>();


    //返回 hm
    public static HashMap<String,ClientThread> getHm() {
        return hm;
    }

    //添加线程对象到 hm 集合
    public static void addClientThread(String userId,ClientThread clientThread) {

        hm.put(userId, clientThread);

    }

    //根据userId 返回ClientThread线程
    public static ClientThread getClientThread(String userId) {
        return hm.get(userId);
    }

    //增加一个方法，从集合中，移除某个线程对象
    public static void removelientThread(String userId) {
        hm.remove(userId);
    }

    //这里编写方法，可以返回在线用户列表
    public static String getOnlineUser() {
        //集合遍历 ，遍历 hashmap的key
        //这里的key就是用户名字;
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while (iterator.hasNext()) {
            onlineUserList += iterator.next().toString() + " ";
        }
        return onlineUserList;
    }
}
