package my.pojo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
public class Person {
    Long id;
    String fullName;
    String emailId;
    String phoneNo;
    Address address;

}
