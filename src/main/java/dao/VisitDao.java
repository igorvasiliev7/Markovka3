package dao;

import model.Client;
import model.Visit;

import java.util.List;

public interface VisitDao {
    void add(Visit visit);
    List<Visit> findByUserId(int id);
    Visit findTheLast();

    List<Visit> findAll();

    void delete(Visit visit);
}
