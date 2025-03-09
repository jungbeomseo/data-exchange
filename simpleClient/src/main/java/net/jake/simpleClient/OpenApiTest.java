package net.jake.simpleClient;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import net.jake.openapi.model.User;
import net.jake.openapi.model.UserRole;
import net.jake.openapi.model.UserRoleDTO;
import reactor.core.publisher.Mono;

public class OpenApiTest {

    public void test() {
        WebClient client = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

        System.out.println(">> [1] REST API TEST SCENARIO\n");
        
        User mockUser = User.builder().name("test 0").build();
        String userId = client.post()
            .uri("/users")
            .body(Mono.just(mockUser), User.class)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        System.out.println("1. created user:\n%s\n".formatted(userId));

        User user = client.get()
            .uri("/users/%s".formatted(userId))
            .retrieve()
            .bodyToMono(User.class)
            .block();
        System.out.println("2. query user:\n%s\n".formatted(user.toString()));

        UserRoleDTO roleDto = UserRoleDTO.builder().role(UserRole.ADMIN).build();
        User updatedUser = client.post()
            .uri("/users/%s/roles".formatted(userId))
            .body(Mono.just(roleDto), UserRoleDTO.class)
            .retrieve()
            .bodyToMono(User.class)
            .block();
        System.out.println("3. add role:\n%s\n".formatted(updatedUser.toString()));
    }
}
