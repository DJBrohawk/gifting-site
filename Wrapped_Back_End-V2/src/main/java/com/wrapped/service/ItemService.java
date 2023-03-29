package com.wrapped.service;

import com.wrapped.entity.Item;
import com.wrapped.controller.ItemController;
import com.wrapped.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    public ItemRepository itemRepository;

    public List<Item> listAllItems() {
        return itemRepository.findAll();
    }


}
