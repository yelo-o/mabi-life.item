package life.mabi.demo.repository;

import life.mabi.demo.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
//    Optional<Item> findById(Item item);
}
