package com.teammoeg.thermopolium.client;
import com.google.common.base.Preconditions;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import java.util.function.Consumer;
/**
 * Codes adapted from ImmersiveEngineering, fall under their license and open-sourced.
 * @author BluSunrize
 * */
public class TransformingVertexBuilder implements IVertexBuilder
{
	private final IVertexBuilder base;
	private final MatrixStack transform;
	ObjectData<Vector2f> uv = new ObjectData<>();
	ObjectData<Vector3d> pos = new ObjectData<>();
	ObjectData<Vec2i> overlay = new ObjectData<>();
	ObjectData<Vec2i> lightmap = new ObjectData<>();
	ObjectData<Vector3f> normal = new ObjectData<>();
	ObjectData<Vector4f> color = new ObjectData<>();

	public TransformingVertexBuilder(IVertexBuilder base, MatrixStack transform)
	{
		this.base = base;
		this.transform = transform;
	}
	@Nonnull
	@Override
	public IVertexBuilder color(int red, int green, int blue, int alpha)
	{
		color.putData(new Vector4f(red/255f, green/255f, blue/255f, alpha/255f));
		return this;
	}
	@Override
	public IVertexBuilder pos(double x, double y, double z) {
		pos.putData(new Vector3d(x, y, z));
		return this;
	}

	@Override
	public IVertexBuilder tex(float u, float v) {
		uv.putData(new Vector2f(u, v));
		return this;
	}

	@Override
	public IVertexBuilder overlay(int u, int v) {
		overlay.putData(new Vec2i(u, v));
		return this;
	}

	@Override
	public IVertexBuilder lightmap(int u, int v) {
		lightmap.putData(new Vec2i(u, v));
		return this;
	}
	@Nonnull
	@Override
	public IVertexBuilder normal(float x, float y, float z)
	{
		normal.putData(new Vector3f(x, y, z));
		return this;
	}

	@Override
	public void endVertex()
	{
		pos.ifPresent(pos -> base.pos(transform.getLast().getMatrix(), (float)pos.x, (float)pos.y, (float)pos.z));
		color.ifPresent(c -> base.color(c.getX(), c.getY(), c.getZ(), c.getW()));
		uv.ifPresent(uv -> base.tex(uv.x, uv.y));
		overlay.ifPresent(overlay -> base.overlay(overlay.x, overlay.y));
		lightmap.ifPresent(lightmap -> base.lightmap(lightmap.x, lightmap.y));
		normal.ifPresent(normal -> base.normal(transform.getLast().getNormal(), normal.getX(), normal.getY(), normal.getZ()));
		base.endVertex();
	}

	public void setLight(int light)
	{
		lightmap.setGlobal(new Vec2i(light&255, light >> 16));
	}

	public void setColor(float r, float g, float b, float a)
	{
		color.setGlobal(new Vector4f(r, g, b, a));
	}

	public void setNormal(float x, float y, float z)
	{
		Vector3f vec = new Vector3f(x, y, z);
		vec.normalize();
		normal.setGlobal(vec);
	}

	public void setOverlay(int packedOverlayIn)
	{
		overlay.setGlobal(new Vec2i(
				packedOverlayIn&0xffff,
				packedOverlayIn >> 16
		));
	}

	private static class Vec2i
	{
		final int x, y;

		private Vec2i(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}

	private static class ObjectData<T>
	{
		@Nullable
		T obj;
		boolean isGlobal;

		public void putData(T newVal)
		{
			Preconditions.checkState(obj==null);
			obj = newVal;
		}

		public void setGlobal(@Nullable T obj)
		{
			this.obj = obj;
			isGlobal = obj!=null;
		}

		public T read()
		{
			T ret = Preconditions.checkNotNull(obj);
			if(!isGlobal)
				obj = null;
			return ret;
		}

		public boolean hasValue()
		{
			return obj!=null;
		}

		public void ifPresent(Consumer<T> out)
		{
			if(hasValue())
				out.accept(read());
		}
	}


}