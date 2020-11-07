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

We just created a **JSON file**. JSON (pronounced like the name, "Jason", or "Jay-sahn") is a file format used a lot in programming. It uses something known as *key-value pairs* to track data.

It might be easier to explain with an example. Let's say I wanted to keep track of some information about myself in JSON. I'm going to list my name, age and occupation. Here's what that would look like:

```json
{
    "name": "Kia",
    "age": 28,
    "occupation": "Software Developer"
}
```

There are a few rules to keep in mind about JSON:

1. Objects always start with a `{` and end with a `}`.
2. Property names (like name, age, occupation) are wrapped in quotation marks.
3. Values (like "Kia", 28 and "Software Developer") _can_ be wrapped in quotes, but not always. For example, 28 is a number so it doesn't need to be quoted.
4. There's always a `:` between the name of a property and its value for that object.
5. You use commas (`,`) to separate each property - and each object, if there are more than one.

JSON can get a bit more complicated, but this is just about all we need to know for our current modding. Let's take another look at the file we just created:

```json
{
	"block.dojomod.basic_block_registry_name": "Basic Block"
}
```

Really, this just associates the _property_ `block.dojomod.basic_block_registry_name` to the _value_ "Basic Block". Forge knows to look for that property because of the registry name we used in our Java code. Of course, you could change "Basic Block" to a number like `45` if you want - then if you booted the game your block would be named `45`.

Okay - so we named our block. Next, let's change how it looks.

## Creating a block state

A block state is extra information about a Minecraft block, like how it behaves or looks. We're going to define a block state for our custom block using a JSON file so we can tell Minecraft what it should look like when we load it into the game.

Create a new `blockstates` folder under `assets/{your mod ID}` - so this folder would be right _next to_ the `lang` folder we just created. We need to put a JSON file in the `blockstates` folder that matches the name of our block's registry name. Since mine was `basic_block_registry_name`, I created `basic_block_registry_name.json` - be sure to name yours appropriately.

Here's what I put in that file:

```json
{
    "variants": {
        "": { "model": "dojomod:block/basic_block_model" }
    }
}
```

We won't spend a ton of time on this file. It basically says, "Hey Minecraft, the _model_ for this block state is under the dojomod's block folder, and its name is basic_block_model." Forge will read that and load the model file with that name in that folder to know what to make the block look like.

Of course, note that I said my model was in the `dojomod`. You'll need to change that to match your mod ID if it's different.

Well, we don't actually _have_ that file yet, do we? That's our next step.

## Creating a block model

Next, create a `models` folder next to the `lang` and `blockstates` folders. Create a `block` folder inside the `models` folder. Add a JSON file with a name matching whatever name you put in the blockstate folder - I created `basic_block_model.json`:

```json
{
    "parent": "block/cube",
    "textures": {
        "down": "dojomod:block/coin",
        "up": "dojomod:block/coin",
        "north": "dojomod:block/coin",
        "east": "dojomod:block/coin",
        "south": "dojomod:block/coin",
        "west": "dojomod:block/coin",
        "particle": "block/lapis_block"
    }
}
```

The first part just says our model's "parent" (what it's based off) is the basic Minecraft cube shape. Next, we create a texture for each side of the block - that's the `down`, `up`, `north`, etc. part. Note that each side is pointing to `dojomod:block/coin`. That's telling Minecraft, "Go in the `dojomod`, look for the `textures/block` folder and load a file called `coin`." (Don't worry - we'll add that file in a second) Again, be sure to change `dojomod` to your mod's ID.

The last line, `particle`, is just telling Minecraft what particle effect to use when we break the block apart. We're using the built-in Minecraft Lapiz Lazuli block's particle effect.

You can probably guess here we could actually use _different_ textures for different sides of our block - say, give it one image on the east side and another on the west. We won't do that in this lesson, but it's definitely worth playing around with if you have time.

Last step - we need to actually add that `coin` texture file.

## Adding our block's texture

