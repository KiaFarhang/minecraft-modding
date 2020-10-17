# Lesson 3 - Adding Our Block to Minecraft

In lesson 2 we created a really simple block class and learned a little bit about Java classes along the way. But right now our block class is just sitting in our project - it's not actually in our Minecraft game yet. This lesson will walk through registering the block with Minecraft so it's in our creative world inventory when we start the game.

## Note: to get the project folder in the state it will be in at the end of this lesson, [visit this link.](https://github.com/KiaFarhang/minecraft-modding/tree/lesson-3/code) ##

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

There's a lot going on here, so let's take it step by step. After the imports and the beginning of the class, we have this:

```java
    private static BasicBlock basicBlock;
    private static BlockItem basicBlockItem;
```

These are called _fields_ - they're objects that every `BasicBlockEventHandler` will have when we create one. The `static` in front of them means every handler will actually share the _same_ version of those objects. If that part sounds weird or complicated, don't worry - it's not something we need to know a great deal about right now.

So what this code is saying is that the `BasicBlockEventHandler` has a `BasicBlock` (called `basicBlock`) and a `BlockItem` (called `basicBlockItem`). We know what the `BasicBlock` is - it's the class we just created. But what's a `BlockItem`?

Well, you know how in Minecraft you have blocks out in the world, but then when you pick up a block it goes in your inventory? That second part is what a `BlockItem` is - it's the code for how the block works when it's in your inventory.

Basically, this code just says "Hey, I'm going to use the BasicBlock and its item version when I insert it into Minecraft. I'm just declaring those variables now."

Next there's this part:

```java
    static {
        basicBlock = new BasicBlock();
        basicBlock.setRegistryName("dojomod", "basic_block");
    }
```

This is more setup code. Code in a `static` block runs once, when Java first boots up. (So in our case, when Minecraft loads our mod) Here we just create a `BasicBlock` and assign it to our variable, then we set our block's _registry name_. We pass two arguments: the ID of our mod, and the name we're going to use to reference this block. So in your code, you'll want to replace `dojomod` with whatever ID you gave your mod. You can feel free to change the name of the second argument to something other than `basic_block` too - just remember what you choose, because we'll use it later.

## Making our event handler do stuff

Okay, so we have an event handler class that we can use to hook into Minecraft and add our custom block. Now, let's add code to do that!

First, we're going to add code that will run when Minecraft registers all the blocks in the game. We're going to have our code say "Hey, Minecraft, by the way - add this custom block I created to the game."

Here's what that code looks like. You'll want to add it right after your `static` block:

```java
    @SubscribeEvent
    public static void onBlocksRegistration(RegistryEvent.Register<Block> blockRegisterEvent) {
    	blockRegisterEvent.getRegistry().register(basicBlock);
    }
```

Okay - this looks scary, but let's take it piece by piece.

`@SubscribeEvent`

This is a Java _annotation_. It's something you can put on your code to give it new properties. The Minecraft Forge `@SubscribeEvent` annotation lets you tell Forge, "Hey, this code I'm annotating - I want it to subscribe to a Minecraft event, so it'll run when that event happens."

In our case, we're annotating our method so it can listen to the event when Minecraft registers every block in the game. That's the next part:

```java
    public static void onBlocksRegistration(RegistryEvent.Register<Block> blockRegisterEvent) {
    	blockRegisterEvent.getRegistry().register(basicBlock);
    }
```

This is our code that actually runs when the block registration happens. It's a _method_, which is just a fancy way of saying a piece of code you can call. (Methods are somewhat like functions, if you've worked with those in other programming languages).

The method takes one parameter - the event that's happening to register all blocks in the game - and it just additionally registers our custom block. That's this line:

```java
    	blockRegisterEvent.getRegistry().register(basicBlock);
```

Okay, now when Minecraft registers all the blocks in the game, our mod will kick in and register our custom one as well. Next, we need to put the block in the player's inventory:

```java
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
```

This is another piece of code that runs when an event triggers in the game. This time, the event is when Minecraft is registering all the _items_ instead of all the blocks.

The first few lines are actually similar to when we created our `BasicBlock` earlier. When we create a `BlockItem`, we have to pass it a `Block` and some `Properties` for the item. So we create those properties first:

```java
    	int MAX_STACK_SIZE = 20;
    	
    	Item.Properties itemProperties = new Item.Properties()
    										.maxStackSize(MAX_STACK_SIZE) // how many of the item you can hold in a single stack
    										.group(ItemGroup.BUILDING_BLOCKS); // where to group the item in the player's inventory
```

Then we create the `BlockItem`:

```java
    	basicBlockItem = new BlockItem(basicBlock, itemProperties);
```

Then we need to set the item's registry name, just like we did with the block earlier. But since the block already has a registry name, we can just use the one we created before:

```java
    	basicBlockItem.setRegistryName(basicBlock.getRegistryName());
```

Finally, just like with our block event code, we need to actually register our block item with the other items in the game:

```java
    	itemRegisterEvent.getRegistry().register(basicBlockItem);
```

Okay, we're just about done. Now it's time to actually add this new event handler to our mod.

## Adding the event handler to our mod

Open up the `ExampleMod` class and look for this part of the code, which starts around line 28:

```java
    public ExampleMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
```

Remember constructors? This is the constructor for our mod class - it runs when Java first boots up our mod. The helpful comments here explain what's going on. Basically, this code is just registering the other methods in this class to run when certain events occur.

Since we're registering a whole class and not just a method, this will look a bit different. But here's the only thing we'll have to add to get our block into Minecraft:

```java
        // Register our block event handler
        FMLJavaModLoadingContext.get().getModEventBus().register(BasicBlockEventHandler.class);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
```

(The second comment + line should already be there; no need to add it.)

Once that's done, use the `runClient` command to open up Minecraft. Start a creative mode world, and check the very bottom of your block inventory. If you see see a black and purple square, you did it - you added a block to Minecraft in your very first mod!

Try placing your block in the world. It looks...glitchy, right? And pretty ugly?

Well, that's because we still need to give our block a real name. We still need to give it a texture, so it looks like a real block. There's a lot more customization we need to do.

So let's jump to lesson 4 to get started!