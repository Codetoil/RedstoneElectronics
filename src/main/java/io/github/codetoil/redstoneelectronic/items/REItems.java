package io.github.codetoil.redstoneelectronic.items;

import io.github.codetoil.redstoneelectronic.RedstoneElectronics;
import io.github.codetoil.redstoneelectronic.blocks.REBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class REItems {
    private static final DeferredRegister<Item> RE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RedstoneElectronics.MODID);
    private static final DeferredRegister<Item> MC_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> RESISTOR_BLOCK_ITEM = RE_ITEMS.register("redstone_resistor", () -> new BlockItem(REBlocks.RESISTOR_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE)));
    public static final RegistryObject<Item> ROTARY_DISTRIBUTOR_BLOCK_ITEM = RE_ITEMS.register("redstone_rotary_selector", () -> new BlockItem(REBlocks.ROTARY_SELECTOR_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE)));
    public static final RegistryObject<Item> ROTARY_SELECTOR_BLOCK_ITEM = RE_ITEMS.register("redstone_rotary_distributer", () -> new BlockItem(REBlocks.ROTARY_DISTRIBUTOR_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE)));
    public static final RegistryObject<Item> MOTOR_BLOCK_ITEM = RE_ITEMS.register("redstone_motor", () -> new BlockItem(REBlocks.MOTOR_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE)));
    public static final RegistryObject<Item> STICK_BLOCK_ITEM = MC_ITEMS.register("stick", () -> new BlockItem(REBlocks.STICK_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static void init() {
        RE_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MC_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
