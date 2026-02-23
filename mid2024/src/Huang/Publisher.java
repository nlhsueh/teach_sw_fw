import java.util.List;
import java.util.ArrayList;

public class Publisher implements Profitable {
    private String name;
    private int creationDate;
    private double profitRate;
    private List<Manga> mangas;

    public Publisher(String name, int creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public void publish(Manga manga, int year) {
        System.out.println("Publishing " + manga.getTitle() + " in year " + year);
    }

    public void setProfitRate(Manga manga, double rate) {
        this.profitRate = rate;
    }

    @Override
    public double getProfit() {
        List<Manga> mangas = new ArrayList<>();
        double totalProfit = 0.0;
        for (Manga manga : mangas) {
            System.out.println("Title: " + manga.getTitle() + ", Created: " + manga.getCreateDate());
            for (MangaVolume volume : manga.getVolumes()) {
                totalProfit += volume.getSalesAmount() * profitRate;
            }
        }
        return totalProfit;
    }

    @Override
    public String toString() {
        return name;
    }
}