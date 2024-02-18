package hypersquare.hypersquare.play.error;

import hypersquare.hypersquare.util.PlotUtilities;
import hypersquare.hypersquare.util.component.BasicComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public class HSException extends RuntimeException {
    private final List<CommandSender> senders;
    private final String msg;

    public HSException(CodeErrorType error, Throwable cause) {
        this(List.of(), error.message, cause);
    }
    public HSException(CommandSender sender, CodeErrorType error) {
        this(List.of(sender), error.message, null);
    }
    public HSException(int plotId, CodeErrorType error, Throwable cause) {
        //noinspection unchecked
        this((List<CommandSender>)(List<?>) PlotUtilities.getOnlinePlotDevs(plotId), error.message, cause);
    }
    public HSException(List<CommandSender> senders, String msg, Throwable cause) {
        super(msg, cause, false, false);
        this.senders = senders;
        this.msg = msg;
    }

    public static void sendErrorSound(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_HURT_CLOSED, 1, 1);
    }

    public static void sendError(CommandSender s, String m) {
        new HSException(List.of(s), m, null).sendMessage();
    }

    private static Component getStackTrace(Throwable cause) {
        StringWriter s = new StringWriter();
        cause.printStackTrace(new PrintWriter(s));
        return BasicComponent.gray(s.toString());
    }

    public void sendMessage() {
        senders.forEach(s -> {
            if (s instanceof Player p) sendErrorSound(p);
        });
        int i = 0;
        Throwable cause = this;
        while (cause != null) {
            String margin = " ".repeat(i);
            String errMsg = (cause instanceof HSException hsCause) ? hsCause.msg : CodeErrorType.INTERNAL_ERROR.message;
            Component msg;
            if (i == 0) msg = cleanMM.deserialize(margin + "<red>Error: <gray>" + errMsg);
            else msg = cleanMM.deserialize(margin + "<red>Caused by: <gray>" + errMsg);
            Component stackTrace = getStackTrace(cause);
            msg = msg.hoverEvent(HoverEvent.showText(stackTrace));
            final Component finalMsg = msg;
            senders.forEach(s -> s.sendMessage(finalMsg));
            cause = cause.getCause();
            i++;
        }
    }
}
