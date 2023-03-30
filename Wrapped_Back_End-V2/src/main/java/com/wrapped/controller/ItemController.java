package com.wrapped.controller;

import com.wrapped.entity.Item;
import com.wrapped.repository.ItemRepository;
import com.wrapped.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ItemController {

    //    Dependency Injections
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;
// End of Dependency Injections

// POST
// Start of save Item,
    @RequestMapping(value = "/saveItem",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void saveItem(@RequestBody Item item) {
        itemRepository.save(item);
    }
// End of save Item Post Method

// GET
// Start of get All items
    @RequestMapping(value = "/getAllItems",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> allItems = itemService.listAllItems();
        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }
//  End of GetAllItems

// GET
// Start of get item By ID
    @RequestMapping(value = "/getItemById",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Optional<Item>> findItemById(int id) {
        return new ResponseEntity<>(itemRepository.findById(id), HttpStatus.OK);
    }
//  End of Get Item By ID Get Method

// PUT
// Start of updateItem PUT method
    @RequestMapping(value = "/updateItem",
     consumes = MediaType.APPLICATION_JSON_VALUE,
     produces = MediaType.APPLICATION_JSON_VALUE,
     method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Optional<Item>> updateItem(@RequestBody Item item) {
        Item updateItem = itemRepository.findById(item.getId()).get();
        updateItem.setId(item.getId());
        updateItem.setItemName(item.getItemName());
        itemRepository.save(updateItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
// End of updateItem PUT method

// DELETE
// Start of Delete Item Method

    @RequestMapping(value = "/deleteItem",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Optional<Item>> deleteItemById(int id) {
        itemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
