package cn.vangelis.fastheadphone.listener;

/**
 * Comment: 蓝牙连接状态监听
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/3/28
 * Email:Vangelis.wang@make1.cn
 */

public interface DeviceConnectStatusListener {

    void connecting();

    void connected();

    void disconnecting();

    void disconnected();
}
