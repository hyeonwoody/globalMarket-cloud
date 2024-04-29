package com.toyproject.globalMarket.libs;



public abstract class BaseObject extends EventManager {

    String objectName;
    int objectId;

    protected BaseObject(String objectName, int objectId){
        LogOutput(LOG_LEVEL.INFO,ObjectName(),MethodName(), 0, "new ObjectName: {0},objectId: {1}", objectName,objectId);
        this.objectName = objectName == null ?  "NoName" : objectName;
        this.objectId = objectId;
    }
    protected String ObjectName() {return this.objectName;}

    protected String MethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodnName = stackTrace[2].getMethodName();
        return methodnName;
    }

    protected int ObjectId() {return this.objectId;}
}
