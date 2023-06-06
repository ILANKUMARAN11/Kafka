import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Earth;
import pojo.Employee;
import pojo.Mars;
import pojo.Universe;
import service.SchemaGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class SchemaGeneratorTest {

    static AvroMapper avroMapper = null;
    static String testResource = null;

    @BeforeAll
    static void initAll() {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        testResource = absolutePath + "/";

        File folder = new File(testResource);
        Arrays.stream(folder.listFiles())
                .filter(f -> f.getName().endsWith(".avro") || f.getName().endsWith(".avsc"))
                .forEach(File::delete);

        avroMapper = AvroMapper.builder()
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .enable(MapperFeature.APPLY_DEFAULT_VALUES)
                //.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .addModule(new AvroJavaTimeModule())
                .build();

    }

    @Test
    @DisplayName("avro extension generate ")
    public void generateAvro() throws IOException {
        String extension = ".avro";
        SchemaGenerator.createAvroSchemaFromClass(Employee.class, avroMapper, extension, "avro", null, testResource);
        Assertions.assertTrue(getFileNameFromClass(Employee.class, extension));
    }

    @Test
    @DisplayName("asvc extension generate ")
    public void generateAsvc() throws IOException {
        String extension = ".avsc";

        SchemaGenerator.createAvroSchemaFromClass(Universe.class, avroMapper, extension, "avro", null, testResource);
        SchemaGenerator.createAvroSchemaFromClass(Earth.class, avroMapper, extension, "avro", null, testResource);
        SchemaGenerator.createAvroSchemaFromClass(Mars.class, avroMapper, extension, "avro", null, testResource);

        Assertions.assertTrue(getFileNameFromClass(Universe.class, extension));
        Assertions.assertTrue(getFileNameFromClass(Earth.class, extension));
        Assertions.assertTrue(getFileNameFromClass(Mars.class, extension));
    }

    Boolean getFileNameFromClass(Class<?> clazz, String extension) {
        String[] className = clazz.getName().split("[.]");
        String lastOne = className[className.length - 1];
        File tempFile = new File(testResource + lastOne + extension);
        boolean exists = tempFile.exists();
        return exists;
    }
}
