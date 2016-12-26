package com.zkn.fullstacktraining.third;

import java.io.Serializable;
import java.util.Date;

/**
 * Person对象
 * @author zkn
 *
 */

public class PersonDomain implements Serializable{

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 年龄
	 */
	private int age;

	/**
	 * 出生日期
	 */
	private Date birthDay;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "PersonDomain [name=" + name + ", age=" + age +",birthDay="+(birthDay==null?"birthDay位空":birthDay.getTime())+"" +"]";
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
}
