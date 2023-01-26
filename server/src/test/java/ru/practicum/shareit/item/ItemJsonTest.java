package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class ItemJsonTest {
    @Autowired
    private JacksonTester<Item> json;

    @Test
    void testItemDto() throws IOException {
        Item item = new Item();
        item.setId(1L);
        item.setName("ItemName");
        item.setDescription("ItemDescription");
        item.setAvailable(true);
        item.setOwner(new User());
        item.setRequest(new ItemRequest());

        JsonContent<Item> result = json.write(item);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("ItemName");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("ItemDescription");
    }
}
