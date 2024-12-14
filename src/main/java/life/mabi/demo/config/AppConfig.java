package life.mabi.demo.config;

import life.mabi.demo.models.Item;
import life.mabi.demo.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
//@Profile("test")
public class AppConfig {
    @Bean
    public CommandLineRunner test(ItemRepository itemRepository) {
        return args -> {
            System.out.println("**** Item Insert 시작");
            List<Item> itemList = IntStream.rangeClosed(1, 10)
                    .mapToObj(i -> Item.builder()
                            .item_name("나이트브링어 " + i + ".0")
                            .description("No." + i + " publishing company")
                            .category("기타 무기 " + i)
                            .build())
                    .collect(Collectors.toList());
            itemRepository.saveAll(itemList);
            System.out.println("**** Item Insert 끝");
        };
    }
}
