package com.teammoeg.thermopolium.data.recipes.numbers;

import java.util.stream.Stream;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.teammoeg.thermopolium.data.recipes.StewNumber;
import com.teammoeg.thermopolium.data.recipes.StewPendingContext;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ConstNumber implements StewNumber {
	float n;
	public ConstNumber(JsonElement num) {
		if(num.isJsonPrimitive())
			n=num.getAsFloat();
		else
			n=num.getAsJsonObject().get("num").getAsFloat();
	}

	public ConstNumber(float n) {
		super();
		this.n = n;
	}

	@Override
	public Float apply(StewPendingContext t) {
		return n;
	}

	@Override
	public boolean fits(ItemStack stack) {
		return false;
	}

	@Override
	public boolean fits(ResourceLocation type) {
		return false;
	}

	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(n);
	}
	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeFloat(n);
	}

	public ConstNumber(PacketBuffer buffer) {
		n=buffer.readFloat();
	}

	@Override
	public String getType() {
		return "const";
	}

	@Override
	public Stream<StewNumber> getItemRelated() {
		return Stream.empty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(n);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstNumber other = (ConstNumber) obj;
		if (Float.floatToIntBits(n) != Float.floatToIntBits(other.n))
			return false;
		return true;
	}

}
