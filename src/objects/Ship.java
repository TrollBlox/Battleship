package objects;

import enums.Direction;
import enums.ShipStatus;
import enums.ShipType;

public class Ship {
    private final int xLocation;
    private final int yLocation;
    private final Direction direction;
    private ShipType type;
    private ShipStatus status;
    private int hits = 0;

    public Ship(int xLocation, int yLocation, Direction direction, ShipType type) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.direction = direction;
        this.type = type;
        this.status = ShipStatus.OPERATIONAL;
    }

    public int getLength() {
        return type.getLength();
    }

    public ShipType getType() {
        return type;
    }

    public void setType(ShipType type) {
        this.type = type;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public Direction getDirection() {
        return direction;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public void hit() {
        hits++;
        if (hits == getLength()) status = ShipStatus.SUNK;
        else status = ShipStatus.DAMAGED;
    }

}
