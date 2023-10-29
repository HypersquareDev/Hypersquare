package hypersquare.hypersquare.items;

import lombok.Getter;
import net.kyori.adventure.text.Component;

import java.awt.*;

import static hypersquare.hypersquare.Hypersquare.mm;

public class ActionArgument {

    @Getter DisplayValue type;
    String description;
    boolean plural = false;
    boolean optional = false;

    public ActionArgument setType(DisplayValue type){
        this.type = type;
        return this;
    }

    public ActionArgument setDescription(String description){
        this.description = description;
        return this;
    }

    public ActionArgument setPlural(boolean plural){
        this.plural = plural;
        return this;
    }
    public ActionArgument setOptional(boolean optional){
        this.optional = optional;
        return this;
    }

}
