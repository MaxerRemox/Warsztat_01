package pl.coderslab;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskManager {

    public static void main(String[] args) throws IOException {
        optionsList();
        Scanner scan = new Scanner(System.in);
        label:
        while (true) {
            String check = scan.next();
            switch (check) {
                case "exit":
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Pa pa :)");
                    break label;
                case "list":
                    showList();
                    break;
                case "add":
                    addToList();
                    optionsList();
                    break;
                case "remove":
                    removeFromList();
                    optionsList();
                    break;
                case "czesc":
                    System.out.println(ConsoleColors.RED_BRIGHT + "Gratulacje znalazles ukryta komende ;)");
                    optionsList();
                    break;
                default:
                    System.out.println(ConsoleColors.PURPLE_BOLD + "Podaj poprawna operacje");
                    optionsList();
                    break;
            }
        }
    }

    private static void optionsList() {
        System.out.println(ConsoleColors.RED + "Lista dostępnych opcji:");
        System.out.println(ConsoleColors.BLUE + "list");
        System.out.println(ConsoleColors.BLUE + "add");
        System.out.println(ConsoleColors.BLUE + "remove");
        System.out.println(ConsoleColors.BLUE + "exit");
    }


    public static void showList() {
        ArrayList<String> result = listFromFile();
        Path path1 = Paths.get("tasks.csv");
        try {
            for (String line : Files.readAllLines(path1)) {
                System.out.println(ConsoleColors.BLUE + result.indexOf(line) + " " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToList() {
        Path path1 = Paths.get("tasks.csv");
        ArrayList<String> result = listFromFile();
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj opis zadania");
        String lines = scan.next();
        System.out.println("Podaj date zadania(yyyy-mm-dd)");
        String date = scan.next();
        System.out.println("Czy zadanie jeste wazne(tak/nie)");
        String importance = scan.next();
        result.add(lines + " " + date + " " + importance);
        try {
            Files.write(path1, result);
        } catch (IOException ex) {
            System.out.println("Nie można zapisać pliku.");
        }
    }

    private static ArrayList<String> listFromFile() {
        ArrayList<String> result = new ArrayList<>();

        try (Scanner s = new Scanner(new FileReader("tasks.csv"))) {
            while (s.hasNext()) {
                result.add(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("nie ma takiego pliku");
        }
        return result;
    }

    public static void removeFromList() throws IOException {
        Path path1 = Paths.get("tasks.csv");
        List<String> result = listFromFile();
        System.out.println("Podaj ktore zadnaie chcesz usunac");
        Scanner scan = new Scanner(System.in);
        boolean end = false;
        while (!end) {
            try {
                int index = scan.nextInt();
                result.remove(index);
                end = true;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(ConsoleColors.YELLOW_BOLD + "Wartosc poza zakresem");
                System.out.println(ConsoleColors.YELLOW_BOLD + "Podaj poprawna wartosc");
            } catch (InputMismatchException e) {
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "To nie jest liczba");
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Podaj liczbe");
                scan.next();
            }
        }

        Files.write(path1, result);

    }
}