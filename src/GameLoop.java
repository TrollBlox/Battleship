import enums.Direction;
import enums.MapStatus;
import enums.ShipStatus;
import enums.ShipType;
import exceptions.ShipIllegalLocationException;
import exceptions.ShipNoneException;
import exceptions.ShipTypeMismatchException;
import objects.Map;
import objects.Player;
import objects.Ship;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameLoop {
    private int turn = 1;
    private final Scanner s;
    private final Player player1;
    private final Player player2;

    public GameLoop() {
        s = new Scanner(System.in);

        player1 = newPlayer(1);
        changeTurn();
        player2 = newPlayer(2);
        loop();
    }

    private void changeTurn() {
        turn++;
        if (turn > 2) turn = 1;
        clearScreen();
        try {
            System.out.println("It is now " + getCurrentPlayer().getNickname() + "'s turn!");
        } catch (NullPointerException e) {
            System.out.println("It is now the other player's turn!");
        }
        clearScreen();
    }

    private void doTurn() {
        String input;
        while (true) {
            try {
                Player player = getCurrentPlayer();
                Player otherPlayer = getOtherPlayer();
                input = getInput_String();
                if (input.length() < 1 || input.length() > 3) {
                    System.out.println("Please enter coordinates in the format A0!");
                    continue;
                }
                int x;
                int y;
                try {
                    x = Integer.parseInt(input.substring(1, 3));
                } catch (IndexOutOfBoundsException e) {
                    x = Integer.parseInt(input.substring(1, 2));
                }
                if (x < 1 || x > 10) {
                    System.out.println("X-Coordinate must be between 1 and 10!");
                    continue;
                }
                y = input.toLowerCase().charAt(0) - 96;
                if (y < 1 || y > 10) {
                    System.out.println("Y-Coordinate must be between A and J!");
                    continue;
                }
                if (player.getMap().getMap(x, y) != MapStatus.NOT_ATTACKED) {
                    System.out.println("You already attacked there!");
                    continue;
                }

                ShipType type = getShipTypeAtCoordinates(otherPlayer, x, y);
                x--;
                y--;
                if (type == ShipType.NONE) {
                    player.getMap().setMap(x, y, MapStatus.MISS);
                    System.out.println("Miss");
                    return;
                }
                player.getMap().setMap(x, y, MapStatus.HIT);
                System.out.println("You hit " + otherPlayer.getNickname() + "'s " + type.toString().replaceAll("_", " ") + "!");
                otherPlayer.getShipByType(type).hit();
                boolean win;
                if (otherPlayer.getShipByType(type).getStatus() == ShipStatus.SUNK) {
                    win = true;
                    for (Ship ship : otherPlayer.getShips()) {
                        if (ship.getStatus() != ShipStatus.SUNK) {
                            win = false;
                            break;
                        }
                    }
                    if (!win) {
                        System.out.println("You sunk " + otherPlayer.getNickname() + "'s " + type.toString().replaceAll("_", " ") + "!");
                    } else {
                        System.out.println("You sunk " + otherPlayer.getNickname() + "'s " + type.toString().replaceAll("_", " ") + " and won!");
                        System.exit(0);
                    }
                }
                return;
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Error");
                continue;
            } catch (ShipTypeMismatchException | ShipNoneException e) {
                // ignore
            }
        }
    }

    private Player getCurrentPlayer() {
        if (turn == 1) return player1;
        else return player2;
    }

    private Player getOtherPlayer() {
        if (getCurrentPlayer() == player1) return player2;
        else return player1;
    }

    private void loop() {
        Player player;
        while (true) {
            changeTurn();
            player = getCurrentPlayer();
            printTurn(player);
            System.out.println("Where would you like to shoot?");
            doTurn();
        }
    }

    private void clearScreen() {
        System.out.print("Press Enter to Continue.");
        s.nextLine();
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private Player newPlayer(int playerNumber) {
        Player player = new Player();
        System.out.println("What is Player " + playerNumber + "'s name?");
        player.setNickname(getInput_String());
        printShips(player);
        for (ShipType type : ShipType.values()) {
            if (type == ShipType.NONE) continue;
            while (true) {
                Ship ship = newShip(type);
                try {
                    if (getShipLocationLegal(player, ship)) {
                        player.setShipByType(type, ship);
                        printShips(player);
                        break;
                    }
                } catch (ShipTypeMismatchException e) {
                    System.out.println("Something went really wrong!");
                    e.printStackTrace();
                    System.exit(0);
                } catch (ShipIllegalLocationException e) {
                    System.out.println("You cannot put a ship there!");
                    continue;
                } catch (ShipNoneException e) {
                    // ignore
                }
            }
        }
        return player;
    }

    private String getInput_String() {
        System.out.print("> ");
        return s.nextLine();
    }

    private void printTurn(Player player) {
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print("     ");
                System.out.print(j + 1);
            }
            System.out.print("\t   ");
        }
        System.out.println();
        for (var i = 0; i < 10; i++) {
            printDoubleLine();
            System.out.println();
            printBoard(player, i);
            System.out.print("\t");
            printShipsLine(player, i);
            System.out.println();
        }
        printDoubleLine();
        System.out.println();
    }

    private void printBoard(Player player, int i) {
        System.out.print("  ");
        System.out.print((char) (i + 65));
        System.out.print("  ");
        for (int j = 0; j < 10; j++) {
            System.out.print("|  ");
            System.out.print(getCharacter(player.getMap(), j + 1, i + 1));
            System.out.print("  ");
        }
        System.out.print("|");
    }

    private void printShipsLine(Player player, int i) {
        System.out.print("  ");
        System.out.print((char) (i + 65));
        System.out.print("  ");
        for (int j = 0; j < 10; j++) {
            System.out.print("|  ");
            System.out.print(getCharacter(player, getOtherPlayer(player), j + 1, i + 1));
            System.out.print("  ");
        }
        System.out.print("|");
    }

    private void printShips(Player player) {
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < 10; i++) {
            System.out.print("     ");
            System.out.print(i + 1);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
            printLine();
            System.out.println();
            System.out.print("  ");
            System.out.print((char) (i + 65));
            System.out.print("  ");
            for (int j = 0; j < 10; j++) {
                System.out.print("|  ");
                System.out.print(getCharacter(player, getOtherPlayer(player), j + 1, i + 1));
                System.out.print("  ");
            }
            System.out.print("|");
        }
        System.out.println();
        printLine();
        System.out.println();
    }


    private Player getOtherPlayer(Player player) {
        if (player == player1) return player2;
        else return player1;
    }

    private char getCharacter(Player player, Player opponent, int x, int y) {
        if (opponent != null) {
//            System.out.print(opponent.getMap().getMap(x, y));
            if (opponent.getMap().getMap(x, y) == MapStatus.HIT) return 'X';
            else if (opponent.getMap().getMap(x, y) == MapStatus.MISS) return 'O';
//            else if (opponent.getMap().getMap(x, y) == MapStatus.NOT_ATTACKED) return 'o';
        }
        return switch (getShipTypeAtCoordinates(player, x, y)) {
            case AIRCRAFT_CARRIER -> 'A';
            case BATTLESHIP -> 'B';
            case DESTROYER -> 'D';
            case SUBMARINE -> 'S';
            case PATROL_BOAT -> 'P';
            default -> ' ';
        };
    }

    private boolean getShipLocationLegal(Player player, Ship ship) throws ShipIllegalLocationException {
        int ship_x = ship.getxLocation();
        int ship_y = ship.getyLocation();
        int ship_length = ship.getLength();
        Direction ship_dir = ship.getDirection();

        switch (ship_dir) {
            case NORTH -> {
                if (ship_y - ship_length < 0) throw new ShipIllegalLocationException();
                for (int i = 1; i <= ship_length; i++) {
                    if (getShipTypeAtCoordinates(player, ship_x, ship_y - i) != ShipType.NONE) throw new ShipIllegalLocationException();
                }
            }
            case SOUTH -> {
                if (ship_y + ship_length > 10) throw new ShipIllegalLocationException();
                for (int i = 1; i <= ship_length; i++) {
                    if (getShipTypeAtCoordinates(player, ship_x, ship_y + i) != ShipType.NONE) throw new ShipIllegalLocationException();
                }
            }
            case EAST -> {
                if (ship_x + ship_length > 10) throw new ShipIllegalLocationException();
                for (int i = 1; i <= ship_length; i++) {
                    if (getShipTypeAtCoordinates(player, ship_x + i, ship_y) != ShipType.NONE) throw new ShipIllegalLocationException();
                }
            }
            case WEST -> {
                if (ship_x - ship_length < 0) throw new ShipIllegalLocationException();
                for (int i = 1; i <= ship_length; i++) {
                    if (getShipTypeAtCoordinates(player, ship_x - i, ship_y) != ShipType.NONE) throw new ShipIllegalLocationException();
                }
            }
        }


        return true;
    }

    private ShipType getShipTypeAtCoordinates(Player player, int x, int y) {
        try {
            for (ShipType type : ShipType.values()) {
                if (type == ShipType.NONE) continue;
                if (getShipAtCoordinates(type, player, x, y)) return type;
            }
        } catch (ShipNoneException | ShipTypeMismatchException e) {
            // ignore
        }
        return ShipType.NONE;
    }

    private boolean getShipAtCoordinates(ShipType shipType, Player player, int x, int y) throws ShipNoneException, ShipTypeMismatchException {
        if (player.getShipByType(shipType) == null) return false;
        int ship_x = player.getShipByType(shipType).getxLocation();
        int ship_y = player.getShipByType(shipType).getyLocation();
        if (x == ship_x && y == ship_y) return true;
        int ship_length = player.getShipByType(shipType).getLength();
        Direction ship_dir = player.getShipByType(shipType).getDirection();

        switch (ship_dir) {
            case NORTH -> {
                if (y > ship_y || ship_y - y > ship_length - 1) {
                    break;
                }

                if (ship_x == x) {
                    return true;
                }
            }
            case SOUTH -> {
                if (y < ship_y || y - ship_y > ship_length - 1) {
                    break;
                }

                if (ship_x == x) {
                    return true;
                }
            }
            case EAST -> {
                if (x < ship_x || x - ship_x > ship_length - 1) {
                    break;
                }

                if (ship_y == y) {
                    return true;
                }
            }
            case WEST -> {
                if (x > ship_x || ship_x - x > ship_length - 1) {
                    break;
                }

                if (ship_y == y) {
                    return true;
                }
            }
            default -> {
                return false;
            }
        }
        return false;
    }

    private void printLine() {
        System.out.print("     +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+");
    }

    private void printDoubleLine() {
        printLine();
        System.out.print("  ");
        printLine();
    }

    private char getCharacter(Map map, int x, int y) {
        if (map.getMap(x, y) == MapStatus.HIT) {
            return 'X';
        } else if (map.getMap(x, y) == MapStatus.MISS) {
            return 'O';
        } else {
            return ' ';
        }
    }

    private Ship newShip(ShipType type) {
        int x;
        int y;
        Direction direction;
        while (true) {
            try {
                String s1 = type.toString().toLowerCase().replaceAll("_", " ") + " (length " + type.getLength() + ")";
                System.out.println("Where is your " + s1 + " (A0)?");
                String input = getInput_String();
                try {
                    x = Integer.parseInt(input.substring(1, 3));
                } catch (IndexOutOfBoundsException e) {
                    x = Integer.parseInt(input.substring(1, 2));
                }
                if (x < 1 || x > 10) {
                    System.out.println("X-Coordinate must be between 1 and 10!");
                    continue;
                }
                y = input.toLowerCase().charAt(0) - 96;
                if (y < 1 || y > 10) {
                    System.out.println("Y-Coordinate must be between A and J!");
                    continue;
                }
                System.out.println("What direction does your ship go (north, south, east, west)?");
                String dir = getInput_String();
                switch (dir) {
                    case "north", "n" -> direction = Direction.NORTH;
                    case "south", "s" -> direction = Direction.SOUTH;
                    case "east", "e" -> direction = Direction.EAST;
                    case "west", "w" -> direction = Direction.WEST;
                    default -> {
                        System.out.println("Please enter a valid direction!");
                        continue;
                    }
                }
                System.out.println("Is this correct?");
                input = getInput_String();
                if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("yes")) continue;
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number!");
                s.next();
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Error");
                continue;
            }
        }

        return new Ship(x, y, direction, type);
    }

}
