package hypersquare.hypersquare.permission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Permissions {
    public static Collection<String> getPermissions() {
        Collection<String> permissions = new ArrayList<>();
        Collections.addAll(permissions, "owner", "admin", "dev","mod", "tester", "default");
        return permissions;
    }
}
