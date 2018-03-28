package cn.vangelis.fastheadphone.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.vangelis.fastheadphone.listener.CloseBluetoothScoListener;
import cn.vangelis.fastheadphone.listener.DeviceConnectStatusListener;
import cn.vangelis.fastheadphone.listener.DeviceScanResultListener;
import cn.vangelis.fastheadphone.listener.OpenBluetoothScoListener;

/**
 * Comment: 蓝牙工具类
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/3/27
 * Email:Vangelis.wang@make1.cn
 */

public class HeadPhoneUtils {

    private static String TAG = "HeadPhoneUtils";

    private static boolean isPrintLog = false;


    private BluetoothAdapter mBlueAdapter;
    private static BluetoothA2dp mBlueA2dp;
    private static BluetoothHeadset mBlueHeadSet;

    private Context mContext;
    private static BluetoothDevice mBluetoothDevice;

    private BroadcastReceiver mBroadcastReceiver;

    @SuppressLint("StaticFieldLeak")
    private static HeadPhoneUtils instance = null;

    private static DeviceScanResultListener mDeviceScanResultListener;
    private static DeviceConnectStatusListener mDeviceConnectStatusListener;

    private HeadPhoneUtils() {
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static HeadPhoneUtils getInstance() {
        if (instance == null) {
            return new HeadPhoneUtils();
        }
        return instance;
    }

    private void initBroadCastReceiver() {
        if (mBroadcastReceiver != null) {
            return;
        }

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                BluetoothDevice device;
                if (intent.getAction() != null) {
                    switch (intent.getAction()) {
                        case BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED:
                            switch (intent.getIntExtra(BluetoothA2dp.EXTRA_STATE, -1)) {
                                case BluetoothA2dp.STATE_CONNECTING:
                                    device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                    printLog("device: " + device.getName() + " connecting");
                                    if (mDeviceConnectStatusListener != null) {
                                        mDeviceConnectStatusListener.connecting();
                                    }
                                    break;
                                case BluetoothA2dp.STATE_CONNECTED:
                                    device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                    printLog("device: " + device.getName() + " connected");
                                    if (mDeviceConnectStatusListener != null) {

                                        mDeviceConnectStatusListener.connected();
                                    }
                                    break;
                                case BluetoothA2dp.STATE_DISCONNECTING:
                                    device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                    printLog("device: " + device.getName() + " disconnecting");
                                    if (mDeviceConnectStatusListener != null) {
                                        mDeviceConnectStatusListener.disconnecting();
                                    }
                                    break;
                                case BluetoothA2dp.STATE_DISCONNECTED:
                                    device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                    printLog("device: " + device.getName() + " disconnected");
                                    if (mDeviceConnectStatusListener != null) {
                                        mDeviceConnectStatusListener.disconnected();
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case BluetoothDevice.ACTION_FOUND:
                            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            int deviceClassType = device.getBluetoothClass().getDeviceClass();
                            if (!TextUtils.isEmpty(device.getName())) {
                                if (mDeviceScanResultListener != null) {
                                    mDeviceScanResultListener.onDeviceDiscovery(deviceClassType, device);
                                }
                            }
                            break;
                        case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                            int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
                            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            switch (bondState) {
                                //配对成功
                                case BluetoothDevice.BOND_BONDED:
                                    printLog("Device:" + device.getName() + " bonded.");
                                    //取消搜索
                                    mBlueAdapter.cancelDiscovery();
                                    //连接蓝牙设备
                                    connect(mBluetoothDevice);
                                    break;
                                case BluetoothDevice.BOND_BONDING:
                                    printLog("Device:" + device.getName() + " bonding.");
                                    break;
                                case BluetoothDevice.BOND_NONE:
                                    printLog("Device:" + device.getName() + " not bonded.");
                                    break;
                                default:
                                    break;

                            }
                            break;
                        case BluetoothAdapter.ACTION_STATE_CHANGED:
                            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                            switch (state) {
                                case BluetoothAdapter.STATE_TURNING_ON:
                                    printLog("BluetoothAdapter is turning on.");
                                    break;
                                case BluetoothAdapter.STATE_ON:
                                    printLog("BluetoothAdapter is on.");
                                    init(context);
                                    break;
                                case BluetoothAdapter.STATE_TURNING_OFF:
                                    printLog("BluetoothAdapter is turning off.");
                                    break;
                                case BluetoothAdapter.STATE_OFF:
                                    printLog("BluetoothAdapter is off.");
                                    break;
                                default:
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        };
    }

    /**
     * 初始化
     *
     * @param context Context
     */
    public void init(Context context) {
        mContext = context;

        if (mBlueAdapter == null) {
            return;
        }

        if (!mBlueAdapter.isEnabled()) {
            mBlueAdapter.enable();
        } else {
            getBlueA2dp();
            getBlueHeadSet();
        }

        initBroadCastReceiver();
        initBluetoothStatusListener();
    }

    /**
     * 是否打印工具内的log
     */
    public void switchPrintLogInUtil(boolean isPrint) {
        isPrintLog = isPrint;
    }

    /**
     * 设置Log的Tag
     * @param tag tag
     */
    public void setLogTag(String tag) {
        TAG = tag;
    }

    /**
     * 打印Log
     * @param msg 打印内容
     */
    private void printLog(String msg) {
        if (isPrintLog) {
            Log.i(TAG, msg);
        }
    }

    /**
     * 注册蓝牙状态监听
     */
    private void initBluetoothStatusListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
        filter.addAction(BluetoothA2dp.ACTION_PLAYING_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mContext.registerReceiver(mBroadcastReceiver, filter);
    }

    /**
     * 获取A2dp
     */
    private void getBlueA2dp() {
        if (mBlueAdapter == null) {
            return;
        }

        if (mBlueA2dp != null) {
            return;
        }

        mBlueAdapter.getProfileProxy(mContext, new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                if (i == BluetoothProfile.A2DP) {
                    printLog("获取了A2DP");
                    mBlueA2dp = (BluetoothA2dp) bluetoothProfile;
                }
            }

            @Override
            public void onServiceDisconnected(int i) {

            }
        }, BluetoothProfile.A2DP);
    }

    /**
     * 获取BluetoothHeadSet
     */
    private void getBlueHeadSet() {
        if (mBlueAdapter == null) {
            return;
        }

        if (mBlueHeadSet != null) {
            return;
        }

        mBlueAdapter.getProfileProxy(mContext, new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                if (i == BluetoothProfile.HEADSET) {
                    printLog("获取了HEADSET");
                    mBlueHeadSet = (BluetoothHeadset) bluetoothProfile;
                }
            }

            @Override
            public void onServiceDisconnected(int i) {
            }
        }, BluetoothProfile.HEADSET);
    }

    /**
     * 开始搜索
     */
    public HeadPhoneUtils startDiscovery() {
        printLog("mBlueAdapter:" + mBlueAdapter);
        if (mBlueAdapter != null && mBlueAdapter.isEnabled() && !mBlueAdapter.isDiscovering()) {
            mBlueAdapter.startDiscovery();
        }
        return this;
    }

    public HeadPhoneUtils setDeviceScanResultListener(DeviceScanResultListener listener) {
        mDeviceScanResultListener = listener;
        return this;
    }

    public HeadPhoneUtils setDeviceConnectStatusListener(DeviceConnectStatusListener listener) {
        mDeviceConnectStatusListener = listener;
        return this;
    }

    /**
     * 停止搜索
     */
    public void stopDiscovery() {
        if (mBlueAdapter != null && mBlueAdapter.isEnabled() && mBlueAdapter.isDiscovering()) {
            mBlueAdapter.cancelDiscovery();
        }
    }

    /**
     * 开始连接
     */
    public HeadPhoneUtils connect(BluetoothDevice device) {
        printLog("mBlueA2dp:" + mBlueA2dp + ",DEVICE:" + device);
        if (mBlueA2dp == null) {
            return this;
        }
        if (device == null) {
            return this;
        }

        mBluetoothDevice = device;

        try {
            @SuppressLint("PrivateApi")
            Method connect = mBlueA2dp.getClass().getDeclaredMethod("connect", BluetoothDevice.class);
            connect.setAccessible(true);
            connect.invoke(mBlueA2dp, mBluetoothDevice);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * 断开连接
     */
    private void disconnect() {
        if (mBlueA2dp == null) {
            return;
        }
        if (mBluetoothDevice == null) {
            return;
        }

        try {
            @SuppressLint("PrivateApi")
            Method disconnect = mBlueA2dp.getClass().getDeclaredMethod("disconnect", BluetoothDevice.class);
            disconnect.setAccessible(true);
            disconnect.invoke(mBlueA2dp, mBluetoothDevice);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始配对
     */
    private void createBond() {
        if (mBluetoothDevice != null) {
            mBluetoothDevice.createBond();
        }
    }

    /**
     * 打开蓝牙通道
     */
    @SuppressLint("NewApi")
    public void openBluetoothSco(OpenBluetoothScoListener listener) {
        printLog("mBluetoothHeadset:" + mBlueHeadSet + ",mBluetoothDevice:" + mBluetoothDevice);
        if (mBlueHeadSet != null && mBluetoothDevice != null) {
            //如果已经打开了蓝牙通道，则不需要再次打开
            if (!mBlueHeadSet.isAudioConnected(mBluetoothDevice)) {
                boolean isSuccess = mBlueHeadSet.startVoiceRecognition(mBluetoothDevice);
                printLog("打开蓝牙通道 isSuccess:" + isSuccess);
                listener.openScoSuccess();
            }
        } else {
            if (mBlueHeadSet == null) {
                listener.openScoFail("没有进行初始化");
            } else {
                listener.openScoFail("当前未有设备连接");
            }
        }
    }

    /**
     * 打开蓝牙通道
     */
    @SuppressLint("NewApi")
    public void openBluetoothSco() {
        printLog("mBluetoothHeadset:" + mBlueHeadSet + ",mBluetoothDevice:" + mBluetoothDevice);
        if (mBlueHeadSet != null && mBluetoothDevice != null) {
            //如果已经打开了蓝牙通道，则不需要再次打开
            if (!mBlueHeadSet.isAudioConnected(mBluetoothDevice)) {
                boolean isSuccess = mBlueHeadSet.startVoiceRecognition(mBluetoothDevice);
                printLog("打开蓝牙通道 isSuccess:" + isSuccess);
            }
        }
    }

    /**
     * 关闭蓝牙通道
     */
    @SuppressLint("NewApi")
    public void closeBluetoothSco() {
        if (mBlueHeadSet != null && mBluetoothDevice != null) {
            boolean isSuccess = mBlueHeadSet.stopVoiceRecognition(mBluetoothDevice);
            printLog("关闭蓝牙通道 isSuccess:" + isSuccess);
        }
    }

    /**
     * 关闭蓝牙通道
     */
    @SuppressLint("NewApi")
    public void closeBluetoothSco(CloseBluetoothScoListener listener) {
        if (mBlueHeadSet != null && mBluetoothDevice != null) {
            boolean isSuccess = mBlueHeadSet.stopVoiceRecognition(mBluetoothDevice);
            printLog("关闭蓝牙通道 isSuccess:" + isSuccess);
            listener.closeScoSuccess();
        } else {
            if (mBlueHeadSet == null) {
                listener.closeScoFail("没有进行初始化");
            } else {
                listener.closeScoFail("当前未有设备连接");
            }
        }
    }

    /**
     * 取消所有配对
     */
    public void unPairAllDevices() {
        for (BluetoothDevice device : mBlueAdapter.getBondedDevices()) {
            try {
                @SuppressLint("PrivateApi")
                Method removeBond = device.getClass().getDeclaredMethod("removeBond");
                removeBond.invoke(device);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解绑一个蓝牙设备
     *
     * @param device 蓝牙设备
     */
    public void unPairOneDevices(BluetoothDevice device) {
        if (device == null) {
            return;
        }

        try {
            @SuppressLint("PrivateApi")
            Method removeBond = device.getClass().getDeclaredMethod("removeBond");
            removeBond.invoke(device);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁
     */
    public void destroy() {

        if (mBlueAdapter == null) {
            return;
        }

        if (mBlueA2dp != null) {
            mBlueA2dp = null;
        }

        if (mBlueHeadSet != null) {
            mBlueHeadSet = null;
        }

        if (mDeviceScanResultListener != null) {
            mDeviceScanResultListener = null;
        }

        if (mBroadcastReceiver != null) {
            mContext.unregisterReceiver(mBroadcastReceiver);
        }

        if (mBlueAdapter.isDiscovering()) {
            mBlueAdapter.cancelDiscovery();
        }

        //关闭ProfileProxy，也就是断开service连接
        mBlueAdapter.closeProfileProxy(BluetoothProfile.A2DP, mBlueA2dp);
    }

}
