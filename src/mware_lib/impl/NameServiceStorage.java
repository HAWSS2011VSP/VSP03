package mware_lib.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class NameServiceStorage {
  final Map<String, Object> objects;
  final ReadWriteLock locker;

  public NameServiceStorage() {
    objects = new HashMap<String, Object>();
    locker = new ReentrantReadWriteLock();
  }

  public void put(final String id, final Object obj) {
    try {
      locker.writeLock().lock();
      objects.put(id, obj);
    } finally {
      locker.writeLock().unlock();
    }
  }

  public Object get(String id) {
    try {
      locker.readLock().lock();
      return objects.get(id);
    } finally {
      locker.readLock().unlock();
    }
  }
}