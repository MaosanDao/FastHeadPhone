package cn.vangelis.fastheadphone.utils;

import android.bluetooth.BluetoothClass;

/**
 * Comment: 设备类型
 *
 * @author Vangelis.Wang in Make1
 * @date 2018/3/28
 * Email:Vangelis.wang@make1.cn
 */

public class BluetoothDeviceType {

    public static final int AUDIO_VIDEO_CAMCORDER = 1076;
    public static final int AUDIO_VIDEO_CAR_AUDIO = 1056;
    public static final int AUDIO_VIDEO_HANDSFREE = 1032;
    public static final int AUDIO_VIDEO_HEADPHONES = 1048;
    public static final int AUDIO_VIDEO_HIFI_AUDIO = 1064;
    public static final int AUDIO_VIDEO_LOUDSPEAKER = 1044;
    public static final int AUDIO_VIDEO_MICROPHONE = 1040;
    public static final int AUDIO_VIDEO_PORTABLE_AUDIO = 1052;
    public static final int AUDIO_VIDEO_SET_TOP_BOX = 1060;
    public static final int AUDIO_VIDEO_UNCATEGORIZED = 1024;
    public static final int AUDIO_VIDEO_VCR = 1068;
    public static final int AUDIO_VIDEO_VIDEO_CAMERA = 1072;
    public static final int AUDIO_VIDEO_VIDEO_CONFERENCING = 1088;
    public static final int AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER = 1084;
    public static final int AUDIO_VIDEO_VIDEO_GAMING_TOY = 1096;
    public static final int AUDIO_VIDEO_VIDEO_MONITOR = 1080;
    public static final int AUDIO_VIDEO_WEARABLE_HEADSET = 1028;
    public static final int COMPUTER_DESKTOP = 260;
    public static final int COMPUTER_HANDHELD_PC_PDA = 272;
    public static final int COMPUTER_LAPTOP = 268;
    public static final int COMPUTER_PALM_SIZE_PC_PDA = 276;
    public static final int COMPUTER_SERVER = 264;
    public static final int COMPUTER_UNCATEGORIZED = 256;
    public static final int COMPUTER_WEARABLE = 280;
    public static final int HEALTH_BLOOD_PRESSURE = 2308;
    public static final int HEALTH_DATA_DISPLAY = 2332;
    public static final int HEALTH_GLUCOSE = 2320;
    public static final int HEALTH_PULSE_OXIMETER = 2324;
    public static final int HEALTH_PULSE_RATE = 2328;
    public static final int HEALTH_THERMOMETER = 2312;
    public static final int HEALTH_UNCATEGORIZED = 2304;
    public static final int HEALTH_WEIGHING = 2316;
    public static final int PHONE_CELLULAR = 516;
    public static final int PHONE_CORDLESS = 520;
    public static final int PHONE_ISDN = 532;
    public static final int PHONE_MODEM_OR_GATEWAY = 528;
    public static final int PHONE_SMART = 524;
    public static final int PHONE_UNCATEGORIZED = 512;
    public static final int TOY_CONTROLLER = 2064;
    public static final int TOY_DOLL_ACTION_FIGURE = 2060;
    public static final int TOY_GAME = 2068;
    public static final int TOY_ROBOT = 2052;
    public static final int TOY_UNCATEGORIZED = 2048;
    public static final int TOY_VEHICLE = 2056;
    public static final int WEARABLE_GLASSES = 1812;
    public static final int WEARABLE_HELMET = 1808;
    public static final int WEARABLE_JACKET = 1804;
    public static final int WEARABLE_PAGER = 1800;
    public static final int WEARABLE_UNCATEGORIZED = 1792;
    public static final int WEARABLE_WRIST_WATCH = 1796;

