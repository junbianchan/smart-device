package co.darma.models.returns;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/11/18.
 */
public class Argument {

    private Object object;

    public Argument(Object object) {
        this.object = object;
    }

    public Object getArgumentByName(String name) {

        if (object != null) {
            try {
                Field f = object.getClass().getDeclaredField(name);
                f.setAccessible(true);
                return f.get(object);

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Argument> packageToList(List objectList) {

        List<Argument> list = new LinkedList<Argument>();

        for (Object o : objectList) {
            list.add(new Argument(o));
        }

        return list;
    }

}
