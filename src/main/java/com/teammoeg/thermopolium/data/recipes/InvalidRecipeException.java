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

package com.teammoeg.thermopolium.data.recipes;

public class InvalidRecipeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRecipeException() {
	}

	public InvalidRecipeException(String message) {
		super(message);
	}

	public InvalidRecipeException(Throwable cause) {
		super(cause);
	}

	public InvalidRecipeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRecipeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
