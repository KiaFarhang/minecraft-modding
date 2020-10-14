# Lesson 1 - Getting Started

Before we start doing all the cool stuff we want in our mod, we need to name the mod and register it with Minecraft. We'll do that in two places - our `mods.toml` file (text) and our `ExampleMod.java` class (Java).

##Note: to get the project folder in the state it will be in at the end of this lesson, [visit this link.](https://github.com/KiaFarhang/minecraft-modding/tree/lesson-1/code)##

## Updating mods.toml

Let's start with `mods.toml`. On the left-hand side of Eclipse (the package explorer), expand your project folder, then expand `src/main/resources/META-INF` and open `mods.toml`.

This file contains information about all the mods available to Minecraft. It's written in [the TOML file format,](https://github.com/toml-lang/toml) which looks a little strange at first. But don't worry, we only need to change a few things.

Scroll down to line 14, where you'll see something like this:

```
# The modid of the mod
modId="examplemod" #mandatory
```

The first line there is a _comment_ because it starts with a `#` sign. That means Minecraft/the computer never reads it; it's just for humans to see as a way of providing more information.

Right now our mod's ID is `examplemod`. Let's change it to something like `dojomod`:

```
# The modid of the mod
modId="dojomod" #mandatory
```

Next, on line 18, let's change our mod's display name. It starts out like this:

```
 # A display name for the mod
displayName="Example Mod" #mandatory
```

And let's change it to this:

```
 # A display name for the mod
displayName="CoderDojo Mod" #mandatory
```

Next, let's update the credits and authors of the mod:

```
# A text field displayed in the mod UI
credits="Built in CoderDojo" #optional
# A text field displayed in the mod UI
authors="Kia Farhang" #optional
```

(Obviously, you can put _your_ name in the author section, not mine.)

Let's change the mod's description, too. This begins on line 30 in the file:

```
# The description text for the mod (multi line!) (#mandatory)
description='''
This is a long form description of the mod. You can write whatever you want here

Have some lorem ipsum.

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed mollis lacinia magna. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed sagittis luctus odio eu tempus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque volutpat ligula eget lacus auctor sagittis. In hac habitasse platea dictumst. Nunc gravida elit vitae sem vehicula efficitur. Donec mattis ipsum et arcu lobortis, eleifend sagittis sem rutrum. Cras pharetra quam eget posuere fermentum. Sed id tincidunt justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
'''
```

Keep the `'''` at the beginning and the end, but delete everything else and fill in with your own description. Mine looks like this:

```
# The description text for the mod (multi line!) (#mandatory)
description='''
This is a mod built in CoderDojo's Minecraft modding class. Hi mom!
'''
```

Lastly, since we changed the ID of the mod, we need to update the _dependencies_ listed in this file. Dependencies are what our mod needs to run. This mod only needs Forge and Minecraft itself. You'll see this starting on line 34:

```
# A dependency - use the . to indicate dependency for a specific modid. Dependencies are optional.
[[dependencies.examplemod]] #optional
    # the modid of the dependency
    modId="forge" #mandatory
    # Does this dependency have to exist - if not, ordering below must be specified
    mandatory=true #mandatory
    # The version range of the dependency
    versionRange="[31,)" #mandatory
    # An ordering relationship for the dependency - BEFORE or AFTER required if the relationship is not mandatory
    ordering="NONE"
    # Side this dependency is applied on - BOTH, CLIENT or SERVER
    side="BOTH"
# Here's another dependency
[[dependencies.examplemod]]
    modId="minecraft"
    mandatory=true
    versionRange="[1.15.2]"
    ordering="NONE"
    side="BOTH"
```

Be sure to change both of the `[[dependencies.examplemod]]` to `[[dependencies.dojomod]]`.

These are all the changes we need to make to the `mods.toml` file. Next we'll change our Java code to point to our new mod ID, so the changes we made show up in the mod list!

## Updating the Java code

Since we changed the ID of our mod, we need to update our mod's Java code to point to that new ID. In Eclipse, open up `src/main/java/com.example.examplemod.ExampleMod.java`. This is the base Java class for the example mod Forge comes with. We're going to change this file and make our own Java files/classes later, but right now we just need to make one quick tweak.

Starting on line 21, you should see this:

```java
// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod
{
```

See where it says `examplemod`? Well, that's no longer the ID of our mod, so we need to update it to match what we put in `mods.toml` earlier. Since we set our mod ID to `dojomod`, let's change the Java code to look like this:

```java
// The value here should match an entry in the META-INF/mods.toml file
@Mod("dojomod")
public class ExampleMod
{
```

Save this file and use the `runClient` task to run Minecraft. Once it opens, choose the `Mods` option on the main menu. You should see your mod name in the list. When you click on it, you'll see you're listed as the author, and your description shows up as well.

Congratulations, you just started your first Minecraft mod! Now, let's make it do some stuff :)

## Troubleshooting

**mods.toml missing metadata for modid {some mod ID here}**

This usually happens when we have a mismatch of mod IDs somewhere between the Java file and `mods.toml`. Check all the places we updated above and make sure you use the same mod ID throughout.