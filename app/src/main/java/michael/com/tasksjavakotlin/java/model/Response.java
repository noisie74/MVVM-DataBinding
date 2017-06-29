package michael.com.tasksjavakotlin.java.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mikhail on 6/17/17.
 */

public class Response {

    private List<Task> tasks;
    @SerializedName("_id")
    private String newTaskId;
    @SerializedName("text")
    private String newTaskTitle;
    @SerializedName("completed")
    private Boolean newTaskStatus;

    public List<Task> getTasksResponse() {
        return tasks;
    }

    public String getNewTaskId() {
        return newTaskId;
    }

    public void setNewTaskId(String newTaskId) {
        this.newTaskId = newTaskId;
    }

    public String getNewTaskTitle() {
        return newTaskTitle;
    }

    public void setNewTaskTitle(String newTaskTitle) {
        this.newTaskTitle = newTaskTitle;
    }

    public Boolean getNewTaskStatus() {
        return newTaskStatus;
    }

    public void setNewTaskStatus(Boolean newTaskStatus) {
        this.newTaskStatus = newTaskStatus;
    }
}
