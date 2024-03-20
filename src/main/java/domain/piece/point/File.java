package domain.piece.point;

public enum File {
    A('a'),
    B('b'),
    C('c'),
    D('d'),
    E('e'),
    F('f'),
    G('g'),
    H('h');
    private char value;

    File(final char value) {
        this.value = value;
    }

    public static File from(char value) {
        for (final File file : File.values()) {
            if (file.value == value) {
                return file;
            }
        }
        throw new IllegalArgumentException(String.format("%c는 파일에 존재하지 않습니다.", value));
    }

}
