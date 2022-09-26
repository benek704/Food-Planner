package pl.coderslab.dao;

import pl.coderslab.model.Administrable;

public class Recipe implements Administrable {

    private int id;
    private String name;
    private String ingredients;
    private String descriptions;
    private String created;
    private String updated;
    private int preparation_time;
    private String preparation;
    private int adminId;

    public Recipe(int id, String name, String ingredients, String descriptions, String created, String updated, int preparation_time, String preparation, int adminId) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.descriptions = descriptions;
        this.created = created;
        this.updated = updated;
        this.preparation_time = preparation_time;
        this.preparation = preparation;
        this.adminId = adminId;
    }
    public Recipe( String name, String ingredients, String descriptions, String created, String updated, int preparation_time, String preparation, int adminId) {
        this.name = name;
        this.ingredients = ingredients;
        this.descriptions = descriptions;
        this.created = created;
        this.updated = updated;
        this.preparation_time = preparation_time;
        this.preparation = preparation;
        this.adminId = adminId;
    }

    public Recipe() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public int getPreparation_time() {
        return preparation_time;
    }

    public String getPreparation() {
        return preparation;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setPreparation_time(int preparation_time) {
        this.preparation_time = preparation_time;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", descriptions='" + descriptions + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", preparation_time='" + preparation_time + '\'' +
                ", preparation='" + preparation + '\'' +
                ", admin_id=" + adminId +
                '}';
    }
}
