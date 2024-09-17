import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in))
        {
        int rounds = 3; // Number of rounds
        int maxAttempts = 5; // Maximum number of attempts per round
        int totalScore = 0;

        System.out.println("Welcome to the Guess the Number Game!");

        for (int round = 1; round <= rounds; round++) {
            int number = 1 + (int) (100 * Math.random());
            int attempts = 0;
            boolean guessed = false;

            System.out.println("\nRound " + round + ":");
            System.out.println("A number is chosen between 1 to 100. Guess the number within " + maxAttempts + " attempts.");

            while (attempts < maxAttempts) {
                int guess = 0;
                boolean validInput = false;

                while (!validInput) {
                    System.out.print("Enter your guess: ");
                    guess = scanner.nextInt();

                    if (guess > 100) {
                        System.out.println("Error: Please enter a number between 1 and 100.");
                    } else {
                        validInput = true;
                    }
                }

                attempts++;

                if (guess == number) {
                    System.out.println("Congratulations! You guessed the number.");
                    guessed = true;
                    totalScore += (maxAttempts - attempts + 1) * 10; // Points based on attempts
                    break;
                } else if (guess < number) {
                    System.out.println("The number is greater than " + guess);
                } else {
                    System.out.println("The number is less than " + guess);
                }
            }

            if (!guessed) {
                System.out.println("You have exhausted all attempts. The number was " + number);
            }

            System.out.println("Your score for this round: " + ((guessed) ? (maxAttempts - attempts + 1) * 10 : 0));
        }

        System.out.println("\nGame Over! Your total score is: " + totalScore);
        scanner.close();
    }
    catch(Exception e){}
    }
}