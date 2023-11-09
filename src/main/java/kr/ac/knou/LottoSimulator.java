package kr.ac.knou;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class LottoSimulator {
    public static void main(String[] args) {
        int[] lottoNumbers = lottoRandomNumbers(); // 로또 당첨번호 생성
        int[] userNumbers = userNumbers(); // 사용자의 로또 번호 입력
        int bonusNumber = bonusRandomNumbers(lottoNumbers); // 보너스 번호 생성 (2등)

        Arrays.sort(lottoNumbers); // 정렬
        Arrays.sort(userNumbers); // 정렬

        System.out.println("이주의 당첨 번호 : " + Arrays.toString(lottoNumbers));
        System.out.println("이주의 보너스 번호 : " + bonusNumber);
        System.out.println("당신의픽! : " + Arrays.toString(userNumbers));

        int matchCount = countMatchingNumbers(lottoNumbers, userNumbers);
        // 스위치문으로 등수출력
        switch (matchCount) {
            case 6:
                System.out.println("1등입니다. 100억원입니다.");
                break;
            case 5:
                // 5개를 맞췄는데 보너스 번호까지 맞춘경우
                if (contains(userNumbers, bonusNumber)) {
                    System.out.println("2등입니다. 5천만원입니다.");
                } else {
                    System.out.println("3등입니다. 150만원입니다.");
                }
                break;
            case 4:
                System.out.println("4등입니다. 5만원입니다.");
                break;
            case 3:
                System.out.println("5등입니다. 5천원입니다.");
                break;
            default:
                System.out.println("꽝입니다.");
        }
    }

    /**
     * 보너스 번호 생성
     * 우승 번호와 중복되지 않게 생성
     */
    private static int bonusRandomNumbers(int[] winningNumbers) {
        int bonusNumber;
        do {
            bonusNumber = randomLottoNumber();
        } while (contains(winningNumbers, bonusNumber)); // 중복이 생기면 다시 랜덤 숫자 생성
        return bonusNumber;
    }

    /**
     1~45까지의 중복이 없는 랜덤 번호 배열 생성
     */
    public static int[] lottoRandomNumbers() {
        int[] numbers = new int[6];

        for (int i = 0; i < 6; i++) {
            int randomNumber;
            do {
                randomNumber = randomLottoNumber();
            } while (contains(numbers, randomNumber)); // 중복이 생기면 다시 랜덤 숫자 생성
            numbers[i] = randomNumber;
        }

        return numbers;
    }

    /**
     * 1~45까지의 랜덤 숫자 생성
     */
    public static int randomLottoNumber() {
        Random random = new Random();
        return random.nextInt(45) + 1; // 44까지의 랜덤 숫자를 생성 + 1 -> 1부터 45 까지의 랜덤숫자
    }

    /**
     사용자로부터 로또 번호 입력 받기
     */
    public static int[] userNumbers() {
        Scanner scanner = new Scanner(System.in);
        int[] numbers = new int[6];

        System.out.println("1부터 45까지 중복 없이 6개의 숫자를 입력하세요.");
        for (int i = 0; i < 6; i++) {
            int input = 0;
            boolean isInvalidInput;
            do {
                System.out.print((i + 1) + "번째 선택 : ");
                // 숫자가 들어왔을때만 검사
                if (scanner.hasNextInt()) {
                    input = scanner.nextInt();
                    // 인풋이 1~45 범위를 벗어나거나, 이미 입력한 숫자인지 확인
                    isInvalidInput = isNotCorrect(input) || contains(numbers, input);
                    if (isInvalidInput) {
                        System.out.println("중복되거나 범위를 벗어난 숫자입니다. 1 ~ 45 사이의 숫자를 입력하세요.");
                    }
                    // 숫자가 아닌 다른 무언가가 들어왔을때 처리
                } else {
                    System.out.println("올바른 숫자가 아닙니다. 1 ~ 45 사이의 숫자를 입력하세요.");
                    isInvalidInput = true;
                    scanner.next(); // 잘못된 입력을 버림
                }
            } while (isInvalidInput);

            numbers[i] = input;
        }

        return numbers;
    }



    /**
     * 숫자가 1~45 범위를 벗어나는지 확인
     * @return 범위를 넘어가면 true, 아니면 false
     */
    public static boolean isNotCorrect(int number){
        return number < 1 || number > 45;
    }

    /**
     배열에서 특정 숫자가 포함되어 있는지 확인
     포함되어 있으면 true, 아니면 false
     */
    public static boolean contains(int[] arr, int number) {
        for (int value : arr) {
            if (value == number) {
                return true;
            }
        }
        return false;
    }

    /**
    로또 번호와 유저 번호가일치하는 숫자 개수 세기
     */
    public static int countMatchingNumbers(int[] lottoNumbers, int[] userNumbers) {
        int count = 0;
        // 로또 번호를 하나씩 비교
        for (int lottoNumber : lottoNumbers) {
            // 사용자의 번호중에 로또번호와 같은게 있는지 검사
            for (int userNumber : userNumbers) {
                if (lottoNumber == userNumber) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}