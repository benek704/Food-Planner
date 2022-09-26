package pl.coderslab.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Plan implements Administrable {
    private String name;
    private String description;
    private Timestamp creationDate;
    private int id;
    private int adminId;

    public Plan(int id, String name, String description, Timestamp creationDate, int adminId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.adminId = adminId;
    }

    public Plan(String name, String description, Timestamp creationDate, int adminId) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.adminId = adminId;
    }

    public Plan(){
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public int getId() {
        return id;
    }

    public int getAdminId() {
        return adminId;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", id=" + id +
                ", adminId=" + adminId +
                '}';
    }
}
