package util;

import com.google.common.base.Function;

import java.util.Collection;
import java.util.List;

/**
 * Collection Utilities
 * @author mohammo on 5/15/2014.
 */
public final class Collections {

    private Collections(){
        //privatized for non-instantiability of this utility class
    }

    /**
     * Returns an unmodifiable list if input is not null, and an empty list if null.
     * @param list Input list
     * @param <T> type
     * @return empty list, if list is null and an unmodifiable list otherwise.
     */
    public static <T> List<T> emptyOrUnmodifiableList(List<T> list){
        return list == null ? java.util.Collections.<T>emptyList() : java.util.Collections.unmodifiableList(list);
    }

    /**
     * Convert the given enums to strings with quote using the function.
     * @param collection
     * @param function
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> String enumsToCommaDelimitedStringWithQuotes(Collection<E> collection, Function<E, String> function ){
        if(collection == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (E eEnum : collection) {
            builder.append("'").append(function.apply(eEnum)).append("',");
        }

        if(builder.length()>0){
            builder.deleteCharAt(builder.length()-1);
        }
        return builder.toString();
    }

    /**
     * Convert the given collection into a comma separated.
     * E.g.
     * Input = Collection<String> c = Arrays.asList("a", "b", "c");
     * Output = 'a','b','c'
     *
     * @param collection input collection
     * @return comma separated values wrapped in quotes.
     */
    public static String toCommaSeparatedStringWithQuotes(Collection<String> collection){
        if(collection == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (String t : collection) {
            if(t!=null){
                builder.append("'").append(t).append("',");
            }
        }
        if(builder.length()>0){
            builder.deleteCharAt(builder.length()-1);
        }
        return builder.toString();
    }
}

