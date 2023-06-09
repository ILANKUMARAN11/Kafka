import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import com.ilan.service.SchemaGenerator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.List;

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

//@Slf4j
@Mojo(name = "avro-schema", defaultPhase = LifecyclePhase.COMPILE)
public class GenerateSchemaMojo extends AbstractMojo {

    @Parameter( property = "extension", defaultValue = "avsc" )
    private String extension;

    @Parameter( property = "nameSpacePrefix", defaultValue = "avro" )
    private String nameSpacePrefix;

    @Parameter( property = "nameSpaceSuffix")
    private String nameSpaceSuffix;


    @Parameter( property = "outputDirectory", defaultValue = "${project.basedir}/src/main/resources/" )
    private String outputDirectory;



    @Parameter( property = "sourceDirectory", defaultValue = "${project.basedir}/target/classes/" )
    private String sourceDirectory;

    @Parameter
    private List<String> excludes;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        File file = new File(sourceDirectory);
        Class<?> cls = null;
        try {
            // Convert File to a URL
            URL url = file.toURI().toURL();          // file:/c:/myclasses/
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);
            //ClassLoader cl = createDirectoryLoader(sourceDirectory);

            // Load in the class; MyClass.class should be located in
            // the directory file:/c:/myclasses/com/mycompany
            cls = cl.loadClass("pojo.common.Details");

            ProtectionDomain pDomain = cls.getProtectionDomain();
            CodeSource cSource = pDomain.getCodeSource();
            URL urlfrom = cSource.getLocation();
            System.out.println(urlfrom.getFile());

        } catch (MalformedURLException e) {
        } catch (ClassNotFoundException | IOException e) {
        }

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        try {
            schemaGenerator.createAvroSchemaFromClass(cls, null, extension, nameSpacePrefix, nameSpaceSuffix, outputDirectory );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static ClassLoader createDirectoryLoader(String directory) throws URISyntaxException, IOException {
        Collection<URL> urls = new ArrayList<URL>();
        File dir = new File(directory);
        File[] files = dir.listFiles();
        for (File f : files) {
            System.out.println(f.getCanonicalPath());
            urls.add(f.toURI().toURL());
        }

        return URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]));
    }*/
}
