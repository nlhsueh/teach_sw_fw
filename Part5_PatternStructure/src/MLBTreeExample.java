import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// 1. Common Component
abstract class MLBComponent {
    protected String name;

    public MLBComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(MLBComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(MLBComponent component) {
        throw new UnsupportedOperationException();
    }

    public MLBComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }
}

// 2. Composite: League and Division
class League extends MLBComponent {
    private List<MLBComponent> children = new ArrayList<>();

    public League(String name) {
        super(name);
    }

    @Override
    public void add(MLBComponent component) {
        children.add(component);
    }

    @Override
    public MLBComponent getChild(int i) {
        return children.get(i);
    }

    public List<MLBComponent> getChildren() {
        return children;
    }
}

class Division extends League {
    public Division(String name) {
        super(name);
    }
}

// 3. Leaf: Team
class Team extends MLBComponent {
    public Team(String name) {
        super(name);
    }
}

// 4. GUI Client using JTree
public class MLBTreeExample extends JFrame {
    public MLBTreeExample() {
        setTitle("MLB Teams");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MLBComponent mlb = createMLBStructure();
        DefaultMutableTreeNode root = buildTree(mlb);
        JTree tree = new JTree(root);

        add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    private MLBComponent createMLBStructure() {
        League mlb = new League("MLB");

        // American League
        League americanLeague = new League("American League");
        
        Division alEast = new Division("East");
        alEast.add(new Team("Baltimore Orioles"));
        alEast.add(new Team("Boston Red Sox"));
        alEast.add(new Team("New York Yankees"));
        alEast.add(new Team("Tampa Bay Rays"));
        alEast.add(new Team("Toronto Blue Jays"));

        Division alCentral = new Division("Central");
        alCentral.add(new Team("Chicago White Sox"));
        alCentral.add(new Team("Cleveland Guardians"));
        alCentral.add(new Team("Detroit Tigers"));
        alCentral.add(new Team("Kansas City Royals"));
        alCentral.add(new Team("Minnesota Twins"));

        Division alWest = new Division("West");
        alWest.add(new Team("Houston Astros"));
        alWest.add(new Team("Los Angeles Angels"));
        alWest.add(new Team("Oakland Athletics"));
        alWest.add(new Team("Seattle Mariners"));
        alWest.add(new Team("Texas Rangers"));

        americanLeague.add(alEast);
        americanLeague.add(alCentral);
        americanLeague.add(alWest);

        // National League
        League nationalLeague = new League("National League");
        
        Division nlEast = new Division("East");
        nlEast.add(new Team("Atlanta Braves"));
        nlEast.add(new Team("Miami Marlins"));
        nlEast.add(new Team("New York Mets"));
        nlEast.add(new Team("Philadelphia Phillies"));
        nlEast.add(new Team("Washington Nationals"));

        Division nlCentral = new Division("Central");
        nlCentral.add(new Team("Chicago Cubs"));
        nlCentral.add(new Team("Cincinnati Reds"));
        nlCentral.add(new Team("Milwaukee Brewers"));
        nlCentral.add(new Team("Pittsburgh Pirates"));
        nlCentral.add(new Team("St. Louis Cardinals"));

        Division nlWest = new Division("West");
        nlWest.add(new Team("Arizona Diamondbacks"));
        nlWest.add(new Team("Colorado Rockies"));
        nlWest.add(new Team("Los Angeles Dodgers"));
        nlWest.add(new Team("San Diego Padres"));
        nlWest.add(new Team("San Francisco Giants"));

        nationalLeague.add(nlEast);
        nationalLeague.add(nlCentral);
        nationalLeague.add(nlWest);

        mlb.add(americanLeague);
        mlb.add(nationalLeague);

        return mlb;
    }

    private DefaultMutableTreeNode buildTree(MLBComponent component) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(component.getName());

        if (component instanceof League) {
            League league = (League) component;
            for (MLBComponent child : league.getChildren()) {
                node.add(buildTree(child));
            }
        }
        return node;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MLBTreeExample().setVisible(true);
        });
    }
}
