# Lesson 3 - Adding Our Block to Minecraft

In lesson 2 we created a really simple block class and learned a little bit about Java classes along the way. But right now our block class is just sitting in our project - it's not actually in our Minecraft game yet. This lesson will walk through registering the block with Minecraft so it's in our creative world inventory when we start the game.

## The Minecraft event system

Minecraft (and lots of other games, and lots of other software) works on an _event system_. When you boot the game up, and while you're playing, events are constantly happening: the player is moving. An enemy is spawning. Etc. etc.

A lot of Minecraft modding involves hooking into events the game already does naturally, and adding our own code to run as part of that event. 

For example, when Minecraft first starts loading there's an event that handles block registration. All of the blocks in the game code get loaded and added to the game.

We're going to hook into that event and say "Hey, Minecraft. When you're registering all the blocks, can you _also_ register the custom block we wrote?" That way Minecraft will know about our block.

We're going to create another class to handle all this event stuff: an event handler.

## Creating an event handler for our block

Let's create a new class called `BasicBlockEventHandler`. We'll start by pulling in all the code we need from Minecraft, then we're going to add a little magic we'll explain:

```java
package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class BasicBlockEventHandler {
    private static BasicBlock basicBlock;
    private static BlockItem basicBlockItem;
    
    static {
        basicBlock = new BasicBlock();
        basicBlock.setRegistryName("dojomod", "basic_block_registry_name");
    }
}
```

