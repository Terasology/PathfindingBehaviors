// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.module.pathfindingbehaviors.actions;

import org.joml.Vector3f;
import org.terasology.engine.logic.behavior.BehaviorAction;
import org.terasology.engine.logic.behavior.core.Actor;
import org.terasology.engine.logic.behavior.core.BaseAction;
import org.terasology.engine.logic.characters.CharacterMoveInputEvent;
import org.terasology.engine.logic.location.LocationComponent;
import org.terasology.module.pathfindingbehaviors.move.MinionMoveComponent;

@BehaviorAction(name = "stop_moving")
public class StopMovingAction extends BaseAction {

    @Override
    public void construct(Actor actor) {

        // Calculating a lot of superfluous stuff to debug; this'll get cleaned up when stopping is figured out
        LocationComponent locationComponent = actor.getComponent(LocationComponent.class);
        MinionMoveComponent moveComponent = actor.getComponent(MinionMoveComponent.class);
        Vector3f worldPos = locationComponent.getWorldPosition(new Vector3f());
        Vector3f targetDirection = moveComponent.target.sub(worldPos, new Vector3f());

        float yaw = (float) Math.atan2(targetDirection.x, targetDirection.z);

        CharacterMoveInputEvent wantedInput = new CharacterMoveInputEvent(0, 0, 0, new Vector3f(), false, false, false, 
            (long) (actor.getDelta() * 1000));
        actor.getEntity().send(wantedInput);
    }
}
