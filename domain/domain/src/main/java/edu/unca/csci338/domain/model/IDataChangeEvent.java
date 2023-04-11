package edu.unca.csci338.domain.model;

public interface IDataChangeEvent<T> {
    public void onDataChanged(T data);
}
