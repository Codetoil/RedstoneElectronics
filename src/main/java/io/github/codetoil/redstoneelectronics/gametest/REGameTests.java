package io.github.codetoil.redstoneelectronics.gametest;

import io.github.codetoil.redstoneelectronics.RedstoneElectronics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.List;

public class REGameTests {
    @GameTest(templateNamespace = RedstoneElectronics.MODID)
    public static void blockExistanceTests(GameTestHelper helper) {
        List.<String>of("redstoneelectronics:redstone_resistor",
                "redstoneelectronics:redstone_rotary_selector",
                "redstoneelectronics:redstone_rotary_distributer",
                "redstoneelectronics:servo_motor",
                "minecraft:stick")
            .forEach((name) -> {
                if (!ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(name))) helper.fail("Block \"" + name + "\" not found in registry");
            });
        helper.succeed();
    }
}