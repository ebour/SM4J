package net.ebour.merger;

import net.ebour.merger.comparator.FieldComparator;
import net.ebour.merger.comparator.MethodComparator;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.impl.JavaClassImpl;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebour on 07/06/15.
 */
public class JavaClassDiffMaker
{
    public JavaClassDiff diff(File oldFile, File newFile) throws FileNotFoundException
    {
        final JavaType<? extends Object> oldClass = Roaster.parse(oldFile);
        final JavaType<? extends Object> newClass = Roaster.parse(newFile);

        return diff(((JavaClassImpl) oldClass), ((JavaClassImpl) newClass));
    }

    public JavaClassDiff diff(JavaClassSource oldClass, JavaClassSource newClass)
    {
        final JavaClassDiff javaClassDiff = new JavaClassDiff();

        javaClassDiff.setAddedMethods   (getAddedMethods    (oldClass, newClass)    );
        javaClassDiff.setUpdatedMethods (getUpdatedMethods  (oldClass, newClass)    );
        javaClassDiff.setDeletedMethods (getDeletedMethods  (oldClass, newClass)    );

        return javaClassDiff;
    }

// - Methods comparison

    public List<MethodSource> getDeletedMethods(File oldFile, File newFile) throws FileNotFoundException
    {
        final JavaType<? extends Object> oldClass = Roaster.parse(oldFile);
        final JavaType<? extends Object> newClass = Roaster.parse(newFile);

        return getDeletedMethods(((JavaClassImpl) oldClass), ((JavaClassImpl) newClass));
    }

    public List<MethodSource> getDeletedMethods(JavaClassSource oldClass, JavaClassSource newClass)
    {
        final List<MethodSource> deletedMethods = new ArrayList<>();
        final MethodComparator methodComparator = new MethodComparator();

        final List<MethodSource<JavaClassSource>> oldClassMethods = ((JavaClassImpl) oldClass).getMethods();
        final List<MethodSource<JavaClassSource>> newClassMethods = ((JavaClassImpl) newClass).getMethods();
        for(MethodSource oldClassMethod : oldClassMethods)
        {
            boolean found = false;
            for(MethodSource newClassMethod : newClassMethods)
            {
                if(methodComparator.equals(newClassMethod, oldClassMethod)
                        || methodComparator.alikeProbabiliy(newClassMethod, oldClassMethod) >= 0.80)
                {
                    found = true;
                    break;
                }
            }

            if(!found)
            {
                deletedMethods.add( oldClassMethod );
            }
        }

        return deletedMethods;
    }

    public List<MethodSource> getAddedMethods(File oldFile, File newFile) throws FileNotFoundException
    {
        final JavaType<? extends Object> oldClass = Roaster.parse(oldFile);
        final JavaType<? extends Object> newClass = Roaster.parse(newFile);

        return getAddedMethods(((JavaClassImpl) oldClass), ((JavaClassImpl) newClass));
    }

    public List<MethodSource> getAddedMethods(JavaClassSource oldClass, JavaClassSource newClass)
    {
        final List<MethodSource> addedMethods = new ArrayList<>();
        final MethodComparator methodComparator = new MethodComparator();

        final List<MethodSource<JavaClassSource>> oldClassMethods = ((JavaClassImpl) oldClass).getMethods();
        final List<MethodSource<JavaClassSource>> newClassMethods = ((JavaClassImpl) newClass).getMethods();
        for(MethodSource newClassMethod : newClassMethods)
        {
            boolean found = false;
            for(MethodSource oldClassMethod : oldClassMethods)
            {
                if(methodComparator.equals(newClassMethod, oldClassMethod)
                        || methodComparator.alikeProbabiliy(newClassMethod, oldClassMethod) >= 0.80)
                {
                    found = true;
                    break;
                }
            }

            if(!found)
            {
                addedMethods.add( newClassMethod );
            }
        }

        return addedMethods;
    }

