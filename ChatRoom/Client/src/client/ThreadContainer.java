package client;

import com.mysql.cj.xdevapi.Client;

import java.util.HashMap;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 19:25
 * @Version 1.0
 */
public class ThreadContainer {
    //用hashmap来管理,key为用户id,value为线程;
    private static HashMap<String, ClientThread> hm = new HashMap<>();
    //将线程放进去的方法
    public static void addThread(String userId, ClientThread clientThread){
        hm.put(userId,clientThread);
    }
    //通过userid可以得到飞鹰的线程;
    public static ClientThread getThread(String userId){
        return hm.get(userId);
    }
}
