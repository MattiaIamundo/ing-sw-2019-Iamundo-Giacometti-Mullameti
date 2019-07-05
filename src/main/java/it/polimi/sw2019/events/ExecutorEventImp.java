package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.client_event.Cevent.DirectionChooseEv;
import it.polimi.sw2019.events.client_event.Cevent.NotifyEndMoveEv;
import it.polimi.sw2019.events.client_event.Cevent.NotifyEndReloadEv;
import it.polimi.sw2019.events.client_event.Cevent.WeaponReloadChooseEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyGrabEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyMoveEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyReloadEv;
import it.polimi.sw2019.events.client_event.StartTurnEv;
import it.polimi.sw2019.events.powerup_events.*;
import it.polimi.sw2019.events.server_event.VCevent.GrabEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.events.server_event.VCevent.PowerupEv;
import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;
import it.polimi.sw2019.events.weapon_event.PowerChooseEv;
import it.polimi.sw2019.events.weapon_event.UnpaidEffectEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.*;

public class ExecutorEventImp implements ExecutorEvent {

    public ExecutorEventImp() {

    }


    public void handleObject(MoveEv moveEv, Game game){
        game.handleEvent(moveEv);
    }

    public void handleObject(ReloadEv reloadEv, Game game){
        game.handleEvent(reloadEv);
    }

    public void handleObject(GrabEv grabEv, Game game){
        game.handleEvent(grabEv);
    }

    public void handleObject(PowerupEv powerupEv, Game game){
        //game.handleEvent(powerupEv);
    }

    public void handleObject(BarbecueSetEv barbecueSetEv, Game game){
        game.handleEvent(barbecueSetEv);
    }

    public void handleObject(BlackHoleSetEv blackHoleSetEv, Game game){
        game.handleEvent(blackHoleSetEv);
    }

    public void handleObject(ChainReactSetEv chainReactSetEv, Game game){
        game.handleEvent(chainReactSetEv);
    }

    public void handleObject(CozyFireModeSetEv cozyFireModeSetEv, Game game){
        game.handleEvent(cozyFireModeSetEv);
    }

    public void handleObject(CyberbladeSetEv cyberbladeSetEv, Game game){
        game.handleEvent(cyberbladeSetEv);
    }

    public void handleObject(ExtraGrenadeSetEv extraGrenadeSetEv, Game game){
        game.handleEvent(extraGrenadeSetEv);
    }

    public void handleObject(FlamethrowerSetEv flamethrowerSetEv, Game game){
        game.handleEvent(flamethrowerSetEv);
    }

    public void handleObject(FocusShotSetEv focusShotSetEv, Game game){
        game.handleEvent(focusShotSetEv);
    }

    public void handleObject(FurnaceSetEv furnaceSetEv, Game game){
        game.handleEvent(furnaceSetEv);
    }

    public void handleObject(GrenadeLaunchSetEv grenadeLaunchSetEv, Game game){
        game.handleEvent(grenadeLaunchSetEv);
    }

    public void handleObject(HeatseekerSetEv heatseekerSetEv, Game game){
        game.handleEvent(heatseekerSetEv);
    }

    public void handleObject(HellionSetEv hellionSetEv, Game game){
        game.handleEvent(hellionSetEv);
    }

    public void handleObject(HighVoltageSetEv highVoltageSetEv, Game game){
        game.handleEvent(highVoltageSetEv);
    }

    public void handleObject(LockRifleSetEv lockRifleSetEv, Game game){
        game.handleEvent(lockRifleSetEv);
    }

    public void handleObject(LongBarrelSetEv longBarrelSetEv, Game game){
        game.handleEvent(longBarrelSetEv);
    }

    public void handleObject(MachineGunSetEv machineGunSetEv, Game game){
        game.handleEvent(machineGunSetEv);
    }

    public void handleObject(NanoTracerSetEv nanoTracerSetEv, Game game){
        game.handleEvent(nanoTracerSetEv);
    }

    public void handleObject(PhaseGlideSetEv phaseGlideSetEv, Game game){
        game.handleEvent(phaseGlideSetEv);
    }

    public void handleObject(PiercingModeSetEv piercingModeSetEv, Game game){
        game.handleEvent(piercingModeSetEv);
    }

    public void handleObject(PlasmaGunSetEv plasmaGunSetEv, Game game){
        game.handleEvent(plasmaGunSetEv);
    }

    public void handleObject(PowerGloveSetEv powerGloveSetEv, Game game){
        game.handleEvent(powerGloveSetEv);
    }

    public void handleObject(PulvModeSetEv pulvModeSetEv, Game game){
        game.handleEvent(pulvModeSetEv);
    }

    public void handleObject(PunisherModeSetEv punisherModeSetEv, Game game){
        game.handleEvent(punisherModeSetEv);
    }

    public void handleObject(RailGunSetEv railGunSetEv, Game game){
        game.handleEvent(railGunSetEv);
    }

    public void handleObject(RocketFistSetEv rocketFistSetEv, Game game){
        game.handleEvent(rocketFistSetEv);
    }

