/**
 *  Redstone Electronics is a MC Mod that adds restone components.
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

package io.github.codetoil.redstoneelectronics;

import io.github.codetoil.redstoneelectronics.gametest.REGameTests;
import io.github.codetoil.redstoneelectronics.world.level.block.REBlocks;
import io.github.codetoil.redstoneelectronics.world.item.REItems;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.REProperties;
import io.github.codetoil.redstoneelectronics.world.level.block.entity.REBlockEntityTypes;

import java.util.stream.Collectors;
import java.io.IOException;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.info.BlockListReport;
import net.minecraft.data.info.CommandsReport;
import net.minecraft.data.info.WorldgenRegistryDumpReport;
import net.minecraft.data.info.RegistryDumpReport;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.event.RegisterGameTestsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value="redstoneelectronics")
public class RedstoneElectronics {
    private static final Logger logger = LogManager.getLogger();
    public static final String MODID = "redstoneelectronics";

    public RedstoneElectronics() {
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerTests);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
        // MinecraftForge.EVENT_BUS.register(this);
        REProperties.init();
        REBlocks.init();
        REItems.init();
        REBlockEntityTypes.init();
    }

    private void registerTests(RegisterGameTestsEvent event) {
        event.register(REGameTests.class);
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeReports())
        {
            gen.addProvider(new BlockListReport(gen));
            gen.addProvider(new RegistryDumpReport(gen));
        }
    }
}

