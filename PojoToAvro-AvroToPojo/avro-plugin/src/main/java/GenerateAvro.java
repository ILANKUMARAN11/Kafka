import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

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
public class GenerateAvro extends AbstractMojo {


    @Parameter( property = "nameSpacePrefix", defaultValue = "avro" )
    private String nameSpacePrefix;

    @Parameter( property = "nameSpaceSuffix", defaultValue = "avro" )
    private String nameSpaceSuffix;



    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

    }
}
