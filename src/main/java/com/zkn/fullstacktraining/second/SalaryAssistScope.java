package com.zkn.fullstacktraining.second;

/**
 * Created by wb-zhangkenan on 2016/12/15.
 */
public class SalaryAssistScope implements Comparable<SalaryAssistScope>{
    /**
     * 截取之后的姓名
     */
    private String name;
    /**
     * 总薪资
     */
    private Long salaryTotal;
    /**
     * 总数
     */
    private Integer count;

    @Override
    public int compareTo(SalaryAssistScope o) {

        return  this.salaryTotal > o.salaryTotal ? -1 : 1;
    }

    public SalaryAssistScope(String name, Long salaryTotal, Integer count) {
        this.name = name;
        this.salaryTotal = salaryTotal;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSalaryTotal() {
        return salaryTotal;
    }

    public void setSalaryTotal(Long salaryTotal) {
        this.salaryTotal = salaryTotal;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public SalaryAssistScope() {
    }

    @Override
    public String toString() {

        return  name + " , " + salaryTotal/10000 + " 万 " + count +" 人";
    }
}
