package cn.vangelis.fastheadphone.listener;

import android.bluetooth.BluetoothDevice;

/**
 * Comment: //todo
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/3/28
 * Email:Vangelis.wang@make1.cn
 */

public interface DeviceScanResultListener {

    void onDeviceDiscovery(int type, BluetoothDevice devices);
}
