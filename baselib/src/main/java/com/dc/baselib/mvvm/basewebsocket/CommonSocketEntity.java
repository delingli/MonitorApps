package com.dc.baselib.mvvm.basewebsocket;

/**
 * WebSocket 接口统一的数据格式
 * Created by ZhangKe on 2018/6/28.
 */
public class CommonSocketEntity<T> {
    private String msg;
    private T data;
    private int code;//10开头成功
    private CommandBean command;
    private String subscribe_id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CommandBean getCommand() {
        return command;
    }

    public void setCommand(CommandBean command) {
        this.command = command;
    }

    public String getSubscribe_id() {
        return subscribe_id;
    }

    public void setSubscribe_id(String subscribe_id) {
        this.subscribe_id = subscribe_id;
    }

    @Override
    public String toString() {
        return "CommonEntity{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", code=" + code +
                ", command=" + command +
                ", subscribe_id='" + subscribe_id + '\'' +
                '}';
    }

    public static class CommandBean {

        private String path;
        private String unique;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUnique() {
            return unique;
        }

        public void setUnique(String unique) {
            this.unique = unique;
        }

        @Override
        public String toString() {
            return "CommandBean{" +
                    "path='" + path + '\'' +
                    ", unique='" + unique + '\'' +
                    '}';
        }
    }
}
