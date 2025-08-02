package school21.AP1JvT03.domain.service;

import school21.AP1JvT03.domain.model.Game;

public interface GameService {
    public Game create();

    public Game nextMove(Game game);

    public boolean checkIsCurrectMove(Game game);

    public boolean isOver(Game game);
}
