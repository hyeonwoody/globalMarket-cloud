package com.toyproject.globalMarket.service.product.store;

import java.util.HashMap;
import java.util.Map;

public class StoreFactory {
    public interface CreateCallback {
        StoreInterface createStore();
    }
    private static final Map<String, CreateCallback> mStores = new HashMap<>();

    public static void registerStore(String type, CreateCallback store){
        mStores.put(type, store);
    }
    static void unregisterStore (String type){
        mStores.remove(type);
    }
    public static StoreInterface createStore (String type){
        return mStores.getOrDefault(type, null).createStore();
    }
}