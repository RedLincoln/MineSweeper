package Control;

import Model.ActionOutcomes;

public interface GameEndedListener {
    void updateGame(ActionOutcomes actionOutcomes);
}
