package Model;

public interface ICell {
    void open();
    void toggleFlag();
    boolean isOpen();
    boolean isFlagged();
    boolean isMine();
}
