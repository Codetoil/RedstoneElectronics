package io.codetoil.redstone_electronics.client;

import io.codetoil.redstone_electronics.world.level.block.RedstoneElectronicsBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class RedstoneElectronicsClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient(ModContainer modContainer) {
		BlockRenderLayerMap.INSTANCE.putBlock(RedstoneElectronicsBlocks.REDSTONE_RESISTOR_BLOCK, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RedstoneElectronicsBlocks.REDSTONE_ROTARY_SELECTOR_BLOCK,
			RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RedstoneElectronicsBlocks.REDSTONE_ROTARY_DISTRIBUTOR_BLOCK,
			RenderType.cutout());
	}
}
