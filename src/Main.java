import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int mode = getGameMode();
        List<Character> characters = characterCreator(mode);
        PrintMenus.printInfoCharacters(characters);
        runBattle(characters, mode == 3);
    }

    public static int getGameMode(){
        PrintMenus.charactersCreatorMenu();
        Scanner scanner = new Scanner(System.in);
        String option;
        do {
            option = scanner.nextLine().trim();
            if (option.equals("1") || option.equals("2") || option.equals("3")){
                break;
            } else {
                System.out.println("Incorrect option. Enter a number from 1 to 3.");
            }
        } while (true);
        return Integer.parseInt(option);
    }

    public static List<Character> characterCreator(int mode){
        List<Character> characters = new ArrayList<>();
        if (mode == 2){
            characters = CharacterImporter.importCharactersFromCSV("./src/characters.csv");
        } else {
            CharacterInput characterInput;
            for (int i=0; i<2; i++){
                if (mode == 1){
                    characterInput = new CharacterInput();
                } else {

                    characterInput = new CharacterInput("random");
                }
                characters.add(characterInput.characterCreatorInput());
            }
        }
        return characters;
    }

    static void runBattle(List<Character> characters, boolean random) {
        int [] charactersIndex = getCharactersIndex(characters, random);
        Character firstCharacter;
        Character secondCharacter;

        int round = 1;
        boolean tie = false;
        do{
            round = 1;
            tie=false;
            firstCharacter = setCharacter(characters.get(charactersIndex[0]));
            secondCharacter = setCharacter(characters.get(charactersIndex[1]));
            while(firstCharacter.isAlive() && secondCharacter.isAlive()){
                firstCharacter.attack(secondCharacter);
                secondCharacter.attack(firstCharacter);
                Printer.asciiRoundStats(firstCharacter,secondCharacter,round);
                round++;
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    System.out.println("Sleep method has an error.");
                }
            }
            if(!firstCharacter.isAlive() && !secondCharacter.isAlive()){
                tie = true;
            }
        }while(tie);

        Character winner = firstCharacter.isAlive() ? firstCharacter : secondCharacter;
        System.out.println("The winner is: " + winner.getName());
        Printer.asciiWinner();
    }

    public static Character setCharacter(Character original){
        Character newCharacter;
        if (original instanceof Warrior) {
            newCharacter = new Warrior(((Warrior)original).getName(), ((Warrior)original).getHp(),
                    ((Warrior)original).getStamina(), ((Warrior)original).getStrength());

        } else {
            newCharacter = new Wizard(((Wizard)original).getName(), ((Wizard)original).getHp(),
                    ((Wizard)original).getMana(), ((Wizard)original).getIntelligence());
        }
        return newCharacter;
    }

    public static int [] getCharactersIndex(List<Character> characters, boolean random){
        Scanner scanner = new Scanner(System.in);
        int firstCharacterIndex = 0;
        int secondCharacterIndex = 1;
        if (!random) {
            System.out.println("Choose two characters for battle:");
            for (int i = 0; i < characters.size(); i++) {
                System.out.println((i + 1) + ". " + characters.get(i).getName());
            }
            do {
                System.out.print("Choose the first character (Enter corresponding number): ");
                firstCharacterIndex = scanner.nextInt() - 1;
            } while (firstCharacterIndex < 0 || firstCharacterIndex >= characters.size());
            do {
                System.out.print("Choose the second character (Enter corresponding number): ");
                secondCharacterIndex = scanner.nextInt() - 1;
            } while (secondCharacterIndex < 0 || secondCharacterIndex >= characters.size() || secondCharacterIndex == firstCharacterIndex);
        }
        return new int[]{firstCharacterIndex, secondCharacterIndex};
    }

}
