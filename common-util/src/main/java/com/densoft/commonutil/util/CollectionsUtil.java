package com.densoft.commonutil.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionsUtil {
    public CollectionsUtil() {
    }

    private static class CollectionUtilHolder {
        static final CollectionsUtil INSTANCE = new CollectionsUtil();
    }

    public static CollectionsUtil getInstance() {
        return CollectionUtilHolder.INSTANCE;
    }

    public  <T> List<T> getListFromIterable(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
