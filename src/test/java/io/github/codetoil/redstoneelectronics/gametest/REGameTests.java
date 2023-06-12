/**
 *  Redstone Electronics is a MC Mod that adds redstone components.
 *  Redstone Electronics (C) 2020-2023  Codetoil
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

 package io.github.codetoil.redstoneelectronics.gametest;

import io.github.codetoil.redstoneelectronics.RedstoneElectronics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.List;

@GameTestHolder(RedstoneElectronics.MODID)
public class REGameTests {
    @GameTest
    public static void blockExistenceTests(GameTestHelper helper) {
        List.<String>of("redstoneelectronics:redstone_resistor",
                "redstoneelectronics:redstone_rotary_selector",
                "redstoneelectronics:redstone_rotary_distributor",
                "redstoneelectronics:servo_motor",
                "minecraft:stick")
            .forEach((name) -> {
                if (!ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(name))) helper.fail("Block \"" + name + "\" not found in registry");
            });
        helper.succeed();
    }

    @GameTest
    public static void motorTest1(GameTestHelper helper) {
        helper.fail("test");
    } 
}