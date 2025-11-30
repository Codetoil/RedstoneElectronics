/**
 * Redstone Electronics is a MC Mod that adds redstone components.
 * Redstone Electronics (C) 2020-2023  Codetoil
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.codetoil.redstone_electronics.world.item;

import io.codetoil.redstone_electronics.Constants;
import io.codetoil.redstone_electronics.world.level.block.REBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class REItems
{
	private static final DeferredRegister<Item> RE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
		Constants.MOD_ID);
	public static final RegistryObject<Item> RESISTOR_BLOCK_ITEM =
		RE_ITEMS.register("redstone_resistor", () ->
			new BlockItem(REBlocks.RESISTOR_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> ROTARY_SELECTOR_BLOCK_ITEM =
		RE_ITEMS.register("redstone_rotary_selector", () ->
			new BlockItem(REBlocks.ROTARY_SELECTOR_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> ROTARY_DISTRIBUTOR_BLOCK_ITEM =
		RE_ITEMS.register("redstone_rotary_distributor", () ->
			new BlockItem(REBlocks.ROTARY_DISTRIBUTOR_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> MOTOR_BLOCK_ITEM =
		RE_ITEMS.register("servo_motor", () ->
			new BlockItem(REBlocks.SERVO_MOTOR_BLOCK.get(), new Item.Properties()));
	private static final DeferredRegister<Item> MC_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
		"minecraft");
	public static final RegistryObject<Item> STICK_BLOCK_ITEM =
		MC_ITEMS.register("stick", () ->
			new BlockItem(REBlocks.STICK_BLOCK.get(), new Item.Properties()));

	public static void init(IEventBus modEventBus) {
		RE_ITEMS.register(modEventBus);
		MC_ITEMS.register(modEventBus);
	}
}
