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
package com.openDLX.gui.command.userLevel;

import javax.swing.JOptionPane;

 import com.openDLX.OpenDLXSimulator;
 import com.openDLX.datatypes.uint32;
 import com.openDLX.datatypes.uint8;
 import com.openDLX.gui.MainFrame;
 import com.openDLX.gui.command.Command;
 import com.openDLX.gui.command.systemLevel.CommandUpdateFrames;
 import com.openDLX.gui.internalframes.util.ValueInput;

public class CommandChangeRegister implements Command
{

    private int row; //in
    private MainFrame mf;
    private OpenDLXSimulator openDLXSim;

    public CommandChangeRegister(int row)
    {
        this.row = row;
        this.mf = MainFrame.getInstance();
        openDLXSim = mf.getOpenDLXSim();
    }

    @Override
    public void execute()
    {
        if (mf.isExecuting())
        {
            try
            {
                Integer value = ValueInput.getValue("change register value: ",0);
                if (value != null)
                {
                    openDLXSim.getPipeline().getRegisterSet().write(new uint8(row), new uint32(value));
                    new CommandUpdateFrames(mf).execute();
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(mf, "for input only Integer - decimal or hex (0x...) allowed");
                execute();
            }
            catch (Exception e)
            {
                System.err.println(e.toString());
                e.printStackTrace();
                JOptionPane.showMessageDialog(mf, "Changing register failed");
            }
        }

    }

}
