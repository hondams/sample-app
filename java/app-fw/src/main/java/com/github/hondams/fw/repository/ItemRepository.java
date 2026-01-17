package com.github.hondams.fw.repository;


import com.github.hondams.fw.entity.Item;

public interface ItemRepository<T extends Item<ID>, ID> {

    T findItemById(ID id);

    void insertItem(T item);

    void deleteItemById(ID id);
}
