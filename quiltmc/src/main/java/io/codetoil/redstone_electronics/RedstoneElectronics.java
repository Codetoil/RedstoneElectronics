package io.codetoil.redstone_electronics;

import io.codetoil.redstone_electronics.world.item.RedstoneElectronicsItems;
import io.codetoil.redstone_electronics.world.level.block.RedstoneElectronicsBlocks;
import io.codetoil.redstone_electronics.world.level.block.entity.RedstoneElectronicsBlockEntityTypes;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class RedstoneElectronics implements ModInitializer
{
	@Override
	public void onInitialize(ModContainer modContainer) {
		RedstoneElectronicsBlocks.init();
		RedstoneElectronicsItems.init();
		RedstoneElectronicsBlockEntityTypes.init();

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS)
			.register((group) -> {
				group.accept(RedstoneElectronicsBlocks.REDSTONE_RESISTOR_BLOCK);
				group.accept(RedstoneElectronicsBlocks.REDSTONE_ROTARY_DISTRIBUTOR_BLOCK);
				group.accept(RedstoneElectronicsBlocks.REDSTONE_ROTARY_SELECTOR_BLOCK);
				group.accept(RedstoneElectronicsBlocks.OAK_AXLE_BLOCK);
			});
	}
}
