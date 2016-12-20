package cloud.swiftnode.kspam.storage;

import cloud.swiftnode.kspam.util.Result;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class CheckStorage {
    private Result result;
    private String ip;

    public CheckStorage(Result result, String ip) {
        this.result = result;
        this.ip = ip;
    }

    public CheckStorage(String ip) {
        this(Result.ERROR, ip);
    }

    public String getIp() {
        return ip;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