    public List<MethodSource> getUpdatedMethods(File oldFile, File newFile) throws FileNotFoundException
    {
        final JavaType<? extends Object> oldClass = Roaster.parse(oldFile);
        final JavaType<? extends Object> newClass = Roaster.parse(newFile);

        return getUpdatedMethods(((JavaClassImpl) oldClass), ((JavaClassImpl) newClass));
    }

    public List<MethodSource> getUpdatedMethods(JavaClassSource oldClass, JavaClassSource newClass)
    {
        final List<MethodSource> updatedMethods = new ArrayList<>();
        final MethodComparator methodComparator = new MethodComparator();

        final List<MethodSource<JavaClassSource>> oldClassMethods = ((JavaClassImpl) oldClass).getMethods();
        final List<MethodSource<JavaClassSource>> newClassMethods = ((JavaClassImpl) newClass).getMethods();
        for(MethodSource newClassMethod : newClassMethods)
        {
            boolean found = false;
            for(MethodSource oldClassMethod : oldClassMethods)
            {
                if(!methodComparator.equals(newClassMethod, oldClassMethod)
                        && methodComparator.alikeProbabiliy(newClassMethod, oldClassMethod) >= 0.80)
                {
                    found = true;
                    break;
                }
            }

            if(found)
            {
                updatedMethods.add(newClassMethod);
            }
        }

        return updatedMethods;
    }

// Field comparison

    public List<FieldSource> getAddedFields(File oldFile, File newFile) throws FileNotFoundException
    {
        final JavaType<? extends Object> oldClass = Roaster.parse(oldFile);
        final JavaType<? extends Object> newClass = Roaster.parse(newFile);

        return getAddedFields(((JavaClassImpl) oldClass), ((JavaClassImpl) newClass));
    }

    public List<FieldSource> getAddedFields(JavaClassSource oldClass, JavaClassSource newClass)
    {
        final List<FieldSource> addedFields = new ArrayList<>();
        final FieldComparator fieldComparator = new FieldComparator();

        final List<FieldSource<JavaClassSource>> oldClassFields = ((JavaClassImpl) oldClass).getFields();
        final List<FieldSource<JavaClassSource>> newClassFields = ((JavaClassImpl) newClass).getFields();
        for(FieldSource newClassField : newClassFields)
        {
            boolean found = false;
            for(FieldSource oldClassField : oldClassFields)
            {
                if(fieldComparator.equals(newClassField, oldClassField)
                        || fieldComparator.alikeProbabiliy(newClass, newClassField, oldClass, oldClassField) >= 0.80)
                {
                    found = true;
                    break;
                }
            }

            if(!found)
            {
                addedFields.add(newClassField);
            }
        }

        return addedFields;
    }

    public List<FieldSource> getDeletedFields(File oldFile, File newFile) throws FileNotFoundException
    {
        final JavaType<? extends Object> oldClass = Roaster.parse(oldFile);
        final JavaType<? extends Object> newClass = Roaster.parse(newFile);

        return getDeletedFields(((JavaClassImpl) oldClass), ((JavaClassImpl) newClass));
    }

    public List<FieldSource> getDeletedFields(JavaClassSource oldClass, JavaClassSource newClass)
    {
        final List<FieldSource> deletedFields = new ArrayList<>();
        final FieldComparator fieldComparator = new FieldComparator();

        final List<FieldSource<JavaClassSource>> oldClassFields = ((JavaClassImpl) oldClass).getFields();
        final List<FieldSource<JavaClassSource>> newClassFields = ((JavaClassImpl) newClass).getFields();
        for(FieldSource oldClassField : oldClassFields)
        {
            boolean found = false;
            for(FieldSource newClassField : newClassFields)
            {
                if(fieldComparator.equals(newClassField, oldClassField)
                        || fieldComparator.alikeProbabiliy(oldClass, newClassField, newClass, oldClassField) >= 0.80)
                {
                    found = true;
                    break;
                }
            }

            if(!found)
            {
                deletedFields.add( oldClassField );
            }
        }

        return deletedFields;
    }

    public boolean equals(File oldClass, File newClass) throws FileNotFoundException
    {
        final JavaClassDiff diff = diff(oldClass, newClass);
        return diff.methodsEquals() || diff.methodsAlike();
    }
}
