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
package com.openDLX.gui.command.systemLevel;

import java.awt.Cursor;
import java.io.File;
import javax.swing.JOptionPane;
 import com.openDLX.gui.MainFrame;
 import com.openDLX.gui.command.Command;
 import com.openDLX.gui.dialog.ConfigFileChooser;

public class CommandOpenConfigFile implements Command
{

    private MainFrame mf;
    private File configFile = null; //out

    public CommandOpenConfigFile(MainFrame mf)
    {
        this.mf = mf;
    }

    @Override
    public void execute()
    {
        try
        {
            mf.getContentPane().setCursor(
                    Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            ConfigFileChooser f = new ConfigFileChooser();
            // in case of "cancel" codeFile is null
            configFile = f.chooseFile();

        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            e.printStackTrace();
            JOptionPane.showMessageDialog(mf, "Opening file failed / getting file path failed");
        }
        finally
        {
            mf.getContentPane().setCursor(
                    Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public File getConfigFile()
    {
        return configFile;
    }

}
