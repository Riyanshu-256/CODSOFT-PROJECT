import java.util.Random;
import java.util.Scanner;

public class Number_Game {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static int easyHighScore = Integer.MAX_VALUE;
    static int mediumHighScore = Integer.MAX_VALUE;
    static int hardHighScore = Integer.MAX_VALUE;

    public static void main(String[] args) {
        System.out.println("\n🎯 Welcome, Challenger, to the Ultimate Number Guessing Game!");

        boolean playAgain = true;

        while (playAgain) {
            playGame();

            String response;
            do {
                System.out.print("\n🔁  Would you like to test your luck in another round? (yes/no):");
                response = scanner.next().trim().toLowerCase();
            } while (!response.equals("yes") && !response.equals("no"));

            playAgain = response.equals("yes");
        }

        System.out.println("\n🎯 Can you beat your high score next time? We'll be waiting... ");
    }

    static void playGame() {
        int min = 1;
        int max = 100;

        Difficulty difficulty = selectDifficulty();
        int maxAttempts = difficulty.maxAttempts;
        String difficultyName = difficulty.name;

        int targetNumber = random.nextInt(max - min + 1) + min;
        int attempts = 0;
        boolean guessedCorrectly = false;

        System.out.println("\n🔢 I've chosen a secret number between " + min + " and " + max + ". Can you find it?" );
        System.out.println("🧠 Difficulty: " + difficultyName + " | 🎯 Attempts allowed: " + maxAttempts + "\n");

        while (attempts < maxAttempts) {
            int guess = getValidatedGuess(min, max);
            attempts++;

            System.out.println("🕒 Attempt " + attempts + " of " + maxAttempts);

            if (guess == targetNumber) {
                guessedCorrectly = true;
                System.out.println("✅ Correct! You guessed the number in " + attempts + " attempts.");

                updateHighScore(difficultyName, attempts);
                break;
            } else if (guess < targetNumber) {
                System.out.println("📉 Too low! Try again.\n");
            } else {
                System.out.println("📈 Too high! Try again.\n");
            }
        }

        if (!guessedCorrectly) {
            System.out.println("❌ Oh no! You've used all your attempts. The secret number was: " + targetNumber);
        }

        showHighScores();
    }

    static class Difficulty {
        String name;
        int maxAttempts;

        Difficulty(String name, int maxAttempts) {
            this.name = name;
            this.maxAttempts = maxAttempts;
        }
    }

    static Difficulty selectDifficulty() {
        System.out.println("\n🎮 Choose a difficulty level:");
        System.out.println("1. Easy   (45 attempts)");
        System.out.println("2. Medium (30 attempts)");
        System.out.println("3. Hard   (20 attempts)");

        while (true) {
            System.out.print("\nEnter 1, 2, or 3: ");
            int choice = getValidInteger();

            switch (choice) {
                case 1: return new Difficulty("Easy", 45);
                case 2: return new Difficulty("Medium", 30);
                case 3: return new Difficulty("Hard", 20);
                default:
                    System.out.println("❗ Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    static int getValidInteger() {
        while (!scanner.hasNextInt()) {
            System.out.print("🎮 Invalid move! Enter a number to keep playing: ");
            scanner.next(); // discard invalid input
        }
        return scanner.nextInt();
    }

    static int getValidatedGuess(int min, int max) {
        int guess;
        do {
            System.out.print("🎲 Your move! Enter a number (" + min + "–" + max + "): ");
            guess = getValidInteger();
            if (guess < min || guess > max) {
                System.out.println("That’s outside the range! Please pick a number between " + min + " and " + max + ".");
            }
        } while (guess < min || guess > max);
        return guess;
    }

    static void updateHighScore(String difficulty, int attempts) {
        switch (difficulty) {
            case "Easy":
                if (attempts < easyHighScore) {
                    easyHighScore = attempts;
                    System.out.println("🏅 Congrats! You've just set a new Easy difficulty high score: " + easyHighScore + " attempts!");
                } else {
                    System.out.println("📌 Easy High Score: " + easyHighScore + " attempts.");
                }
                break;
            case "Medium":
                if (attempts < mediumHighScore) {
                    mediumHighScore = attempts;
                    System.out.println("🏆 New Medium High Score: " + mediumHighScore + " attempts!");
                } else {
                    System.out.println("📌 Medium High Score: " + mediumHighScore + " attempts.");
                }
                break;
            case "Hard":
                if (attempts < hardHighScore) {
                    hardHighScore = attempts;
                    System.out.println("🏆 New Hard High Score: " + hardHighScore + " attempts!");
                } else {
                    System.out.println("📌 Hard High Score: " + hardHighScore + " attempts.");
                }
                break;
        }
    }

    static void showHighScores() {
        System.out.println("\n📊 Current High Scores:");
        System.out.println("Easy   : " + (easyHighScore == Integer.MAX_VALUE ? "N/A" : easyHighScore + " attempts"));
        System.out.println("Medium : " + (mediumHighScore == Integer.MAX_VALUE ? "N/A" : mediumHighScore + " attempts"));
        System.out.println("Hard   : " + (hardHighScore == Integer.MAX_VALUE ? "N/A" : hardHighScore + " attempts"));
    }
}
