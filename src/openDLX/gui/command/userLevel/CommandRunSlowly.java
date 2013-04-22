/*******************************************************************************
 * openDLX - A DLX/MIPS processor simulator.
 * Copyright (C) 2013 The openDLX project, University of Augsburg, Germany
 * Project URL: <http://sourceforge.net/projects/opendlx>
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
package openDLX.gui.command.userLevel;

import openDLX.gui.MainFrame;
import openDLX.gui.command.Command;
import openDLX.gui.command.systemLevel.ThreadCommandRunSlowly;
import openDLX.gui.dialog.Player;

public class CommandRunSlowly implements Command
{

    private MainFrame mf;

    public CommandRunSlowly(MainFrame mf)
    {
        this.mf = mf;
    }

    @Override
    public void execute()
    { //check if state is executing and check if the current openDLX has finished (when it has finished ->updates are no longer allowed)
        if (mf.isExecuting() && mf.isUpdateAllowed())
        {
            Thread t10 = new Thread(new ThreadCommandRunSlowly(mf));
            t10.start();
            Player p = new Player(mf);
        }
    }

}
