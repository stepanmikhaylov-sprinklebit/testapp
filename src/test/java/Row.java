import java.util.Date;

public class Row {
    private String name;
    private String nodes;
    private String priority;
    private String due;
    private String createdAt;
    private String updatedAt;

    public Row() {

    }

    public Row(String name, String nodes, String priority, String due, String createdAt, String updatedAt) {
        this.name = name;
        this.nodes = nodes;
        this.priority = priority;
        this.due = due;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
