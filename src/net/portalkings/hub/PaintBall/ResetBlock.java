package net.portalkings.hub.PaintBall;

import org.bukkit.block.Block;

public class ResetBlock implements Runnable {
	Block block;
	public ResetBlock(Block block){
		super();
		this.block = block;
	}
	@Override
	public void run() {
		block.setData((byte) 0);
	}

}
