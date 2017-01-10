package com.zkn.fullstacktraining.fifth.variable;

/**
 * Created by zkn on 2017/1/10.
 */
public enum VariableEnum {

    AtomicLong(AtomicLongVariable.class,"Atomic耗时："),
    LongAdder(LongAdderVariable.class,"LongAdder耗时："),
    Normal(NormalVariable.class,"normal耗时："),
    Synchronize(SynchronizeVariable.class,"SynchronizeVariable耗时："),
    Volatile(VolatileVariable.class,"VolatileVariable耗时：");

    private Class counterClass;
    private String desc;

    VariableEnum(Class counterClass, String desc) {
        this.counterClass = counterClass;
        this.desc = desc;
    }

    public static String getDesc(Class clazz){

        for(VariableEnum variableEnum : VariableEnum.values()){
            if(variableEnum.counterClass.equals(clazz))
                    return variableEnum.desc;
        }
        return "";
    }
}
