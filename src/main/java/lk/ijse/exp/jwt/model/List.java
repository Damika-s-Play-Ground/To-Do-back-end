package lk.ijse.exp.jwt.model;

/**
 * @author : Damika Anupama Nanayakkara <damikaanupama@gmail.com>
 * @since : 03/01/2021
 **/
public class List {
    private String listId;
    private String taskId;

    public List() {
    }

    public List(String listId, String taskId) {
        this.listId = listId;
        this.taskId = taskId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "List{" +
                "listId='" + listId + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
