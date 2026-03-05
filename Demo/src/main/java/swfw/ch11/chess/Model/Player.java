package swfw.ch11.chess.Model;

public class Player {
    private String name;
    private Gender gender;
    private int loseTime;
    private int winTime;
    private Side side;

    public Player(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.loseTime = 0;
        this.winTime = 0;
        this.setSide(Side.UNDEFINED);
    }

    public String getName() {
        return this.name;
    }

    public void incrementLoseTime() {
        ++this.loseTime;
    }

    public void incrementWinTime() {
        ++this.winTime;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return this.side;
    }

    public int getLoseTime() {
        return loseTime;
    }

    public int getWinTime() {
        return winTime;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
