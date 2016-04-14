package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.Direction;
import com.elstele.bill.utils.Enums.Status;

import java.util.Date;

public class DirectionBuilder implements TestObjectCreator<DirectionBuilder, Direction> {
    private Direction direction;

    @Override
    public DirectionBuilder build() {
        direction = new Direction();
        direction.setStatus(Status.ACTIVE);
        return this;
    }

    public DirectionBuilder withId(int id){
        direction.setId(id);
        return this;
    }

    public DirectionBuilder withDescription(String description){
        direction.setDescription(description);
        return this;
    }
    public DirectionBuilder withPrefix(String prefix){
        direction.setPrefix(prefix);
        return this;
    }
    public DirectionBuilder withTariffZone(Integer tariffZone){
        direction.setTarifZone(tariffZone);
        return this;
    }
    public DirectionBuilder withAdditionalKode(String additionalKode){
        direction.setAdditionalKode(additionalKode);
        return this;
    }
    public DirectionBuilder withTrunkGroup(String trunkGroup){
        direction.setTrunkgroup(trunkGroup);
        return this;
    }
    public DirectionBuilder withValidFromDate(Date validFrom){
        direction.setValidFrom(validFrom);
        return this;
    }
    public DirectionBuilder withValidToDate(Date validTo){
        direction.setValidTo(validTo);
        return this;
    }

    @Override
    public Direction getRes() {
        if(direction == null){
            build();
        }
        return direction;
    }
}
