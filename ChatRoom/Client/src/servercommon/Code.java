package servercommon;

public interface Code {
    String LOGIN_SUCCEED = "1"; //表示登录成功
    String LOGIN_FAIL = "2"; // 表示登录失败
    String TO_ALL_MES = "3"; //普通群聊信息包
    String GET_ONLINE_FRIEND = "4"; //要求返回在线用户列表
    String RET_ONLINE_FRIEND = "5"; //返回在线用户列表
    String CLIENT_EXIT = "6"; //客户端请求退出
    String TO_ONE_MES = "7"; //私聊消息报
    String FILE_MES = "8"; //文件消息(发送文件)
    String DISONLINE_MES = "8";//表示该信息是离线留言
}
