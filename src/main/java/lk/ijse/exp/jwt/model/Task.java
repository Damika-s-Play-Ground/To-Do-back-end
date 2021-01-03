package lk.ijse.exp.jwt.model;

import java.util.Date;

/**
 * @author : Damika Anupama Nanayakkara <damikaanupama@gmail.com>
 * @since : 03/01/2021
 **/
public class Task {
    private String tid;
    private String description;
    private Date date;

    public Task() {
    }

    public Task(String tid, String description, Date date) {
        this.tid = tid;
        this.description = description;
        this.date = date;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "tid='" + tid + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
