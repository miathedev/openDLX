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

import java.io.File;
import openDLX.gui.MainFrame;
import openDLX.gui.command.Command;
import openDLX.gui.command.systemLevel.CommandResetSimulator;
import openDLX.gui.command.systemLevel.CommandSaveFrameConfigurationSysLevel;
import openDLX.gui.command.systemLevel.CommandStartExecuting;

public class CommandResetCurrentProgram implements Command
{

    private MainFrame mf;

    public CommandResetCurrentProgram(MainFrame mf)
    {
        this.mf = mf;
    }

    @Override
    public void execute()
    {
        if (mf.isExecuting())
        {
            File configFile = new File(mf.getConfigFile().getAbsolutePath());
            //save current window position
            CommandSaveFrameConfigurationSysLevel c11 = new CommandSaveFrameConfigurationSysLevel(mf);
            c11.execute();
            //delete old openDLX and clean/remove all frames
            CommandResetSimulator c10 = new CommandResetSimulator(mf);
            c10.execute();
            //initialize openDLX and create internal frames, set status to executing
            CommandStartExecuting c7 = new CommandStartExecuting(mf, configFile);
            c7.execute();
        }
    }

}
