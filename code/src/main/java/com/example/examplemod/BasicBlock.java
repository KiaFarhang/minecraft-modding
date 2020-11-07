package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block{
    public BasicBlock () {
		super(Block.Properties.create(Material.ROCK));		
	}
}