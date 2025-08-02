package school21.AP1JvT03.datasourse.mapper;

import school21.AP1JvT03.datasourse.model.RepositoryModel;
import school21.AP1JvT03.domain.model.Game;
import school21.AP1JvT03.domain.model.GameStatus;
import school21.AP1JvT03.domain.model.Matrix;

public class RepositoryMapper {
    public RepositoryModel toRepositoryModel(Game game) {
        return new RepositoryModel(game.getId(), game.getStatus().toString(), game.getMatrix().getMatrix());
    }

    public Game toGame(RepositoryModel model) {
        return new Game(model.getId(), GameStatus.valueOf(model.getStatus()), new Matrix(model.getMatrix()));
    }
}
