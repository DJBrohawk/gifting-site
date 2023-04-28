package com.wrapped.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrapped.Wrapped_Back_EndV2.WrappedBackEndV2Application;
import com.wrapped.entity.Item;
import com.wrapped.repository.ItemRepository;
import com.wrapped.repository.PersonRepository;
import com.wrapped.repository.UserRepository;
import com.wrapped.service.ItemService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = { ItemService.class, ItemController.class, ItemRepository.class })
@WebMvcTest(TestItemController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class TestItemController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
     private PersonRepository personRepository;

     @MockBean
     private UserRepository userRepository;

    Item testItemOne;
    Item testItemTwo;
    List<Item> itemList = new ArrayList<>();

    private void setUpTestData() {
        testItemOne = new Item(1, "TestItemOne");
        testItemTwo = new Item(2, "TestItemTwo");

        itemList.add(testItemOne);
        itemList.add(testItemTwo);
    }

    @BeforeEach
    void setUp() {
        setUpTestData();
    }

    @AfterAll
    void tearDown() {
        personRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();


    }


    @Test
    void testGetAllItems() throws Exception {
        // mock the itemRepository to return the list of items
        when(itemRepository.findAll()).thenReturn(itemList);

        // use MockMvc to perform a GET request to the getAllItems endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getAllItems")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());


        String responseContent = result.getResponse().getContentAsString();
        List<Item> items = new ObjectMapper().readValue(responseContent, new TypeReference<List<Item>>() {
        });
        assertEquals(2, items.size());
    }

    @Test
    void testFindItemById() throws Exception {
        // Mock the item repository to return a test item when its findById method is called
        when(itemRepository.findById(1)).thenReturn(Optional.of(testItemOne));

        // Make a GET request to the findItemById endpoint with id=1
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getItemById?id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Convert the JSON response to an Item object
        ObjectMapper objectMapper = new ObjectMapper();
        Item responseItem = objectMapper.readValue(result.getResponse().getContentAsString(), Item.class);

        // Verify that the response item has the expected properties
        assertEquals(testItemOne.getId(), responseItem.getId());
        assertEquals(testItemOne.getItemName(), responseItem.getItemName());

        // Verify that the item repository's findById method was called once with the correct ID
        verify(itemRepository, times(1)).findById(1);
    }


    @Test
    void testUpdateItem() throws Exception {
        // Arrange
        Item item = new Item(3, "Item ");
        Item updatedItem = new Item(3, "Updated Item ");
        when(itemRepository.findById(3)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(updatedItem);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/updateItem")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        verify(itemRepository, times(1)).findById(3);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void testDeleteItemById() throws Exception {
        // Arrange
        Item item = new Item(7, "Item ");
        itemRepository.save(item);
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/deleteItem?id=" + 7)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // Assert
        verify(itemRepository, times(1)).deleteById(item.getId());
    }







}