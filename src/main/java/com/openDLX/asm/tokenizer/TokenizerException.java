/*******************************************************************************
 * openDLX - A DLX/MIPS processor simulator.
 * Copyright (C) 2013 The openDLX project, University of Augsburg, Germany
 * Project URL: <https://sourceforge.net/projects/opendlx>
 * Development branch: <https://github.com/smetzlaff/openDLX>
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program, see <LICENSE>. If not, see
 * <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.openDLX.asm.tokenizer;

 import com.openDLX.asm.AssemblerException;

@SuppressWarnings("serial")
public class TokenizerException extends AssemblerException {
	private Position position_;

	public TokenizerException(String message, Position position) {
		super(message);
		this.position_ = position;
	}

	public String getMessage() {
		return super.getMessage() + " at" + position_;
	}

	public Position getPosition() {
		return position_;
	}
}