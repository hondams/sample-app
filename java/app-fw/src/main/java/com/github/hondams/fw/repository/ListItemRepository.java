package com.github.hondams.fw.repository;


import com.github.hondams.fw.entity.ListItem;
import java.util.List;

public interface ListItemRepository<T extends ListItem<ID, ID2>, ID, ID2> {

    List<T> findItemsById(ID id);

    void insertItems(List<T> items);

    void deleteItemsById(ID id);
}
