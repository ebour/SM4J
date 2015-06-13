package net.ebour.sm4j.comparator;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * Created by ebour on 13/06/15.
 */
public class ClassComparator
{
    public static boolean equals(JavaClassSource oldClass, JavaClassSource newClass)
    {
        return nameEquals(oldClass, newClass)
                && fieldEquals(oldClass, newClass)
                && methodEquals(oldClass, newClass);
    }

    public static boolean methodEquals(JavaClassSource oldClass, JavaClassSource newClass)
    {
        for(MethodSource oldMethod : oldClass.getMethods())
        {
            boolean found = false;
            for(MethodSource newMethod : newClass.getMethods())
            {
                if(MethodComparator.equals(oldMethod, newMethod))
                {
                    found = true;
                    break;
                }

            }

            if(!found)
            {
                return false;
            }
        }

        return true;
    }

    public static boolean fieldEquals(JavaClassSource oldClass, JavaClassSource newClass)
    {
        for(FieldSource oldField : oldClass.getFields())
        {
            boolean found = false;
            for(FieldSource newField : newClass.getFields())
            {
                if(FieldComparator.equals(oldField, newField))
                {
                    found = true;
                    break;
                }

            }

            if(!found)
            {
                return false;
            }
        }

        return true;
    }

    public static boolean nameEquals(JavaClassSource oldClass, JavaClassSource newClass)
    {
        return oldClass.getName().equals(newClass.getName());
    }


}
