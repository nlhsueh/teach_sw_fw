package factorymethod.maze;

// #### 方法1: 未使用設計樣式
class MazeGameLegacy {
    public Maze createMaze() {
        Maze maze = new Maze();
        Room r1 = new Room(1);
        Room r2 = new Room(2);
        Door door = new Door(r1, r2);

        maze.addRoom(r1);
        maze.addRoom(r2);
        r1.setSide(Direction.NORTH, new Wall());
        r1.setSide(Direction.EAST, door);
        r1.setSide(Direction.SOUTH, new Wall());
        r1.setSide(Direction.WEST, new Wall());
        r2.setSide(Direction.NORTH, new Wall());
        r2.setSide(Direction.EAST, new Wall());
        r2.setSide(Direction.SOUTH, new Wall());
        r2.setSide(Direction.WEST, door);
        return maze;
    }
}

// #### 方法2: 使用設計樣式
public class MazeGame {
    public Maze createMaze() {
        Maze maze = makeMaze();
        Room r1 = makeRoom(1);
        Room r2 = makeRoom(2);
        Door door = makeDoor(r1, r2);

        maze.addRoom(r1);
        maze.addRoom(r2);
        r1.setSide(Direction.NORTH, makeWall());
        r1.setSide(Direction.EAST, door);
        r1.setSide(Direction.SOUTH, makeWall());
        r1.setSide(Direction.WEST, makeWall());
        r2.setSide(Direction.NORTH, makeWall());
        r2.setSide(Direction.EAST, makeWall());
        r2.setSide(Direction.SOUTH, makeWall());
        r2.setSide(Direction.WEST, door);
        return maze;
    }

    public Maze makeMaze() { return new Maze(); }
    public Room makeRoom(int n) { return new Room(n); }
    public Wall makeWall() { return new Wall(); }
    public Door makeDoor(Room r1, Room r2) { return new Door(r1, r2); }
}

class EnchantedMazeGame extends MazeGame {
    @Override
    public Room makeRoom(int n) { return new EnchantedRoom(n); }
    @Override
    public Wall makeWall() { return new EnchantedWall(); }
    @Override
    public Door makeDoor(Room r1, Room r2) { return new EnchantedDoor(r1, r2); }
}

// 輔助類別
class Maze { void addRoom(Room r) {} }
class Room { Room(int n) {} void setSide(Direction d, Object o) {} }
class Wall {}
class Door { Door(Room r1, Room r2) {} }
class EnchantedRoom extends Room { EnchantedRoom(int n) { super(n); } }
class EnchantedWall extends Wall {}
class EnchantedDoor extends Door { EnchantedDoor(Room r1, Room r2) { super(r1, r2); } }
enum Direction { NORTH, EAST, SOUTH, WEST }
