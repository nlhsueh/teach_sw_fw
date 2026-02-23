package question01;

import java.util.*;

public class Dragon {
    public static void main(String[] args) {
        MangaArtist akira = new MangaArtist("鳥山明", "1955/4/5", "Japan");
        Manga dragon = akira.create("七龍珠", 1984); // name, create date
        Publisher shueisha = new Publisher("集英社", 1926); // name, creation date
        shueisha.publish(dragon, 2002); // manga, year

        Manga slump = akira.create("怪博士與機器娃娃", 1980);
        shueisha.publish(slump, 1980);

        akira.died("2024/3/1"); // set author dead date

        System.out.println(akira);
        System.out.println(dragon);
        System.out.println(slump);
        System.out.println(shueisha);
    }
}

class MangaArtist {
    private String name, birthday, country;
    private String diedDate;
    ArrayList<Manga> mangaList= new ArrayList<>();
    public MangaArtist(String name, String birthday, String country) {
        this.name = name;
        this.birthday = birthday;
        this.country = country;
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
        String s = String.format("%s is a Manga artist, born at %s, died at %s; ", name, birthday, diedDate);
        return s.concat("He wrote many mangas: \n\t").concat(mangaList.toString());
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
        return (String.format("Managa name: %s; created in: %d", name, year));
    }
}

class Publisher {
    private String title;
    private int year;
     ArrayList<Manga> mangaList = new ArrayList<>();
     Map<Manga, Integer> mangaMap = new HashMap<>(); // manga and its published year

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
        for (Map.Entry<Manga, Integer> x : mangaMap.entrySet()) {
            String mangaStr = String.format("\n\t %s, published in %d", x.getKey(), x.getValue());
            s = s.concat(mangaStr);
        }
        return (s);
    }
}