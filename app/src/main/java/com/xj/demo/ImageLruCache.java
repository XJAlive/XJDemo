package com.xj.demo;

import android.util.LruCache;

public class ImageLruCache<K,V> extends LruCache<K,V> {

    public ImageLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected V create(K key) {
        return super.create(key);
    }
}
