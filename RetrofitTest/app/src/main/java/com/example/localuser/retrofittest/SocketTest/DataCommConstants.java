package com.example.localuser.retrofittest.SocketTest;

public class DataCommConstants {
    /**
     * 设置跑步机模式
     *0=>手动
     * 1=>自动
     */
    public final static String KEY_SN_MODE = "sn_mode";

    /**
     * 命令序列号
     */
    public final static String KEY_SN_CMD_ID = "sn_cmd_id";

    /**
     * 命令执行结果
     * 0=>失败
     * 1=>成功
     */
    public final static String KEY_SN_CMD_RESULT = "sn_cmd_result";

    /**
     * 儿童锁
     * 0-未锁，1-锁定
     */
    public final static String KEY_SN_CHILD_LOCK = "sn_child_lock";

    /**
     * 设置走步机最大速度
     */
    public final static String KEY_SN_APP_MAX_SPEED = "sn_app_max_speed";

    /**
     * 走步机速度
     */
    public final static String KEY_SN_SPPED = "sn_speed";

    /**
     *走步机网络状态
     * 0=>offline
     * 1=>local
     * 2=>cloud
     * 3=>updating
     * 4=>uap
     * 5=>unprov
     */
    public final static String KEY_SN_NETWORK_STATE = "sn_network_state";

    /**
     * 走步机状态
     * 0=>待机
     * 1=>安全锁脱离
     * 2=>倒计
     * 3=>运行
     * 4=>暂停
     * 5=>减速
     * 6=>结束
     * 7=>休眠
     * 8=>错误
     */
    public final static String KEY_SN_STATE = "sn_state";

    /**
     * 错误信息
     * 0001=>通讯错误
     * 0002=>失速保护
     * 0003=>无速度信号
     * 0004=>扬升自检失败
     * 0005=>过流保护
     * 0006=>电机未接或开路
     * 0010=>电机瞬间过流或短路
     * 0013=>通讯错误
     */
    public final static String KEY_SN_ERROR_INFO = "sn_error_info";
}
