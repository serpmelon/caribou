package com.togo.expression.result;



/**
 * @Author taiyn
 * @Description the expression execution result
 * @Date 1:07 下午 2020/4/21
 **/
public class ExecuteResult<T> {

    private T data;

    public T result() {
        return data;
    }

    public ExecuteResult(){}

    public ExecuteResult(T t) {
        this.data = t;
    }

    public static <T> ExecuteResult<T> build(Object object, Class<T> klass){

        ExecuteResultTransfer transfer = ExecuteResultTransferFactory.select(object);

        return new ExecuteResult<>(transfer.transfer(object, klass));
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
