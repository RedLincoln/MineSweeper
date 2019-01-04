package Model;

public enum CellState {
    //Closed with no flag
    NO_FLAGGED,

    //Closed with flag
    FLAGGED,

    //open with mine
    MINE,

    //open safe
    NO_MINE,

    //open with num
    NUM,
}
