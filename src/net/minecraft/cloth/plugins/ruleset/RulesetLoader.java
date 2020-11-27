package net.minecraft.cloth.plugins.ruleset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class RulesetLoader {
    public void initializeRulesets(){
        Logger logger = Logger.getLogger("Minecraft");
        File RulesetDirectory = new File("rulesets");
        String[] RulesetFiles = RulesetDirectory.list();
        if(RulesetFiles != null) {
            for (int i = 0; i < RulesetFiles.length; i++) {
                logger.info("[Rulesets] Found File: " + RulesetFiles[i]);
                File fileRef = new File(RulesetFiles[i]);
                try {
                    Reader reader = Files.newBufferedReader(Paths.get(fileRef.getAbsolutePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
