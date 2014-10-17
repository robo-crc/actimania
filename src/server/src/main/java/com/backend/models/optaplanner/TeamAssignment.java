/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.backend.models.optaplanner;

import com.backend.models.School;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class TeamAssignment 
{
    private GameProcess game;

    // planning variable
    private School school;

    public TeamAssignment() {
    }

    public TeamAssignment(GameProcess game) {
        this.game = game;
    }

    public GameProcess getGame() {
        return game;
    }

    public void setGame(GameProcess game) {
        this.game = game;
    }

    @PlanningVariable(valueRangeProviderRefs = {"blueTeam"})
    public School getBlueTeam() {
        return school;
    }
    
    @PlanningVariable(valueRangeProviderRefs = {"yellowTeam"})
    public School getYellowTeam() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