    public void handleObject(RocketJumpSetEv rocketJumpSetEv, Game game){
        game.handleEvent(rocketJumpSetEv);
    }

    public void handleObject(RocketLaunchSetEv rocketLaunchSetEv, Game game){
        game.handleEvent(rocketLaunchSetEv);
    }

    public void handleObject(ScannerModeSetEv scannerModeSetEv, Game game){
        game.handleEvent(scannerModeSetEv);
    }

    public void handleObject(SecondLockSetEv secondLockSetEv, Game game){
        game.handleEvent(secondLockSetEv);
    }

    public void handleObject(ShadowstepSetEv shadowstepSetEv, Game game){
        game.handleEvent(shadowstepSetEv);
    }

    public void handleObject(ShockwaveSetEv shockwaveSetEv, Game game){
        game.handleEvent(shockwaveSetEv);
    }

    public void handleObject(ShotgunSetEv shotgunSetEv, Game game){
        game.handleEvent(shotgunSetEv);
    }

    public void handleObject(SledgehammerSetEv sledgehammerSetEv, Game game){
        game.handleEvent(sledgehammerSetEv);
    }

    public void handleObject(SliceAndDiceSetEv sliceAndDiceSetEv, Game game){
        game.handleEvent(sliceAndDiceSetEv);
    }

    public void handleObject(ThorSetEv thorSetEv, Game game){
        game.handleEvent(thorSetEv);
    }

    public void handleObject(TractorBeamSetEv tractorBeamSetEv, Game game){
        game.handleEvent(tractorBeamSetEv);
    }

    public void handleObject(TurretTripodSetEv turretTripodSetEv, Game game){
        game.handleEvent(turretTripodSetEv);
    }

    public void handleObject(VortexSetEv vortexSetEv, Game game){
        game.handleEvent(vortexSetEv);
    }

    public void handleObject(WhisperSetEv whisperSetEv, Game game){
        game.handleEvent(whisperSetEv);
    }

    public void handleObject(ZX2SetEv zx2SetEv, Game game){
        game.handleEvent(zx2SetEv);
    }

    public void handleObject(PowerUpSetEv powerUpSetEv, Game game){
        game.handleEvent(powerUpSetEv);
    }

    public void handleObject(NewtonSetEv newtonSetEv, Game game){
        game.handleEvent(newtonSetEv);
    }

    public void handleObject(TagbackGrenadeSetEv tagbackGrenadeSetEv, Game game){
        game.handleEvent(tagbackGrenadeSetEv);
    }

    public void handleObject(TargetingScopeSetEv targetingScopeSetEv, Game game){
        game.handleEvent(targetingScopeSetEv);
    }

    public void handleObject(TeleporterSetEv teleporterSetEv, Game game){
        game.handleEvent(teleporterSetEv);
    }




    public void updateObject(StartTurnEv startTurnEv, Game game) {
        //
    }


    public void updateObject(NotifyMoveEv moveEv, Game game) {
        game.update(moveEv);
    }

    public void updateObject(NotifyGrabEv grabEv, Game game) {
        game.update(grabEv);
    }

    public void updateObject(NotifyReloadEv reloadEv, Game game) {
        game.update(reloadEv);
    }

    public void updateObject(NotifyEndMoveEv endMoveEv, Game game) {
        game.update(endMoveEv);
    }

    public void updateObject(NotifyEndReloadEv endReloadEv, Game game) {
        game.update(endReloadEv);
    }

    public void updateObject(DirectionChooseEv directionChooseEv, Game game) {
        game.update(directionChooseEv);
    }

    public void updateObject(WeaponReloadChooseEv weaponReloadChooseEv, Game game) {
        game.update(weaponReloadChooseEv);
    }

    public void updateObject(BarbecueChooseEv barbecueChooseEv, Game game){
        game.update(barbecueChooseEv);
    }

    public void updateObject(BlackHoleChooseEv blackHoleChooseEv, Game game){
        game.update(blackHoleChooseEv);
    }

    public void updateObject(ChainReactChooseEv chainReactChooseEv, Game game){
        game.update(chainReactChooseEv);
    }

    public void updateObject(CozyFireModeChooseEv cozyFireModeChooseEv, Game game){
        game.update(cozyFireModeChooseEv);
    }

    public void updateObject(CyberbladeChooseEv cyberbladeChooseEv, Game game){
        game.update(cyberbladeChooseEv);
    }

    public void updateObject(ExtraGrenadeChooseEv extraGrenadeChooseEv, Game game){
        game.update(extraGrenadeChooseEv);
    }

    public void updateObject(FlamethrowerChooseEv flamethrowerChooseEv, Game game){
        game.update(flamethrowerChooseEv);
    }

    public void updateObject(FocusShotChooseEv focusShotChooseEv, Game game){
        game.update(focusShotChooseEv);
    }

    public void updateObject(FurnaceChooseEv furnaceChooseEv, Game game){
        game.update(furnaceChooseEv);
    }

    public void updateObject(GrenadeLaunchChooseEv grenadeLaunchChooseEv, Game game){
        game.update(grenadeLaunchChooseEv);
    }

