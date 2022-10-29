import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import com.google.gson.Gson;

class Game implements Verbs  {

    private Data obj = makeObj();
    private Location inventory = obj.getLocations().get(13);
    private List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));
    private Scanner inputScanner = new Scanner(System.in);
    private int count = 0;
    private Location currentLocation = obj.getLocations().get(0);
    private String oldLocation = "";

    Game() throws IOException {
    }

    public Data makeObj() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("./resources/Location.json"));
        Data obj = gson.fromJson(reader, Data.class);
        return obj;
    }

    public void execute() throws IOException {
        title();
        gameObjective();
        beginGame();
    }

    private void title() throws IOException {
        System.out.println();
        System.out.println("\033[35m" + Files.readString(Path.of("./resources/welcome.txt")) + "\033[0m");
        System.out.println();
    }

    private void gameObjective() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("./resources/introduction.json"));

        Introduction obj = gson.fromJson(reader, (Type) Introduction.class);
        String gameIntro = obj.getIntroduction();
        String gameObj = obj.getObjective();
        String gameWin = obj.getWin();
        System.out.println("\033[35m" + gameIntro + "\n" + gameObj + "\n" + gameWin + "\033[0m");
        System.out.println();
    }

    private void beginGame() throws IOException {
        String start;

        System.out.println("\033[35m" + "Do you want to start the game? yes | no" + "\033[0m");
        start = inputScanner.nextLine().trim().toLowerCase();
        if (start.equals("yes") || start.equals("y")) {

            ClearConsole.clearConsole();
            chooseLocation();
        } else if (start.equals("no") || start.equals("n")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        } else {
            System.out.println("Please enter 'yes' to continue or 'no' to quit the game.");
            beginGame();
        }
    }

    private void quitGame() throws IOException {
        String quit;

        System.out.println("Are you sure you want to 'quit'? yes| no");
        quit = inputScanner.nextLine().trim().toLowerCase();
        if (quit.equals("yes")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        }
        else {
            System.out.println("\n\u001B[91m                         *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
            chooseLocation();
        }
    }

    public void getItem(String itemInput, Location currentLocation) throws IOException {

        List<String> roomItems = new ArrayList<String>(Arrays.asList(currentLocation.getItem()));

        // Add items to room "drop", use later if we add DROP feature
//        roomItems.add("stones");
//        roomItems.add("pebbles");
        // Pick up item step 1, remove from room items
        roomItems.remove(itemInput);
        inventoryItems.add(itemInput);

        // Pick up item step 2, Put item in inventory
        String[] toInventory = new String[inventoryItems.size()];
        toInventory = inventoryItems.toArray(toInventory);
        inventory.setItem(toInventory);

        // INVENTORY PRINT OUT
        System.out.println("\n");
        System.out.printf("You picked up a \033[32m%s\033[0m and added it to your inventory.\n", itemInput);
        // END of INVENTORY

        // NOTE convert roomItems List to array. Update masterObj with changes
        String[] updatedRoomItems = roomItems.toArray(new String[0]);
        currentLocation.setItem(updatedRoomItems);
    }

    public void checkInventory() {
        System.out.println();
        System.out.println("*** Inventory ***");
        System.out.printf("\033[92m%s\033[0m", inventoryItems);
        System.out.println();
    }


    public void chooseLocation() throws IOException {
        Gson gson = new Gson();

//        inventoryItems.add("password");
//        inventoryItems.add("diamond key");
//        inventoryItems.add("sword");
//        inventoryItems.add("tunic");
//        inventoryItems.add("wizard robes");
//        inventoryItems.add("knife");

        Reader read = Files.newBufferedReader(Paths.get("./resources/characters.json"));
        Characters object = gson.fromJson(read, Characters.class);

        while (true) {

            if (currentLocation.getName().equals("Laboratory") && (inventoryItems.contains("poison")))
            {
                System.out.println("You have poisoned the wizard. You return home as a hero who saved your kingdom.");
                break;
            }

            if(!oldLocation.equals(currentLocation.getName())) {
                System.out.println("\n\u001B[35m                                              *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");

                System.out.println(currentLocation.getDescription() + "\n");

                for (ExtraCharacters extraCharacters : object.getCharacters())
                    if ((currentLocation.getName().equals(extraCharacters.getRoom())))
                        System.out.printf("You see a \u001B[93m %s \u001B[0m. It says: %s%n", extraCharacters.getName(), extraCharacters.getQuote());
                System.out.println();
                if(currentLocation.getName().equals("Wizard's Foyer")) {
                    if(!inventoryItems.contains("wizard robes")) {
                        System.out.println("\033[91mThe monster bites your head off and you die!\033[0m");
                        System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                        break;
                    }
                    else {
                        System.out.println("\033[91mMonster:\033[0m Oh, good thing you have those \033[92mWIZARD ROBES\033[0m on, master. I was about to bite your head off until I saw those.");
                    }
                }
                if(currentLocation.getName().equals("Wizard’s Chambers")) {
                    if(!inventoryItems.contains("knife")) {
                        System.out.println("\033[91mThe Wizard suddenly changes his mind and blasts your head off with a thunder bolt... and you die!\033[0m");
                        System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                        break;
                    }
                    else {
                        System.out.println("\033[36mThe Wizard suddenly changes his mind and attacks you with a thunder bolt but you matrix dodge it and shank him with the\033[0m \033[92mKNIFE\033[0m \033[36mand he dies!\033[0m");
                        System.out.println("You have shanked the wizard to death. You return home as a hero who saved your kingdom!");
                        break;
                    }
                }

                System.out.printf("You see these items: \u001B[32m %s \u001B[0m%n", Arrays.deepToString(currentLocation.getItem()));
                System.out.println("From the " + currentLocation.getName() + " you can go to the:");
                for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
                    System.out.printf("       \u001B[31m %s: %s \u001B[0m%n", direction.getKey(), direction.getValue());

                oldLocation = currentLocation.getName();
            }

            System.out.println("");
            System.out.println("\033[36m What would you like to do now?\033[0m\n\033[90mEnter 'quit' to exit game.\nEnter 'view' to see the map.\nEnter 'help' for list of valid commands.\n Enter 'inventory' to list all your items.\033[0m");
            String userInput = inputScanner.nextLine().trim().toLowerCase();

            String[] parseInput = userInput.split(" ");

            if(userInput.equals("quit")) {
                quitGame();
            }
            else if(userInput.equals("inventory")) {
                checkInventory();
            }

            else if(userInput.equals("help")) {
                System.out.println("All commands must be in this format 'VERB<space>NOUN'\nOr 'quit' to exit game");
                HelpMenu.printMenuHeader();
                HelpMenu.buildMenu().forEach(HelpMenu::printMenu);
            }
            else if(userInput.equals("view")) {
                KingdomMap.printMapHeader();
                KingdomMap.showKingdomMap().forEach(KingdomMap::printMap);
                System.out.println("\n\u001B[91m                         *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
            }
            else if(parseInput.length == 2 || parseInput.length == 3) {
                String inputVerb = parseInput[0];
                String inputNoun = parseInput[1];
                if(parseInput.length == 3) {
                    inputNoun = String.format("%s %s", inputNoun, parseInput[2]);
                }
                if (Verbs.getMoveActions().contains(inputVerb)) {

                        if (currentLocation.directions.get(inputNoun) != null) {
                            String locationInput = currentLocation.directions.get(inputNoun);
                            if(locationInput.equals("Great Hall") && currentLocation.getName().equals("Courtyard") && count == 0) {
                                if(inventoryItems.contains("password")) {
                                    System.out.println("\033[31mGuard:\033[0m That's the right \033[92mPASSWORD\033[0m. Go ahead and pass.");
                                    count++;
                                    currentLocation = obj.getPickedLocation(locationInput);
                                }
                                else {
                                    System.out.println("\033[31mGuard:\033[0m Wrong \033[92mPASSWORD\033[0m! Get outta here, ya scum!");
                                    System.out.println("\n\u001B[91m                         *********  You reamin in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
                                }
                            }
                            else if(locationInput.equals("Royal Lounge") && currentLocation.getName().equals("Great Hall") && count == 1) {
                                if(inventoryItems.contains("tunic") && inventoryItems.contains("sword")) {
                                    System.out.println("\033[31mGuard:\033[0m I don't know you... but you have the Kingdom's \033[92mTUNIC\033[0m... and that \033[92mSWORD\033[0m... You must be new... go ahead and pass.");
                                    count++;
                                    currentLocation = obj.getPickedLocation(locationInput);
                                }
                                else {
                                    System.out.println("\033[31mGuard:\033[0m Where do you think you're goin? Only knights can pass through here.\nAnd not just any bloak with a Kingdom's \033[92mTUNIC\033[0m.\nYou need a \033[92mSWORD\033[0m too.");
                                    System.out.println("\n\u001B[91m                         *********  You remain in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
                                }
                            }
                            else if(locationInput.equals("Wizard's Foyer") && currentLocation.getName().equals("Great Hall") && count == 2) {
                                if(inventoryItems.contains("diamond key")) {
                                    System.out.println("That \033[92mDIAMOND KEY\033[0m did the trick. I'm in...");
                                    count++;
                                    currentLocation = obj.getPickedLocation(locationInput);
                                }
                                else {
                                    System.out.println("Hmm, it's locked. There's an emblem in the shape of a \033[92mDIAMOND\033[0m on the door");
                                    System.out.println("\n\u001B[91m                         *********  You remain in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
                                }
                            }
                            else {
                                currentLocation = obj.getPickedLocation(locationInput);
                            }
                        }
                        else {
                            System.out.println("\n\u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m is not a valid direction. Choose again...");
                        }
                }
                else if (Verbs.getItemActions().contains(inputVerb)) {
                        if (Arrays.toString(currentLocation.getItem()).contains(inputNoun)){
                            getItem(inputNoun, currentLocation);
                        }
                        else {
                            System.out.println("\n\u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m is not a valid \033[92mITEM\033[0m. Choose again...");
                        }

                }
                else if (Verbs.getCharacterActions().contains(inputVerb)) {
                    System.out.println("This VERB is for a character interaction");
                }
                else if (Verbs.getAreaActions().contains(inputVerb)) {
                    System.out.println("This VERB is for area interactions");
                }
                else {
                    System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'view' or 'help' or 'inventory'");
                }
            }
            else {
                System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'view' or 'help' or 'inventory'");
            }
        }
    }
}
