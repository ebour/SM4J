package net.ebour.sm4j;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by ebour on 13/06/15.
 */
@RunWith(JUnitParamsRunner.class)
public class JavaClassDiffMakerTest
{
    private JavaClassDiffMaker cut;

    @Before
    public void beforeTest()
    {
        this.cut = new JavaClassDiffMaker();
    }

    @Test
    @Parameters({"ClassV1.java, ClassV2.java, 0",
                "ClassV1.java, ClassV3.java, 0",
                "ClassV1.java, ClassV4.java, 1",
                "ClassV1.java, ClassV5.java, 0",
                "ClassV1.java, ClassV6.java, 0"})
    public void shouldListAddedMethods(final String oldVersion, final String newVersion, final int expectedResult) throws FileNotFoundException
    {
        // Arrange
        final File oldFile = new File("target/test-classes/moved-blocks/" + oldVersion);
        final File newFile = new File("target/test-classes/moved-blocks/" + newVersion);

        // Act
        List addedMethods = cut.getAddedMethods(oldFile, newFile);

        // Assert
        assertThat(addedMethods.size(), is(expectedResult));
    }

    @Test
    @Parameters({"ClassV1.java, ClassV2.java, 0",
                "ClassV1.java, ClassV3.java, 1",
                "ClassV1.java, ClassV4.java, 0",
                "ClassV1.java, ClassV5.java, 0",
                "ClassV1.java, ClassV6.java, 0"})
    public void shouldListDeletedMethods(final String oldVersion, final String newVersion, final int expectedResult) throws FileNotFoundException
    {
        // Arrange
        final File oldFile = new File("target/test-classes/moved-blocks/" + oldVersion);
        final File newFile = new File("target/test-classes/moved-blocks/" + newVersion);

        // Act
        List deletedMethods = cut.getDeletedMethods(oldFile, newFile);

        // Assert
        assertThat(deletedMethods.size(), is(expectedResult));
    }

    @Test
    @Parameters({"ClassV1.java, ClassV2.java, 0",
                "ClassV1.java, ClassV3.java, 0",
                "ClassV1.java, ClassV4.java, 1",
                "ClassV1.java, ClassV5.java, 1",
                "ClassV1.java, ClassV6.java, 1",
                "ClassV5.java, ClassV7.java, 1"})
    public void shouldListUpdatedMethods(final String oldVersion, final String newVersion, final int expectedResult) throws FileNotFoundException
    {
        // Arrange
        final File oldFile = new File("target/test-classes/moved-blocks/" + oldVersion);
        final File newFile = new File("target/test-classes/moved-blocks/" + newVersion);

        // Act
        List<MethodSource> updatedMethods = cut.getUpdatedMethods(oldFile, newFile);

        // Assert
        assertThat(updatedMethods.size(), is(expectedResult));
        if(updatedMethods.size()>0)
        {
            assertThat(updatedMethods.get(0).getName(), equalTo("methodA"));
        }
    }

    @Test
    @Parameters({"ClassV5.java, ClassV7.java, true"})
    public void shouldListUpdatedMethods(final String oldVersion, final String newVersion, final boolean expectedResult) throws FileNotFoundException
    {
        // Arrange
        final File oldFile = new File("target/test-classes/moved-blocks/" + oldVersion);
        final File newFile = new File("target/test-classes/moved-blocks/" + newVersion);

        // Act
        boolean areEquals = cut.equals(oldFile, newFile);

        // Assert
        assertThat(areEquals, is(expectedResult));
    }

    @Test
    @Parameters({"ClassV1.java, ClassV2.java, 0",
                "ClassV1.java, ClassV3.java, 1",
                "ClassV1.java, ClassV4.java, 1",
                "ClassV1.java, ClassV5.java, 1",
                "ClassV1.java, ClassV6.java, 1",
                "ClassV5.java, ClassV7.java, 1"})
    public void shouldListDeletedFields(final String oldVersion, final String newVersion, final int expectedResult) throws FileNotFoundException
    {
        // Arrange
        final File oldFile = new File("target/test-classes/moved-blocks/" + oldVersion);
        final File newFile = new File("target/test-classes/moved-blocks/" + newVersion);

        // Act
        List deletedFields = cut.getDeletedFields(oldFile, newFile);

        // Assert
        assertThat(deletedFields.size(), is(expectedResult));
    }

    @Test
    @Parameters({"ClassV1.java, ClassV2.java, 0",
                "ClassV1.java, ClassV3.java, 0",
                "ClassV1.java, ClassV4.java, 0",
                "ClassV1.java, ClassV5.java, 1",
                "ClassV1.java, ClassV6.java, 0",
                "ClassV5.java, ClassV7.java, 1"})
    public void shouldListAddedFields(final String oldVersion, final String newVersion, final int expectedResult) throws FileNotFoundException
    {
        // Arrange
        final File oldFile = new File("target/test-classes/moved-blocks/" + oldVersion);
        final File newFile = new File("target/test-classes/moved-blocks/" + newVersion);

        // Act
        List addedFields = cut.getAddedFields(oldFile, newFile);

        // Assert
        assertThat(addedFields.size(), is(expectedResult));
    }

}
