package kaaes.spotify.webapi.android;

import android.os.Parcelable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/*
 * Inspired by the code in the Random Beans project which looks
 * cool but would be a bit of an overkill for these tests.
 * Random Beans repository URL: https://github.com/benas/random-beans
 */

public class ModelPopulator {

    public static final int DEFAULT_STRING_LENGTH = 10;
    public static final int DEFAULT_COLLECTION_SIZE = 5;

    public static final Random RANDOM = new Random();
    public static final Class<String> DEFAULT_GENERIC_CLASS = String.class;
    private final List<String> mExcludeFields;

    public static class PopulationException extends RuntimeException {
        public PopulationException(String details, Throwable throwable) {
            super(details, throwable);
        }
    }

    public ModelPopulator(String... excludeFields) {
        mExcludeFields = new ArrayList<>();
        for (String field : excludeFields) {
            mExcludeFields.add(field.toLowerCase());
        }
    }

    public <T> T populateWithRandomValues(final Class<T> type) {
        try {
            T instance = type.newInstance();

            ArrayList<Field> fields = new ArrayList<>(Arrays.asList(type.getDeclaredFields()));
            fields.addAll(getInheritedFields(type));

            for (Field field : fields) {

                if (shouldSkipField(field)) {
                    continue;
                }

                if (isCollectionType(field.getType())) {
                    field.set(instance, populateCollectionField(field));
                } else {
                    field.set(instance, getRandomValueOfType(field.getType()));
                }
            }

            return instance;
        } catch (Exception e) {
            throw new PopulationException("Populating object failed", e);
        }
    }

    private boolean shouldSkipField(Field field) {
        return mExcludeFields.contains(field.getName().toLowerCase());
    }

    private List<Field> getInheritedFields(Class type) {
        List<Field> inheritedFields = new ArrayList<>();
        while (type.getSuperclass() != null) {
            Class superclass = type.getSuperclass();
            inheritedFields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            type = superclass;
        }
        return inheritedFields;
    }

    private boolean isCollectionType(Class type) {
        return Map.class.isAssignableFrom(type) || List.class.isAssignableFrom(type);
    }

    private Object populateCollectionField(Field field) {

        Class type = field.getType();

        /* List */
        if (List.class.isAssignableFrom(type)) {
            Type genericType = field.getGenericType();
            ParameterizedType pt = (ParameterizedType) genericType;
            Type actualType = pt.getActualTypeArguments()[0];
            Class elementClass;

            if (actualType instanceof Class) {
                elementClass = (Class) actualType;
            } else {
                // Lists with generics will be populated by default type
                elementClass = DEFAULT_GENERIC_CLASS;
            }

            List list = new ArrayList();
            for (int i = 0; i < DEFAULT_COLLECTION_SIZE; i++) {
                list.add(getRandomValueOfType(elementClass));
            }
            return list;
        }

        /* Map */
        if (Map.class.isAssignableFrom(type)) {
            Type genericType = field.getGenericType();
            ParameterizedType pt = (ParameterizedType) genericType;
            Type[] arguments = pt.getActualTypeArguments();

            Type key = arguments[0];
            Type value = arguments[1];

            Class keyClass;
            if (key instanceof Class) {
                keyClass = (Class) key;
            } else {
                // Maps with generics will be populated by default type
                keyClass = DEFAULT_GENERIC_CLASS;
            }

            Class valueClass;
            if (value instanceof Class) {
                valueClass = (Class) value;
            } else {
                // Maps with generics will be populated by default type
                valueClass = DEFAULT_GENERIC_CLASS;
            }

            Map map = new HashMap();
            for (int i = 0; i < DEFAULT_COLLECTION_SIZE; i++) {
                map.put(getRandomValueOfType(keyClass), getRandomValueOfType(valueClass));
            }
            return map;
        }

        throw new UnsupportedOperationException("Unsupported collection field type! " + type);
    }

    private Object getRandomValueOfType(Class type) {

        /* Another model */
        if (Parcelable.class.isAssignableFrom(type)) {
            return populateWithRandomValues(type);
        }

        /* String */
        if (type.equals(String.class)) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < DEFAULT_STRING_LENGTH; i++) {
                builder.append((char) (RANDOM.nextInt(26) + 'a'));
            }
            return builder.toString();
        }

        /* Integer */
        if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return RANDOM.nextInt();
        }

        /* Long */
        if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return RANDOM.nextLong();
        }

        /* Float */
        if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            return RANDOM.nextFloat();
        }

        /* Boolean */
        if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
            return RANDOM.nextBoolean();
        }

        throw new UnsupportedOperationException("Unsupported field type! " + type);
    }
}
