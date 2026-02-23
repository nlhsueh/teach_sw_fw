import java.util.*;

public class MangaArtist implements Profitable, Comparable<MangaArtist> {
    private String name;
    private String birthDate;
    private String nationality;
    private String deathDate;
    private double profit;

    public MangaArtist(String name, String birthDate, String nationality) {
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public double getProfit() {
        return profit;
    }

    public void died(String date) {
        this.deathDate = date;
    }

    public Manga create(String title, int createDate) {
        return new Manga(title, createDate);
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public int compareTo(MangaArtist other) {
        return Double.compare(other.getProfit(), this.getProfit());
    }
}
