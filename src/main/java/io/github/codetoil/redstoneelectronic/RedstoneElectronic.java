/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.Block$Properties
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$Properties
 *  net.minecraft.item.ItemGroup
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.RegistryEvent$Register
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.InterModComms
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
 *  net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
 *  net.minecraftforge.fml.event.server.FMLServerStartingEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  net.minecraftforge.registries.IForgeRegistryEntry
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package io.github.codetoil.redstoneelectronic;

import io.github.codetoil.redstoneelectronic.block.MotorBlock;
import io.github.codetoil.redstoneelectronic.block.RedstoneRotaryDistributor;
import io.github.codetoil.redstoneelectronic.block.RedstoneRotarySelector;
import io.github.codetoil.redstoneelectronic.block.ResistorBlock;
import io.github.codetoil.redstoneelectronic.block.StickBlock;
import io.github.codetoil.redstoneelectronic.properties.PropertiesRE;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value="redstoneelectronics")
public class RedstoneElectronic {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "redstoneelectronics";
    public static Block resistorBlock;
    public static Block rotaryDistributorBlock;
    public static Block rotarySelectorBlock;
    public static Block stickBlock;
    public static Block motorBlock;
    public static Item resistorBlockItem;
    public static Item rotaryDistributorBlockItem;
    public static Item rotarySelectorBlockItem;
    public static Item stickItem;
    public static Item motorItem;

    public RedstoneElectronic() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register((Object)this);
        PropertiesRE.init();
    }

    private void setup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", (Object)Blocks.field_150346_d.getRegistryName());
    }

    private void doClientStuff(FMLClientSetupEvent event) {
        LOGGER.info("Got game settings {}", (Object)((Minecraft)event.getMinecraftSupplier().get()).field_71474_y);
    }

    private void enqueueIMC(InterModEnqueueEvent event) {
        InterModComms.sendTo((String)"redstoneElectronic", (String)"helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(RegistryEvent.Register<Block> blockRegistryEvent) {
            LOGGER.info("HELLO from Register Block");
            resistorBlock = (Block)new ResistorBlock(Block.Properties.func_200945_a((Material)Material.field_151594_q).func_200943_b(0.0f).func_200947_a(SoundType.field_185848_a)).setRegistryName(RedstoneElectronic.MODID, "redstone_resistor");
            rotarySelectorBlock = (Block)new RedstoneRotarySelector(Block.Properties.func_200945_a((Material)Material.field_151594_q).func_200943_b(0.0f).func_200947_a(SoundType.field_185848_a)).setRegistryName(RedstoneElectronic.MODID, "redstone_rotary_selector");
            rotaryDistributorBlock = (Block)new RedstoneRotaryDistributor(Block.Properties.func_200945_a((Material)Material.field_151594_q).func_200943_b(0.0f).func_200947_a(SoundType.field_185848_a)).setRegistryName(RedstoneElectronic.MODID, "redstone_rotary_distributer");
            stickBlock = (Block)new StickBlock(Block.Properties.func_200945_a((Material)Material.field_151575_d).func_200943_b(2.0f).func_200947_a(SoundType.field_185848_a)).setRegistryName("minecraft", "stick");
            motorBlock = new MotorBlock(Block.Properties.func_200945_a((Material)Material.field_76233_E).func_200943_b(0.5f));
            blockRegistryEvent.getRegistry().registerAll((IForgeRegistryEntry[])new Block[]{resistorBlock, rotarySelectorBlock, rotaryDistributorBlock, stickBlock});
        }

        @SubscribeEvent
        public static void onItemRegistry(RegistryEvent.Register<Item> itemRegistryEvent) {
            resistorBlockItem = (Item)new BlockItem(resistorBlock, new Item.Properties().func_200916_a(ItemGroup.field_78028_d)).setRegistryName(RedstoneElectronic.MODID, "redstone_resistor");
            rotarySelectorBlockItem = (Item)new BlockItem(rotarySelectorBlock, new Item.Properties().func_200916_a(ItemGroup.field_78028_d)).setRegistryName(RedstoneElectronic.MODID, "redstone_rotary_selector");
            rotaryDistributorBlockItem = (Item)new BlockItem(rotaryDistributorBlock, new Item.Properties().func_200916_a(ItemGroup.field_78028_d)).setRegistryName(RedstoneElectronic.MODID, "redstone_rotary_distributer");
            stickItem = (Item)new BlockItem(stickBlock, new Item.Properties().func_200916_a(ItemGroup.field_78035_l)).setRegistryName("minecraft", "stick");
            motorItem = (Item)new BlockItem(motorBlock, new Item.Properties().func_200916_a(ItemGroup.field_78028_d)).setRegistryName(RedstoneElectronic.MODID, "redstone_motor");
            itemRegistryEvent.getRegistry().registerAll((IForgeRegistryEntry[])new Item[]{resistorBlockItem, rotarySelectorBlockItem, rotaryDistributorBlockItem, stickItem});
        }
    }
}

