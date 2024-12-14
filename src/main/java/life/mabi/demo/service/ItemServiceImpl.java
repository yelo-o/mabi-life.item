package life.mabi.demo.service;

import life.mabi.demo.dto.ItemDto;
import life.mabi.demo.dto.PageResponse;
import life.mabi.demo.exceptions.ResourceNotFoundException;
import life.mabi.demo.models.Item;
import life.mabi.demo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public ItemDto createItem(ItemDto itemDto) {
        Item item = mapToEntity(itemDto);
        Item newItem = itemRepository.save(item);

        ItemDto itemResponse = mapToDto(newItem);

        return itemResponse;
    }


    @Override
    public PageResponse<?> getAllItem(int pageNo, int pageSize) {
        Pageable pageable =
                PageRequest.of(pageNo, pageSize, Sort.by("id").descending());

        Page<Item> itemPage = itemRepository.findAll(pageable);
        List<Item> listOfBook = itemPage.getContent();
        List<ItemDto> content = listOfBook.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PageResponse<ItemDto> bookResponse = new PageResponse<>();
        bookResponse.setContent(content);
        bookResponse.setPageNo(itemPage.getNumber()); //페이지 번호
        bookResponse.setPageSize(itemPage.getSize()); //페이지 사이즈
        bookResponse.setTotalElements(itemPage.getTotalElements()); //전체 엘리멘트 개수
        bookResponse.setTotalPages(itemPage.getTotalPages()); //전체 엘리멘트 개수/페이지 사이즈
        bookResponse.setLast(itemPage.isLast());

        return bookResponse;
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = getExistItem(id);
        return mapToDto(item);
    }

    @Override
    public ItemDto updateItemById(ItemDto itemDto, Long id) {
        Item item = getExistItem(id);
        if (itemDto.getItem_name() != null) {
            item.setItem_name(itemDto.getItem_name());
        }
        return mapToDto(item);
    }

    @Override
    public void deleteItemById(Long id) {
        Item item = getExistItem(id);
        itemRepository.delete(item);
    }

    private Item getExistItem(Long id) {
        return itemRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book could not be found"));
    }

    //Dto 클래스로 변환
    private ItemDto mapToDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .item_name(item.getItem_name())
                .category(item.getCategory())
                .description(item.getDescription())
                .build();
    }

    //Entity 클래스로 변환
    private Item mapToEntity(ItemDto itemDto) {
        return Item.builder()
                .item_name(itemDto.getItem_name())
                .category(itemDto.getCategory())
                .description(itemDto.getDescription())
                .build();
    }

}
