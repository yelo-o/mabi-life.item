package life.mabi.demo.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import life.mabi.demo.models.Item;
import life.mabi.demo.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner test(ItemRepository itemRepository, ObjectMapper objectMapper) {
        return args -> {
            try {
                // items.json 파일 읽기
                Resource resource = new ClassPathResource("items.json");
                List<Item> items = objectMapper.readValue(
                        resource.getInputStream(),
                        new TypeReference<List<Item>>() {}
                );

                // 데이터베이스에 저장
                itemRepository.saveAll(items);

            } catch (IOException e) {
                throw new RuntimeException("Failed to load items data", e);
            }
        };
    }
}