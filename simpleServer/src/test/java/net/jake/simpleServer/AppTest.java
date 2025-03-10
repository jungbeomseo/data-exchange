package net.jake.simpleServer;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jake.openapi.model.User;

@AutoConfigureMockMvc
@SpringBootTest
public class AppTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Test1() throws Exception {
        String uri = "/users";
        ObjectMapper objectMapper = new ObjectMapper();

        User user = User.builder().name("test 0").build();
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        MyUser myUser = MyUser.builder().name("test 1").build();
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(myUser)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Builder
    @Data
    static class MyUser {
        String name;
    }
}
