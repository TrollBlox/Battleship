package objects;

import enums.MapStatus;
import java.util.Arrays;

public class Map {
    private MapStatus[][] map = new MapStatus[][]{
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
            {MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED, MapStatus.NOT_ATTACKED},
    };

    public MapStatus[][] getMap() {
        return map;
    }

    public MapStatus getMap(int x, int y) {
        return map[x - 1][y - 1];
    }

    public void setMap(MapStatus[][] map) {
        this.map = map;
    }

    public void setMap(int x, int y, MapStatus value) {
        map[x][y] = value;
    }
}
