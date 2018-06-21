package io.anuke.mindustry.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.badlogic.gdx.utils.ObjectIntMap;
import io.anuke.mindustry.content.blocks.Blocks;

public class ColorMapper{
	/**maps color IDs to their actual RGBA8888 colors*/
	private static int[] colorIDS;
	/**Maps RGBA8888 colors to pair IDs.*/
	private static IntIntMap reverseIDs = new IntIntMap();

	private static ObjectIntMap<Block> reverseColors = new ObjectIntMap<>();
	private static Array<BlockPair> pairs = new Array<>();
	private static IntMap<BlockPair> colors;

	private static void init(){
		if(colors != null) return;

		colors = map(
			"323232", pair(Blocks.stone),
			"50965a", pair(Blocks.grass),
			"506eb4", pair(Blocks.water),
			"465a96", pair(Blocks.deepwater),
			"252525", pair(Blocks.blackstone),
			"988a67", pair(Blocks.sand),
			"c2d1d2", pair(Blocks.snow),
			"c4e3e7", pair(Blocks.ice),
			"6e501e", pair(Blocks.dirt),
			"ed5334", pair(Blocks.lava),
			"292929", pair(Blocks.oil),
			"c3a490", pair(Blocks.iron),
			"161616", pair(Blocks.coal),
			"6277bc", pair(Blocks.titanium),
			"c594dc", pair(Blocks.thorium),
			"9790b5", pair(Blocks.lead),
			"000000", pair(Blocks.space)
		);
	}
	
	public static BlockPair get(int color){
		init();
		return colors.get(color);
	}
	
	public static Array<BlockPair> getPairs(){
		init();
		return pairs;
	}
	
	public static int getColor(Block block){
		init();
		return reverseColors.get(block, 0);
	}
	
	private static BlockPair pair(Block floor, Block wall){
		return new BlockPair(floor, wall);
	}
	
	private static BlockPair pair(Block floor){
		return new BlockPair(floor, Blocks.air);
	}
	
	private static IntMap<BlockPair> map(Object...objects){
		colorIDS = new int[objects.length/2];
		IntMap<BlockPair> colors = new IntMap<>();
		for(int i = 0; i < objects.length/2; i ++){
			int color = Color.rgba8888(Color.valueOf((String)objects[i*2]));
			colors.put(color, (BlockPair)objects[i*2+1]);
			pairs.add((BlockPair)objects[i*2+1]);
			colorIDS[i] = color;
			reverseIDs.put(color, i);
		}
		for(Entry<BlockPair> e : colors.entries()){
			reverseColors.put(e.value.wall == Blocks.air ? e.value.floor : e.value.wall, e.key);
		}
		return colors;
	}
	
	public static class BlockPair{
		public final Block floor, wall;
		
		public Block dominant(){
			return wall == Blocks.air ? floor : wall;
		}
		
		private BlockPair(Block floor, Block wall){
			this.floor = floor;
			this.wall = wall;
		}
	}
}
