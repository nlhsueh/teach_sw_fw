package question03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class Dragon {
    public static void main(String[] args) {
        MangaArtist akira = new MangaArtist("鳥山明", "1955/04/05", "Japan");
        MangaArtist oda = new MangaArtist("尾田榮一郎", "1975/01/01", "Japan");
        MangaArtist aoyama = new MangaArtist("青山剛昌", "1963/6/22", "Japan");
        MangaArtist inoue = new MangaArtist("井上雄彥", "1967/01/12", "Japan");
        MangaArtist takahashi = new MangaArtist("高橋留美子", "1957/10/10", "Japan");


        inoue.setProfit(1000);
        takahashi.setProfit(800);
        akira.setProfit(30000);
        oda.setProfit(2000);
        aoyama.setProfit(1500);

        ArrayList<MangaArtist> authors = new ArrayList<>();

        authors.add(inoue);
        authors.add(takahashi);
        authors.add(akira);
        authors.add(oda);
        authors.add(aoyama);

        Collections.sort(authors);

        for (MangaArtist author : authors) {
            System.out.println(author);
        }
    }
}

interface Profitable {
    public long getProfitable();
}
class MangaArtist implements Profitable, Comparable<Profitable> {
    private String name, birthday, country;
    private String diedDate;
    private long profit;
    ArrayList<Manga> mangaList= new ArrayList<>();
    public MangaArtist(String name, String birthday, String country) {
        this.name = name;
        this.birthday = birthday;
        this.country = country;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }

    public Manga create(String mangaName, int year) {
        Manga m = new Manga(mangaName, year);
        mangaList.add(m);
        return m;
    }

    public void died(String diedDate) {
        this.diedDate = diedDate;
    }

    public String toString() {
        String s = String.format("%s got profit %d million", this.name, this.profit);
        return s;
    }

    public long getProfitable() {
        return this.profit;
    }

    @Override
    public int compareTo(Profitable other) {
        return Long.compare(this.profit, other.getProfitable());
    }
}

class Manga {
    private String name;
    private int year;
    public Manga(String manga_name, int year) {
        this.name = manga_name;
        this.year = year;
    }

    public String toString() {
        return (String.format("Manage name: %s; created in: %d", name, year));
    }
}

class Publisher {
    private String title;
    private int year;
     ArrayList<Manga> mangaList = new ArrayList<>();
     Map<Manga, Object> mangaMap = new HashMap<>();

    public Publisher(String title, int year) {
        this.title = title;
        this.year = year;
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
}


