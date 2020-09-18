package cn.org.zmabel.admin.advice;

public class AdviceException extends RuntimeException{
    private String state;
    private String msg;

    public AdviceException(String state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
