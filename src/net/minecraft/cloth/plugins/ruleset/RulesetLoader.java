package net.minecraft.cloth.plugins.ruleset;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RulesetLoader {
    public void initializeRulesets(){
        Logger logger = Logger.getLogger("Minecraft");
        Gson gson = new Gson();
        File RulesetDirectory = new File("rulesets");
        String[] RulesetFiles = RulesetDirectory.list();
        if(RulesetFiles != null) {
            for (int i = 0; i < RulesetFiles.length; i++) {
                logger.info("[Rulesets] Found File: " + RulesetFiles[i]);
                File fileRef = new File("./rulesets/"+RulesetFiles[i]);
                try {
                    Reader reader = Files.newBufferedReader(Paths.get(fileRef.getAbsolutePath()));
                    Map<String, ArrayList<String>> ruleset = gson.fromJson(reader, Map.class);
                    rulesetOverrides.add(ruleset);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
    public List<Map> rulesetOverrides = new ArrayList<Map>();
}
