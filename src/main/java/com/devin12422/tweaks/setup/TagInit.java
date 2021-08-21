package com.devin12422.tweaks.setup;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.tag.Tag;

public class TagInit {

    public static final Tag<Item> DOUBLE_HANDED_ITEMS = TagRegistry.item(new Identifier("tweaks", "double_handed_items"));
    public static final Tag<Item> ACCROSS_DOUBLE_HANDED_ITEMS = TagRegistry.item(new Identifier("tweaks", "accross_double_handed_items"));

    public static void init() {
    }

}
