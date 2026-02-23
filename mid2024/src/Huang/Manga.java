import java.util.List;
import java.util.ArrayList;

public class Manga {
    private String title;
    private int createDate;
    private List<MangaVolume> volumes;

    public Manga(String title, int createDate) {
        this.title = title;
        this.createDate = createDate;
        this.volumes = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public int getCreateDate() {
        return createDate;
    }

    public List<MangaVolume> getVolumes() {
        return volumes;
    }

    public void publish(MangaVolume volume, String publishDate, double price) {
        volume.setPublishDate(publishDate);
        volume.setPrice(price);
        volumes.add(volume);
    }

    public double getTotalProfit() {
        double totalProfit = 0.0;
        for (MangaVolume volume : volumes) {
            totalProfit += volume.getSalesAmount();
        }
        return totalProfit;
    }

    @Override
    public String toString() {
        return title;
    }
}
