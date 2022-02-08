/*
 * Copyright (c) 2022 TeamMoeg
 *
 * This file is part of Thermopolium.
 *
 * Thermopolium is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Thermopolium is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thermopolium. If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.thermopolium.data.recipes.numbers;

import java.util.stream.Stream;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.teammoeg.thermopolium.data.TranslationProvider;
import com.teammoeg.thermopolium.data.recipes.StewNumber;
import com.teammoeg.thermopolium.data.recipes.StewPendingContext;
import com.teammoeg.thermopolium.util.FloatemTagStack;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ItemTag implements StewNumber {

	ResourceLocation tag;

	public ItemTag(JsonElement jo) {
		if (jo.isJsonObject())
			tag = new ResourceLocation(jo.getAsJsonObject().get("tag").getAsString());
		else
			tag = new ResourceLocation(jo.getAsString());
	}

	public ItemTag(ResourceLocation tag) {
		super();
		this.tag = tag;
	}

	@Override
	public Float apply(StewPendingContext t) {
		return t.getOfType(tag);
	}

	@Override
	public boolean fits(FloatemTagStack stack) {
		return stack.getTags().contains(tag);
	}

	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(tag.toString());
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeResourceLocation(tag);
	}

	public ItemTag(PacketBuffer buffer) {
		tag = buffer.readResourceLocation();
	}

	@Override
	public String getType() {
		return "tag";
	}

	@Override
	public Stream<StewNumber> getItemRelated() {
		return Stream.of(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ItemTag))
			return false;
		ItemTag other = (ItemTag) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	@Override
	public Stream<ResourceLocation> getTags() {
		return Stream.of(tag);
	}

	@Override
	public String getTranslation(TranslationProvider p) {
		return p.getTranslation("tag."+this.tag.toString().replaceAll("[:/]","."));
	}
}
