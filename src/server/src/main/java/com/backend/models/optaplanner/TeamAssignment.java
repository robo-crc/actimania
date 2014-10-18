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
    private int gameNo;

    // planning variable
    private School school;

    public TeamAssignment() 
    {
    }

    public TeamAssignment(int _gameNo) {
        this.gameNo = _gameNo;
    }
    
    public int getGameNo()
    {
    	return gameNo;
    }

    @PlanningVariable(valueRangeProviderRefs = {"school"})
    public School getSchool() {
        return school;
    }
    
    public void setSchool(School _school) 
    {
        school = _school;
    }
}
