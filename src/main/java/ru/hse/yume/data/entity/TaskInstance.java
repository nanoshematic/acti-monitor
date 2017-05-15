package ru.hse.yume.data.entity;

import java.util.Date;

/**
 * Author: Alexey Batrakov
 * Date: 23/04/17.
 */
public class TaskInstance extends ActivityInstance {
    private Date dueDate;

    private Boolean overDue;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getOverDue() {
        return overDue;
    }

    public void setOverDue(Boolean overDue) {
        this.overDue = overDue;
    }
}
