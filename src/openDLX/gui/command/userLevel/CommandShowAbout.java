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

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import openDLX.config.GlobalConfig;
import openDLX.gui.MainFrame;
import openDLX.gui.command.Command;

public class CommandShowAbout implements Command
{

    @Override
    public void execute()
    {
    	URL imageURL = CommandShowAbout.class.getResource("/openDLX/img/openDLX_small.png");
    	
    	System.out.println("Image URL is: " + imageURL);
    	// FIXME icon is not loaded correctly
//    	final ImageIcon icon = new ImageIcon(imageURL, "Icon");    	    	
    	final ImageIcon icon = new ImageIcon(getClass().getResource("/openDLX/img/openDLX_small.png"), "Icon");    	    	
    	System.out.println("Image Size: " + icon.getIconHeight() + "x" + icon.getIconWidth() + " -> " + icon.getDescription());
    	JOptionPane.showMessageDialog(MainFrame.getInstance(), GlobalConfig.ABOUT, "About", JOptionPane.INFORMATION_MESSAGE, icon);
    }

}