    public void updateObject(HeatseekerChooseEv heatseekerChooseEv, Game game){
        game.update(heatseekerChooseEv);
    }

    public void updateObject(HellionChooseEv hellionChooseEv, Game game){
        game.update(hellionChooseEv);
    }

    public void updateObject(HighVoltageChooseEv highVoltageChooseEv, Game game){
        game.update(highVoltageChooseEv);
    }

    public void updateObject(LockRifleChooseEv lockRifleChooseEv, Game game){
        game.update(lockRifleChooseEv);
    }

    public void updateObject(LongBarrelChooseEv longBarrelChooseEv, Game game){
        game.update(longBarrelChooseEv);
    }

    public void updateObject(MachineGunChooseEv machineGunChooseEv, Game game){
        game.update(machineGunChooseEv);
    }

    public void updateObject(NanoTracerChooseEv nanoTracerChooseEv, Game game){
        game.update(nanoTracerChooseEv);
    }

    public void updateObject(PhaseGlideChooseEv phaseGlideChooseEv, Game game){
        game.update(phaseGlideChooseEv);
    }

    public void updateObject(PiercingModeChooseEv piercingModeChooseEv, Game game){
        game.update(piercingModeChooseEv);
    }

    public void updateObject(PlasmaGunChooseEv plasmaGunChooseEv, Game game){
        game.update(plasmaGunChooseEv);
    }

    public void updateObject(PowerChooseEv powerChooseEv, Game game){
        game.update(powerChooseEv);
    }

    public void updateObject(PowerGloveChooseEv powerGloveChooseEv, Game game){
        game.update(powerGloveChooseEv);
    }

    public void updateObject(PulvModeChooseEv pulvModeChooseEv, Game game){
        game.update(pulvModeChooseEv);
    }

    public void updateObject(PunisherModeChooseEv punisherModeChooseEv, Game game){
        game.update(punisherModeChooseEv);
    }

    public void updateObject(RailGunChooseEv railGunChooseEv, Game game){
        game.update(railGunChooseEv);
    }

    public void updateObject(RocketFistChooseEv rocketFistChooseEv, Game game){
        game.update(rocketFistChooseEv);
    }

    public void updateObject(RocketJumpChooseEv rocketJumpChooseEv, Game game){
        game.update(rocketJumpChooseEv);
    }

    public void updateObject(RocketLaunchChooseEv rocketLaunchChooseEv, Game game){
        game.update(rocketLaunchChooseEv);
    }

    public void updateObject(ScannerModeChooseEv scannerModeChooseEv, Game game){
        game.update(scannerModeChooseEv);
    }

    public void updateObject(SecondLockChooseEv secondLockChooseEv, Game game){
        game.update(secondLockChooseEv);
    }

    public void updateObject(ShadowstepChooseEv shadowstepChooseEv, Game game){
        game.update(shadowstepChooseEv);
    }

    public void updateObject(ShockwaveChooseEv shockwaveChooseEv, Game game){
        game.update(shockwaveChooseEv);
    }

    public void updateObject(ShotgunChooseEv shotgunChooseEv, Game game){
        game.update(shotgunChooseEv);
    }

    public void updateObject(SledgehammerChooseEv sledgehammerChooseEv, Game game){
        game.update(sledgehammerChooseEv);
    }

    public void updateObject(SliceAndDiceChooseEv sliceAndDiceChooseEv, Game game){
        game.update(sliceAndDiceChooseEv);
    }

    public void updateObject(ThorChooseEv thorChooseEv, Game game){
        game.update(thorChooseEv);
    }

    public void updateObject(TractorBeamChooseEv tractorBeamChooseEv, Game game){
        game.update(tractorBeamChooseEv);
    }

    public void updateObject(TurretTripodChooseEv turretTripodChooseEv, Game game){
        game.update(turretTripodChooseEv);
    }

    public void updateObject(VortexChooseEv vortexChooseEv, Game game){
        game.update(vortexChooseEv);
    }

    public void updateObject(WhisperChooseEv whisperChooseEv, Game game){
        game.update(whisperChooseEv);
    }

    public void updateObject(ZX2ChooseEv zx2ChooseEv, Game game){
        game.update(zx2ChooseEv);
    }

    public void updateObject(UnpaidEffectEv unpaidEffectEv, Game game){
        game.update(unpaidEffectEv);
    }

    public void updateObject(PowerUpChooseEv powerUpChooseEv, Game game){
        game.update(powerUpChooseEv);
    }

    public void updateObject(NewtonChooseEv newtonChooseEv, Game game){
        game.update(newtonChooseEv);
    }

    public void updateObject(TagbackGrenadeChooseEv tagbackGrenadeChooseEv, Game game){
        game.update(tagbackGrenadeChooseEv);
    }

    public void updateObject(TargetingScopeChooseEv targetingScopeChooseEv, Game game){
        game.update(targetingScopeChooseEv);
    }

    public void updateObject(TeleporterChooseEv teleporterChooseEv, Game game){
        game.update(teleporterChooseEv);
    }
}
