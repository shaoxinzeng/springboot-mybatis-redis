package com.hkd.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 集合工具类
 * User: xuedaiyao
 */
public class CollectionUtil {

    public static List<String> splitList(String str, String regex) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<>(0);
        }
        String[] strs = str.split(regex);
        List<String> list = new ArrayList<>(strs.length);
        Collections.addAll(list, strs);
        return list;
    }

    /**
     * 将字符串转换为整数数组
     *
     * @param str   字符串
     * @param regex 字符串分隔符
     * @return 整数数组
     */
    public static List<Integer> convertList(String str, String regex) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        String[] strs = str.split(regex);
        List<Integer> list = new ArrayList<>(strs.length);
        for (String s : strs) {
            list.add(Integer.valueOf(s.trim()));
        }
        return list;
    }

    /**
     * 将字符串转换为整数数组
     *
     * @param str   字符串
     * @param regex 字符串分隔符
     * @return 整数数组
     */
    public static Set<Integer> convertSet(String str, String regex) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptySet();
        }
        String[] strs = str.split(regex);
        Set<Integer> set = Sets.newHashSetWithExpectedSize(strs.length);
        for (String s : strs) {
            if(StringUtils.isNotEmpty(s)) {
                set.add(Integer.valueOf(s));
            }
        }
        return set;
    }
    /**
     * 将字符串转换为整数数组
     *
     * @param str   字符串
     * @param regex 字符串分隔符
     * @return 整数数组
     */
    public static Set<Long> convertSet2(String str, String regex) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptySet();
        }
        String[] strs = str.split(regex);
        Set<Long> set = Sets.newHashSetWithExpectedSize(strs.length);
        for (String s : strs) {
            set.add(Long.valueOf(s));
        }
        return set;
    }

    /**
     * 将字符串转换为字符串集合
     *
     * @param str 字符串
     * @param regex 字符串分隔符
     * @return
     */
    public static Set<String> convertSet3(String str, String regex) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptySet();
        }
        String[] strings = str.split(regex);
        Set<String> set = Sets.newHashSetWithExpectedSize(strings.length);
        for (String s : strings) {
            set.add(s);
        }
        return set;
    }

    /**
     * 将字符串转换为整数数组
     *
     * @param str   字符串
     * @param regex 字符串分隔符
     * @return 整数数组
     */
    public static List<Long> convertList2(String str, String regex) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        String[] strs = str.split(regex);
        List<Long> list = new ArrayList<>(strs.length);
        for (String s : strs) {
            list.add(Long.valueOf(s));
        }
        return list;
    }

    /**
     * 根据数组生成集合
     *
     * @param objs 集合数组
     * @param <T>  泛型
     * @return 集合
     */
    @SafeVarargs
    public static <T> Set<T> asSet(T... objs) {
        Set<T> set = Sets.newHashSetWithExpectedSize(objs.length);
        Collections.addAll(set, objs);
        return set;
    }

    @SafeVarargs
    public static <T> List<T> asArrayList(T... objs) {
        List<T> list = new ArrayList<>(objs.length);
        Collections.addAll(list, objs);
        return list;
    }

    /**
     * 创建指定属性成为一个集合，比如说将一个患者信息数组建立出一个患者编号集合
     *
     * @param objs     数组
     * @param clazz    类型
     * @param property 属性名
     * @param <T>      泛型
     * @return 指定属性的集合
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> buildSet(List<?> objs, Class<T> clazz, String property) {
        if (objs.isEmpty()) {
            return Sets.newHashSetWithExpectedSize(0);
        }
        Set<T> results = Sets.newHashSetWithExpectedSize(objs.size());
        try {
            Field field = getField(objs, property);
            for (Object obj : objs) {
                T val = (T) field.get(obj);
                if (val == null) {
                    continue;
                }
                results.add(val);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    /**
     * 创建指定属性为KEY, objs的每个元素为值的Multimap的Map集合。
     *
     * @param objs     数组
     * @param keyClazz 值类型，即{@code property}的类型
     * @param valClazz 值类型
     * @param property 属性名
     * @param <K>      泛型
     * @param <V>      泛型
     * @return 指定属性的Map集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, List<V>> buildMultimap(List<V> objs, Class<K> keyClazz, Class<V> valClazz,
                                                       String property) {
        if (objs.isEmpty()) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<K, List<V>> results = Maps.newHashMapWithExpectedSize(objs.size());
        try {
            Field field = getField(objs, property);
            for (V obj : objs) {
                K key = (K) field.get(obj);
                List<V> value = results.get(key);
                if (value == null) {
                    results.put(key, value = new ArrayList<>());
                }
                value.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    /**
     * 创建指定属性成为一个数组，比如说将一个患者信息数组建立出一个患者编号数组
     *
     * @param objs     数组
     * @param clazz    类型
     * @param property 属性名
     * @param <T>      泛型
     * @return 指定属性的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> buildList(List<?> objs, Class<T> clazz, String property) {
        if (objs.isEmpty()) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<T> results = new ArrayList<>(objs.size());
        try {
            Field field = getField(objs, property);
            for (Object obj : objs) {
                T val = (T) field.get(obj);
                if (val == null) {
                    continue;
                }
                results.add(val);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    /**
     * 创建指定属性为KEY, objs的每个元素为值的Multimap的Map集合。
     *
     * @param objs 数组
     * @param keyClazz 值类型，即{@code property}的类型
     * @param valClazz 值类型
     * @param property 属性名
     * @param <K> 泛型
     * @param <V> 泛型
     * @return 指定属性的Map集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> buildMap(List<V> objs, Class<K> keyClazz, Class<V> valClazz,
                                            String property) {
        if (objs.isEmpty()) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<K, V> results = Maps.newHashMapWithExpectedSize(objs.size());
        try {
            Field field = getField(objs, property);
            for (V obj : objs) {
                K key = (K) field.get(obj);
                results.put(key, obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    /**
     * 创建数组里某个属性的次数
     *
     * @param objs 数组
     * @param keyClazz key的类
     * @param property key的属性名。当key对应的值为null是，不计数
     * @param <K> key的类
     * @return 次数集合
     */
    @SuppressWarnings("unchecked")
    public static <K> Map<K, Integer> buildCountMap(List<?> objs, Class<K> keyClazz, String property) {
        if (objs.isEmpty()) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<K, Integer> results = Maps.newHashMapWithExpectedSize(objs.size());
        try {
            Field field = getField(objs, property);
            for (Object obj : objs) {
                K val = (K) field.get(obj);
                if (val != null) {
                    incr(results, val);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    /**
     * 创建数组里某个属性为key，值属性为次数
     *
     * @param objs 数组
     * @param keyClazz key的类
     * @param property key的属性名。当key对应的值为null是，不计数
     * @param valProperty val的属性名
     * @param <K> key的类
     * @return 次数集合
     */
    @SuppressWarnings("unchecked")
    public static <K> Map<K, Integer> buildCountMap(List<?> objs, Class<K> keyClazz, String property, String valProperty) {
        if (objs.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<K, Integer> results = Maps.newHashMapWithExpectedSize(objs.size());
        try {
            Field field = getField(objs, property);
            Field field2 = getField(objs, valProperty);
            for (Object obj : objs) {
                K val = (K) field.get(obj);
                Object val2 = field2.get(obj);
                if (val != null && val2 != null) {
                    incr(results, val, ((Number) val2).intValue());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    /**
     * @param objs     对象数组
     * @param property 属性
     * @return 对象元素里的指定属性Field, 并设置该field可以被访问
     */
    public static Field getField(List<?> objs, String property) {
        Field field = ReflectionUtils.findField(objs.get(0).getClass(), property);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return field;
    }

    /**
     * @param obj      对象
     * @param property 属性
     * @return 对象元素里的指定属性Field, 并设置该field可以被访问
     */
    public static Field getField(Object obj, String property) {
        Field field = ReflectionUtils.findField(obj.getClass(), property);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return field;
    }

    /**
     * 提升计数
     *
     * @param counts 计数集合
     * @param key key
     * @param <K> 泛型
     * @return 结果
     */
    public static <K> Integer incr(Map<K, Integer> counts, K key) {
        return incr(counts, key, 1);
    }

    public static <K> Integer incr(Map<K, Integer> counts, K key, int count) {
        Integer value = counts.get(key);
        if (value == null) {
            value = count;
        } else {
            value += count;
        }
        counts.put(key, value);
        return value;
    }

    /**
     * 校验集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 校验集合是否不为空
     *
     * @param collection
     * @return
     */
    public static boolean notEmpty(Collection collection){
        return !isEmpty(collection);
    }

    /**
     * 从集合中获取随机一个元素
     *
     * @param collection     集合
     * @return 随机元素
     */
    public static <T> T getRandomElement(Collection<T> collection) {
        if (null == collection || collection.isEmpty()) {
            return null;
        }
        Object element = collection.toArray()[new Random().nextInt(collection.size())];
        return (T)element;
    }

    /**
     * 取两集合交集
     * @param collection
     * @param collection2
     * @param <T>   类型
     * @return  交集集合
     */
    public static <T>  Collection<T>  getIntersection(Collection<T> collection,Collection<T> collection2) {
        if (null == collection || collection.isEmpty()) {
            return null;
        }
        collection.retainAll(collection2);
        return collection;
    }


    /**
     * 两个列表中的对象字段映射，即把列表2的某些字段映射到列表1的某些字段
     * @param targetList 目标列表
     * @param originList 源列表
     * @param <T> 泛型
     * @param <V> 泛型
     */
    public static <T, V> void mappingList( List<T> targetList, List<V> originList, Map.Entry<String, String> keyEntry, Map<String, String> fieldMap) {
        //参数校验部分
        if(isEmpty(targetList) || isEmpty(originList) || keyEntry == null || fieldMap == null) {
            return;
        }
        String firstKey = keyEntry.getKey();
        String secondKey = keyEntry.getValue();
        try{
            Map<Object, V> vMap = Maps.newHashMapWithExpectedSize(originList.size());
            Field secondKeyField;
            try{
                secondKeyField = originList.get(0).getClass().getDeclaredField(secondKey);
            }catch (NoSuchFieldException e) {
                secondKeyField = originList.get(0).getClass().getSuperclass().getDeclaredField(secondKey);
            }
            secondKeyField.setAccessible(true);
            for(V v : originList) {
                vMap.put(secondKeyField.get(v), v);
            }

            List<Field[]> fieldMatches = new ArrayList<>();
            for(String key : fieldMap.keySet()) {
                Field tField = targetList.get(0).getClass().getDeclaredField(key);
                Field vField = originList.get(0).getClass().getDeclaredField(fieldMap.get(key));
                tField.setAccessible(true);
                vField.setAccessible(true);
                fieldMatches.add(new Field[]{tField, vField});
            }
            for(T t : targetList) {
                Field firstKeyFirst;
                try{
                    firstKeyFirst = t.getClass().getDeclaredField(firstKey);
                }catch (NoSuchFieldException e) {
                    firstKeyFirst = t.getClass().getSuperclass().getDeclaredField(firstKey);
                }
                firstKeyFirst.setAccessible(true);
                Object key = firstKeyFirst.get(t);
                for(Field[] fieldMatch : fieldMatches) {
                    fieldMatch[0].set(t, fieldMatch[1].get(vMap.get(key)));
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}