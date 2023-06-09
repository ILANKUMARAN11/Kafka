import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import service.SchemaGenerator;

/**

 <plugin>
 <groupId>sample.plugin</groupId>
 <artifactId>hello-maven-plugin</artifactId>
 <version>1.0-SNAPSHOT</version>
 <configuration>
 <nameSpacePrefix>avro</nameSpacePrefix>
 <nameSpacePrefix>Welcome</nameSpacePrefix>
 </configuration>
 </plugin>


 */

@Mojo(name = "pojo-to-schema", requiresDependencyResolution = ResolutionScope.TEST, defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GenerateSchemaMojo extends AbstractMojo {

    @Parameter( property = "extension", defaultValue = "avsc" )
    private String extension;

    @Parameter( property = "nameSpacePrefix", defaultValue = "avro" )
    private String nameSpacePrefix;

    @Parameter( property = "nameSpaceSuffix")
    private String nameSpaceSuffix;


    @Parameter( property = "outputDirectory", defaultValue = "${project.basedir}/src/main/resources/" )
    private String outputDirectory;



    @Parameter( property = "sourceDirectory", defaultValue = "${project.basedir}/src/main/java/" )
    private String sourceDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        //schemaGenerator.createAvroSchemaFromClass(sourceDirectory, null, extension, nameSpacePrefix, nameSpaceSuffix, outputDirectory );
    }
}
