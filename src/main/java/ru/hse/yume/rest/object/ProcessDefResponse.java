package ru.hse.yume.rest.object;

/**
 * Author: Alexey Batrakov
 * Date: 16/04/17.
 */
public class ProcessDefResponse {
    private String key;

    private String name;

    private Integer count;

    private Integer atRiskCount;

    private Integer overdueCount;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAtRiskCount() {
        return atRiskCount;
    }

    public void setAtRiskCount(Integer atRiskCount) {
        this.atRiskCount = atRiskCount;
    }

    public Integer getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(Integer overdueCount) {
        this.overdueCount = overdueCount;
    }
}
