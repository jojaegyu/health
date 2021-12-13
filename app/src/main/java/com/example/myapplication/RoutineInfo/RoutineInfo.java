package com.example.myapplication.RoutineInfo;

public class RoutineInfo {
    String routineName;
    String exerciseName;
    int weight;
    int set;
    int number;


    public RoutineInfo(String routineName, String exerciseName, int weight, int set, int number) {
        this.routineName = routineName;
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.set = set;
        this.number = number;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void print(){
        System.out.println(routineName);
        System.out.println(exerciseName);
        System.out.println(weight);
        System.out.println(set);
        System.out.println(number);
    }
}
