package com.puyue.www.qiaoge.model;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class AppointModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"dateStr":"今天","hours":[{"hourStr":"15点","minutes":[{"minute":"05分","dateTime":"2022-01-07 15:05:00"},{"minute":"59分","dateTime":"2022-01-07 15:59:00"},{"minute":"60分","dateTime":"2022-01-07 15:60:00"}]}]},{"dateStr":"01月09日","hours":[{"hourStr":"00点","minutes":[{"minute":"00分","dateTime":"2022-01-09 00:00:00"},{"minute":"01分","dateTime":"2022-01-09 00:01:00"},{"minute":"30分","dateTime":"2022-01-09 00:30:00"},{"minute":"31分","dateTime":"2022-01-09 00:31:00"},{"minute":"32分","dateTime":"2022-01-09 00:32:00"},{"minute":"33分","dateTime":"2022-01-09 00:33:00"},{"minute":"34分","dateTime":"2022-01-09 00:34:00"},{"minute":"35分","dateTime":"2022-01-09 00:35:00"},{"minute":"36分","dateTime":"2022-01-09 00:36:00"},{"minute":"37分","dateTime":"2022-01-09 00:37:00"},{"minute":"38分","dateTime":"2022-01-09 00:38:00"},{"minute":"39分","dateTime":"2022-01-09 00:39:00"},{"minute":"40分","dateTime":"2022-01-09 00:40:00"},{"minute":"41分","dateTime":"2022-01-09 00:41:00"},{"minute":"42分","dateTime":"2022-01-09 00:42:00"},{"minute":"43分","dateTime":"2022-01-09 00:43:00"},{"minute":"44分","dateTime":"2022-01-09 00:44:00"},{"minute":"45分","dateTime":"2022-01-09 00:45:00"},{"minute":"46分","dateTime":"2022-01-09 00:46:00"},{"minute":"47分","dateTime":"2022-01-09 00:47:00"},{"minute":"48分","dateTime":"2022-01-09 00:48:00"},{"minute":"49分","dateTime":"2022-01-09 00:49:00"},{"minute":"50分","dateTime":"2022-01-09 00:50:00"},{"minute":"51分","dateTime":"2022-01-09 00:51:00"},{"minute":"52分","dateTime":"2022-01-09 00:52:00"},{"minute":"53分","dateTime":"2022-01-09 00:53:00"},{"minute":"54分","dateTime":"2022-01-09 00:54:00"},{"minute":"55分","dateTime":"2022-01-09 00:55:00"},{"minute":"56分","dateTime":"2022-01-09 00:56:00"},{"minute":"57分","dateTime":"2022-01-09 00:57:00"},{"minute":"58分","dateTime":"2022-01-09 00:58:00"},{"minute":"59分","dateTime":"2022-01-09 00:59:00"},{"minute":"60分","dateTime":"2022-01-09 00:60:00"}]},{"hourStr":"21点","minutes":[{"minute":"00分","dateTime":"2022-01-09 21:00:00"},{"minute":"01分","dateTime":"2022-01-09 21:01:00"},{"minute":"02分","dateTime":"2022-01-09 21:02:00"},{"minute":"57分","dateTime":"2022-01-09 24:57:00"},{"minute":"58分","dateTime":"2022-01-09 24:58:00"},{"minute":"59分","dateTime":"2022-01-09 24:59:00"},{"minute":"60分","dateTime":"2022-01-09 24:60:00"}]}]}]
     * success : true
     * error : false
     */

    private int code;
    private String message;
    private boolean success;
    private boolean error;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements IPickerViewData {
        /**
         * dateStr : 今天
         * hours : [{"hourStr":"15点","minutes":[{"minute":"05分","dateTime":"2022-01-07 15:05:00"},{"minute":"59分","dateTime":"2022-01-07 15:59:00"},{"minute":"60分","dateTime":"2022-01-07 15:60:00"}]}]
         */

        private String dateStr;
        private List<HoursBean> hours;

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public List<HoursBean> getHours() {
            return hours;
        }

        public void setHours(List<HoursBean> hours) {
            this.hours = hours;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "dateStr='" + dateStr + '\'' +
                    ", hours=" + hours +
                    '}';
        }

        @Override
        public String getPickerViewText() {
            return dateStr;
        }

        public static class HoursBean implements IPickerViewData{
            /**
             * hourStr : 15点
             * minutes : [{"minute":"05分","dateTime":"2022-01-07 15:05:00"},{"minute":"59分","dateTime":"2022-01-07 15:59:00"},{"minute":"60分","dateTime":"2022-01-07 15:60:00"}]
             */

            private String hourStr;
            private List<MinutesBean> minutes;

            public String getHourStr() {
                return hourStr;
            }

            public void setHourStr(String hourStr) {
                this.hourStr = hourStr;
            }

            public List<MinutesBean> getMinutes() {
                return minutes;
            }

            public void setMinutes(List<MinutesBean> minutes) {
                this.minutes = minutes;
            }

            @Override
            public String getPickerViewText() {
                return hourStr;
            }

            public static class MinutesBean implements IPickerViewData{
                /**
                 * minute : 05分
                 * dateTime : 2022-01-07 15:05:00
                 */

                private String minute;
                private String dateTime;

                public String getMinute() {
                    return minute;
                }

                public void setMinute(String minute) {
                    this.minute = minute;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                @Override
                public String getPickerViewText() {
                    return minute;
                }
            }

            @Override
            public String toString() {
                return "HoursBean{" +
                        "hourStr='" + hourStr + '\'' +
                        ", minutes=" + minutes +
                        '}';
            }
        }
    }
}
