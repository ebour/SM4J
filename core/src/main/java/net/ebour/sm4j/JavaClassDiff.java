package net.ebour.sm4j;

import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebour on 07/06/15.
 */
public class JavaClassDiff
{
    private final List<MethodSource> added   = new ArrayList<>();
    private final List<MethodSource> removed = new ArrayList<>();
    private final List<MethodSource> updated = new ArrayList<>();

    public void setDeletedMethods(List<MethodSource> deletedMethods)
    {
        this.removed.addAll(deletedMethods);
    }

    public void setAddedMethods(List<MethodSource> addedMethods)
    {
        this.added.addAll(addedMethods);
    }

    public void setUpdatedMethods(List<MethodSource> updatedMethods)
    {
        this.updated.addAll( updatedMethods );
    }

    public boolean methodsEquals()
    {
        return methodsAlike() && updated.isEmpty();
    }

    public boolean methodsAlike()
    {
        return added.isEmpty() && removed.isEmpty();
    }
}
