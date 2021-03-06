# FastHeadPhone
![buildPassing](https://img.shields.io/bitbucket/pipelines/atlassian/adf-builder-javascript/task/SECO-2168.svg)
![minSdk](https://img.shields.io/badge/minSdk-19-orange.svg)
![jCenter](https://img.shields.io/badge/jCenter-0.0.1-blue.svg)

本工具类可以快速实现以下功能：
* 搜索、绑定、连接蓝牙设备
* 解绑一个或者所有蓝牙设备
* 查看蓝牙设备的类型
* 打开和关闭蓝牙音频传输通道

## 引入步骤
### 将下方的lastVersion替换为上方jCenter的版本号
```java
implementation 'cn.vangelis:fastheadphone:$lastVersion$'
```
## 使用步骤
### 初始化
```Java
//首先初始化工具类(建议在Application中进行)
HeadPhoneUtil.getInstance().init(this);
```
### 监听事件
```Java
//设置搜索监听事件
HeadPhoneUtils.getInstance().setDeviceScanResultListener(new DeviceScanResultListener() {
            @Override
            public void onDeviceDiscovery(int type, BluetoothDevice device) {
                //type为蓝牙设备的类型
                //device为搜索到的蓝牙设备
            }
        });
        
//设置蓝牙设备状态监听
HeadPhoneUtils.getInstance().setDeviceConnectStatusListener(new DeviceConnectStatusListener() {
            @Override
            public void connecting() {
              //连接中
            }

            @Override
            public void connected() {
              //已连接
            }

            @Override
            public void disconnecting() {
              //正在断开连接
            }

            @Override
            public void disconnected() {
              //已断开
            }
        });
```
### 具体实用方法
```Java
//开始搜索
HeadPhoneUtils.getInstance().startDiscovery();
//停止搜索
HeadPhoneUtils.getInstance().stopDiscovery();
//连接
HeadPhoneUtils.getInstance().connect(BluetoothDevice device);
//解除一个设备的绑定
HeadPhoneUtils.getInstance().unPairOneDevices(BluetoothDevice device);
//解除所有绑定
HeadPhoneUtils.getInstance().unPairAllDevices();
//打开蓝牙音频道
HeadPhoneUtils.getInstance().openBluetoothSco();
//关闭蓝牙音频道
HeadPhoneUtils.getInstance().closeBluetoothSco();
//销毁资源（*注意：销毁资源后，若要再次使用，则需要重新初始化一次*）
HeadPhoneUtils.getInstance().destroy();
```
### 工具方法
```Java
//获取蓝牙设备的类型
String BluetoothDeviceType.getDeviceTypeName(int type);
//打开或者关闭工具类的Log打印（默认关闭）
HeadPhoneUtils.getInstance().switchPrintLogInUtil(boolean isPrint);
//设置工具类Log的TAG
HeadPhoneUtils.getInstance().setLogTag(String tag);
```
### Todo-List
* 增加搜索的选项设置（搜索次数、搜索时间）
### 更新日志
* 2018.03.28 发布版本 V0.0.1
### 说明
此框架为自己的练手所用，请谨慎使用。手动滑稽.jpg
### 联系方式
* QQ：460977141
