package br.com.eduardo.loan.util.type;

import java.util.HashSet;

public enum StatusParam {
    
    INSTANCE;

    protected HashSet<Integer> status = new HashSet<Integer>();
    
    private StatusParam(){}

    public HashSet<Integer> getStatus() {
        if (status.isEmpty()) {
            status.add(Status.LENDED);
            status.add(Status.RETURNED);
        }
        return status;
    }

    public void add(int status) {
        this.status.add(status);
    }

    public void remove(int status) {
        this.status.remove(status);
    }
}