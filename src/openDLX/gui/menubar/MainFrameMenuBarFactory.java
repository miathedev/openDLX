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
package openDLX.gui.menubar;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.*;
import openDLX.gui.LookAndFeel.LookAndFeelStrategyJava;
import openDLX.gui.LookAndFeel.LookAndFeelStrategySystemMonoSpaced;
import openDLX.gui.MainFrame;
import openDLX.gui.Preference;
import openDLX.gui.command.EventCommandLookUp;
import openDLX.gui.command.userLevel.*;
import openDLX.gui.internalframes.concreteframes.ClockCycleFrame;
import openDLX.gui.internalframes.concreteframes.CodeFrame;
import openDLX.gui.internalframes.concreteframes.editor.EditorFrame;
import openDLX.gui.internalframes.concreteframes.LogFrame;
import openDLX.gui.internalframes.concreteframes.MemoryFrame;
import openDLX.gui.internalframes.concreteframes.RegisterFrame;
import openDLX.gui.internalframes.concreteframes.StatisticsFrame;
import openDLX.gui.internalframes.factories.InternalFrameFactory;

public class MainFrameMenuBarFactory extends JMenuBarFactory
{

    MainFrame mf;

    public MainFrameMenuBarFactory(ActionListener al, ItemListener il, MainFrame mf)
    {
        super(al, il);
        this.mf = mf;
    }

    @Override
    public JMenuBar createJMenuBar()
    {
        JMenu fileMenu = new JMenu("File");
        JMenu simulatorMenu = new JMenu("Simulator");
        JMenu windowMenu = new JMenu("Window");
        JMenu helpMenu = new JMenu("Help");

        jmb.add(fileMenu);
        jmb.add(simulatorMenu);
        jmb.add(windowMenu);
        jmb.add(helpMenu);

        //if  parameter command = null, comand is not yet implemented and should be implemented soon 
        EventCommandLookUp.put(addMenuItem(fileMenu, "Open", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK), StateValidator.executingOrLazyStates).hashCode(), new CommandLoadFile(mf));
        EventCommandLookUp.put(addMenuItem(fileMenu, "Open and assemble", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK), StateValidator.executingOrLazyStates).hashCode(), new CommandLoadAndRunFile(mf));
        EventCommandLookUp.put(addMenuItem(fileMenu, "Add code", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.Event.CTRL_MASK), StateValidator.executingOrLazyStates).hashCode(), new CommandLoadFileBelow(mf));
        EventCommandLookUp.put(addMenuItem(fileMenu, "Save", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK), StateValidator.executingOrLazyStates).hashCode(), new CommandSave());
        EventCommandLookUp.put(addMenuItem(fileMenu, "Run from configuration file", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.ALT_MASK), StateValidator.executingOrLazyStates).hashCode(), new CommandRunFromConfigurationFile(mf));
        EventCommandLookUp.put(addMenuItem(fileMenu, "Exit program", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.Event.ALT_MASK), StateValidator.allStates).hashCode(), new CommandExitProgram(mf));

        EventCommandLookUp.put(addMenuItem(simulatorMenu, "Run program", KeyStroke.getKeyStroke("F5"), StateValidator.executingStates).hashCode(), new CommandRun(mf));
        EventCommandLookUp.put(addMenuItem(simulatorMenu, "Run program slowly", KeyStroke.getKeyStroke("F6"), StateValidator.executingStates).hashCode(), new CommandRunSlowly(mf));
        EventCommandLookUp.put(addMenuItem(simulatorMenu, "Stop running", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.Event.CTRL_MASK), StateValidator.RunningStates).hashCode(), new CommandStopRunning(mf));
        simulatorMenu.addSeparator();
        EventCommandLookUp.put(addMenuItem(simulatorMenu, "Do cycle", KeyStroke.getKeyStroke("F7"), StateValidator.executingStates).hashCode(), new CommandDoCycle(mf));
        EventCommandLookUp.put(addMenuItem(simulatorMenu, "Do X cycles", KeyStroke.getKeyStroke("F8"), StateValidator.executingStates).hashCode(), new CommandDoXCycles(mf));
        EventCommandLookUp.put(addMenuItem(simulatorMenu, "Run to address X", KeyStroke.getKeyStroke("F9"), StateValidator.executingStates).hashCode(), new CommandRunToAddressX(mf));
        EventCommandLookUp.put(addMenuItem(simulatorMenu, "Restart program", KeyStroke.getKeyStroke("F4"), StateValidator.executingStates).hashCode(), new CommandResetCurrentProgram(mf));
        simulatorMenu.addSeparator();
        EventCommandLookUp.put(addMenuItem(simulatorMenu, "Options", null, StateValidator.executingOrLazyStates).hashCode(), new CommandShowOptionDialog());
        {
        	// TODO: update the menu entry for forwarding after changing it in the options dialog 
            OpenDLXSimCheckBoxMenuItem fw_checkitem = addCheckBoxMenuItem(simulatorMenu, "Forwarding", null, StateValidator.executingOrLazyStates);
            EventCommandLookUp.put(fw_checkitem.hashCode(), new CommandForwarding(fw_checkitem));
            fw_checkitem.setSelected(Preference.pref.getBoolean(Preference.forwardingPreferenceKey, true));
        }

        EventCommandLookUp.put(addMenuItem(windowMenu, "Save current window configuration", null, StateValidator.executingOrLazyStates).hashCode(), new CommandSaveFrameConfigurationUsrLevel(mf));
        EventCommandLookUp.put(addMenuItem(windowMenu, "Load saved window configuration", null, StateValidator.executingOrLazyStates).hashCode(), new CommandLoadFrameConfigurationUsrLevel(mf));
        EventCommandLookUp.put(addMenuItem(windowMenu, "Clear all preferences", null, StateValidator.executingOrLazyStates).hashCode(), new CommandClearAllPreferences());

        /*this is a submenu of windowMenu
         JMenu defaultWindowConfigurationMenu = new JMenu("Load default window configuration");
         windowMenu.add(defaultWindowConfigurationMenu);
         EventCommandLookUp.put(addMenuItem(defaultWindowConfigurationMenu, "standard", null).hashCode(), null);
         EventCommandLookUp.put(addMenuItem(defaultWindowConfigurationMenu, "full", null).hashCode(), null);
         EventCommandLookUp.put(addMenuItem(defaultWindowConfigurationMenu, "edit only", null).hashCode(), null);*/
        windowMenu.addSeparator();

        createWindowCheckboxes(windowMenu); //see below

        windowMenu.addSeparator();
        // a group of radio buttons
        ButtonGroup lookAndFeelOptionsGroup = new ButtonGroup();
        //add here new LookAndFeel options  
        JRadioButtonMenuItem item = addRadioButtonMenuItem(windowMenu, "System Look and Feel enabled (" + System.getProperty("os.name") + ")", null, lookAndFeelOptionsGroup, StateValidator.allStates);
        item.setName(LookAndFeelStrategySystemMonoSpaced.getLookAndFeelName());
        String laf = UIManager.getLookAndFeel().getClass().toString();

        /*quite dirty code follows here now, breaking every known code convention :-) - luckily its just a small piece of
         * code in a huge program so we can afford to leave it like it its- so better dont try to change it*/
        if (laf.substring(laf.length() - 17).equals(item.getName().substring(item.getName().length() - 17)))
        {
            item.setSelected(true);
        }
        EventCommandLookUp.put(item.hashCode(), new CommandSetLaFSystem(item));



        item = addRadioButtonMenuItem(windowMenu, "Java Look and Feel enabled", null, lookAndFeelOptionsGroup, StateValidator.allStates);
        item.setName(LookAndFeelStrategyJava.getLookAndFeelName());

        if (laf.substring(laf.length() - 17).equals(item.getName().substring(item.getName().length() - 17)))
        {
            item.setSelected(true);
        }
        EventCommandLookUp.put(item.hashCode(), new CommandSetLaFJava(item));

        /*end of dirty code*/
        //help
        OpenDLXSimCheckBoxMenuItem checkitem = addCheckBoxMenuItem(helpMenu, "Display tooltips", null, StateValidator.executingOrLazyStates);
        EventCommandLookUp.put(checkitem.hashCode(), new CommandDisplayTooltips(checkitem));
        // get preference and set selected if tooltips are enabled
        checkitem.setSelected(Preference.pref.getBoolean(CommandDisplayTooltips.preferenceKey, true));
        EventCommandLookUp.put(addMenuItem(helpMenu, "Tutorial", null, StateValidator.executingOrLazyStates).hashCode(), new CommandTutorial());
