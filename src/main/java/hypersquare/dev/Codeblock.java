package hypersquare.dev;

public enum Codeblock {
    PLAYER_EVENT("PLAYER EVENT"),
    IF_PLAYER("IF PLAYER");

    final String id;
    Codeblock(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
