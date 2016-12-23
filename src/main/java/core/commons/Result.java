/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.commons;

import core.enums.ResultEnum;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Created by A.Litvinau on 12/10/2016.
 * <p>
 * Класс-обертка для переноса результатов выполнения методов.
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

    public String errorsToString() {
        return payload.stream().collect(joining("\n"));
    }
}
