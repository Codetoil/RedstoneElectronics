/**
 *  Redstone Electronics is a MC Mod that adds redstone components.
 *  Redstone Electronics (C) 2020-2023  Codetoil
 * <p>
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * <p>
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * <p>
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.codetoil.redstoneelectronics;

import io.github.codetoil.redstoneelectronics.world.level.block.REBlocks;
import io.github.codetoil.redstoneelectronics.world.item.REItems;
import io.github.codetoil.redstoneelectronics.world.level.block.state.properties.REProperties;
import io.github.codetoil.redstoneelectronics.world.level.block.entity.REBlockEntityTypes;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.info.BlockListReport;
import net.minecraft.data.info.RegistryDumpReport;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value=RedstoneElectronics.MODID)
public class RedstoneElectronics {
    public static final Logger logger = LogManager.getLogger();
    public static final String MODID = "redstoneelectronics";

    public RedstoneElectronics() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
        REProperties.init();
        REBlocks.init();
        REItems.init();
        REBlockEntityTypes.init();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(REBlocks.RESISTOR_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(REBlocks.ROTARY_DISTRIBUTOR_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(REBlocks.ROTARY_SELECTOR_BLOCK.get(), RenderType.cutout());
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeReports())
        {
            gen.m_123914_(new BlockListReport(gen));
            gen.m_123914_(new RegistryDumpReport(gen));
        }
    }
}

