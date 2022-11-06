package io.github.codetoil.redstoneelectronic;

import io.github.codetoil.redstoneelectronic.block.MotorBlock;
import io.github.codetoil.redstoneelectronic.block.RedstoneRotaryDistributor;
import io.github.codetoil.redstoneelectronic.block.RedstoneRotarySelector;
import io.github.codetoil.redstoneelectronic.block.ResistorBlock;
import io.github.codetoil.redstoneelectronic.block.StickBlock;
import io.github.codetoil.redstoneelectronic.properties.REProperties;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value="redstoneelectronics")
public class RedstoneElectronics {
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

    public RedstoneElectronics() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        REProperties.init();
    }

    private void setup(FMLCommonSetupEvent event) {
    }

    private void doClientStuff(FMLClientSetupEvent event) {
    }

    private void enqueueIMC(InterModEnqueueEvent event) {
    }

    private void processIMC(InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(RegistryEvent.Register<Block> blockRegistryEvent) {
            LOGGER.info("HELLO from Register Block");
            resistorBlock = (Block)new ResistorBlock(Block.Properties.of(Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)).setRegistryName(RedstoneElectronics.MODID, "redstone_resistor");
            rotarySelectorBlock = (Block)new RedstoneRotarySelector(Block.Properties.of(Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)).setRegistryName(RedstoneElectronics.MODID, "redstone_rotary_selector");
            rotaryDistributorBlock = (Block)new RedstoneRotaryDistributor(Block.Properties.of((Material)Material.DECORATION).strength(0.0f).sound(SoundType.WOOD)).setRegistryName(RedstoneElectronics.MODID, "redstone_rotary_distributer");
            stickBlock = (Block)new StickBlock(Block.Properties.of(Material.WOOD).strength(2.0f).sound(SoundType.WOOD)).setRegistryName("minecraft", "stick");
            motorBlock = new MotorBlock(Block.Properties.of(Material.PISTON).strength(0.5f));
            blockRegistryEvent.getRegistry().registerAll(resistorBlock, rotarySelectorBlock, rotaryDistributorBlock, stickBlock);
        }

        @SubscribeEvent
        public static void onItemRegistry(RegistryEvent.Register<Item> itemRegistryEvent) {
            resistorBlockItem = new BlockItem(resistorBlock, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName(RedstoneElectronics.MODID, "redstone_resistor");
            rotarySelectorBlockItem = new BlockItem(rotarySelectorBlock, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName(RedstoneElectronics.MODID, "redstone_rotary_selector");
            rotaryDistributorBlockItem = new BlockItem(rotaryDistributorBlock, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName(RedstoneElectronics.MODID, "redstone_rotary_distributer");
            stickItem = new BlockItem(stickBlock, new Item.Properties().tab(ItemGroup.TAB_MATERIALS)).setRegistryName("minecraft", "stick");
            motorItem = new BlockItem(motorBlock, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName(RedstoneElectronics.MODID, "redstone_motor");
            itemRegistryEvent.getRegistry().registerAll(resistorBlockItem, rotarySelectorBlockItem, rotaryDistributorBlockItem, stickItem);
        }
    }
}

