package question02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dragon {
    public static void main(String[] args) {
        MangaArtist akira = new MangaArtist("Toriyama Akira", "1955/4/5", "Japan");
        Manga dragon = akira.create("七龍珠", 1984); // name, create date
        Publisher shueisha = new Publisher("集英社", 1926); // name, creation date
        shueisha.publish(dragon, 2002);
        shueisha.setProfitRate(dragon, 0.8f); // 出版社分潤 0.8, 所以作者是分潤 1-0.8 = 0.2

        MangaVolume dragon01 = new MangaVolume(dragon, "七龍珠第一集");
        dragon.publish(dragon01, "2002/3/1", 500); // 發行日，價格 (publish date, price)
        dragon01.setSalesVolume(100000); // (copies sold)

        MangaVolume dragon02 = new MangaVolume(dragon, "七龍珠第二集");
        dragon.publish(dragon01, "2002/4/1", 550); // 發行日，價格 (publish date, price)
        dragon01.setSalesVolume(100000); // (copies sold)

        Profitable[] earners = new Profitable[] { (Profitable) akira, (Profitable) shueisha };
        for (Profitable x : earners) {
            // 印出獲利狀況 (print profit status)
            String name = "";
            if (x instanceof Author) name = ((Author)x).getName();
            if (x instanceof Publisher) name = ((Publisher)x).getTitle();
            System.out.printf("The profit of %s is %.2f", name, x.getProfit());
            System.out.println("\n");
        }
    }
}

interface Profitable {
    public double getProfit();
}
class Author {
    protected String name, birthday, country;
    protected String diedDate;
    public Author(String name, String birthday, String country) {
        this.name = name;
        this.birthday = birthday;
        this.country = country;
    }
    public void died(String diedDate) {
        this.diedDate = diedDate;
    }
    public String getName() {return this.name; }

}

class MangaArtist extends Author implements Profitable {

    ArrayList<Manga> mangaList= new ArrayList<>();
    public MangaArtist(String name, String birthday, String country) {
        super(name, birthday, country);
    }

    public Manga create(String mangaName, int year) {
        Manga m = new Manga(mangaName, year);
        mangaList.add(m);
        m.setAuthor(this);
        return m;
    }

    @Override
    public double getProfit() {
        double sum = 0.0;
        for (Manga m : mangaList) {
            sum += m.getProfit() * m.getAuthorProfitRate();
        }
        return sum;
    }

    public String toString() {
        String s = String.format("%s is a Manga artist, born at %s, died at %s; ", name, birthday, diedDate);
        return s.concat("He wrote many mangas: \n\t").concat(mangaList.toString());
    }
}

class Manga implements Profitable {
    private String name;
    private MangaArtist author;
    private int year;

    Map<MangaVolume, Object> volumes = new HashMap<>();
    // ManagaVolume, (publish date, price)
    private float authorProfitRate;
    public Manga(String manga_name, int year) {
        this.name = manga_name;
        this.year = year;
    }

    public void setAuthor(MangaArtist author) {
        this.author = author;
    }

    public void setAuthorProfitRate(float rate) {
        this.authorProfitRate = rate;
    }

    public float getAuthorProfitRate() {
        return this.authorProfitRate;
    }

    public MangaArtist getAuthor() {
        return this.author;
    }

    public void publish(MangaVolume volume, String date, int price) {
        Object[] dateNprice = {date, price};
        volume.price = price;
        volumes.put(volume, dateNprice);
    }
    public String toString() {
        return (String.format("Manage name: %s; created in: %d", name, year));
    }

    @Override
    public double getProfit() {
        double sum = 0.0;
        for (Map.Entry<MangaVolume, Object> v : volumes.entrySet()) {
            MangaVolume theVolume = v.getKey();
            sum += theVolume.getSalesVolume() * theVolume.price;
        }
        return sum;
    }
}

class Publisher implements Profitable{
    private String title;
    private int year;
     Map<Manga, Object> mangaMap = new HashMap<>();
     Map<Manga, Object> mangaProfitRate = new HashMap<>();

    public Publisher(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return this.title;
    }
    public void setProfitRate(Manga m, float rate) {
        if (mangaMap.containsKey(m)) {
            mangaProfitRate.put(m, Float.valueOf(rate));
        }
        float rateForAuthor = 1-rate;
        m.setAuthorProfitRate(rateForAuthor);
    }
    public void publish(Manga m, int year) {
        mangaMap.put(m, Integer.valueOf(year));
    }
    public String toString() {
        String s = String.format("Publish title: %s; created in: %d", title, year);
        s = s.concat("; It publish the mangas:");
        for (Map.Entry<Manga, Object> x : mangaMap.entrySet()) {
            s = s.concat("\n\t" + x);
        }
        return (s);
    }

    public double getProfit() {
        double sum = 0.0;
        for (Map.Entry<Manga, Object> m : mangaMap.entrySet()) {
            Manga theManga = (Manga)m.getKey();
            float rate = (float)mangaProfitRate.get(theManga);
            sum += theManga.getProfit() * rate;
        }
        return sum;
    }
}

class MangaVolume {
    private Manga manga;
    private String title;
    private float saleVolume;
    float price;
    public MangaVolume(Manga m, String title) {
        this.title = title;
        this.manga = m;
    }

    public void setSalesVolume(float sales) {
        this.saleVolume = sales;
    }

    public double getSalesVolume() {
        return this.saleVolume;
    }
}


