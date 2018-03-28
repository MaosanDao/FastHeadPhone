package cn.vangelis.fastheadphone.listener;

/**
 * Comment: 关闭蓝牙耳机通道的监听
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/3/28
 * Email:Vangelis.wang@make1.cn
 */

public interface CloseBluetoothScoListener {

    void closeScoSuccess();

    void closeScoFail(String error);
}
