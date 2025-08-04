package me.matl114.logitech.manager;

import me.matl114.logitech.utils.Debug;
import me.matl114.logitech.core.Registries.RecipeSupporter;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class PostSetupTasks {
    //Tasks to be executed after server startup initialization
    public static boolean startPostRegister=false;
    public static boolean recipeSupportorInit=false;
    public static HashSet<Runnable> registerTasks=new LinkedHashSet<>();
    public static void addPostRegisterTask(Runnable t) {
        registerTasks.add(t);
    }
    public static void schedulePostRegister(){
        startPostRegister=true;
        Debug.logger("START ADDON POSTREGISTER TASKS");
        RecipeSupporter.init();
        for(Runnable t : registerTasks){
            t.run();
        }
    }
}
