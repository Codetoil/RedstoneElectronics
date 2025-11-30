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

package io.codetoil.redstone_electronics;

import io.codetoil.redstone_electronics.world.item.REItems;
import io.codetoil.redstone_electronics.world.level.block.REBlocks;
import io.codetoil.redstone_electronics.world.level.block.entity.REBlockEntityTypes;
import io.codetoil.redstone_electronics.world.level.block.state.properties.REProperties;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.info.BlockListReport;
import net.minecraft.data.info.RegistryDumpReport;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(value = Constants.MOD_ID)
public class RedstoneElectronics
{

	public RedstoneElectronics(FMLJavaModLoadingContext context) {
		IEventBus modEventBus = context.getModEventBus();
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::gatherData);
		MinecraftForge.EVENT_BUS.addListener(this::buildCreativeModeTabContents);
		REProperties.init();
		REBlocks.init(modEventBus);
		REItems.init(modEventBus);
		REBlockEntityTypes.init(modEventBus);
	}

	private void clientSetup(FMLClientSetupEvent event) {
	}

	private void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
			event.accept(REBlocks.RESISTOR_BLOCK);
			event.accept(REBlocks.ROTARY_DISTRIBUTOR_BLOCK);
			event.accept(REBlocks.ROTARY_SELECTOR_BLOCK);
			event.accept(REBlocks.STICK_BLOCK);
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

