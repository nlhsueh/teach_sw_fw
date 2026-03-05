package swfw.ch11.chess.Panel;

import swfw.ch11.chess.MainFrame;
import swfw.ch11.chess.Manager.PlayerManager;
import swfw.ch11.chess.Model.Gender;
import swfw.ch11.chess.Model.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private DefaultListModel<String> playerListModel;
    private JList<String> playerList;
    private JTextArea playerInfoArea;

    public PlayerPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("玩家列表與新增", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        playerListModel = new DefaultListModel<>();
        playerList = new JList<>(playerListModel);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshPlayerList();

        playerInfoArea = new JTextArea(5, 30);
        playerInfoArea.setEditable(false);
        playerInfoArea.setLineWrap(true);
        playerInfoArea.setWrapStyleWord(true);

        playerList.addListSelectionListener(e -> {
            int index = playerList.getSelectedIndex();
            if (index != -1) {
                Player selectedPlayer = PlayerManager.getPlayers().get(index);
                playerInfoArea.setText("""
                    名稱: %s
                    性別: %s
                    勝場: %d
                    敗場: %d
                    """.formatted(
                        selectedPlayer.getName(),
                        convertGenderToChinese(selectedPlayer.getGender()),
                        selectedPlayer.getWinTime(),
                        selectedPlayer.getLoseTime()
                ));
            }
        });

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(new JScrollPane(playerList));
        centerPanel.add(new JScrollPane(playerInfoArea));
        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField nameField = new JTextField(10);
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"男", "女", "其他"});
        JButton addButton = new JButton("新增玩家");

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                String genderStr = (String) genderBox.getSelectedItem();
                Gender gender = switch (genderStr) {
                    case "男" -> Gender.MALE;
                    case "女" -> Gender.FEMALE;
                    default -> Gender.OTHER;
                };
                Player newPlayer = new Player(name, gender);
                PlayerManager.addPlayer(newPlayer);
                refreshPlayerList();
                nameField.setText("");
            }
        });

        inputPanel.add(new JLabel("名稱:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("性別:"));
        inputPanel.add(genderBox);
        inputPanel.add(addButton);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("返回");
        backButton.addActionListener(e -> frame.showMainMenuPanel());
        backPanel.add(backButton);

        southPanel.add(inputPanel);
        southPanel.add(backPanel);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void refreshPlayerList() {
        playerListModel.clear();
        for (Player p : PlayerManager.getPlayers()) {
            playerListModel.addElement(p.getName());
        }
    }

    private String convertGenderToChinese(Gender gender) {
        return switch (gender) {
            case MALE -> "男";
            case FEMALE -> "女";
            default -> "其他";
        };
    }
}

