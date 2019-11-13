package step2;

import java.util.Arrays;
import java.util.Scanner;

public class InputView {

    private static Scanner scan = new Scanner(System.in);

    public static int inputCash() {
        System.out.println("구입금액을 입력해 주세요.");
        String cash = scan.nextLine();

        InputValidationCheck.checkEmpty(cash);
        InputValidationCheck.checkCash(cash);

        return Integer.parseInt(cash) / Lotto.LOTTO_PRICE;
    }

    public static int[] inputWinningNumbers() {
        System.out.println("지난 주 당첨 번호를 입력해 주세요.");

        String[] inputWinningNumbers = scan.nextLine().split(",");
        String[] trimWinningNumbers = new String[Lotto.WINNING_NUMBERS_LENGTH];
        for (int i = 0; i < Lotto.WINNING_NUMBERS_LENGTH; i++) {
            trimWinningNumbers[i] = inputWinningNumbers[i].trim();
        }
        InputValidationCheck.checkWinningNumbers(trimWinningNumbers);
        InputValidationCheck.checkOverlapWinningNumber(trimWinningNumbers);

        int[] winningNumbers =InputValidationCheck.checkWinningNumberRange(trimWinningNumbers);
        Arrays.sort(winningNumbers);

        return winningNumbers;
    }
}