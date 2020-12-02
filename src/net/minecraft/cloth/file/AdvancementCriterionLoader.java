package net.minecraft.cloth.file;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static net.minecraft.Globals.advancementNames;

public class AdvancementCriterionLoader {


    public static Map loadAdvancementCriterion() {
        Logger logger = Logger.getLogger("Minecraft");
        Gson gson = new Gson();
        Map<String, String> advancementCriterionMap = null;
        File advancementCriterionFile = new File("advancementCriterion.json");
        try {
            if (advancementCriterionFile.createNewFile()) {
                logger.info("[Cloth] No advancement criterion exist. Falling back to internal globals");
                return advancementNames;
            }
            //otherwise...
            Reader reader = Files.newBufferedReader(Paths.get(advancementCriterionFile.getAbsolutePath()));
            advancementCriterionMap = gson.fromJson(reader, HashMap.class);


        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access advancementCriterion file: ");
            e.printStackTrace();

        }
        if (advancementCriterionFile != null) {
            return advancementCriterionMap;
        } else {
            return advancementNames;
        }

    }
}
