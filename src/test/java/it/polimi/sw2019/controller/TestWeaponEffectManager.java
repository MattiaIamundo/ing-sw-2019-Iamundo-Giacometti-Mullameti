package it.polimi.sw2019.controller;

import it.polimi.sw2019.controller.weaponeffect.EffectController;
import it.polimi.sw2019.controller.weaponeffect.WeaponEffectManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class TestWeaponEffectManager {

    @Test
    public void TestInitializtaionOfEffectController(){
        WeaponEffectManager weaponEffectManager = new WeaponEffectManager();

        HashMap<String, EffectController> effectControllerHashMap = weaponEffectManager.getEffectControllers();
        Assert.assertEquals(45, effectControllerHashMap.size());
    }
}
