/* CommandProperties.java | A collection of properties, such as
 * permissions, that may be unique for any command.
 * Copyright (C) 2014 Zach Ohara
 *
 * This file is part of the Chezburgr's Essentials Plugin for Bukkit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.chezburgr.dataform;

public class CommandProperties {

	public int argsMin;
	public int argsMax;
	public boolean limited;
	public boolean args;
	public boolean useTarget;
	public boolean restrictTarget;

	public CommandProperties(boolean limited, int argsMin, int argsMax, boolean useTarget, boolean restrictTarget) {
		this.argsMin = argsMin;
		this.argsMax = argsMax;
		this.limited = limited;
		this.useTarget = useTarget;
		this.restrictTarget = restrictTarget;
		if (argsMin == 0 && argsMax == 0) {
			this.args = false;
		} else {
			this.args = true;
		}
	}
}
