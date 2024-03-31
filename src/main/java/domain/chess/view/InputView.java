package domain.chess.view;

import java.util.Scanner;

public class InputView {
    private InputView() {
        throw new UnsupportedOperationException("생성할 수 없습니다.");
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static ChessCommand inputChessCommand() {
        System.out.println("게임 시작은 start, 종료는 end 명령을 입력하세요. \n" +
                "진행 결과를 보려면 status 명령을 입력하세요. 이동은 move 명령을 입력하세요.");
        return ChessCommand.from(scanner.next());
    }

    public static String inputGameId() {
        System.out.println("게임 방 번호를 입력해주세요");
        return scanner.next();
    }

    public static String inputChessPoint() {
        return scanner.next();
    }

    public static void clear() {
        scanner.nextLine();
    }
}