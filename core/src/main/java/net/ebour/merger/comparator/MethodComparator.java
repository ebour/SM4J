package net.ebour.merger.comparator;

import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

import java.util.List;

/**
 * Created by ebour on 07/06/15.
 */
public class MethodComparator
{
    public static boolean nameEquals(MethodSource oldMethod, MethodSource newMethod)
    {
        return oldMethod.getName().equals(newMethod.getName());
    }

    public static boolean signatureEquals(MethodSource oldMethod, MethodSource newMethod)
    {
        return nameEquals(oldMethod, newMethod)
                && parameterNameEquals(oldMethod, newMethod)
                && parameterTypeEquals(oldMethod, newMethod);
    }

    public static boolean parameterTypeEquals(MethodSource oldMethod, MethodSource newMethod)
    {
        int paramIdx = 0;
        final List<ParameterSource> parameters = oldMethod.getParameters();
        for(ParameterSource parameter : parameters)
        {
            final ParameterSource newParameterSource = (ParameterSource) newMethod.getParameters().get(paramIdx);

            if(!parameter.getType().getQualifiedName().equals(newParameterSource.getType().getQualifiedName()))
            {
                return false;
            }

            paramIdx ++;
        }

        return true;
    }

    public static boolean parameterLengthEquals(MethodSource oldMethod, MethodSource newMethod)
    {
        final List<ParameterSource> oldMethodParameters = oldMethod.getParameters();
        final List<ParameterSource> newMethodParameters = newMethod.getParameters();

        return oldMethodParameters.size() == newMethodParameters.size();
    }

    public static boolean parameterNameEquals(MethodSource oldMethod, MethodSource newMethod)
    {
        if(!parameterLengthEquals(oldMethod, newMethod))
        {
            return false;
        }

        int paramIdx = 0;
        final List<ParameterSource> oldMethodParameters = oldMethod.getParameters();
        for(ParameterSource oldMethodParameter : oldMethodParameters)
        {
            ParameterSource newParameterSource = (ParameterSource) newMethod.getParameters().get(paramIdx);

            if(!oldMethodParameter.getName().equals(newParameterSource.getName()))
            {
                return false;
            }

            paramIdx ++;
        }

        return true;
    }

    public static boolean bodyEquals(MethodSource oldMethod, MethodSource newMethod)
    {
        return oldMethod.getBody().equals(newMethod.getBody());
    }

    public static boolean equals(MethodSource oldMethod, MethodSource newMethod)
    {
        return signatureEquals(oldMethod, newMethod) && bodyEquals(oldMethod, newMethod);
    }

    public static double alikeProbabiliy(MethodSource oldMethod, MethodSource newMethod)
    {
        if(nameEquals(oldMethod, newMethod))
        {
            return 0.80;
        }
        else
        {
            if(parameterLengthEquals(oldMethod, newMethod)
                    && parameterTypeEquals(oldMethod, newMethod)
                    && !parameterNameEquals(oldMethod, newMethod))
            {
                int parameterIdx = 0;
                String oldMethodBody = oldMethod.getBody();
                for(ParameterSource parameter : (List<ParameterSource>) oldMethod.getParameters())
                {
                    final String oldParameterName = parameter.getName();
                    final String newParameterName = ((ParameterSource) newMethod.getParameters().get(parameterIdx)).getName();
                    if(newParameterName.equals(oldParameterName))
                    {
                        oldMethodBody = oldMethodBody.replaceAll(oldParameterName, newParameterName);
                    }
                    parameterIdx++;
                }

                if(oldMethodBody.equals(newMethod.getBody()))
                {
                    return 1.0;
                }
            }

            return 0.20;
        }
    }
}
