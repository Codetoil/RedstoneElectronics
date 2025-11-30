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

package io.codetoil.redstone_electronics;

import io.codetoil.redstone_electronics.world.item.RedstoneElectronicsItems;
import io.codetoil.redstone_electronics.world.level.block.RedstoneElectronicsBlocks;
import io.codetoil.redstone_electronics.world.level.block.entity.RedstoneElectronicsBlockEntityTypes;
import io.codetoil.redstone_electronics.world.level.block.state.properties.REProperties;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.info.BlockListReport;
import net.minecraft.data.info.RegistryDumpReport;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.concurrent.CompletableFuture;

@Mod(value = Constants.MOD_ID)
public class RedstoneElectronics
{

	public RedstoneElectronics(IEventBus modEventBus, ModContainer container) {
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::gatherData);
		modEventBus.addListener(this::buildCreativeModeTabContents);
		REProperties.init();
		RedstoneElectronicsBlocks.init(modEventBus);
		RedstoneElectronicsItems.init(modEventBus);
		RedstoneElectronicsBlockEntityTypes.init(modEventBus);
	}

	private void clientSetup(FMLClientSetupEvent event) {
	}

	private void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
			event.accept(RedstoneElectronicsBlocks.REDSTONE_RESISTOR_BLOCK.get());
			event.accept(RedstoneElectronicsBlocks.ROTARY_DISTRIBUTOR_BLOCK.get());
			event.accept(RedstoneElectronicsBlocks.REDSTONE_ROTARY_SELECTOR_BLOCK.get());
			event.accept(RedstoneElectronicsBlocks.OAK_AXLE_BLOCK.get());
		}
	}

	private void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		if (event.includeReports()) {
			gen.addProvider(true, new BlockListReport(gen.getPackOutput(),
				CompletableFuture.supplyAsync(VanillaRegistries::createLookup, Util.backgroundExecutor())));
			gen.addProvider(true, new RegistryDumpReport(gen.getPackOutput()));
		}
	}
}

