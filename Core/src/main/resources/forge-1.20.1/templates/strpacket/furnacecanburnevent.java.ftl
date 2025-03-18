<#-- @formatter:off -->
<#include "../procedures.java.ftl">
<#include "../triggers.java.ftl">

package ${package}.event;

public class FurnaceCanBurnEvent extends Event {
	private final ItemStack item;
	private final ItemStack fuel;
	private final int litTime;

	public FurnaceCanBurnEvent(ItemStack item, ItemStack fuel,int litTime) {
		this.fuel = fuel;
		this.item = item;
		this.litTime = litTime;
	}

	public ItemStack getFuel() {
		return fuel;
	}

	public ItemStack getItem() {
		return item;
	}

	public int getLitTime() {
		return litTime;
	}

	@Override
	public boolean isCancelable() {
		return true;
	}
}