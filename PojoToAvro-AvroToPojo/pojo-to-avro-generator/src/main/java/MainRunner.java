import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.avro.AvroFactory;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import my.pojo.Employee;
import org.apache.avro.Schema;
import pojo.Earth;
import pojo.Mars;
import pojo.Universe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class MainRunner {


    public static void main(String ilan[]) throws IOException {
//        ObjectMapper mapper = new ObjectMapper(new AvroFactory());
//        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        AvroSchemaGenerator gen = new AvroSchemaGenerator();
//        mapper.acceptJsonFormatVisitor(Employee.class, gen);


        AvroMapper avroMapper = AvroMapper.builder()
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .enable(MapperFeature.APPLY_DEFAULT_VALUES)
                //.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .addModule(new AvroJavaTimeModule())
                .build();


        createAvroSchemaFromClass(Employee.class, avroMapper, ".avro");

        createAvroSchemaFromClass(Universe.class, avroMapper, ".avsc");
        createAvroSchemaFromClass(Earth.class, avroMapper, ".avsc");
        createAvroSchemaFromClass(Mars.class, avroMapper, ".avsc");


    }


    private static void createAvroSchemaFromClass(Class<?> clazz, AvroMapper avroMapper, String extension) throws IOException {

        AvroSchemaGenerator gen = new AvroSchemaGenerator();
        gen.enableLogicalTypes();
        avroMapper.acceptJsonFormatVisitor(clazz, gen);
        AvroSchema schemaWrapper = gen.getGeneratedSchema();

        org.apache.avro.Schema avroSchema = schemaWrapper.getAvroSchema();
        String avroSchemaInJSON = avroSchema.toString(Boolean.TRUE);
        String actualNameSpace = avroSchema.getNamespace();
        String overrideNameSpace = "avro."+avroSchema.getNamespace();
        org.apache.avro.Schema schemaInAvroNameSpace = new Schema.Parser().parse(avroSchemaInJSON.replace(actualNameSpace, overrideNameSpace));



        String dir = System.getProperty("user.dir");
        //Write to File
        Path fileName = Path.of(dir+"/"+"avro-to-pojo-generator"+"/src/main/resources/"+clazz.getSimpleName() + extension);
        Files.writeString(fileName, schemaInAvroNameSpace.toString(Boolean.TRUE));
    }
}
