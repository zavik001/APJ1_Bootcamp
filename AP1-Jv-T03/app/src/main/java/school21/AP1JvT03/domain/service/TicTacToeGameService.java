package school21.AP1JvT03.domain.service;

import school21.AP1JvT03.datasourse.repository.Repository;
import school21.AP1JvT03.domain.model.Game;

public class TicTacToeGameService implements GameService {
    public final Repository repository;

    public TicTacToeGameService(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Game create() {
        return new Game();
    }

    @Override
    public Game nextMove(Game game) {
        return game;
    }

    @Override
    public boolean checkIsCurrectMove(Game game) {
        return true;
    }

    @Override
    public boolean isOver(Game game) {
        return true;
    }
}
