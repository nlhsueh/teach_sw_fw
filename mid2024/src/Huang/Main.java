import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        MangaArtist akira = new MangaArtist("鳥山明", "1955/4/5", "Japan");
        Manga dragon = akira.create("七龍珠", 1984);
        Publisher shueisha = new Publisher("集英社", 1926);
        shueisha.publish(dragon, 2002);
        Manga slump = akira.create("怪博士與機器娃娃", 1980);
        shueisha.publish(slump, 1980);
        akira.died("2024/3/1");
        MangaArtist oda = new MangaArtist("尾田榮一郎", "1975/01/01", "Japan");
        MangaArtist aoyama = new MangaArtist("青山剛昌", "1963/6/22", "Japan");
        MangaArtist inoue = new MangaArtist("井上雄彥", "1967/01/12", "Japan");
        MangaArtist takahashi = new MangaArtist("高橋留美子", "1957/10/10", "Japan");

        akira.setProfit(300000000);
        oda.setProfit(2000000000);
        aoyama.setProfit(1500000000);
        inoue.setProfit(1000000000);
        takahashi.setProfit(800000000);

        List<MangaArtist> artists = new ArrayList<>();
        artists.add(akira);
        artists.add(oda);
        artists.add(aoyama);
        artists.add(inoue);
        artists.add(takahashi);

        Collections.sort(artists);

        for (MangaArtist artist : artists) {
            System.out.println(artist.getName() + " - Profit: " + artist.getProfit());
        }

        System.out.println("Manga artist: " + akira.getName() + ", Birth date: " + akira.getBirthDate() + ", Nationality: " + akira.getNationality());
        System.out.println("Mangas created:");
        System.out.println("Title: " + dragon.getTitle() + ", Created: " + dragon.getCreateDate());
        System.out.println("Title: " + slump.getTitle() + ", Created: " + slump.getCreateDate());
        System.out.println("Mangas published by Shueisha:");
        System.out.println("Title: " + dragon.getTitle() + ", Published year: " + 2002);
        System.out.println("Title: " + slump.getTitle() + ", Published year: " + 1980);

        Profitable[] earners = new Profitable[]{akira, shueisha};
        for (Profitable earner : earners) {
            if (earner != null) {
                System.out.println("The profit of " + earner.toString() + " is " + earner.getProfit());
            } else {
                System.out.println("The earner is null.");
            }
        }
    }
}
