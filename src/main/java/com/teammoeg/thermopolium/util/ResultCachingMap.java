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

package com.teammoeg.thermopolium.util;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.function.Function;

import com.teammoeg.thermopolium.data.recipes.ComplexCalculated;

public class ResultCachingMap<T, U> extends HashMap<T, U> {
	private static final long serialVersionUID = 1L;
	final Function<? super T, ? extends U> mapper;

	public ResultCachingMap(Function<? super T, ? extends U> mapper) {
		super();
		this.mapper = mapper;
	}

	// try high performance method first,if causing trouble use lower one.
	public U compute(T sn) {
		if (sn instanceof ComplexCalculated)
			return secureCompute(sn);
		try {
			return computeIfAbsent(sn, mapper);
		} catch (ConcurrentModificationException cme) {
			return secureCompute(sn);
		}
	}

	// Lower performance, prevents CME
	private U secureCompute(T sn) {
		U f = get(sn);
		if (f != null)
			return f;
		f = mapper.apply(sn);
		put(sn, f);
		return f;
	}
}