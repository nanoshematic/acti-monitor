package ru.hse.yume.rest.object;

/**
 * Author: Alexey Batrakov
 * Date: 16/04/17.
 */
public class ProcessDefActivitiResponse {
    private String key;

    private String name;

    private Integer count;

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
}
