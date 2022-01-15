package client;

import client.MessageManager;
import client.UserManager;
import utils.Utility;

/**
 * @title: ${}
 * @Author FengXQ
 * @Date: 2021/11/9 17:00
 * @Version 1.0
 *
 *
 * 昨晚过后,再用用一下群聊的方法来改一下;
 *
 *
 * 在线列表还没完成;
 * 数据库存消息记录类还没建;
 * 好像在客户端不需要建立一个线程容器;,直接用MessageManager类里面的socket就行;
 * 11.10做吧
 * 进群人员广播,退群人员广播,
 */
public class ClientMain {

    private int key = 0;//键盘输入值
    private boolean loop = true;//二级菜单循环显示,循环标志,
    private UserManager userManager = new UserManager();//用于管理用户的包;
    private MessageManager messageManager = new MessageManager();//用于管理message发送的类



    public static void main(String[] args) {
        new ClientMain().Mene();
        System.out.println("客户端退出系统....");
    }
    //显示选择菜单;
    private void Mene(){
        //一直处于循环中,知道你想要主动退出为止,
        while(loop){//只要用户随时想要退出,只要他一句话的事情,他就可以,直接退出系统;
            System.out.println("欢迎来到登录网络系统");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 2 注册系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择: ");
            key = Utility.readInt();
            switch (key){
                case 1:
                    System.out.print("请输入用户号: ");
                    String userId = Utility.readString(20);
                    System.out.print("请输入密  码: ");
                    String password = Utility.readString(20);
                    if (userManager.checkUser(userId,password)){
                        //如果判断成功,就进入第二级菜单;
                        System.out.println("欢迎(用户 " + userId + " )登陆成功");
                        while(loop){
                            System.out.println("\n=========网络通信系统二级菜单(用户 " + userId + " )=======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择: ");
                            key  = Utility.readInt();
                            switch (key){
                                case 1:
                                    //获取在线用户列表;
                                    //这里又要用到客户管理的一个方法了
                                    userManager.onlineUserList();
                                    break;
                                case 2:
                                    //群发消息
                                    System.out.println("输入你想要说的话");
                                    String contentAll = Utility.readString(100);
                                    //这里就需要一个管理客户端收发message对象的类了;
                                    //使用里面的群发消息方法
                                    messageManager.sendMessageToAll(contentAll,userId);

                                    break;
                                case  3:
                                    System.out.print("输入你想要私聊的对象:");
                                    String getterId = Utility.readString(20);
                                    System.out.println("content");
                                    String contentOne = Utility.readString(100);
                                    //这里又要用的管理message类里面的私发消息方法
                                    messageManager.sendMessageToOne(contentOne,userId,getterId);
                                    //私聊消息
                                    break;
                                case 9:
                                    //退出系统
                                    loop = false;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }else{
                        //如果登陆失败
                        System.out.println("======登陆失败,请重新输入=====");
                    }
                    break;
                case 2:
                    //这里为注册系统;,等待你注册
                    break;
                case 9:
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }
}
