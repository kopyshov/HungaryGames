package com.kopyshov.model.entities.creatures;

import com.kopyshov.model.ActionMapModel;
import com.kopyshov.model.GraphNode;

public interface Attacker {
    void attack(ActionMapModel actionMapModel, GraphNode prey);
}
