# Lesson 2 - Building Our First Block

We're going to start adding our first piece of code to Minecraft: a custom block. It'll be a simple one - it won't have any texture or custom functionality yet - but creating it will teach us a lot about how Minecraft mods work. And in the next lesson we'll actually add it to the game :)

## Note: to get the project folder in the state it will be in at the end of this lesson, [visit this link.](https://github.com/KiaFarhang/minecraft-modding/tree/lesson-2/code) ##

## Creating a block class

First we need to create a file to contain our custom block code. In Java, most code lives in _classes_. A [Java class](https://docs.oracle.com/javase/tutorial/java/concepts/class.html) is basically a blueprint for an object. We create _instances_ of classes that can have different properties.

Sound confusing? An example might help.

## Java classes - the basics

Let's say you and your friend both have bikes. Your bike is red and her bike is blue. Your bike has 3 speeds; your friend's bike has 5. They're both bikes, but they have different properties.

Here's how that would look in Java code:

```java
    public class Bike {
        private String color;
        private int speeds;

        public Bike(String color, int speeds) {
            this.color = color;
            this.speeds = speeds;
        }
    }
```

This would be the bike _class_. It's the blueprint every bike is built off of. We can create as many bikes from this blueprint as we like - so let's make your bike and your friend's.

```java
    Bike yourBike = new Bike("Red", 3); // Your bike is red with three speeds
    Bike yourFriendsBike = new Bike("Blue", 5); // Your friend's bike is blue with 5 speeds
```

## Back to the block

So what does this have to do with Minecraft?

Well, we need to create a class to represent our block. This class will let us give our block properties - like the type of material it's made out of - just like our bike class above let us create bikes with different colors and speeds.

We're going to start really simple at first and create a block that's always made outt of Rock - but this is something we can customize later on.

In Eclipse, right click on the `com.example.examplemod` package where our `ExampleMod.java` file lives. After right clicking, choose `New -> Java Class`. The only thing we need to fill out in the form that pops up is the name of our block class. For this tutorial, let's name it `BasicBlock`.

This will create a skeleton for our class that looks like this:

```java
package com.example.examplemod;

public class BasicBlock {

}
```

Our block doesn't do anything yet, so we need to fill it out with some more detail. First, let's _import_ some Minecraft code we're going to use in our block:

```java
package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock {

}
```

Check it out - Minecraft already has a Block class. That's great, because we're going to have our class _extend_ that one. When we extend a class in Java, our class gets everything that class already has - all its properties, all its methods (the things it can do), etc. That's really useful for this case - the Minecraft `Block` class already has _tons_ of code we need, so rather than write that code ourselves we can just make our block extend it.

To extend a class, you just say what you're extending when you declare the class's name. So we'd change our code like this:

```java
package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block{

}
```

Tada! That's all it takes. A quick note here - in Java you can only extend a single class. We couldn't do something like:

```java
/**
* Doesn't work - can't extend multiple classes.
*/
public class BasicBlock extends Block, OtherBlock{

}
```

We're almost done with our block class, just need one more piece: a constructor. In Java, a constructor is what you use to create an instance of your class. So when we wrote this example code earlier:

```java
    Bike yourBike = new Bike("Red", 3); // Your bike is red with three speeds
    Bike yourFriendsBike = new Bike("Blue", 5); // Your friend's bike is blue with 5 speeds
```

We were calling the _constructor_ on the bike class twice: once to create `yourBike` and the other time to create `yourFriendsBike`. In Java, we typically use the `new` keyword to use a constructor.

Let's update our class to give it a constructor:

```java
package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block{
    public BasicBlock () {
		super(Block.Properties.create(Material.ROCK));
	}
}
```

All our constructor does is call `super` - which means it's calling the constructor of _its parent class_. (In this case, the Minecraft `Block` class). In plain English, when we construct an instance of our `BasicBlock` class, all it does is go "Okay, I'm going to construct a `Block` (my parent) and pass in the rock material."

So now we have a basic rock block we can add to our game. Awesome! But now the question is - how do we actually _get it_ into our game? Well, that's where lesson 3 picks up. :)