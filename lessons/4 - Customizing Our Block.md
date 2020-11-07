# Lesson 4 - Customizing our Block

Okay, so we've added an ugly, nameless block to Minecraft. Not a bad start, but in this lesson we'll touch up our block and make it a bit more presentable. There won't be as much Java code in this lesson - instead, we'll learn about how Minecraft loads assets (things like textures) and a very popular file format in programming known as JSON.

## Creating the asset folder structure

Forge expects the assets for our mod to be in a very specific place - and if we put them anywhere else, it won't know where to find them. Most of the work today will be in the `src/main/resources` folder, whereas before we were working out of `src/main/java`. In Java projects (not just Minecraft), the `resources` folder is generally where you put non-code stuff like configuration files, etc.

First, create an `assets` folder inside `resources`, so we have `src/main/resources/assets`. Then, we need a folder inside `assets` with **The same name as our mod ID**. So remember in `ExampleMod.java` when we assed our mod ID around line 22?

```java
@Mod("dojomod")
public class ExampleMod
```

In this case, my mod ID is `dojomod`, so I would create a folder named `dojomod` in the `assets` folder we just created. Make sure your folder name matches the name of your mod ID.

## Getting our block's registry name

Minecraft uses a registry to keep track of all the blocks, items, etc. in the game. We gave our block a registry name in the last lesson, and we need it for this lesson too. The registry name is how we'll link the configuration we do to our block.

All the files we're going to create to house names, images, etc. can store that information for lots of items. So we'll use the registry name of our block to say "Hey, Minecraft - for this block, use this name. And use this image." Since our Java code uses the same registry name, Forge will know to link the right name and image to the right block.

Open up the block event handler we created in the last lesson. (Mine was called `BasicBlockEventHandler.java`). Look for the block of `static` code near the top:

```java
    static {
        basicBlock = new BasicBlock();
        basicBlock.setRegistryName("dojomod", "basic_block_registry_name");
    }
```

The block's registry name is whatever we passed as the second argument to that `setRegistryName` method. So in this case, the registry name is `basic_block_registry_name`, but yours might be different if you used a different name in the Java code.

Keep track of that registry name, as we'll use it in the next steps.

## Naming our block

First, let's give our block a proper name when it shows up in our inventory. Create a `lang` folder under your ID's folder, so the folder path looks like this:

`src/main/resources/assets/{your mod ID}/lang`

We need to create an English-language naming file - since Minecraft is running in English, that's the language file Forge will look for when loading our mod. This file **must** be named `en_us.json`. Create that file then fill it out with the following. Don't worry if this doesn't make sense - we'll explain in a second!

```json
{
	"block.dojomod.basic_block_registry_name": "Basic Block"
}
```

The part on the left is the path to your block's registry name. Since my mod ID is `dojomod` and my registry name from above is `basic_block_registry_name`, I wrote `block.dojomod.basic_block_registry_name`. But if you have a different mod ID and/or registry name, be sure to swap them out here.

The part on the right is the name I want to show up for the block in Minecraft. I called it "Basic Block", but you can feel free to customize that, too - just be sure it's in quotes. (We'll explain why in a second)

Before we get into what exactly we just did, boot up Minecraft and hover over your block in your inventory. You should see the name you entered when you hover over it. Congratulations, you just named your first Minecraft item!

## So....What is this weird file?

