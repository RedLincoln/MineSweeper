package Model;

public interface Cell {
    void open();
    boolean isMine();
    void addClickListener(ClickListener clickListener);
    boolean changeFlag();
    int minesAround();
}
