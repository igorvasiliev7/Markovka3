package dao;

import model.Call;

import java.util.List;

public interface CallDao {
    void call(Call call);

    List<Call> findByUserId(int id);

    Call findTheLast();

    List<Call> findAll();

    void delete(Call call);
}
