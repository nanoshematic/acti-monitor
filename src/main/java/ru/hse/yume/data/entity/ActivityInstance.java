package ru.hse.yume.data.entity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Author: Alexey Batrakov
 * Date: 11/04/17.
 */
public class ActivityInstance {
    private String id;

    private String actId;

    private String procDefId;

    private String actName;

    private Date startTime;

    private Date endTime;

    private String assignee;

    private String duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDuration() {
//        return duration;
        if (duration == null) {
            return "";
        }
        Long millis = Long.parseLong(duration);
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder("");
        if (days != 0) {
            sb.append(days);
            sb.append(" Days, ");
        }
        if (hours != 0 || sb.length() != 0) {
            sb.append(hours);
            sb.append(" Hours, ");
        }
        if (minutes != 0 || sb.length() != 0) {
            sb.append(minutes);
            sb.append(" Minutes, ");
        }
        sb.append(seconds);
        sb.append(" Seconds");

        return sb.toString();
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
