package com.github.hondams.common.repository;


import java.util.List;

public interface ListItemRepository<T extends ListItem<ID>, ID> {

    List<T> findItemsById(ID id);

    void insertItems(List<T> items);

    void deleteItemsById(ID id);
}
