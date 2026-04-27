package abstractfactory.maze;

class MazeFactory {
    public Maze makeMaze() { return new Maze(); }
    public Room makeRoom(int n) { return new Room(n); }
    public Wall makeWall() { return new Wall(); }
    public Door makeDoor(Room r1, Room r2) { return new Door(r1, r2); }
}

class MazeGame {
    static String NORTH = "north";
    static String EAST = "east";
    static String SOUTH = "south";
    static String WEST = "west";

    public Maze createMaze(MazeFactory factory) {
        Maze maze = factory.makeMaze();
        Room r1 = factory.makeRoom(1);
        Room r2 = factory.makeRoom(2);
        Door door = factory.makeDoor(r1, r2);

        maze.addRoom(r1);
        maze.addRoom(r2);
        r1.setSide(NORTH, factory.makeWall());
        r1.setSide(EAST, door);
        r1.setSide(SOUTH, factory.makeWall());
        r1.setSide(WEST, factory.makeWall());
        r2.setSide(NORTH, factory.makeWall());
        r2.setSide(EAST, factory.makeWall());
        r2.setSide(SOUTH, factory.makeWall());
        r2.setSide(WEST, door);
        return maze;
    }
}

class EnchantedMazeFactory extends MazeFactory {
    @Override
    public Maze makeMaze() { return new EnchantedMaze(); }
    @Override
    public Room makeRoom(int n) { return new EnchantedRoom(n); }
    @Override
    public Wall makeWall() { return new EnchantedWall(); }
    @Override
    public Door makeDoor(Room r1, Room r2) { return new EnchantedDoor(r1, r2); }
}

// 輔助類別
class Maze { void addRoom(Room r) {} }
class Room { Room(int n) {} void setSide(String d, Object o) {} }
class Wall {}
class Door { Door(Room r1, Room r2) {} }
class EnchantedMaze extends Maze {}
class EnchantedRoom extends Room { EnchantedRoom(int n) { super(n); } }
class EnchantedWall extends Wall {}
class EnchantedDoor extends Door { EnchantedDoor(Room r1, Room r2) { super(r1, r2); } }
