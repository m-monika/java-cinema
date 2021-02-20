package cinema.movie.rules.database;

import cinema.movie.rules.Database;
import cinema.movie.rules.Rule;
import cinema.movie.rules.NoRules;

public class MySQL implements Database{
    public Rule getForMovie(int idMovie)
    {
        return new NoRules();
    }
}
