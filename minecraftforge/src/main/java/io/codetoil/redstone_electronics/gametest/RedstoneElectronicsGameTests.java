/**
 * Redstone Electronics is a MC Mod that adds redstone components.
 * Redstone Electronics (C) 2020-2025  Codetoil
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

package io.codetoil.redstone_electronics.gametest;

import io.codetoil.redstone_electronics.Constants;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@GameTestHolder(Constants.MOD_ID)
public class RedstoneElectronicsGameTests
{
	@GameTest
	public static void blockExistenceTests(GameTestHelper helper) {
		List.<String>of("redstone_electronics:redstone_resistor",
				"redstone_electronics:redstone_rotary_selector",
				"redstone_electronics:redstone_rotary_distributor",
				"redstone_electronics:servo_motor",
				"redstone_electronics:oak_axle")
			.forEach((name) -> {
				if (!ForgeRegistries.BLOCKS.containsKey(ResourceLocation.parse(name)))
					helper.fail("Block \"" + name + "\" not found in registry");
			});
		helper.succeed();
	}

	@GameTest
	public static void itemExistenceTests(GameTestHelper helper) {
		List.<String>of("redstone_electronics:redstone_resistor",
				"redstone_electronics:redstone_rotary_selector",
				"redstone_electronics:redstone_rotary_distributor",
				"redstone_electronics:servo_motor",
				"redstone_electronics:oak_axle")
			.forEach((name) -> {
				if (!ForgeRegistries.ITEMS.containsKey(ResourceLocation.parse(name)))
					helper.fail("Item \"" + name + "\" not found in registry");
			});
		helper.succeed();
	}
}