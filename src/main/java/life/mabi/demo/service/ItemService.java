package life.mabi.demo.service;

import life.mabi.demo.dto.ItemDto;
import life.mabi.demo.dto.PageResponse;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto);
    PageResponse<?> getAllItem(int pageNo, int pageSize);

    ItemDto getItemById(Long id);

    ItemDto updateItemById(ItemDto itemDto, Long id);

    void deleteItemById(Long id);
}
