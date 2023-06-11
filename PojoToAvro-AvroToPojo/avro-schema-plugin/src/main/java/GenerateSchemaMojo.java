import lombok.extern.slf4j.Slf4j;
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
import java.util.*;


@Slf4j
@Mojo(name = "avro-schema", defaultPhase = LifecyclePhase.COMPILE)
public class GenerateSchemaMojo extends AbstractMojo {

    //private static Logger log = LoggerFactory.getLogger(GenerateSchemaMojo.class);
    @Parameter(property = "extension", defaultValue = "avsc")
    private String extension;

    @Parameter(property = "nameSpacePrefix", defaultValue = "avro")
    private String nameSpacePrefix;

    @Parameter(property = "nameSpaceSuffix")
    private String nameSpaceSuffix;

    @Parameter(property = "outputDirectory", defaultValue = "${project.basedir}/src/main/resources/")
    private String outputDirectory;

    @Parameter(property = "sourceDirectory", defaultValue = "${project.basedir}/target/classes/")
    private String sourceDirectory;

    @Parameter
    private List<String> excludes;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("extension ::: {}", extension);
        log.info("nameSpacePrefix ::: {}", nameSpacePrefix);
        log.info("nameSpaceSuffix ::: {}", nameSpaceSuffix);
        log.info("outputDirectory ::: {}", outputDirectory);
        log.info("sourceDirectory ::: {}", sourceDirectory);
        log.info("excludes size ::: {}, excludes list :::  {}", excludes.size(), excludes);
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        File file = new File(sourceDirectory);
        Map<String, List<String>> directoryMapping = new HashMap<>();
        this.getAllFiles(file, directoryMapping, nameSpacePrefix);
        try {
            // Convert File to a URL
            URL url = file.toURI().toURL();          // file:/c:/myclasses/
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);
            // Load in the class; MyClass.class should be located in
            // the directory file:/c:/myclasses/com/mycompany

            for (Map.Entry<String, List<String>> entry : directoryMapping.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                for (String className : value) {
                    Class<?> cls = cl.loadClass(className);
                    log.info("Generating schema for Class :: {}", className);
                    schemaGenerator.createAvroSchemaFromClass(cls, null, extension, nameSpacePrefix, nameSpaceSuffix, outputDirectory);
                    ProtectionDomain pDomain = cls.getProtectionDomain();
                    CodeSource cSource = pDomain.getCodeSource();
                    URL urlfrom = cSource.getLocation();
                    log.debug("URL from file :: {}", urlfrom.getFile());
                }
            }
        } catch (MalformedURLException e) {
            log.error("MalformedURLException :: {}", e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException :: {}", e.getMessage());
        } catch (IOException e) {
            log.error("IOException :: {}", e.getMessage());
        }
    }

    private static void getAllFiles(File curDir, Map<String, List<String>> directoryMapping, String nameSpacePrefix) {
        File[] filesList = curDir.listFiles();
        for (File f : filesList) {
            if (f.isDirectory()) {
                getAllFiles(f, directoryMapping, nameSpacePrefix);
            }
            if (f.isFile()) {
                if (f.getName().endsWith(".class") && !f.getName().endsWith("$Builder.class")) {
                    String folderParent = f.getParent().replace("\\", ".");
                    String packageName = folderParent.substring(folderParent.indexOf("target.classes.")+ 15);
                    String absolutePath = f.getAbsolutePath().replace("\\", ".");
                    log.debug(f.getName() + " :: " + absolutePath);
                    int indexOfTarget = absolutePath.indexOf("target.classes.") + 15;
                    String packageWithClass = absolutePath.substring(indexOfTarget);
                    int indexOfClass = packageWithClass.indexOf(".class");
                    String className = packageWithClass.substring(0, indexOfClass);

                    if (!packageName.startsWith(nameSpacePrefix) || packageName.startsWith(nameSpacePrefix)) {
                        if (directoryMapping.containsKey(packageName)) {
                            List<String> existingPath = directoryMapping.get(packageName);
                            existingPath.add(className);
                        } else {
                            List<String> newPath = new ArrayList<>();
                            newPath.add(className);
                            directoryMapping.put(packageName, newPath);
                        }
                    }
                }
            }

        }
    }
}
