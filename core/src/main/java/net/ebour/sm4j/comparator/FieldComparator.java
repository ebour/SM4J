package net.ebour.sm4j.comparator;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Created by ebour on 13/06/15.
 */
public class FieldComparator
{
    public static boolean equals(FieldSource oldField, FieldSource newField)
    {
        return typeEquals(oldField, newField) && nameEquals(oldField, newField);
    }

    public static boolean typeEquals(FieldSource oldField, FieldSource newField)
    {
        return oldField.getType().getQualifiedName().equals(newField.getType().getQualifiedName());
    }

    public static boolean nameEquals(FieldSource oldField, FieldSource newField)
    {
        return oldField.getName().equals(newField.getName());
    }

    public double alikeProbabiliy(JavaClassSource oldClass, FieldSource oldClassField, JavaClassSource newClass, FieldSource newClassField)
    {
        if(nameEquals(oldClassField, newClassField)
            && typeEquals(oldClassField, newClassField))
        {
            return 1.0;
        }

        if(!nameEquals(oldClassField, newClassField)
                && typeEquals(oldClassField, newClassField))
        {
            final String oldClassFieldName = oldClassField.getName();
            final String newClassFieldName = newClassField.getName();
            final String newClassBody = newClass.toString();

            String oldClassBody = oldClass.toString();
            oldClassBody = oldClassBody.replaceAll(oldClassFieldName, newClassFieldName);

            if(oldClassBody.equals(newClassBody))
            {
                return 1.0;
            }
        }
        return 0;
    }
}
