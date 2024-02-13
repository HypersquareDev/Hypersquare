package hypersquare.hypersquare.dev.target;

public enum TargetType {
    PLAYER, ENTITY, ALL;

    public static TargetType ofCodeblock(String id) {
        return switch(id) {
            case "player_action" -> PLAYER;
            case "if_player" -> PLAYER;

            case "entity_action" -> ENTITY;
            case "if_entity" -> ENTITY;
            default -> null;
        };
    }
}
