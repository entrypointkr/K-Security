package kr.rvs.ksecurity.initializer;

import org.bukkit.ChatColor;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class Result {
    private boolean success;
    private String message;

    public static Result ofDefault() {
        return new Result(true, "완료");
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return ChatColor.GRAY + message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toMessage(String name) {
        return String.format(
                "%s %s %s",
                name,
                isSuccess() ? ChatColor.GREEN + "O" : ChatColor.RED + "X",
                getMessage()
        );
    }
}