EventCommandLookUp.put(addMenuItem(helpMenu,"About",null,StateValidator.executingOrLazyStates).hashCode(), new CommandShowAbout());
        return jmb;
    }

    private void createWindowCheckboxes(JMenu windowMenu)
    {
        //box name = frame title
        String name = InternalFrameFactory.getFrameName(EditorFrame.class);
        JCheckBoxMenuItem box = addCheckBoxMenuItem(windowMenu, "Display Editor", null, StateValidator.allStates);
        box.setName(name);
        EventCommandLookUp.put(box.hashCode(), new CommandChangeWindowVisibility(box, mf));
        mf.boxes.add(box);


        name = InternalFrameFactory.getFrameName(LogFrame.class);
        box = addCheckBoxMenuItem(windowMenu, "Display Log", null, StateValidator.executingOrRunningStates);
        box.setName(name);
        EventCommandLookUp.put(box.hashCode(), new CommandChangeWindowVisibility(box, mf));
        mf.boxes.add(box);

        name = InternalFrameFactory.getFrameName(CodeFrame.class);
        box = addCheckBoxMenuItem(windowMenu, "Display Code", null, StateValidator.executingOrRunningStates);
        box.setName(name);
        EventCommandLookUp.put(box.hashCode(), new CommandChangeWindowVisibility(box, mf));
        mf.boxes.add(box);

        name = InternalFrameFactory.getFrameName(RegisterFrame.class);
        box = addCheckBoxMenuItem(windowMenu, "Display Register Set", null, StateValidator.executingOrRunningStates);
        box.setName(name);
        EventCommandLookUp.put(box.hashCode(), new CommandChangeWindowVisibility(box, mf));
        mf.boxes.add(box);

        name = InternalFrameFactory.getFrameName(ClockCycleFrame.class);
        box = addCheckBoxMenuItem(windowMenu, "Display Clock Cycle Diagram", null, StateValidator.executingOrRunningStates);
        box.setName(name);
        EventCommandLookUp.put(box.hashCode(), new CommandChangeWindowVisibility(box, mf));
        mf.boxes.add(box);

        name = InternalFrameFactory.getFrameName(StatisticsFrame.class);
        box = addCheckBoxMenuItem(windowMenu, "Display Statistics", null, StateValidator.executingOrRunningStates);
        box.setName(name);
        EventCommandLookUp.put(box.hashCode(), new CommandChangeWindowVisibility(box, mf));
        mf.boxes.add(box);

        name = InternalFrameFactory.getFrameName(MemoryFrame.class);
        box = addCheckBoxMenuItem(windowMenu, "Display Memory", null, StateValidator.executingOrRunningStates);
        box.setName(name);
        EventCommandLookUp.put(box.hashCode(), new CommandChangeWindowVisibility(box, mf));
        mf.boxes.add(box);
    }

}
