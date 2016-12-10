package core.commons;

import core.enums.ResultEnum;

import java.util.List;

/**
 * Created by A.Litvinau on 12/10/2016.
 */
public class Result {

    private ResultEnum result;
    private List<String> payload;

    /**
     * Success constructor
     *
     * @param result
     */
    public Result(ResultEnum result) {
        this.result = result;
    }

    /**
     * Error constructor
     *
     * @param result
     * @param payload
     */
    public Result(ResultEnum result, List<String> payload) {
        this.result = result;
        this.payload = payload;
    }

    public ResultEnum getResult() {
        return result;
    }

    public void setResult(ResultEnum result) {
        this.result = result;
    }

    public List<String> getPayload() {
        return payload;
    }

    public void setPayload(List<String> payload) {
        this.payload = payload;
    }
}
