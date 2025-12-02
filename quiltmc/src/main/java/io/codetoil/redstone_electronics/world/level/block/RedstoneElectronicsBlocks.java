package io.codetoil.redstone_electronics.world.level.block;

import io.codetoil.redstone_electronics.Constants;
import io.codetoil.redstone_electronics.world.level.block.AxleBlock;
import io.codetoil.redstone_electronics.world.level.block.RedstoneResistorBlock;
import io.codetoil.redstone_electronics.world.level.block.RedstoneRotaryDistributorBlock;
import io.codetoil.redstone_electronics.world.level.block.RedstoneRotarySelectorBlock;
import io.codetoil.redstone_electronics.world.level.block.servo_motor.ServoMotorBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class RedstoneElectronicsBlocks
{
	public static final RedstoneResistorBlock REDSTONE_RESISTOR_BLOCK =
		register("redstone_resistor", new RedstoneResistorBlock(BlockBehaviour.Properties.of().instabreak()
			.sound(SoundType.STONE)
			.pushReaction(PushReaction.DESTROY)));
	public static final RedstoneRotarySelectorBlock REDSTONE_ROTARY_SELECTOR_BLOCK =
		register("redstone_rotary_selector", new RedstoneRotarySelectorBlock(BlockBehaviour.Properties.of()
			.instabreak().sound(SoundType.STONE)
			.pushReaction(PushReaction.DESTROY)));
	public static final RedstoneRotaryDistributorBlock REDSTONE_ROTARY_DISTRIBUTOR_BLOCK =
		register("redstone_rotary_distributor", new RedstoneRotaryDistributorBlock(BlockBehaviour.Properties.of()
			.instabreak().sound(SoundType.STONE)
			.pushReaction(PushReaction.DESTROY)));
	public static final ServoMotorBlock SERVO_MOTOR_BLOCK =
		register("servo_motor", new ServoMotorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
			.strength(0.5f)
			.sound(SoundType.STONE)));
	public static final AxleBlock OAK_AXLE_BLOCK =
		register("oak_axle", new AxleBlock(BlockBehaviour.Properties.of().strength(2.0f)
			.sound(SoundType.WOOD)));

	public static <B extends Block> B register(String name, B block) {
		// Register the block and its item.
		ResourceLocation id = ResourceLocation.tryBuild(Constants.MOD_ID, name);

		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

	public static void init() { }
}
