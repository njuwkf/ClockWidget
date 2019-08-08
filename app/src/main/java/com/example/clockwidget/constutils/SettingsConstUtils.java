package com.example.clockwidget.constutils;

/**
 * @author 吴科烽
 * @date 2019-08-07
 **/
public final class SettingsConstUtils {

    private SettingsConstUtils(){
    }

    public static final String str_twelvehour = "12小时制";
    public static final String str_twentyfourhour = "24小时制";

    public static final String str_digital_clock = "数字时钟";
    public static final String str_view_clock = "图形时钟";

    public static final String str_am = "上午";
    public static final String str_pm = "下午";

    public enum FontColor{
        /**
         * 黑色
         */
        BLACK("黑"),
        /**
         * 白色
         */
        WHITE("白"),
        /**
         * 红色
         */
        RED("红"),
        /**
         * 黄色
         */
        YELLOW("黄"),
        /**
         * 绿色
         */
        GREEN("绿"),
        /**
         * 蓝色
         */
        BLUE("蓝");
        private String action;
        FontColor(String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }
    }
}
