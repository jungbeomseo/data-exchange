package net.jake.simpleServer;

// import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
// import org.opentest4j.AssertionFailedError;

// import com.fasterxml.jackson.databind.DeserializationFeature;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class AppTest {
    // ObjectMapper objectMapper = new ObjectMapper()
        // .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        // ;

    @Test
    public void jsonTest() throws Exception {
        // User user = new User("id1");
        
        // String userToString = objectMapper.writeValueAsString(user);
        // System.out.println(userToString);

        // User stringToUser = objectMapper.readValue(userToString, User.class);
        // assertTrue(user.equals(stringToUser));
    }

    @Test()
    public void jsonTest2() throws Exception {
        // NewUser newUser = new NewUser("id1", "jake");
        // String newUserToString = objectMapper.writeValueAsString(newUser);
        // System.out.println(newUserToString);

        // try {
        //     User stringToUser = objectMapper.readValue(newUserToString, User.class);
        //     System.out.println(stringToUser);
        //     throw new AssertionFailedError();
        // } catch (UnrecognizedPropertyException e) {
        //     assertTrue(e.getMessage().contains("Unrecognized field"));
        // }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User {
        private String id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class NewUser {
        private String id;
        private String name;
    }
}
