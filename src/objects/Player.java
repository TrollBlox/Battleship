package objects;

import enums.ShipType;
import exceptions.ShipNoneException;
import exceptions.ShipTypeMismatchException;

public class Player {
    private final Map map;
    private Ship aircraftCarrier;
    private Ship battleship;
    private Ship destroyer;
    private Ship submarine;
    private Ship patrolBoat;

    private String nickname;

    public Player() {
        map = new Map();
    }

    public Map getMap() {
        return map;
    }

    public Ship getAircraftCarrier() {
        return aircraftCarrier;
    }

    public Ship getBattleship() {
        return battleship;
    }

    public Ship getDestroyer() {
        return destroyer;
    }

    public Ship getSubmarine() {
        return submarine;
    }

    public Ship getPatrolBoat() {
        return patrolBoat;
    }

    public void setAircraftCarrier(Ship aircraftCarrier) throws ShipTypeMismatchException {
        if (aircraftCarrier.getType() != ShipType.AIRCRAFT_CARRIER) throw new ShipTypeMismatchException();
        this.aircraftCarrier = aircraftCarrier;
    }

    public void setBattleship(Ship battleship) throws ShipTypeMismatchException {
        if (battleship.getType() != ShipType.BATTLESHIP) throw new ShipTypeMismatchException();
        this.battleship = battleship;
    }

    public void setDestroyer(Ship destroyer) throws ShipTypeMismatchException {
        if (destroyer.getType() != ShipType.DESTROYER) throw new ShipTypeMismatchException();
        this.destroyer = destroyer;
    }

    public void setSubmarine(Ship submarine) throws ShipTypeMismatchException {
        if (submarine.getType() != ShipType.SUBMARINE) throw new ShipTypeMismatchException();
        this.submarine = submarine;
    }

    public void setPatrolBoat(Ship patrolBoat) throws ShipTypeMismatchException {
        if (patrolBoat.getType() != ShipType.PATROL_BOAT) throw new ShipTypeMismatchException();
        this.patrolBoat = patrolBoat;
    }

    public Ship getShipByType(ShipType type) throws ShipNoneException, ShipTypeMismatchException {
        switch (type) {
            case AIRCRAFT_CARRIER -> {
                return getAircraftCarrier();
            }
            case BATTLESHIP -> {
                return getBattleship();
            }
            case DESTROYER -> {
                return getDestroyer();
            }
            case SUBMARINE -> {
                return getSubmarine();
            }
            case PATROL_BOAT -> {
                return getPatrolBoat();
            }
            case NONE -> throw new ShipNoneException();
            default -> throw new ShipTypeMismatchException();
        }
    }

    public void setShipByType(ShipType type, Ship ship) throws ShipTypeMismatchException, ShipNoneException {
        switch (type) {
            case AIRCRAFT_CARRIER -> setAircraftCarrier(ship);
            case BATTLESHIP -> setBattleship(ship);
            case DESTROYER -> setDestroyer(ship);
            case SUBMARINE -> setSubmarine(ship);
            case PATROL_BOAT -> setPatrolBoat(ship);
            case NONE -> throw new ShipNoneException();
            default -> throw new ShipTypeMismatchException();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public Ship[] getShips() {
        return new Ship[]{getAircraftCarrier(), getBattleship(), getDestroyer(), getSubmarine(), getPatrolBoat()};
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
