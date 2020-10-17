package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BasicBlockEventHandler {
    private static BasicBlock basicBlock;
    private static BlockItem basicBlockItem;
    
    static {
        basicBlock = new BasicBlock();
        basicBlock.setRegistryName("dojomod", "basic_block_registry_name");
    }
    
    @SubscribeEvent
    public static void onBlocksRegistration(RegistryEvent.Register<Block> blockRegisterEvent) {
    	blockRegisterEvent.getRegistry().register(basicBlock);
    }
    
    @SubscribeEvent
    public static void onItemsRegistration(RegistryEvent.Register<Item> itemRegisterEvent) {
    	int MAX_STACK_SIZE = 20;
    	
    	Item.Properties itemProperties = new Item.Properties()
    										.maxStackSize(MAX_STACK_SIZE)
    										.group(ItemGroup.BUILDING_BLOCKS);
    	basicBlockItem = new BlockItem(basicBlock, itemProperties);
    	basicBlockItem.setRegistryName(basicBlock.getRegistryName());
    	itemRegisterEvent.getRegistry().register(basicBlockItem);
    }
}