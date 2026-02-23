public class MangaVolume {
    private Manga manga;
    private String title;
    private String publishDate;
    private double price;
    private int salesVolume;

    public MangaVolume(Manga manga, String title) {
        this.manga = manga;
        this.title = title;
    }

    public void setPublishDate(String date) {
        this.publishDate = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSalesVolume(int volume) {
        this.salesVolume = volume;
    }

    public double getSalesAmount() {
        return salesVolume * price;
    }
}
