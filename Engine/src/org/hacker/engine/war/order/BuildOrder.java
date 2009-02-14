package org.hacker.engine.war.order;

import org.hacker.engine.war.Terra;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.unit.Artillery;
import org.hacker.engine.war.unit.City;
import org.hacker.engine.war.unit.Grunt;
import org.hacker.engine.war.unit.Hovercraft;
import org.hacker.engine.war.unit.Unit;
import org.hacker.engine.war.unit.UnitTypes;

public class BuildOrder extends Order {
    public final int buildType;

    public BuildOrder(int buildItem) {
        this.buildType = buildItem;
    }

    public static BuildOrder fromType(int buildItem) {
        BuildOrder bo = new BuildOrder(buildItem);
        switch (buildItem) {
            case UnitTypes.GRUNT:
                bo.execTime = 32.0;
                break;
            case UnitTypes.HOVERCRAFT:
                bo.execTime = 36.0;
                break;
            case UnitTypes.ARTIL:
                bo.execTime = 40.0;
                break;
            default:
                return null;
        }
        return bo;
    }

    @Override
    public void execute(Unit unit, Terra terra, VisualsListener vis) {
        // randomize position a bit
        double x = unit.x + terra.r.nextDouble() * 4.0 - 2.0;
        double y = unit.y + terra.r.nextDouble() * 4.0 - 2.0;
        Unit nunit;
        switch (buildType) {
            case UnitTypes.GRUNT:
                nunit = new Grunt(terra.getNextID(), x, y);
                break;
            case UnitTypes.HOVERCRAFT:
                nunit = new Hovercraft(terra.getNextID(), x, y);
                break;
            case UnitTypes.ARTIL:
                nunit = new Artillery(terra.getNextID(), x, y);
                break;
            default:
                throw new RuntimeException("no such build type");
        }
        if (vis != null)
                vis.newCityBuild((City) unit);
        unit.getFaction().addUnit(nunit);
        terra.addUnit(nunit);
    }

        @Override
        public int getOrderType() {
                return BUILD;
        }
}
