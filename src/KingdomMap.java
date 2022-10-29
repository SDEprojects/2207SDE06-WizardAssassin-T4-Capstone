import java.util.*;

class KingdomMap {
    private String[][] kingdomMap;

    public KingdomMap(String[][] kingdomMap) {
        this.setKingdomMap(kingdomMap);
    }

    public String[][] getKingdomMap() {
        return kingdomMap;
    }

    public void setKingdomMap(String[][] kingdomMap) {
        this.kingdomMap = kingdomMap;
    }

    public static void printMapHeader() {
        System.out.println("\033[30m                         *********************************************\033[0m");
        System.out.println("\033[33m                                             MAP                       \033[0m");
        System.out.println("\033[30m                         *********************************************\033[0m");
    }

    public void printMap() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.printf("\033[33m%2s\033[0m", this.getKingdomMap()[i][j]);
            }
            System.out.println();
        }
    }

    public static List<KingdomMap> showKingdomMap () {
        List<KingdomMap> mapList = new ArrayList<>();
         String[][] areaMap = {
                 {"*", "***", "***", "**********", "*****************", "*********", "**************", "***", "***", "***", "***", "***", "***", "***", "****************"},
                 {"*", "************", "|", "**************", "|", " Lab", "|", " Wizards Tower  ", "|", "*", "*", "*", "|", "   Church  ", " | *****************"},
                 {"*", "************", "|", "**************", "|", "    ", "|", " ____X__________", "|", "*", "*", "*", "|", "           ", " | *****************"},
                 {"*", "************", "|", "**************", "|", "    ", " ", "                ", "|", "*", "*", "*", "|", " ____  ____", " | *****************"},
                 {"*", "************", "|", "**************", "|", "    ", "|", "   Foyer        ", "|", " ", " ", " ", " ", "           ", "              ******"},
                 {"*", "************", "|", "**************", "|", " ___", " | ____XX_________", "|", " ", " ", " ", " ", " ", "         ", "              ******"},
                 {"*", "************", "|", "**************", "|", "    ", "           ", "        |", " ", " ", " ", " ", " ", "          ", "             ******"},
                 {"*", "************", "|", "**************", "|", "    ", "           ", "        |", " ", " ", " ", " ", " ", "         Courtyard     ", "******"},
                 {"*", "            ", "|", "              ", "|", "    ", "           ", "        X", " ", " ", " ", " ", " ", "                       ", "******"},
                 {"*", "            ", "X", "              ", "|", "    ", "           ", "        X", " ", " ", " ", " ", " ", "                       ", "******"},
                 {"*", "            ", "X", "              ", "X", "    ", " Great Hall", "        |", " ", " ", " ", " ", " ", "                       ", "******"},
                 {"*", "King's Suite", "|", "    Lounge    ", "X", "    ", "           ", "        |", " ", " ", " ", " ", " ", "                       ", "******"},
                 {"*", "            ", "|", " _____  ______", "|", "____", "_____  ____", "__", "_____________", "___", "_____  _____", "___", "______", "*****", "****"},
                 {"*", "            ", "|", "              ", "|", "    ", "           ", "|", " |", "        ", " ***", " Watchtower", " |||", " Armory", "|*********"},
                 {"*", "            ", "|", " Royal Library", "|", "    ", "  Kitchen  ", "|", " |", " Dungeon", " |||", "           ", " ***", "       ", "|*********"},
                 {"*", "***", "***", "***", "***", "***", "***", "***", "***", "***", "***", "***", "***************", "*****************", "****************************"}
         };
         mapList.add(new KingdomMap(areaMap));
         return mapList;
    }
}