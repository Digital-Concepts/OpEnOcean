package org.eclipse.smarthome.binding.openocean.api.telegram;

public class TelegramInfo {
    private String status = null;

    private String data = null;

    private String rorg = null;

    private String dbm = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRorg() {
        return rorg;
    }

    public void setRorg(String rorg) {
        this.rorg = rorg;
    }

    public String getDbm() {
        return dbm;
    }

    public void setDbm(String dbm) {
        this.dbm = dbm;
    }

    @Override
    public String toString() {
        return "ClassPojo [status = " + status + ", data = " + data + ", rorg = " + rorg + ", dbm = " + dbm + "]";
    }
}
