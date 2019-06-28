package it.polimi.sw2019.controller;

import it.polimi.sw2019.controller.weaponeffect.EffectController;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class WeaponEffectManagerTest {

    @Test
    public void TestInitializtaionOfEffectController(){
        WeaponEffectManager weaponEffectManager = new WeaponEffectManager(null, null);

        HashMap<String, EffectController> effectControllerHashMap = weaponEffectManager.getEffectControllers();
        Assert.assertEquals(45, effectControllerHashMap.size());
    }
}
