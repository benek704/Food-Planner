package pl.coderslab.model;

import pl.coderslab.dao.Recipe;

public class MealData {
    private Recipe recipe;
    private String name;
    private int order;
    private int id;

    public MealData(Recipe recipe, int id, String name, int order) {
        this.recipe = recipe;
        this.name = name;
        this.order = order;
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getId() { return this.id; }

    @Override
    public String toString() {
        return "MealData{" +
                "recipe=" + recipe +
                ", name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
