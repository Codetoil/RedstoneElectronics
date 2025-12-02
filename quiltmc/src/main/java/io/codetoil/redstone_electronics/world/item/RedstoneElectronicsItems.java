package io.codetoil.redstone_electronics.world.item;

import io.codetoil.redstone_electronics.Constants;
import io.codetoil.redstone_electronics.world.level.block.RedstoneElectronicsBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class RedstoneElectronicsItems
{
	public static final Item RESISTOR_BLOCK_ITEM =
		register("redstone_resistor",
			new BlockItem(RedstoneElectronicsBlocks.REDSTONE_RESISTOR_BLOCK, new Item.Properties()));
	public static final Item ROTARY_SELECTOR_BLOCK_ITEM =
		register("redstone_rotary_selector",
			new BlockItem(RedstoneElectronicsBlocks.REDSTONE_ROTARY_SELECTOR_BLOCK, new Item.Properties()));
	public static final Item ROTARY_DISTRIBUTOR_BLOCK_ITEM =
		register("redstone_rotary_distributor",
			new BlockItem(RedstoneElectronicsBlocks.REDSTONE_ROTARY_DISTRIBUTOR_BLOCK, new Item.Properties()));
	public static final Item MOTOR_BLOCK_ITEM =
		register("servo_motor",
			new BlockItem(RedstoneElectronicsBlocks.SERVO_MOTOR_BLOCK, new Item.Properties()));
	public static final Item OAK_AXLE_BLOCK_ITEM =
		register("oak_axle",
			new BlockItem(RedstoneElectronicsBlocks.OAK_AXLE_BLOCK, new Item.Properties()));

	public static Item register( String id, Item item) {
		// Create the identifier for the item.
		ResourceLocation itemID = ResourceLocation.tryBuild(Constants.MOD_ID, id);

		// Register and return the item
		return Registry.register(BuiltInRegistries.ITEM, itemID, item);
	}

	public static void init() { }
}
