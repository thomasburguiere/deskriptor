package ch.burg.deskriptor.model;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    public void should_be_able_to_create_item_with_builder() {
        final Item rat = Item.builder().withName("rat").build();

        assertThat(rat.toString()).isEqualTo("Item(name=rat)");
    }

}