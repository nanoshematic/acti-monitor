package ru.hse.yume.data.entity;

/**
 * Author: Alexey Batrakov
 * Date: 10/05/17.
 */
public class TaskDuration {
    private String assignee;

    private Long duration;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