    /**
     * 返回设备的类型
     *
     * @param type DeviceType
     * @return 类型名
     */
    public static String getDeviceTypeName(int type) {
        String name;
        switch (type) {
            case BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER:
                name = "录像机";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO:
                name = "车载设备";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE:
                name = "蓝牙耳机";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER:
                name = "扬声器";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE:
                name = "麦克风";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO:
                name = "打印机";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX:
                name = "BOX";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED:
                name = "未知的";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_VCR:
                name = "录像机";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA:
                name = "照相机录像机";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING:
                name = "conferencing";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER:
                name = "显示器和扬声器";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY:
                name = "游戏";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR:
                name = "显示器";
                break;
            case BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET:
                name = "可穿戴设备";
                break;
            case BluetoothClass.Device.PHONE_CELLULAR:
                name = "手机";
                break;
            case BluetoothClass.Device.PHONE_CORDLESS:
                name = "无线电设备";
                break;
            case BluetoothClass.Device.PHONE_ISDN:
                name = "手机服务数据网";
                break;
            case BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY:
                name = "手机调节器";
                break;
            case BluetoothClass.Device.PHONE_SMART:
                name = "智能手机";
                break;
            case BluetoothClass.Device.PHONE_UNCATEGORIZED:
                name = "未知手机";
                break;
            case BluetoothClass.Device.WEARABLE_GLASSES:
                name = "可穿戴眼睛";
                break;
            case BluetoothClass.Device.WEARABLE_HELMET:
                name = "可穿戴头盔";
                break;
            case BluetoothClass.Device.WEARABLE_JACKET:
                name = "可穿戴上衣";
                break;
            case BluetoothClass.Device.WEARABLE_PAGER:
                name = "客串点寻呼机";
                break;
            case BluetoothClass.Device.WEARABLE_UNCATEGORIZED:
                name = "未知的可穿戴设备";
                break;
            case BluetoothClass.Device.WEARABLE_WRIST_WATCH:
                name = "手腕监听设备";
                break;
            case BluetoothClass.Device.TOY_CONTROLLER:
                name = "可穿戴设备";
                break;
            case BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE:
                name = "玩具doll_action_figure";
                break;
            case BluetoothClass.Device.TOY_GAME:
                name = "游戏";
                break;
            case BluetoothClass.Device.TOY_ROBOT:
                name = "玩具遥控器";
                break;
            case BluetoothClass.Device.TOY_UNCATEGORIZED:
                name = "玩具未知设备";
                break;
            case BluetoothClass.Device.TOY_VEHICLE:
                name = "vehicle";
                break;
            case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE:
                name = "健康状态-血压";
                break;
            case BluetoothClass.Device.HEALTH_DATA_DISPLAY:
                name = "健康状态数据";
                break;
            case BluetoothClass.Device.HEALTH_GLUCOSE:
                name = "健康状态葡萄糖";
                break;
            case BluetoothClass.Device.HEALTH_PULSE_OXIMETER:
                name = "健康状态脉搏血氧计";
                break;
            case BluetoothClass.Device.HEALTH_PULSE_RATE:
                name = "健康状态脉搏速率";
                break;
            case BluetoothClass.Device.HEALTH_THERMOMETER:
                name = "健康状态体温计";
                break;
            case BluetoothClass.Device.HEALTH_WEIGHING:
                name = "健康状态体重";
                break;
            case BluetoothClass.Device.HEALTH_UNCATEGORIZED:
                name = "未知健康状态设备";
                break;
            case BluetoothClass.Device.COMPUTER_DESKTOP:
                name = "电脑桌面";
                break;
            case BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA:
                name = "手提电脑或Pad";
                break;
            case BluetoothClass.Device.COMPUTER_LAPTOP:
                name = "便携式电脑";
                break;
            case BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA:
                name = "微型电脑";
                break;
            case BluetoothClass.Device.COMPUTER_SERVER:
                name = "电脑服务";
                break;
            case BluetoothClass.Device.COMPUTER_UNCATEGORIZED:
                name = "未知的电脑设备";
                break;
            case BluetoothClass.Device.COMPUTER_WEARABLE:
                name = "可穿戴的电脑";
                break;
            default:
                name = "未知设备";
        }
        return name;
    }
}
