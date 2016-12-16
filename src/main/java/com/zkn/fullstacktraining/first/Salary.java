package com.zkn.fullstacktraining.first;

/**
 * Created by zkn on 2016/12/3.
 */
public class Salary {
    /**
     * 姓名
     */
    private String name;
    /**
     * 基本工资
     */
    private int baseSalary;
    /**
     * 奖金
     */
    private int bonus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getResult(){

        return baseSalary*13+bonus;
    }

    public Salary(String name, int baseSalary, int bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    public Salary() {
    }

    @Override
    public String toString() {

        return "姓名：" + name +" 工资："+getResult();
    }

    public String getFileLine(){

        return name+","+baseSalary+","+bonus;
    }
}
