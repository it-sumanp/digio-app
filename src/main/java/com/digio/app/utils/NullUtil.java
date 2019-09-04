package com.digio.app.utils;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by catabre on 25/5/18.
 */
public class NullUtil {
    /**
     * This method returns true if the collection is null or is empty.
     * @param collection
     * @return true | false
     */
    public static boolean isEmpty( Collection<?> collection ){
        if( collection == null || collection.isEmpty() ){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty( Collection<?> collection ){
        return !isEmpty(collection);
    }

    /**
     * This method returns true of the map is null or is empty.
     * @param map
     * @return true | false
     */
    public static boolean isEmpty( Map<?, ?> map ){
        if( map == null || map.isEmpty() ){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty( Map<?, ?> map ){
        return !isEmpty(map);
    }

    /**
     * This method returns true if the objet is null.
     * @param object
     * @return true | false
     */
    public static boolean isEmpty( Object object ){
        if( object == null ){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty( Object object ){
        return !isEmpty(object);
    }

    /**
     * This method returns true if the input array is null or its length is zero.
     * @param array
     * @return true | false
     */
    public static boolean isEmpty( Object[] array ){
        if( array == null || array.length == 0 ){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty( Object[] array ){
        return !isEmpty(array);
    }

    /**
     * This method returns true if the input string is null or its length is zero.
     * @param string
     * @return true | false
     */
    public static boolean isEmpty( String string ){
        if( string == null || string.trim().length() == 0 ){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty( String string ){
        return !isEmpty(string);
    }

    /**
     * This method returns Stream from a collection.
     * @param collection
     * @return Object
     */
    public static <T> Stream<T> collectionAsStream(Collection<T> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }

}
