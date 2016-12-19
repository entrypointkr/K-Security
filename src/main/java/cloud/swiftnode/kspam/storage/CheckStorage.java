package cloud.swiftnode.kspam.storage;

import cloud.swiftnode.kspam.util.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class CheckStorage {
    private static List<String> ipList = new ArrayList<>();
    private Result result;
    private String ip;

    public CheckStorage(Result result, String ip) {
        ipList = new ArrayList<>();
        this.result = result;
        this.ip = ip;
    }

    public CheckStorage(String ip) {
        this(Result.ERROR, ip);
    }

    public static List<String> getCachedIpList() {
        return ipList;
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
