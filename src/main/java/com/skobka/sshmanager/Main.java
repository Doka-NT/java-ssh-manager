package com.skobka.sshmanager;

import com.skobka.sshmanager.app.AppInterface;
import com.skobka.sshmanager.app.AppJavaFx;
import com.skobka.sshmanager.app.AppSwing;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
        Options options = new Options();

        Option uiOption = new Option("i", "ui", true, "user interface (swing, javafx)");
        uiOption.setRequired(false);
        options.addOption(uiOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar SshManager.jar", options);

            System.exit(1);
            return;
        }

        String ui = cmd.getOptionValue("ui", "swing");

        AppInterface app = ui.equals("javafx") ? new AppJavaFx() : new AppSwing();

        app.run();
    }
}
