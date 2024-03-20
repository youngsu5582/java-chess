package view;

public enum ChessCommand {

    START("start"),
    END("end");

    private final String commandText;

    ChessCommand(final String commandText) {
        this.commandText = commandText;
    }

    public static ChessCommand from(String commandText){
        for (ChessCommand value : values()) {
            if (value.commandText.equals(commandText)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("%s는 없는 명령입니다.", commandText));
    }
}