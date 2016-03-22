package org.eclipse.smarthome.binding.openocean.api.profiles;

import java.util.ArrayList;

public class Profile {

    private String title = null;

    private ArrayList<FunctionGroups> functionGroups = null;

    private String eep = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<FunctionGroups> getFunctionGroups() {
        return functionGroups;
    }

    public void setFunctionGroups(ArrayList<FunctionGroups> functionGroups) {
        this.functionGroups = functionGroups;
    }

    public String getEep() {
        return eep;
    }

    public void setEep(String eep) {
        this.eep = eep;
    }

    @Override
    public String toString() {
        return "ClassPojo [title = " + title + ", functionGroups = " + functionGroups + ", eep = " + eep + "]";
    }
}
