package org.eclipse.smarthome.binding.openocean.api.system;

public class SystemInfo {

    private String possibleBaseIdChanges;

    private String coreVersion;

    private String chipId;

    private String baseId;

    public String getPossibleBaseIdChanges() {
        return possibleBaseIdChanges;
    }

    public void setPossibleBaseIdChanges(String possibleBaseIdChanges) {
        this.possibleBaseIdChanges = possibleBaseIdChanges;
    }

    public String getCoreVersion() {
        return coreVersion;
    }

    public void setCoreVersion(String coreVersion) {
        this.coreVersion = coreVersion;
    }

    public String getChipId() {
        return chipId;
    }

    public void setChipId(String chipId) {
        this.chipId = chipId;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    @Override
    public String toString() {
        return "ClassPojo [possibleBaseIdChanges = " + possibleBaseIdChanges + ", coreVersion = " + coreVersion
                + ", chipId = " + chipId + ", baseId = " + baseId + "]";
    }
}
