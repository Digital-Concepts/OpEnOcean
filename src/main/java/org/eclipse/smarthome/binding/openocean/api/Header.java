package org.eclipse.smarthome.binding.openocean.api;

public class Header {

    private String timestamp = null;

    private String content = null;

    private String gateway = null;

    private String httpStatus = null;

    private Integer code = null;

    private String message = null;

    public Header() {

    }

    public Header(String timestamp, String content) {
        super();
        this.timestamp = timestamp;
        this.content = content;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    @Override
    public String toString() {
        return "ClassPojo [timestamp = " + timestamp + ", content = " + content + ", gateway = " + gateway
                + ", status = " + httpStatus + "]";
    }
}
