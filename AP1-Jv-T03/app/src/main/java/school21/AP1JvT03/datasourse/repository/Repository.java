package school21.AP1JvT03.datasourse.repository;

import java.util.HashMap;
import java.util.Map;

import school21.AP1JvT03.datasourse.mapper.RepositoryMapper;
import school21.AP1JvT03.datasourse.model.RepositoryModel;
import school21.AP1JvT03.domain.model.Game;

public class Repository {
    private Map<String, RepositoryModel> repo;
    private RepositoryMapper mapper;

    public Repository() {
        repo = new HashMap<>(1000);
        mapper = new RepositoryMapper();
    }

    public void save(Game game) {
        RepositoryModel model = mapper.toRepositoryModel(game);
        String id = model.getId();
        repo.put(id, model);
    }

    public boolean findById(String id) {
        return repo.containsKey(id);
    }

    public Game getById(String id) {
        RepositoryModel model = repo.get(id);
        Game game = mapper.toGame(model);
        return game;
    }

    public void deleteById(String id) {
        repo.remove(id);
    }
}
