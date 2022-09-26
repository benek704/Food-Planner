package pl.coderslab.model;

import pl.coderslab.dao.Recipe;

import java.util.*;

public class PlanDetails {
    SortedMap<DayName, List<MealData>> mealPlan;
    Plan plan;

    public PlanDetails() {
        mealPlan = new TreeMap<>(Comparator.comparingInt(DayName::getDisplayOrder));
    }

    public void addRecipeForTheDay(DayName dayName, MealData mealData){
        mealPlan.putIfAbsent(dayName, new ArrayList<>());
        mealPlan.get(dayName).add(mealData);
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public SortedMap<DayName, List<MealData>> getMealPlan() {
        return mealPlan;
    }

    public Plan getPlan() {
        return plan;
    }
}
