package org.eclipse.smarthome.binding.openocean.api.profiles;

public class Range {
    private String min;

    private String unit;

    private String max;

    private String step;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "ClassPojo [min = " + min + ", unit = " + unit + ", max = " + max + ", step = " + step + "]";
    }
}
