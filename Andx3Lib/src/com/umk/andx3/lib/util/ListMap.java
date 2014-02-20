package com.umk.andx3.lib.util;

/**
 * @author Winnid
 * @title 兼具 List 和 Map 功能的容器类
 * @version:1.0
 * @since：14-1-16
 */
import java.util.*;

public class ListMap<K, V> {

    private List<Item> values = new ArrayList<Item>();

    /**
     * 获取元素个数
     *
     * @return 元素个数
     */
    public int size() {
        return values.size();
    }

    /**
     * 判断容器是否为空
     *
     * @return 如果为空则返回 true。
     */
    public boolean isEmpty() {
        return values.isEmpty();
    }

    /**
     * 获得一个值迭代器
     *
     * @return 值迭代器
     */
    public Iterator<V> iterator() {
        return values().iterator();
    }

    /**
     * 获得一个值数组
     *
     * @return 值数组
     */
    public V[] toArray() {
        Object[] arr = new Object[values.size()];
        for (int i = 0; i < values.size(); i++) {
            arr[i] = values.get(i).value;
        }
        return (V[]) arr;
    }

    /**
     * 检查指定的键是否存在
     *
     * @param key 键
     *
     * @return 如果存在则返回 true。
     */
    public boolean containsKey(K key) {
        if (key == null) return false;
        for (Item item : values) {
            if (item.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查指定的值是否存在
     *
     * @param value 值
     *
     * @return 如果存在则返回 true。
     */
    public boolean containsValue(V value) {
        for (Item item : values) {
            if (item.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过键获得值
     *
     * @param key 键
     *
     * @return 值
     */
    public V get(K key) {
        for (Item item : values) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return null;
    }

    /**
     * 设置值。如果键不存在则添加。
     *
     * @param key   键
     * @param value 值
     *
     * @return 原来的值。如果键不存在则返回 null。
     */
    // 这里要注意，key 必须是唯一的，所以如果 key 已经存在则做替换，否则做添加
    public V put(K key, V value) {
        for (Item item : values) {
            if (item.key.equals(key)) {
                V replaced = item.value;
                item.value = value;
                return replaced;
            }
        }

        Item item = new Item(key, value);
        values.add(item);
        return null;
    }

    /**
     * 删除键。值也会删除。
     *
     * @param key 键
     *
     * @return 如果键存在则返回 true。
     */
    public boolean remove(K key) {
        for (Item item : values) {
            if (item.key.equals(key)) {
                values.remove(item);
                return true;
            }
        }
        return false;
    }

    /**
     * 删除指定位置的键和值
     *
     * @param index 位置
     *
     * @return 该位置的值
     *
     * @throws IndexOutOfBoundsException 如果位置超过范围
     */
    public V remove(int index) {
        return values.remove(index).value;
    }

    /**
     * 清除容器中的所有键和值
     */
    public void clear() {
        values.clear();
    }

    /**
     * 获取指定位置上的值
     *
     * @param index 位置
     *
     * @return 值
     *
     * @throws IndexOutOfBoundsException 如果位置超过范围
     */
    public V get(int index) {
        return values.get(index).value;
    }

    /**
     * 设置指定位置的值
     *
     * @param index 位置
     * @param value 新的值
     *
     * @return 旧的值
     *
     * @throws IndexOutOfBoundsException 如果位置超过范围
     */
    public V set(int index, V value) {
        Item item = values.get(index);
        V old_value = item.value;
        item.value = value;
        return old_value;
    }

    /**
     * 在指定位置添加一个新的键和值
     *
     * @param index 位置
     * @param key   键
     * @param value 值
     *
     * @throws IllegalStateException 如果键已经存在
     */
    public void add(int index, K key, V value) {
        if (containsKey(key)) {
            throw new IllegalStateException("key alreay exists.");
        }
        Item item = new Item(key, value);
        values.add(index, item);
    }

    /**
     * 根据值查找所在的第一个位置
     *
     * @param value 值
     *
     * @return 值所在的第一个位置
     */
    public int indexOfValue(V value) {
        for (int i = 0; i < values.size(); i++) {
            Item item = values.get(i);
            if (item.value.equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据键查找所在位置
     *
     * @param key 键
     *
     * @return 键所在位置
     */
    public int indexOfKey(K key) {
        if (key == null) {
            return -1;
        }
        for (int i = 0; i < values.size(); i++) {
            Item item = values.get(i);
            if (item.key.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取一个包含元素子集和的容器。容器的元素个数为 toIndex - fromIndex
     *
     * @param fromIndex 子集和的开始位置（含）
     * @param toIndex   子集和的结束位置（不含）
     *
     * @return 包含元素子集和的容器
     */
    public ListMap subList(int fromIndex, int toIndex) {
        ListMap<K, V> map = new ListMap<K, V>();
        map.values.addAll(values.subList(fromIndex, toIndex));
        return map;
    }

    /**
     * 获取值 List 对象
     * @return 包含值的 List 对象
     */
    public List<V> values() {
        List<V> list = new ArrayList<V>();
        for (Item item : values) {
            list.add(item.value);
        }
        return list;
    }

    /**
     * 获取包含键的 List 对象
     * @return 包含键的 List 对象
     */
    public List<K> keys() {
        List<K> list = new ArrayList<K>();
        for (Item item : values) {
            list.add(item.key);
        }
        return list;
    }

    /**
     * 对键进行从小到大排序
     */
    public void sortKey() {
        Collections.sort(values, new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                Comparable c1 = (Comparable) i1.key;
                Comparable c2 = (Comparable) i2.key;
                if (c1 == null && c2 == null) return 0;
                if (c1 == null) return -1;
                if (c2 == null) return 1;
                return c1.compareTo(c2);
            }
        });
    }

    /**
     * 对值进行从小到大排序
     */
    public void sortValue() {
        Collections.sort(values, new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                Comparable c1 = (Comparable) i1.value;
                Comparable c2 = (Comparable) i2.value;
                if (c1 == null && c2 == null) return 0;
                if (c1 == null) return -1;
                if (c2 == null) return 1;
                return c1.compareTo(c2);
            }
        });
    }

    /**
     * 对容器元素进行倒排
     */
    public void reverse() {
        Collections.reverse(values);
    }

    /**
     * 内部类
     */
    private class Item {

        public K key;

        public V value;

        public Item(K key, V value) {
            if (key == null) {
                throw new NullPointerException("key cannot be null.");
            }
            this.key = key;
            this.value = value;
        }
    }
}